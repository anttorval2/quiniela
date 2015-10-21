package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

	//RelationShips.........................
	
	private Collection<Quiniela> quinielas;

	@Valid
	@NotNull
	@OneToMany(mappedBy= "administrator")
	public Collection<Quiniela> getQuinielas() {
		return quinielas;
	}

	public void setQuinielas(Collection<Quiniela> quinielas) {
		this.quinielas = quinielas;
	}
	
   
}
