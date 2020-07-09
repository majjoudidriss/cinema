
package org.sid.cinema.web;

import java.util.List;

import javax.validation.Valid;

import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.dao.ProjectionRepository;
import org.sid.cinema.dao.SalleRepository;
import org.sid.cinema.dao.SeanceRepository;
import org.sid.cinema.dao.VilleRepository;
import org.sid.cinema.entities.Cinema;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Projection;
import org.sid.cinema.entities.Salle;
import org.sid.cinema.entities.Seance;
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
public class ProjectionController {

@Autowired private ProjectionRepository projectionRepository;
@Autowired private SalleRepository salleRepository;
@Autowired private FilmRepository filmRepository;
@Autowired private SeanceRepository seanceRepository;

@GetMapping("/projection")
	public String projection(Model model,@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name ="size",defaultValue = "5") int size,
			@RequestParam(name ="keyword",defaultValue = "") String mc){
		Page<Projection> pageProjections=projectionRepository.findByFilmTitreContains(mc,PageRequest.of(page,size));
		List<Projection> projections=pageProjections.getContent();
		model.addAttribute("projections",projections);
		model.addAttribute("keyword",mc);
		model.addAttribute("pages",new int[pageProjections.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("size",size);
		return"projection";
	}
	@GetMapping("/deleteProjection")
	public String deleteCinema(Long id,int page,int size,String keyword)
	{ 
		projectionRepository.deleteById(id);
		return "redirect:/projection?page="+page+"&size="+size+"&keyword="+keyword;
	}
	@PostMapping("/addProjection")
	public String addCinema( @Valid Projection projection,BindingResult bindingResult,Model model)
	{		
		if(bindingResult.hasErrors())
			return "/ProjectionForme";
		projectionRepository.save(projection);
		model.addAttribute("projection",projection);
		return "ProjectionConfirmation";
	}
	@GetMapping("/ProjectionForme")
	public String ProjectionForme(Model model)
	{ 	
		List<Seance> seances=seanceRepository.findAll();
		model.addAttribute("seances",seances);
		List<Film> films=filmRepository.findAll();
		model.addAttribute("films",films);
		List<Salle> salles=salleRepository.findAll();
		model.addAttribute("salles",salles);
		model.addAttribute("projection",new Projection());
		return "/ProjectionForme";
		}
	@GetMapping("editProjection")
	public String editProjection(Model model,Long id)
	{ 
		List<Seance> seances=seanceRepository.findAll();
		model.addAttribute("seances",seances);
		List<Film> films=filmRepository.findAll();
		model.addAttribute("films",films);
		List<Salle> salles=salleRepository.findAll();
		model.addAttribute("salles",salles);
		Projection projection=projectionRepository.findById(id).get();
		model.addAttribute("projection",projection);
		String mode="edit";
		model.addAttribute("mode",mode);
		return "/ProjectionForme";
		}
}
