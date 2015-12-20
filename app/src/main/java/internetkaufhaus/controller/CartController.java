
package internetkaufhaus.controller;

import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;

import javax.validation.Valid;

import org.javamoney.moneta.Money;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.forms.BillingAdressForm;
import internetkaufhaus.forms.PaymentForm;
import internetkaufhaus.forms.ShippingAdressForm;
import internetkaufhaus.model.ConcreteMailSender;
import internetkaufhaus.model.ReturnManager;
import internetkaufhaus.repositories.ConcreteOrderRepository;

@Controller
@SessionAttributes("cart")
class CartController {

	private final OrderManager<Order> orderManager;
	private MailSender sender;
	private final ConcreteOrderRepository concreteOrderRepo;

	/**
	 * This is the constructor. It's neither used nor does it contain any functionality other than storing function arguments as class attribute, what do you expect me to write here?
	 * 
	 * @param userRepo
	 * @param concreteOrderRepo
	 * @param orderManager
	 * @param prodSearch
	 * @param sender
	 */
	@Autowired
	public CartController(ConcreteOrderRepository concreteOrderRepo, OrderManager<Order> orderManager, MailSender sender) {
		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
		this.concreteOrderRepo = concreteOrderRepo;
		this.sender = sender;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param concreteproduct
	 * @param number
	 * @param cart
	 * @return
	 */
	@RequestMapping(value = "/cart", method = RequestMethod.POST)
	public String addProduct(@RequestParam("prodId") ConcreteProduct concreteproduct, @RequestParam("dropDown") int number, @ModelAttribute Cart cart) {
		int amount = number;
		if (number < 0 || number > 5)
			amount = 1;
		cart.addOrUpdateItem(concreteproduct, Quantity.of(amount));
		// get first Category of product and redirect to associated catalog search
		return "redirect:catalog/" + concreteproduct.getCategories().iterator().next() + "/name/1/5/1";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String cartRedirect() {
		return "cart";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param cart
	 * @return
	 */
	@RequestMapping(value = "/clearCart", method = RequestMethod.POST)
	public String clearCart(@ModelAttribute Cart cart) {
		cart.clear();
		return "redirect:/cart";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param cart
	 * @param identifier
	 * @return
	 */
	@RequestMapping(value = "/deleteItem", method = RequestMethod.POST)
	public String deleteItem(@ModelAttribute Cart cart, @RequestParam("cid") String identifier) {
		cart.removeItem(identifier);
		return "redirect:/cart";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param cart
	 * @param identifier
	 * @param amount
	 * @return
	 */
	@RequestMapping(value = "/changeAmount", method = RequestMethod.POST)
	public String changeAmount(@ModelAttribute Cart cart, @RequestParam("cid") String identifier, @RequestParam("amount") int amount) {
		Optional<CartItem> cartitem = cart.getItem(identifier);
		if (!cartitem.isPresent())
			return "redirect:/cart";
		cart.removeItem(identifier);
		cart.addOrUpdateItem(cartitem.get().getProduct(), Quantity.of(amount));
		return "redirect:/cart";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param option
	 * @param userAccount
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/orderdata/{option}", method = RequestMethod.POST)
	public String orderdata(@PathVariable("option") int option, @LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		return userAccount.map(account -> {
			model.addAttribute("option", option);
			return "orderdata";
		}).orElse("redirect:/login");
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param option
	 * @param userAccount
	 * @param model
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value = "/orderdata/{option}", method = RequestMethod.GET)
	public String orderdataredirect(@PathVariable("option") int option, @LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		return userAccount.map(account -> {
			model.addAttribute("option", option);
			return "orderdata";
		}).orElse("redirect:/login");
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param modelmap
	 * @param cart
	 * @param paymentForm
	 * @param result
	 * @param shippingAdressForm
	 * @param billingAdressForm
	 * @param userAccount
	 * @param redir
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value = "/payed", method = RequestMethod.POST)
	public String payed(@ModelAttribute Cart cart, @ModelAttribute("paymentForm") @Valid PaymentForm paymentForm, BindingResult result, @ModelAttribute("shippingAdressForm") @Valid ShippingAdressForm shippingAdressForm, @ModelAttribute("billingAdressForm") @Valid BillingAdressForm billingAdressForm, @LoggedIn Optional<UserAccount> userAccount, RedirectAttributes redir) {
		if (result.hasErrors()) {
			redir.addFlashAttribute("message", result.getAllErrors());
			return "redirect:/orderdata/1";
		}
		return userAccount.map(account -> {
			org.javamoney.moneta.Money dailyWithdrawalLimit = Money.of(1000000000, EURO);
			org.javamoney.moneta.Money creditLimit = Money.of(1000000000, EURO);
			LocalDateTime validFrom = LocalDateTime.MIN;

			ConcreteOrder order = new ConcreteOrder(account, Cash.CASH);
			Order o = order.getOrder();

			cart.addItemsTo(o);

			String billingAdress = billingAdressForm.getBillingFirstName() + " " + billingAdressForm.getBillingLastName() + "\n" + billingAdressForm.getBillingStreet() + " " + billingAdressForm.getBillingHouseNumber() + "\n" + billingAdressForm.getBillingAdressLine2() + "\n" + billingAdressForm.getBillingZipCode() + "" + billingAdressForm.getBillingTown();

			CreditCard paymentMethod = new CreditCard(paymentForm.getCardName(), paymentForm.getCardAssociationName(), paymentForm.getCardNumber(), paymentForm.getNameOnCard(), billingAdress, validFrom, paymentForm.getExpiryDateLocalDateTime(), paymentForm.getCardVerificationCode(), dailyWithdrawalLimit, creditLimit);

			o.setPaymentMethod(paymentMethod);

			order.setBillingAdress(billingAdressForm.getBillingAdress());

			order.setShippingAdress(shippingAdressForm.getShippingAdress());

			order.setDateOrdered(LocalDateTime.now());
			orderManager.save(o);
			orderManager.payOrder(o);

			order.setStatus(o.getOrderStatus());
			concreteOrderRepo.save(order);

			String articles = "";
			Iterator<OrderLine> i = order.getOrder().getOrderLines().iterator();
			OrderLine current;
			while (i.hasNext()) {
				current = i.next();
				articles += "\n" + current.getQuantity().toString() + "x " + current.getProductName() + " für gesamt " + current.getPrice().toString();
			}
			articles += "\nGesamtpreis: " + order.getOrder().getTotalPrice().toString();

			cart.clear();

			new ConcreteMailSender(this.sender).sendMail(account.getEmail(), "Sehr geehrte(r) " + account.getFirstname() + " " + account.getLastname() + "!\nIhre Bestellung ist soeben bei uns eingetroffen und wird nun bearbeitet!\nIhre Bestellung umfasst folgende Artikel:" + articles, "nobody@nothing.com", "Bestellung eingetroffen!");

			return "redirect:/";
		}).orElse("redirect:/login");
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param model
	 * @param userAccount
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value = "/returOrders", method = RequestMethod.GET)
	public String returnRedirect(Model model, @LoggedIn Optional<UserAccount> userAccount) {
		model.addAttribute("ordersCompletedInReturnTime", ReturnManager.getConcreteOrderDuringLastTwoWeeks(concreteOrderRepo, userAccount));
		return "returOrders";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param orderId
	 * @param reason
	 * @param cart
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value = "/returOrders", method = RequestMethod.POST)
	public String returnOrder(@RequestParam("orderId") Long orderId, @RequestParam("dropDown") String reason) {
		concreteOrderRepo.findById(orderId).setReturned(true);
		concreteOrderRepo.findById(orderId).setReturnReason(reason);
		concreteOrderRepo.save(concreteOrderRepo.findById(orderId));
		return "redirect:/";
	}

}
