package internetkaufhaus.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.SessionAttributes;

// TODO: Auto-generated Javadoc
/**
 * This is the error controller. It controls errors. Or does it error controls? You never know...
 * In this class you may find the "controllers" (handlers in this case) for error pages, should you choose to look for them.
 * Unfortunately this doesn't always work as expected, but whaddaya know, spring's a bitch.
 * 
 * @author max
 *
 */
@Controller
@SessionAttributes("cart")
public class ErrorController {
	
	/**
	 * This is an exception handler. It handles exceptions. Or does is except handles?
	 *
	 * @return the string
	 */
	@ExceptionHandler(RuntimeException.class)
	public String error404() {
		return "error404";
	}

	/**
	 * This is an exception handler. It handles exceptions. Or does is except handles?
	 *
	 * @return the string
	 */
	@ExceptionHandler(IOException.class)
	public String error500() {
		return "error500";
	}
}
