package org.sid.cinema.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.sid.cinema.dao.CategorieRepository;
import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.dao.PlaceRepository;
import org.sid.cinema.dao.ProjectionRepository;
import org.sid.cinema.dao.SalleRepository;
import org.sid.cinema.dao.SeanceRepository;
import org.sid.cinema.dao.TicketRepository;
import org.sid.cinema.dao.VilleRepository;
import org.sid.cinema.entities.Categorie;
import org.sid.cinema.entities.Cinema;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Place;
import org.sid.cinema.entities.Projection;
import org.sid.cinema.entities.Salle;
import org.sid.cinema.entities.Seance;
import org.sid.cinema.entities.Ticket;
import org.sid.cinema.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Transactional
@Service
public class CinemaInitServiceImpl implements ICinemaInitService {
	@Autowired private VilleRepository villeRepository; 
	@Autowired private CinemaRepository cinemaRepository;
	@Autowired private SalleRepository sallerepository;
	@Autowired private PlaceRepository placeRepository;
	@Autowired private FilmRepository filmRepository;
	@Autowired private ProjectionRepository projectionRepository;
	@Autowired private CategorieRepository categorieRepository;
	@Autowired private SeanceRepository seanceRepository;
	@Autowired private TicketRepository ticketRepository;
	
	
	
	@Override
	public void initVilles() {
		// TODO Auto-generated method stub
		Stream.of("Casablanca","Marrackech","rabat").forEach(nameville->{
			Ville ville=new Ville();
			ville.setName(nameville);
			villeRepository.save(ville);
		});
	} 

	@Override
	public void initCinemas() {
		// TODO Auto-generated method stub
		villeRepository.findAll().forEach(ville->{
			Stream.of("Megarama","IMAX","Founoun","CHAHRAZAD","DAOULIZ").forEach(namecinema->{
				Cinema cinema=new Cinema();
				cinema.setName(namecinema);
				cinema.setVille(ville);
				cinema.setNombreSalles(3+(int)(Math.random()*7));
				cinemaRepository.save(cinema);
			});
		});
		
	}

	@Override
	public void initSalles() {
		// TODO Auto-generated method stub
		cinemaRepository.findAll().forEach(cinema->{
			for(int i=0;i<cinema.getNombreSalles();i++)
			{
				Salle salle=new Salle();
				salle.setName("Salle "+(i+1));
				salle.setCinema(cinema);
				salle.setNombrePlace(15+(int)(Math.random()*20));
				sallerepository.save(salle);
			}
		});
	}

	@Override
	public void initPalces() {
		// TODO Auto-generated method stub
		sallerepository.findAll().forEach(salle->{
			for(int i=0;i<salle.getNombrePlace();i++)
				{
				Place place=new Place();
				place.setNumero(i+1);
				place.setSalle(salle);
				placeRepository.save(place);
				}
		});
	}

	/**
	 *
	 */
	@Override
	public void initSeances() {
		// TODO Auto-generated method stub
		DateFormat df=new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(heure->{
			Seance seance =new Seance();
			seanceRepository.save(seance);
			try {
				seance.setHeureDebut(df.parse(heure));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void initCategories() {
		// TODO Auto-generated method stub
		Stream.of("Action","Histoire","Drama","Fiction").forEach(nameCategorie->{
			Categorie categorie =new Categorie();
			categorie.setName(nameCategorie);
			categorieRepository.save(categorie);
		});
	}

	@Override
	public void initFilms() {
		// TODO Auto-generated method stub
		double[] durees=new double[] {1,1.5,2,2.5,3};
		categorieRepository.findAll().forEach(categorie->{
			Stream.of("game of thrones ","segneur des anneaux","spiderman","ironman","cat woman").forEach(titre->{
				Film film=new Film();
				film.setTitre(titre);
				film.setDuree(durees[new Random().nextInt(durees.length)]);
				film.setCategorie(categorie);
				film.setPhoto(titre.replaceAll(" ","")+".jpg");
				filmRepository.save(film);
			});
		});
		
	}

	@Override
	public void initProjections() {
		double[] prices=new double[] {30,50,60,70,90,100};
		List<Film> films=filmRepository.findAll();
		// TODO Auto-generated method stub
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{
					int index=new Random().nextInt(films.size());
					Film film=films.get(index);
						seanceRepository.findAll().forEach(seance->{
							Projection projection=new Projection();
							projection.setDateProjection(new Date());
							projection.setFilm(film);
							projection.setPrix(prices[new Random().nextInt(prices.length)]);
							projection.setSalle(salle);
							projection.setSeance(seance);
							projectionRepository.save(projection);
							
						}); 
					
				});
			});
		});
	}

	@Override
	public void initTickets() {
		// TODO Auto-generated method stub
	projectionRepository.findAll().forEach(projection->{
		projection.getSalle().getPlaces().forEach(place->{
			Ticket ticket=new Ticket();
			ticket.setPlace(place);
			ticket.setPrix(projection.getPrix());
			ticket.setProjection(projection);
			ticket.setReserve(false);
			ticketRepository.save(ticket);
			
		});
	});
	}

}
