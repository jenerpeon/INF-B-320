package internetkaufhaus.advices;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import internetkaufhaus.model.Search;
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

/**
 * This is a Header Advice. It Advices Headers. Or does it Head Advices?
 * 
 * @author max
 *
 */
@ControllerAdvice
public class HeaderAdvice {

	private final OrderManager<Order> orderManager;
	private final Search prodSearch;
	private MailSender sender;
	private final ConcreteOrderRepository concreteOrderRepo;
	private final ConcreteUserAccountRepository userRepo;

	/**
	 * This is the constructor. It's neither used nor does it contain any functionality other than storing function arguments as class attribute, what do you expect me to write here? It's copied from CartController.
	 * 
	 * @param prodSearch
	 */
	@Autowired
	public HeaderAdvice(ConcreteUserAccountRepository userRepo, ConcreteOrderRepository concreteOrderRepo, OrderManager<Order> orderManager, Search prodSearch, MailSender sender) {
		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
		this.prodSearch = prodSearch;
		this.concreteOrderRepo = concreteOrderRepo;
		this.sender = sender;
		this.userRepo = userRepo;
	}

	/**
	 * This is a Model Attribute. It Models Attributes. Or does it Attribute Models? Adds the available categories to the variables used by thymeleaf so the category list can be displayed in the navigation header.
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @return
	 */
	@ModelAttribute("categories")
	public Iterable<String> addCatalog(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		return prodSearch.getCategories();
	}

	@ModelAttribute("cart")
	public Cart getCart() {
		return new Cart();
	}

	/**
	 * This is a Model Attribute. It Models Attributes. Or does it Attribute Models? Adds the current cart price to the variables used by thymeleaf so an overview about the cart can be displayed in the navigation header.
	 * 
	 * @param cart
	 * @return
	 */
	@ModelAttribute("cartprice")
	public String addCartPrice(@ModelAttribute Cart cart) {
		return cart.getPrice().toString();
	}

	/**
	 * This is a Model Attribute. It Models Attributes. Or does it Attribute Models? Adds the current number of items in the cart to the variables used by thymeleaf so an overview about the cart can be displayed in the navigation header.
	 * 
	 * @param cart
	 * @return
	 */
	@ModelAttribute("cartsize")
	public int addCartSize(@ModelAttribute Cart cart) {
		Iterator<CartItem> i = cart.iterator();
		int size = 0;
		while (i.hasNext()) {
			size++;
			i.next();
		}
		return size;
	}

	/**
	 * This is an Exception Hander. It handles Exceptions. Or does it Except Handles?
	 * 
	 * @param exception
	 * @return
	 *
	 * @ExceptionHandler(value = Exception.class) public String handleExceptions(Exception exception) { return "/error"; }
	 */
}
