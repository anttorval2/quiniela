package controllers.User;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.User;
import services.UserService;


@Controller
@RequestMapping("/register/administrator")
public class RegisterUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private UserService userService;
	
	
	// Constructors
	// ---------------------------------------------------------------

	public RegisterUserController() {
		super();
	}


	// Registration -----------------------------------------------------------


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		
		User user = userService.create();

		result = createEditModelAndView(user);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid User user,
			BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(user);

		} else {

			try {

				userService.save(user);

				result = new ModelAndView("redirect:../../security/login.do");

			} catch (DataIntegrityViolationException oops) {

				result = createEditModelAndView(user,
						"register.duplicate.error");

			} catch (Throwable oops) {
				oops.getMessage();



						result = createEditModelAndView(user,
								"register.commit.error");
					
				}

			
		}
		return result;
	}
	
	

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(User user) {

		ModelAndView result;

		result = createEditModelAndView(user, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(User user,
			String message) {

		ModelAndView result;

		result = new ModelAndView("register/edit");
		result.addObject("user", user);
		result.addObject("message", message);

		return result;
	}

}