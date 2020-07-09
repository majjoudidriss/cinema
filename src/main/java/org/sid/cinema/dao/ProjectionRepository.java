package org.sid.cinema.dao;

import java.util.List;

import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Projection;
import org.sid.cinema.entities.Salle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
@RepositoryRestResource
@CrossOrigin("*")
public interface ProjectionRepository extends JpaRepository<Projection, Long>{

 	public Page<Projection> findByFilmTitreContains(String mc,Pageable pageable);
	/*public List<Projection> findByFilm(Film film);
		public List<Projection> findBySalle(Salle salle);
*/
}
