package controllers.Administrator;

import java.util.Collection;

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
import domain.Quiniela;
import domain.User;
import services.QuinielaService;
import services.UserService;

@Controller
@RequestMapping("/quiniela/administrator")
public class QuinielaAdministratorController extends AbstractController {

    // Services -----------------------------------------------------------

    @Autowired
    private QuinielaService quinielaService;
  
    @Autowired
    private UserService userService;

    // Constructors -----------------------------------------------------------

    public QuinielaAdministratorController() {
        super();
    }

    // List........................

    //Muestra un listado de todas las quinielas
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {

        ModelAndView result;
        Collection<Quiniela> quinielas;

        quinielas = quinielaService.findMyQuinielas();

        result = new ModelAndView("quiniela/list");
        result.addObject("quinielas", quinielas);
        result.addObject("canCreate", true);

        result.addObject("requestURI", "quiniela/administrator/list.do");

        return result;
    }
    
    //Muestra un listado de todas las quinielas de un usuario
    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    public ModelAndView list2(@RequestParam int userId) {

        ModelAndView result;
        Collection<Quiniela> quinielas;
        
        User user = userService.findOne(userId);

        quinielas = quinielaService.findQuinielasForUser(user);

        result = new ModelAndView("quiniela/list");
        result.addObject("quinielas", quinielas);
        result.addObject("requestURI", "quiniela/administrator/list2.do");

        return result;
    }

         
    @RequestMapping(value = "/crear", method = RequestMethod.GET)
    public ModelAndView crear() {

        ModelAndView result;

        Quiniela quiniela;

        quiniela = quinielaService.create();

        result = createEditModelAndView(quiniela);

        result.addObject("quiniela", quiniela);


        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Quiniela quiniela, BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors()) {
            result = createEditModelAndView(quiniela);
        } else {
            try {
            	quinielaService.save(quiniela);
                result = new ModelAndView("redirect:list.do");
            } catch (Throwable oops) {
                result = createEditModelAndView(quiniela, "quiniela.commit.error");
            }
        }

        return result;

    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(Quiniela quiniela) {
        assert quiniela != null;
        ModelAndView result;

        result = createEditModelAndView(quiniela, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(Quiniela quiniela, String message) {

        Assert.notNull(quiniela);
        ModelAndView result;

        result = new ModelAndView("quiniela/edit");

        result.addObject("quiniela", quiniela);
        result.addObject("requestURI", "quiniela/administrator/edit.do?quinielaId=" + quiniela.getId());
        result.addObject("message", message);


        return result;
    }

}
