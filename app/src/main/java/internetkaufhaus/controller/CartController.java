
package internetkaufhaus.controller;

import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.javamoney.moneta.Money;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.payment.Cash;
import org.salespointframework.payment.CreditCard;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.forms.BillingAdressForm;
import internetkaufhaus.forms.PaymentForm;
import internetkaufhaus.forms.ShippingAdressForm;
import internetkaufhaus.model.Search;
import internetkaufhaus.repositories.ConcreteOrderRepository;
@Controller
@SessionAttributes("cart")
class CartController {

	private final OrderManager<Order> orderManager;
	private final Search prodSearch;
	private MailSender sender;
	private final ConcreteOrderRepository concreteOrderRepo;

	@Autowired
	public CartController(ConcreteOrderRepository concreteOrderRepo,OrderManager<Order> orderManager, Search prodSearch, MailSender sender) {

		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
		this.prodSearch = prodSearch;
		this.concreteOrderRepo = concreteOrderRepo;
        this.sender = sender;
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
    return "redirect:catalog/"+concreteproduct.getCategories().iterator().next()+"/5/1";

	}

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String cartRedirect(Model model) {
		return "cart";
	}
	
	@ModelAttribute("cart")
	public Cart getCart(){
	   return new Cart(); 
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
	@RequestMapping(value="/orderdata/{option}", method = RequestMethod.POST)
	public String orderdata(@PathVariable("option") int option, @LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		
		return userAccount.map(account -> {

			model.addAttribute("option", option);

			return "orderdata";
		}).orElse("redirect:/login");
	}
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value="/orderdata/{option}", method = RequestMethod.GET)
	public String orderdataredirect(@PathVariable("option") int option, @ModelAttribute Cart cart, @LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		
		return userAccount.map(account -> {
			model.addAttribute("option", option);
			return "orderdata";
		}).orElse("redirect:/login");
	}
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value="/payed", method = RequestMethod.POST)
	public String payed(@ModelAttribute Cart cart, @ModelAttribute("paymentForm") @Valid PaymentForm paymentForm, @ModelAttribute("shippingAdressForm") @Valid ShippingAdressForm shippingAdressForm, @ModelAttribute("billingAdressForm") @Valid BillingAdressForm billingAdressForm, BindingResult result, @LoggedIn Optional<UserAccount> userAccount) {
		return userAccount.map(account -> {
			org.javamoney.moneta.Money dailyWithdrawalLimit = Money.of(1000000000, EURO);
			org.javamoney.moneta.Money creditLimit = Money.of(1000000000, EURO);
			LocalDateTime validFrom = LocalDateTime.MIN;
			
			ConcreteOrder order = new ConcreteOrder(account, Cash.CASH);
			Order o = order.getOrder();
			
			cart.addItemsTo(o);
			
			
			String billingAdress = billingAdressForm.getBillingFirstName() + " " + billingAdressForm.getBillingLastName() + 
					"\n" + billingAdressForm.getBillingStreet() + " " + billingAdressForm.getBillingHouseNumber() + "\n" +
					billingAdressForm.getBillingAdressLine2() + "\n" + billingAdressForm.getBillingZipCode() + "" +
					billingAdressForm.getBillingTown();
			
			CreditCard paymentMethod = new CreditCard(paymentForm.getCardName(), paymentForm.getCardAssociationName(), paymentForm.getCardNumber(), paymentForm.getNameOnCard(), billingAdress, validFrom, paymentForm.getExpiryDateLocalDateTime(), paymentForm.getCardVerificationCode(), dailyWithdrawalLimit, creditLimit);
			
			o.setPaymentMethod(paymentMethod);
			
			order.setBillingAdress(billingAdressForm.getBillingAdress());
			
			order.setShippingAdress(shippingAdressForm.getShippingAdress());
			
			order.setDateOrdered(LocalDateTime.now());
			orderManager.save(o);
			orderManager.payOrder(o);

            order.setStatus(o.getOrderStatus()); 		
            System.out.println(order.getStatus()+"="+o.getOrderStatus());
			concreteOrderRepo.save(order);
           
			cart.clear();

			return "redirect:/";
		}).orElse("redirect:/login");
	}
	
}
