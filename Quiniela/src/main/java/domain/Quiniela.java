package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Access(AccessType.PROPERTY)
public class Quiniela extends DomainEntity{
	
	private String jornada;
	private Integer numAciertos;
	private Date fechaLimite;
	private boolean isValid;
	
	
	@NotBlank
	public String getJornada() {
		return jornada;
	}

	public void setJornada(String jornada) {
		this.jornada = jornada;
	}

	public Integer getNumAciertos() {
		return numAciertos;
	}

	public void setNumAciertos(Integer numAciertos) {
		this.numAciertos = numAciertos;
	}
	
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yy")
	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	
	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	
	//RelationShips............................................



	private Collection<Partido> partidos;
	private Administrator administrator;
	private User user;
	
	@Valid
	@ManyToOne(optional=true)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}

	@Valid
    @NotNull
	@OneToMany(mappedBy = "quiniela")
	public Collection<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(Collection<Partido> partidos) {
		this.partidos = partidos;
	}
	
	

}