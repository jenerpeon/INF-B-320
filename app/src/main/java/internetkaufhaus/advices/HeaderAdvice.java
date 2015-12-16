package internetkaufhaus.advices;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import internetkaufhaus.model.Search;

@ControllerAdvice
public class HeaderAdvice {
	private Search prodSearch;

	/**
	 * This is the constructor.
	 * It's neither used nor does it contain any functionality other than storing function arguments as class attribute, what do you expect me to write here?
	 * 
	 * @param prodSearch
	 */
	@Autowired
	public HeaderAdvice(Search prodSearch) {
		this.prodSearch = prodSearch;
	}

	/**
	 * This is a Model Attribute. It Models Attributes.
	 * Adds the available categories to the variables used by thymeleaf so the category list can be displayed in the navigation header.
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @return
	 */
	@ModelAttribute(value = "categories")
	public Iterable<String> addCatalog(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		return prodSearch.getCategories();
	}

	/**
	 * This is a Model Attribute. It Models Attributes.
	 * Adds the current cart price to the variables used by thymelead so an overview about the cart can be displayed in the navigation header.
	 * 
	 * @param cart
	 * @return
	 */
	@ModelAttribute(value = "cartprice")
	public String addCartPrice(@ModelAttribute Cart cart) {
		return cart.getPrice().toString();
	}

	/**
	 * This is a Model Attribute. It Models Attributes.
	 * Adds the current number of items in the cart to the variables used by thymeleaf so an overview about the cart can be displayed in the navigation header.
	 * 
	 * @param cart
	 * @return
	 */
	@ModelAttribute(value = "cartsize")
	public int addCartSize(@ModelAttribute Cart cart) {
		Iterator<CartItem> i = cart.iterator();
		int size = 0;
		while (i.hasNext()) {
			size++;
		}
		return size;
	}

	/**
	 * This is an Exception Hander. It handles Exceptions.
	 * 
	 * @param exception
	 * @return
	 *
	@ExceptionHandler(value = Exception.class)
	public String handleExceptions(Exception exception) {
		return "/error";
	}*/
}
