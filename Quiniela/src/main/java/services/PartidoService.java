package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Partido;
import domain.Quiniela;
import domain.User;
import repositories.PartidoRepository;

@Service
@Transactional
public class PartidoService {

    // Managed repository ---------------------------------------

    @Autowired
    private PartidoRepository partidoRepository;

    // Supporting services --------------------------------------
    
    @Autowired
    private AdministratorService administratorService;
    
    @Autowired
    private QuinielaService quinielaService;
    
    @Autowired
    private UserService userService;

    // Constructors ---------------------------------------------

    public PartidoService() {
        super();
    }


    //methods --------------------------------------

	public Collection<Partido> findAll() {
		return partidoRepository.findAll();
	}


	public Partido findOneToEdit(int partidoId) {
		 return partidoRepository.findOne(partidoId);
	}

	
    public Partido create(Quiniela q) {

    	Partido result = new Partido();
    	result.setQuiniela(q);

        return result;
    }
    
    public void save(Partido partido) {
        
        String jornada = partido.getQuiniela().getJornada();
        Collection<Quiniela> quinielas = quinielaService.findQuinielasByJornada(jornada);
        for(Quiniela q : quinielas){
        	Partido p = create(q);
        	p.setEquipo1(partido.getEquipo1());
        	p.setEquipo2(partido.getEquipo2());
        	partidoRepository.save(p);
        }
        
    }
    
    public void save2(Partido partido) {
    	
    	
        partidoRepository.save(partido);
        
    }
    
    public void save3(Partido partido) {
    	
    	
        partidoRepository.save(partido);
        
    }


	public boolean usuarioPuedeIntroducirPronostico(Partido partido) {
		User user = userService.findByPrincipal();
		Assert.isTrue(user.equals(partido.getQuiniela().getUser()));
		Date currentDate = new Date();
		boolean res = false;
		
		if(partido.getQuiniela().getFechaLimite().after(currentDate) || 
		   partido.getQuiniela().getFechaLimite().equals(currentDate)){
			res = true;
		}
		
		return res;
	}


	public boolean todosLosResultadosEstanPuestos(Quiniela q) {
		boolean res = true;
		
		for(Partido p: q.getPartidos()){
			if(p.getResultado() == null){
				res = false;
				break;
			}
		}
		
		return res;
		
	}


	public String getGanador(Quiniela q) {
		
		Collection<Quiniela> quinielas = quinielaService.findQuinielasByJornada(q.getJornada());
		String ganador = "";
		
		for(Quiniela we: quinielas){
			if(we.getUser() == null){
				quinielas.remove(we);
				break;
			}
		}
		
		Integer max = 0;
		for(Quiniela quiniela : quinielas){
			if(quiniela.getNumAciertos() > max){
				max = quiniela.getNumAciertos();
				ganador = quiniela.getUser().getUserAccount().getUsername();
			}else if(quiniela.getNumAciertos() == max){
				ganador += " y "+quiniela.getUser().getUserAccount().getUsername();
			}
		}
		
		return ganador;
	}


	public boolean aciertosHanSidoCalculados(Quiniela q) {
		
		boolean res = false;
		
		Collection<Quiniela> quinielas = quinielaService.findQuinielasByJornada(q.getJornada());
		
		for(Quiniela qui: quinielas){
			if(qui.getNumAciertos() != null){
				res = true;
				break;
			}
		}

		return res;
	}



}