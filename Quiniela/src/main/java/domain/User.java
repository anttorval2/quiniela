package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	private String telefono;

	@NotBlank
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	//RelationsShips...........................................
	
	private Collection<Quiniela> quinielas;

	
	@Valid
	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<Quiniela> getQuinielas() {
		return quinielas;
	}

	public void setQuinielas(Collection<Quiniela> quinielas) {
		this.quinielas = quinielas;
	}
	
	
	
	
   
}
