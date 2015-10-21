package controllers.Administrator;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Partido;
import domain.Quiniela;
import services.PartidoService;
import services.QuinielaService;

@Controller
@RequestMapping("/partido/administrator")
public class PartidoAdministratorController extends AbstractController {

    // Services -----------------------------------------------------------

    @Autowired
    private QuinielaService quinielaService;
    
    @Autowired
    private PartidoService partidoService;
  


    // Constructors -----------------------------------------------------------

    public PartidoAdministratorController() {
        super();
    }

    // List........................
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam int quinielaId) {

        ModelAndView result;
        Collection<Partido> partidos = quinielaService.findPartidos(quinielaId);
        Quiniela q = quinielaService.findOneToEdit(quinielaId);

        result = new ModelAndView("partido/list");
        result.addObject("partidos", partidos);
        Date currentMoment = new Date();
              
		if(partidoService.aciertosHanSidoCalculados(q)){
			 String ganador = partidoService.getGanador(q);
			 result.addObject("mostrarMensaje", true);
			 result.addObject("ganador", ganador);
		} 
				
        if(q.getUser()== null){
        	if(q.getFechaLimite().before(currentMoment) && partidoService.todosLosResultadosEstanPuestos(q) && 
        		!(partidoService.aciertosHanSidoCalculados(q))){
        	result.addObject("canCalcularAciertos", true);
        	}
        	if(q.getFechaLimite().after(currentMoment) || q.getFechaLimite().equals(currentMoment)){
            result.addObject("canCreate", true);
        	}
        }
        result.addObject("quinielaId", quinielaId);
        if(q.getUser()==null){
        	result.addObject("canEdit", true);
        }else{
        	result.addObject("canEdit", false);
        }
        result.addObject("requestURI", "partido/administrator/list.do?quinielaId="+quinielaId);

        return result;
    }    
    
    //Calcula los aciertos de cada usuario para una quiniela concreta
    @RequestMapping(value = "/calculo", method = RequestMethod.GET)
    public ModelAndView calculo(@RequestParam int quinielaId) {

        ModelAndView result;
        result = new ModelAndView("partido/list");
        
        Quiniela q = quinielaService.findOneToEdit(quinielaId);
        Collection<Partido> partidos = quinielaService.findPartidos(quinielaId);
        
		Date currentMoment = new Date();
		if(q.getFechaLimite().before(currentMoment)){
			 quinielaService.calcularAciertos(q);
			 String ganador = partidoService.getGanador(q);
			 result.addObject("mostrarMensaje", true);
			 result.addObject("ganador", ganador);

			 
		}       
		result.addObject("quinielaId", quinielaId);
		result.addObject("partidos", partidos);
        
        result.addObject("requestURI", "partido/administrator/list.do?quinielaId="+quinielaId);

        return result;
    }
    


    @RequestMapping(value = "/crear", method = RequestMethod.GET)
    public ModelAndView crear(@RequestParam int quinielaId) {

        ModelAndView result;

        Quiniela quiniela = quinielaService.findOneToEdit(quinielaId);

        Partido partido = partidoService.create(quiniela);

        result = createEditModelAndView(partido);

        result.addObject("partido", partido);
        result.addObject("isEdit", false);
        result.addObject("requestURI", "partido/administrator/crear.do?quinielaId=" + quinielaId);

        return result;
    }
    
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int partidoId) {

        ModelAndView result;

        Partido partido = partidoService.findOneToEdit(partidoId);
        //Assert.isTrue(partido.getResultado()==null);
        result = createEditModelAndView(partido);

        result.addObject("partido", partido);
        result.addObject("isEdit", true);
        result.addObject("requestURI", "partido/administrator/edit.do?partidoId=" + partidoId);
        
        return result;
    }
    
    //Para crear los partidos
    @RequestMapping(value = "/crear", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Partido partido, BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors()) {
            result = createEditModelAndView(partido);
        } else {
            try {
            	partidoService.save(partido);
                result = new ModelAndView("redirect:list.do?quinielaId="+partido.getQuiniela().getId());
            } catch (Throwable oops) {
                result = createEditModelAndView(partido, "partido.commit.error");
            }
        }

        return result;

    }
    
    //Para poner resultados
    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save2(@Valid Partido partido, BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors()) {
            result = createEditModelAndView(partido);
        } else {
            try {
            	partidoService.save2(partido);
                result = new ModelAndView("redirect:list.do?quinielaId="+partido.getQuiniela().getId());
            } catch (Throwable oops) {
                result = createEditModelAndView(partido, "partido.commit.error");
            }
        }

        return result;

    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(Partido partido) {
        assert partido != null;
        ModelAndView result;

        result = createEditModelAndView(partido, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(Partido partido, String message) {

        Assert.notNull(partido);
        ModelAndView result;

        result = new ModelAndView("partido/edit");
        
        if(partido.getId()==0){
            result.addObject("partido", partido);
            result.addObject("isEdit", false);
        }

        result.addObject("partido", partido);
        result.addObject("message", message);


        return result;
    }
    
}
