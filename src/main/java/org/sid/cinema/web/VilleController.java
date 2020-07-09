package org.sid.cinema.web;

import java.util.List;

import javax.validation.Valid;

import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.dao.VilleRepository;
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
public class VilleController  {
	@Autowired private VilleRepository villeRepository;
		@GetMapping("/Ville")
	public List<Ville> listVilles(Model model,@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name ="size",defaultValue = "5") int size,
			@RequestParam(name ="keyword",defaultValue = "") String mc) {
		Page<Ville> pagevilles=villeRepository.findByNameContains(mc, PageRequest.of(page,size));
		List<Ville> villes=pagevilles.getContent();
		model.addAttribute("villes",villes);
		model.addAttribute("pages",new int[pagevilles.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword",mc);
		model.addAttribute("size",size);

		return villes;
	}
		@GetMapping("/deleteVille")
		public String delete(Long id,int page,int size,String keyword)
		{ 
			villeRepository.deleteById(id);
			return "redirect:/Ville?page="+page+"&size="+size+"&keyword="+keyword;
		}
		@PostMapping("/addVille")
		public String addVille(@Valid Ville ville,BindingResult bindingResult,Model model)
		{	
			if(bindingResult.hasErrors())
				return "villeforme";
			villeRepository.save(ville);
			model.addAttribute("ville",ville);
			return "Villeconfirmation";
		}
		@GetMapping("/villeforme")
		public String formVille(Model model)
		{ 
			model.addAttribute("ville",new Ville());
			return "villeforme";
			}
		@GetMapping("/")
		public String k()
		{ 
			return "redirect:/Ville";

			}
		@GetMapping("/editVille")
		public String editVille(Model model,Long id)
		{ 
			Ville ville=villeRepository.findById(id).get();
			model.addAttribute("ville",ville);
			return "villeforme";
			}
}
