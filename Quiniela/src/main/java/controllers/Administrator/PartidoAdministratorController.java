package controllers.Administrator;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import controllers.AbstractController;
import domain.Partido;
import domain.Quiniela;
import forms.Prueba;
import services.MailService;
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
    
	@Resource
	private MailService mailService;  


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
        if(q.getUser()==null && !q.getPartidos().isEmpty() && !partidoService.aciertosHanSidoCalculados(q)){
        	result.addObject("canEdit", true);
        }else{
        	result.addObject("canEdit", false);
        }
        result.addObject("requestURI", "partido/administrator/list.do?quinielaId="+quinielaId);

        return result;
    }    
    
    
    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public void print(HttpServletResponse response, @RequestParam int quinielaId) throws IOException {
        try {
            Quiniela q  = quinielaService.findOneToEdit(quinielaId);
            Document document = new Document();
            response.setHeader("Content-Disposition", "attachment;filename=" + q.getJornada());
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(new Chunk(""));
            Chunk chunkTitle = new Chunk("   "+q.getJornada(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 20,
                    Font.TIMES_ROMAN, Color.BLACK));

            Paragraph parrafo = new Paragraph();
            document.add(parrafo);
            document.add(new Chunk("    ",FontFactory.getFont(FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK)));
            document.add(parrafo);
            document.add(chunkTitle);
            document.add(parrafo);
            document.add(new Chunk("    ",FontFactory.getFont(FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK)));
            document.add(parrafo);
            int i = 1;
            for(Partido p: q.getPartidos()){
            	document.add(new Chunk("  "+i+". "+p.getEquipo1()+" - "+p.getEquipo2()+"   Resultado: "+p.getResultado(), 
            				FontFactory.getFont(FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK)));
            	document.add(parrafo);
            	i++;
            }

            document.add(parrafo);
            document.add(new Chunk("    ",FontFactory.getFont(FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK)));
            document.add(parrafo);
            document.add(parrafo);
            document.add(new Chunk("    ",FontFactory.getFont(FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK)));
            document.add(parrafo);
            document.add(new Chunk("  "+q.getUser().getUserAccount().getUsername(),FontFactory.getFont(FontFactory.COURIER, 12, Font.TIMES_ROMAN, Color.BLACK)));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    //Calcula los aciertos de cada usuario para una quiniela concreta
    @RequestMapping(value = "/calculo", method = RequestMethod.GET)
    public ModelAndView calculo(@RequestParam int quinielaId) {

        ModelAndView result;
        result = new ModelAndView("partido/list");
        
        Quiniela q = quinielaService.findOneToEdit(quinielaId);
        Collection<Partido> partidos = quinielaService.findPartidos(quinielaId);
        
		Date currentMoment = new Date();
		String ganador = "";
		if(q.getFechaLimite().before(currentMoment)){
			 quinielaService.calcularAciertos(q);
			 ganador = partidoService.getGanador(q);
			 result.addObject("mostrarMensaje", true);
			 result.addObject("ganador", ganador);

			 
		}       
		int pos=ganador.indexOf(' ');
		String ganadorModificado = "";
		if(pos != -1){
			ganadorModificado = ganador.substring(0, pos);
		}else{
			ganadorModificado = ganador;
		}
		Quiniela quinielaGanador = quinielaService.findQuinielaForUsernameAndJornada(ganadorModificado,q.getJornada());

		result.addObject("quinielaId", quinielaId);
		result.addObject("partidos", partidos);
        
        result.addObject("requestURI", "partido/administrator/list.do?quinielaId="+quinielaId);
        
        //Enviar correos
        Collection<Quiniela> quinielas = quinielaService.findQuinielasByJornada(q.getJornada());
        
		for(Quiniela we: quinielas){
			if(we.getUser() == null){
				quinielas.remove(we);
				break;
			}
		}
		
        for(Quiniela qui: quinielas){
        	mailService.send(qui.getUser().getEmailAddress(), "Ganador de "+qui.getJornada(), 
    				"El ganador de "+qui.getJornada()+" ha sido "+ganador+" con "+quinielaGanador.getNumAciertos()+" aciertos.\n"
    						+ "Tus aciertos: "+qui.getNumAciertos());
        }


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
    public ModelAndView edit(@RequestParam int quinielaId) {

        ModelAndView result;

        Collection<Partido> partidos = quinielaService.findPartidos(quinielaId);
        Prueba prueba = new Prueba();
        List<Partido> l = new ArrayList<Partido>();
        result = createEditModelAndView2(prueba);
        for(Partido partido : partidos){
        	l.add(partido);
        }
        prueba.setPrueba(l);

        result.addObject("prueba", prueba);
        result.addObject("isEdit", true);
        result.addObject("requestURI", "partido/administrator/edit.do?quinielaId=" + quinielaId);
        
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
    public ModelAndView save2(@Valid Prueba p, BindingResult binding) {

        ModelAndView result;
        
        if (binding.hasErrors()) {
            result = createEditModelAndView2(p);
        } else {
            try {
            	for(Partido partido: p.getPrueba()){
            		partidoService.save3(partido);
            	}
            	
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

        result.addObject("isEdit", true);
        result.addObject("prueba", p);
        result.addObject("message", message);


        return result;
    }
    
}
