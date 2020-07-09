package org.sid.cinema.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.sid.cinema.dao.CategorieRepository;
import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.entities.Categorie;
import org.sid.cinema.entities.Cinema;
import org.sid.cinema.entities.Film;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FilmController {

@Autowired private CategorieRepository categorieRepository;
@Autowired private FilmRepository filmRepository;
	@GetMapping("/film")
	public String Cinema(Model model,@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name ="size",defaultValue = "5") int size,
			@RequestParam(name ="keyword",defaultValue = "") String mc){
		Page<Film> pageFilms=filmRepository.findByTitreContains(mc,PageRequest.of(page,size));
		List<Film> films=pageFilms.getContent();
		model.addAttribute("films",films);
		model.addAttribute("keyword",mc);
		model.addAttribute("pages",new int[pageFilms.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("size",size);	
		List<Categorie> categories=categorieRepository.findAll();
		model.addAttribute("categories",categories);
		model.addAttribute("film",new Film());
		String imagePath=System.getProperty("user.home")+"/imageSpring/";
		model.addAttribute("imagePath",imagePath);
		return"film";
	}
	@GetMapping("/deleteFilm")
	public String deleteFilm(Long id,int page,int size,String keyword)
	{ 
		filmRepository.deleteById(id);
		return "redirect:/film?page="+page+"&size="+size+"&keyword="+keyword;
	}
	/*@PostMapping("/addFilm")
	public String addFilm( @Valid Film film,BindingResult bindingResult)
	{		
		if(bindingResult.hasErrors())
		return "redirect:/film";
		filmRepository.save(film);
		return "redirect:/film";
	}
	*/
	@PostMapping("/addFilm")
    public String saveFilm(@Valid Film f, BindingResult bindingResult, Model model,MultipartFile file) throws IOException {
        String UPLOADED_FOLDER = System.getProperty("user.home")+"/imageSpring/";
         byte[] bytes = file.getBytes();
         Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
         Files.write(path, bytes);
         f.setPhoto(file.getOriginalFilename());
        if (bindingResult.hasErrors())
        	return "/FilmForme";
        filmRepository.save(f);
        model.addAttribute("film",f);
        return "FilmConfirmation";

    }
	@GetMapping("/FilmForme")
	public String FilmForme(Model model)
	{ 	
		List<Categorie> categories=categorieRepository.findAll();
		model.addAttribute("categories",categories);
		model.addAttribute("film",new Film());
		return "/FilmForme";
		}
	@GetMapping("editFilm")
	public String editFilm(Model model,Long id)
	{ 
		List<Categorie> categories=categorieRepository.findAll();
		model.addAttribute("categories",categories);
		Film film=filmRepository.findById(id).get();
		model.addAttribute("film",film);
		String mode="edit";
		model.addAttribute("mode",mode);
		return "/FilmForme";
		}
}
