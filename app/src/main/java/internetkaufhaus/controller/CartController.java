
package internetkaufhaus.controller;

import internetkaufhaus.model.ConcreteProduct;

import java.util.Optional;

import org.salespointframework.catalog.Product;
import org.salespointframework.core.AbstractEntity;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
//@PreAuthorize("isAuthenticated()")
@SessionAttributes("cart")
class CartController {

	private final OrderManager<Order> orderManager;

	@Autowired
	public CartController(OrderManager<Order> orderManager) {

		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
	}

	@ModelAttribute("cart")
	public Cart initializeCart() {
		return new Cart();
	}

	@RequestMapping(value = "/cart", method = RequestMethod.POST)
	public String addProduct(@RequestParam("pid") ConcreteProduct concreteproduct, 
			@RequestParam("dropdown") int number, @ModelAttribute Cart cart) {

		int amount;	
		if(number < 0 || number > 5)
		{
			amount = 1;
		}
		else
		{
			amount = number;
		}

		cart.addOrUpdateItem(concreteproduct, Quantity.of(amount));

		switch (concreteproduct.getType()) {
			case Fuzz:
				return "redirect:catalog/Fuzz";
			case Trash:
				return "redirect:catalog/Trash";
			default:
				return "redirect:catalog/Garbage";
		}
	}

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String cartRedirect() {
		return "cart";
	}

	
	@RequestMapping(value = "/clearCart", method = RequestMethod.POST)
	public String clearCart(@ModelAttribute Cart cart,@LoggedIn Optional<UserAccount> userAccount)
	{	
			cart.clear();
			return "redirect:/cart";

	}
	
	@RequestMapping(value = "/deleteItem", method = RequestMethod.POST)
	public String deleteItem(@ModelAttribute Cart cart, @RequestParam("cid") String identifier)
	{
		cart.removeItem(identifier);
		return "redirect:/cart";
	}
}
