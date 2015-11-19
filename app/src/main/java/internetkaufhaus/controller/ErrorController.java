package internetkaufhaus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ErrorController {

	@ExceptionHandler(RuntimeException.class)
	public String error404(ModelMap model) {

		return "error";
	}

}
