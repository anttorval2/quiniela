package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Partido;
import domain.Quiniela;
import domain.User;
import repositories.QuinielaRepository;

@Service
@Transactional
public class QuinielaService {

	// Managed repository ---------------------------------------

	@Autowired
	private QuinielaRepository quinielaRepository;

	// Supporting services --------------------------------------

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private UserService userService;

	// Constructors ---------------------------------------------

	public QuinielaService() {
		super();
	}

	// methods --------------------------------------

	public Collection<Quiniela> findAll() {
		return quinielaRepository.findAll();
	}

	public Quiniela findOneToEdit(int quinielaId) {
		return quinielaRepository.findOne(quinielaId);
	}

	public Collection<Partido> findPartidos(int quinielaId) {
		return quinielaRepository.findPartidos(quinielaId);
	}

	public Quiniela create() {

		Quiniela result = new Quiniela();
		Collection<Partido> partidos = new ArrayList<Partido>();
		Administrator admin = administratorService.findByPrincipal();
		result.setAdministrator(admin);

		result.setPartidos(partidos);

		return result;
	}

	public void save(Quiniela quiniela) {
		
		Assert.isTrue(quiniela.getFechaLimite().after(new Date()));

		Collection<User> usuarios = userService.findAll();
		Administrator admin = administratorService.findByPrincipal();
		quiniela.setAdministrator(admin);

		for (User u : usuarios) {
			quiniela.setUser(u);
			quinielaRepository.save(quiniela);

		}

		quiniela.setUser(null);
		quinielaRepository.save(quiniela);

	}

	public Collection<Quiniela> findMyQuinielas() {
		Administrator admin = administratorService.findByPrincipal();
		return quinielaRepository.findMyQuinielas(admin.getId());
	}

	public Collection<Quiniela> findQuinielasForUser(User user) {
		return quinielaRepository.findQuinielasForUser(user.getId());
	}

	public Collection<Quiniela> findQuinielasByJornada(String jornada) {
		return quinielaRepository.findQuinielasByJornada(jornada);
	}

	public Collection<Quiniela> findMyQuinielasForUser() {
		User user = userService.findByPrincipal();
		return quinielaRepository.findQuinielasForUser(user.getId());
	}

	public boolean sePuedeApostar(Quiniela q) {
		boolean res = false;
		Date fechaActuarl = new Date();
		if (q.getFechaLimite().after(fechaActuarl) || q.getFechaLimite().equals(fechaActuarl)) {
			res = true;
		}
		return res;
	}

	public void calcularAciertos(Quiniela q) {
				
		Date currentMoment = new Date();
		Assert.isTrue(q.getFechaLimite().before(currentMoment));
		Collection<Quiniela> quinielas = findQuinielasByJornada(q.getJornada());
		
		
		for(Quiniela we: quinielas){
			if(we.getUser() == null){
				quinielas.remove(we);
				break;
			}
		}
				
		
		for(Quiniela qui : quinielas){
			Integer numAciertos = 0;
			for(Partido p : qui.getPartidos()){
				for(Partido p2 : q.getPartidos()){
					if( p2.getEquipo1().equals(p.getEquipo1()) && p2.getEquipo2().equals(p.getEquipo2()) ){
						if(p2.getResultado() != null && p.getResultado() !=null && 
						   p2.getResultado().equals(p.getResultado())){
							numAciertos++;
						}
					}
				}
			}	
			qui.setNumAciertos(numAciertos);
		}
	}

	public Quiniela findQuinielaForUsernameAndJornada(String ganador, String jornada) {
		return quinielaRepository.findQuinielaForUsernameAndJornada(ganador, jornada);
	}

}
