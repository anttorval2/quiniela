package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Access(AccessType.PROPERTY)
public class Partido extends DomainEntity{
	
	private String equipo1;
	private String equipo2;
	private String resultado;
	
	
	@NotBlank
	public String getEquipo1() {
		return equipo1;
	}
	public void setEquipo1(String equipo1) {
		this.equipo1 = equipo1;
	}
	
	@NotBlank
	public String getEquipo2() {
		return equipo2;
	}
	public void setEquipo2(String equipo2) {
		this.equipo2 = equipo2;
	}
	
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	
	//RelationShips...........................................
	
	private Quiniela quiniela;

	
	@Valid
    @NotNull
	@ManyToOne(optional=false)
	public Quiniela getQuiniela() {
		return quiniela;
	}
	public void setQuiniela(Quiniela quiniela) {
		this.quiniela = quiniela;
	}
	
	
	

}

