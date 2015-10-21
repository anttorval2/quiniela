package controllers.User;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Quiniela;
import services.QuinielaService;
import services.UserService;

@Controller
@RequestMapping("/quiniela/user")
public class QuinielaUserController extends AbstractController {

    // Services -----------------------------------------------------------

    @Autowired
    private QuinielaService quinielaService;
  
    @Autowired
    private UserService userService;

    // Constructors -----------------------------------------------------------

    public QuinielaUserController() {
        super();
    }

    // List........................

    //Muestra un listado de todas las quinielas
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {

        ModelAndView result;
        Collection<Quiniela> quinielas;

        quinielas = quinielaService.findMyQuinielasForUser();

        result = new ModelAndView("quiniela/list");
        result.addObject("quinielas", quinielas);

        result.addObject("requestURI", "quiniela/user/list.do");

        return result;
    }
    
}
