package org.sid.cinema.dao;

import java.util.List;

import org.sid.cinema.entities.Cinema;
import org.sid.cinema.entities.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
@RepositoryRestResource
@CrossOrigin("*")

public interface CinemaRepository extends JpaRepository<Cinema, Long>{

	public Page<Cinema> findByNameContains(String mc,Pageable pageable);
	public List<Cinema> findByVille(Ville ville);
	public Cinema findById(int id);
//	public Page<Cinema> findByVilleId(int id,Pageable pageable);
	//public Page<Cinema> findByVilleIdAndNameContains(int id,String mc,Pageable pageable);

}
