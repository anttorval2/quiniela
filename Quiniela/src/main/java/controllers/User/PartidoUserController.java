package controllers.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
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
import forms.Prueba;
import services.MailService;
import services.PartidoService;
import services.QuinielaService;

@Controller
@RequestMapping("/partido/user")
public class PartidoUserController extends AbstractController {

    // Services -----------------------------------------------------------

    @Autowired
    private QuinielaService quinielaService;
    
    @Autowired
    private PartidoService partidoService;
    
	@Resource
	private MailService mailService;
  


    // Constructors -----------------------------------------------------------

    public PartidoUserController() {
        super();
    }

    // List........................
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam int quinielaId) {

        ModelAndView result;
        Collection<Partido> partidos = quinielaService.findPartidos(quinielaId);
        Quiniela q = quinielaService.findOneToEdit(quinielaId);

        result = new ModelAndView("partido/list");
        
		if(partidoService.aciertosHanSidoCalculados(q)){
			 String ganador = partidoService.getGanador(q);
			 result.addObject("mostrarMensaje", true);
			 result.addObject("ganador", ganador);
		} 

        result.addObject("partidos", partidos);
        result.addObject("quinielaId", quinielaId);
        result.addObject("q", q);
        if(q.getNumAciertos() != null){
            result.addObject("mostrarAciertos", true);
        }
        if(quinielaService.sePuedeApostar(q)){
        	result.addObject("canEdit", true);
        }else{
        	result.addObject("canEdit", false);
        }
        result.addObject("requestURI", "partido/user/list.do?quinielaId="+quinielaId);

        return result;
    }    
    

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int quinielaId) {

        ModelAndView result;
        Quiniela q = quinielaService.findOneToEdit(quinielaId);
        Collection<Partido> partidos = quinielaService.findPartidos(quinielaId);
        Prueba prueba = new Prueba();
        List<Partido> l = new ArrayList<Partido>();
        for(Partido partido : partidos){
        	l.add(partido);
        }
        prueba.setPrueba(l);
        result = createEditModelAndView2(prueba);

        result.addObject("prueba", prueba);
        if(partidoService.usuarioPuedeIntroducirPronostico2(q)){
            result.addObject("isEdit", true);

        }
        result.addObject("requestURI", "partido/user/edit.do?quinielaId=" + quinielaId);
              
        
        return result;
    }
    
   
    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save2(@Valid Prueba p, BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors()) {
            result = createEditModelAndView2(p);
        } else {
            try {
            	String contenidoCorreo = "Tus pronósticos para "+p.getPrueba().get(0).getQuiniela().getJornada()+": \n";
            	int i = 1;
            	for(Partido partido: p.getPrueba()){
            		contenidoCorreo += "\n"+String.valueOf(i)+". "+ partido.getEquipo1()+" - "+
            							partido.getEquipo2()+". Resultado: "+ partido.getResultado();
            		partidoService.save2(partido);
            		i++;
            	}
                //Envio de correo
            	mailService.send(p.getPrueba().get(0).getQuiniela().getUser().getEmailAddress(), 
            					"Tus pronósticos para "+p.getPrueba().get(0).getQuiniela().getJornada(), contenidoCorreo);
            	
                result = new ModelAndView("redirect:list.do?quinielaId="+p.getPrueba().get(0).getQuiniela().getId());
            } catch (Throwable oops) {
                result = createEditModelAndView2(p, "partido.commit.error");
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
    
    protected ModelAndView createEditModelAndView2(Prueba p) {
        assert p != null;
        ModelAndView result;

        result = createEditModelAndView2(p, null);

        return result;
    }

    protected ModelAndView createEditModelAndView2(Prueba p, String message) {

        Assert.notNull(p);
        ModelAndView result;

        result = new ModelAndView("partido/edit");
        
        if(!p.getPrueba().isEmpty() && partidoService.usuarioPuedeIntroducirPronostico2(p.getPrueba().get(0).getQuiniela())){
            result.addObject("isEdit", true);
        }else{
        	result.addObject("isEdit", false);
        }
        result.addObject("prueba", p);
        result.addObject("message", message);


        return result;
    }
    
}
