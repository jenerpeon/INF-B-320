package internetkaufhaus.advices;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import internetkaufhaus.model.NavItem;
import internetkaufhaus.model.Search;

/**
 * This is a Header Advice. It Advices Headers. Or does it Head Advices?
 * 
 * @author max
 *
 */
@ControllerAdvice
@SessionAttributes("cart")
public class HeaderAdvice {

	private final Search prodSearch;

	/**
	 * This is the constructor. It's neither used nor does it contain any
	 * functionality other than storing function arguments as class attribute,
	 * what do you expect me to write here? It's copied from CartController.
	 * 
	 * @param prodSearch
	 *            singleton, passed by spring/salespoint
	 * @param sender
	 *            singleton, passed by spring/salespoint
	 */
	@Autowired
	public HeaderAdvice(Search prodSearch) {
		this.prodSearch = prodSearch;
	}

	/**
	 * This is a Model Attribute. It Models Attributes. Or does it Attribute
	 * Models? Adds the available categories to the variables used by thymeleaf
	 * so the category list can be displayed in the navigation header.
	 * 
	 * @return navigation
	 */
	@ModelAttribute("categories")
	public List<NavItem> addCatalog() {
		Iterable<String> categories = prodSearch.getCategories();
		List<NavItem> navigation = new ArrayList<NavItem>();
		for (String category : categories) {
			NavItem nav = new NavItem(category, category, "category");
			navigation.add(nav);
		}
		return navigation;
	}

	@ModelAttribute("cart")
	public Cart getCart(@ModelAttribute("cart") Cart cart) {
		return cart;
	}

	/**
	 * This is a Model Attribute. It Models Attributes. Or does it Attribute
	 * Models? Adds the current cart price to the variables used by thymeleaf so
	 * an overview about the cart can be displayed in the navigation header.
	 * 
	 * @param cart
	 * @return cartPrice
	 */
	@ModelAttribute("cartprice")
	public String addCartPrice(@ModelAttribute("cart") Cart cart) {
		return cart.getPrice().toString();
	}

	/**
	 * This is a Model Attribute. It Models Attributes. Or does it Attribute
	 * Models? Adds the current number of items in the cart to the variables
	 * used by thymeleaf so an overview about the cart can be displayed in the
	 * navigation header.
	 * 
	 * @param cart
	 * @return cartSize
	 */
	@ModelAttribute("cartsize")
	public int addCartSize(@ModelAttribute("cart") Cart cart) {
		Iterator<CartItem> i = cart.iterator();
		int size = 0;
		while (i.hasNext()) {
			size++;
			i.next();
		}
		return size;
	}

	/**
	 * This is an Exception Hander. It handles Exceptions. Or does it Except
	 * Handles?
	 * 
	 * @param exception
	 * @return "error500"
	 */
	@ExceptionHandler(value = Exception.class)
	public String handleExceptions(Exception exception) {
		if (exception instanceof AccessDeniedException) {
			return "redirect:/#login";
		}
		System.out.println(exception.toString());
		System.out.println(exception.getMessage());
		exception.printStackTrace();
		return "error500";
	}
}
