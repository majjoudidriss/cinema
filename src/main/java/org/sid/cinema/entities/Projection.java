package org.sid.cinema.entities;

import java.util.Collection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity 
@Data @AllArgsConstructor  @ToString @NoArgsConstructor
public class Projection {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)	
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date dateProjection;
	private double prix;
	@ManyToOne 
	private Film film;
	@ManyToOne 
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Salle salle;
	
	@OneToMany(mappedBy = "projection")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Collection<Ticket> tickets;
	@ManyToOne 
	private Seance seance;
}
