
package internetkaufhaus.controller;

import internetkaufhaus.model.ConcreteProduct;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import internetkaufhaus.model.mailSender;
import java.util.Optional;

import org.salespointframework.catalog.Product;
import org.salespointframework.core.AbstractEntity;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
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
import internetkaufhaus.model.search;
@Controller
//@PreAuthorize("isAuthenticated()")
@SessionAttributes("cart")
class CartController {

	private final OrderManager<Order> orderManager;
  private final search prodSearch;

	@Autowired
	public CartController(OrderManager<Order> orderManager, search prodSearch) {

		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
		this.prodSearch = prodSearch;
	}

	@ModelAttribute("cart")
	public Cart initializeCart() {
		return new Cart();
	}

	@RequestMapping(value = "/cart", method = RequestMethod.POST)
	public String addProduct(@RequestParam("prodId") ConcreteProduct concreteproduct, 
			@RequestParam("dropDown") int number, @ModelAttribute Cart cart) {

		int amount = number;	

		if(number < 0 || number > 5)
			amount = 1;

		cart.addOrUpdateItem(concreteproduct, Quantity.of(amount));
		// get first Category of product and redirect to associated catalog search
    return "redirect:catalog/"+concreteproduct.getCategories().iterator().next();

	}

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String cartRedirect(Model model) {
	    model.addAttribute("categories", prodSearch.getCagegories());
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
	@RequestMapping(value ="/changeAmount", method = RequestMethod.POST)
	public String changeAmount(@ModelAttribute Cart cart, @RequestParam("cid") String identifier, @RequestParam("amount") int amount )
	{
		Optional <CartItem> cartitem = cart.getItem(identifier);		

		if(!cartitem.isPresent())
			return "redirect:/cart";

		cart.removeItem(identifier);
		cart.addOrUpdateItem(cartitem.get().getProduct(), Quantity.of(amount));

		return "redirect:/cart";
	}
	
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public String pay(@ModelAttribute Cart cart, @LoggedIn Optional<UserAccount> userAccount) {
		
		//return userAccount.map(account -> {

			//Order order = new Order(account, Cash.CASH);
			
			mailSender mailsender = new mailSender("steveJobs@heaven.com", "me@web.de", "Greetings from earth!", "Hello Steve.");
			
			//cart.addItemsTo(order);

			//orderManager.payOrder(order);
			mailsender.sendMail();
			//orderManager.completeOrder(order);

			cart.clear();

			return "redirect:/";
		//}).orElse("redirect:/cart");
	}
}
