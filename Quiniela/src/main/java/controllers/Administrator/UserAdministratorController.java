package controllers.Administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.User;
import services.UserService;

@Controller
@RequestMapping("/user/administrator")
public class UserAdministratorController extends AbstractController {

    // Services -----------------------------------------------------------

    @Autowired
    private UserService userService;

    // Constructors -----------------------------------------------------------

    public UserAdministratorController() {
        super();
    }

    // List........................

    //Muestra un listado de todos los usuarios
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {

        ModelAndView result;
        Collection<User> users;

        users = userService.findAll();

        result = new ModelAndView("user/list");
        result.addObject("users", users);
        result.addObject("requestURI", "user/administrator/list.do");

        return result;
    }

}
