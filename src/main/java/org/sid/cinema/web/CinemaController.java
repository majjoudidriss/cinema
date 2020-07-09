package org.sid.cinema.web;

import java.util.List;

import javax.validation.Valid;

import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.dao.VilleRepository;
import org.sid.cinema.entities.Cinema;
import org.sid.cinema.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CinemaController {
@Autowired private VilleRepository villeRepository;
@Autowired private CinemaRepository cinemaRepository;
	@GetMapping("/cinema")
	public String Cinema(Model model,@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name ="size",defaultValue = "5") int size,
			@RequestParam(name ="keyword",defaultValue = "") String mc){
		Page<Cinema> pageCinemas=cinemaRepository.findByNameContains(mc,PageRequest.of(page,size));
		List<Cinema> cinemas=pageCinemas.getContent();
		model.addAttribute("cinemas",cinemas);
		model.addAttribute("keyword",mc);
		model.addAttribute("pages",new int[pageCinemas.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("size",size);
		return"cinema";
	}
	@GetMapping("/deleteCinema")
	public String deleteCinema(Long id,int page,int size,String keyword)
	{ 
		cinemaRepository.deleteById(id);
		return "redirect:/cinema?page="+page+"&size="+size+"&keyword="+keyword;
	}
	@PostMapping("/addCinema")
	public String addCinema( @Valid Cinema cinema,BindingResult bindingResult,Model model)
	{		
		if(bindingResult.hasErrors())
			return "/CinemaForme";
		cinemaRepository.save(cinema);
		model.addAttribute("cinema",cinema);
		return "CinemaConfirmation";
	}
	@GetMapping("/CinemaForme")
	public String formCinema(Model model)
	{ 	
		List<Ville> villes=villeRepository.findAll();
		model.addAttribute("villes",villes);
		model.addAttribute("cinema",new Cinema());
		return "/CinemaForme";
		}
	@GetMapping("editCinema")
	public String editCinema(Model model,Long id)
	{ 
		List<Ville> villes=villeRepository.findAll();
		model.addAttribute("villes",villes);
		Cinema cinema=cinemaRepository.findById(id).get();
		model.addAttribute("cinema",cinema);
		String mode="edit";
		model.addAttribute("mode",mode);
		return "/CinemaForme";
		}
}
