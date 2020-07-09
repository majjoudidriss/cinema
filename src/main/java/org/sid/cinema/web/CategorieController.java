package org.sid.cinema.web;

import java.util.List;

import javax.validation.Valid;

import org.sid.cinema.dao.CategorieRepository;
import org.sid.cinema.dao.VilleRepository;
import org.sid.cinema.entities.Categorie;
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

public class CategorieController {
	@Autowired private CategorieRepository categorieRepository;
	@GetMapping("/categorie")
public String listVilles(Model model,@RequestParam(name="page",defaultValue = "0") int page,
		@RequestParam(name ="size",defaultValue = "5") int size,
		@RequestParam(name ="keyword",defaultValue = "") String mc) {
	Page<Categorie> pageCategories=categorieRepository.findByNameContains(mc, PageRequest.of(page,size));
	List<Categorie> categories=pageCategories.getContent();
	model.addAttribute("categories",categories);
	model.addAttribute("pages",new int[pageCategories.getTotalPages()]);
	model.addAttribute("currentPage",page);
	model.addAttribute("keyword",mc);
	model.addAttribute("size",size);
	model.addAttribute("categorie",new Categorie());
	 return"categorie";
}
	@GetMapping("/deleteCategorie")
	public String delete(Long id,int page,int size,String keyword)
	{ 
		categorieRepository.deleteById(id);
		return "redirect:/categorie?page="+page+"&size="+size+"&keyword="+keyword;
	}
	@PostMapping("/addCategorie")
	public String addVille(@Valid Categorie categorie,BindingResult bindingResult,Model model)
	{	
		if(bindingResult.hasErrors())
			return "/CategorieForme";
		categorieRepository.save(categorie);
		model.addAttribute("categorie",categorie);
		return "CategorieConfirmation";
	}
	
	@GetMapping("/CategorieForme")
	public String formCategorie(Model model)
	{ 	;
		model.addAttribute("categorie",new Categorie());
		return "/CategorieForme";
		}
	@GetMapping("editCategorie")
	public String editCategorie(Model model,Long id)
	{ 
		Categorie categorie=categorieRepository.findById(id).get();
		model.addAttribute("categorie",categorie);
		String mode="edit";
		model.addAttribute("mode",mode);
		return "/CategorieForme";
		}
}
