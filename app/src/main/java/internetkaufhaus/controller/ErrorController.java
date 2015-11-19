package internetkaufhaus.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ErrorController {
	@ExceptionHandler(RuntimeException.class)
	public String error404() {
		return "error404";
	}

	@ExceptionHandler(IOException.class)
	public String error500() {
		return "error500";
	}
}
