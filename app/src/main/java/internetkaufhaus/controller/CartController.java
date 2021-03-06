
package internetkaufhaus.controller;

import static org.salespointframework.core.Currencies.EURO;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.Optional;

import javax.validation.Valid;

import org.javamoney.moneta.Money;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.payment.CreditCard;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.forms.PaymentForm;
import internetkaufhaus.model.Creditmanager;
import internetkaufhaus.services.ConcreteMailService;
import internetkaufhaus.services.DataService;

// TODO: Auto-generated Javadoc
/**
 * This is the cart controller. It controls the cart. Or maybe it carts the
 * controls? You never know... In this class you may find the controllers for
 * the shopping cart interfaces, should you choose to look for them.
 * 
 * @author max
 *
 */
@Controller
@SessionAttributes("cart")
class CartController {

	@Autowired
	private DataService dataService;

	/** The sender. */
	private final ConcreteMailService sender;

	/**
	 * This is the constructor. It's neither used nor does it contain any
	 * functionality other than storing function arguments as class attribute,
	 * what do you expect me to write here?
	 *
	 * @param concreteOrderRepo
	 *            the concrete order repo
	 * @param orderManager
	 *            the order manager
	 * @param sender
	 *            the sender
	 */
	@Autowired
	public CartController(ConcreteMailService sender) {
		this.sender = sender;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page adds a product to the cart and then redirects back to the
	 * catalog.
	 *
	 * @param concreteproduct
	 *            the concreteproduct
	 * @param number
	 *            the number
	 * @param cart
	 *            the cart
	 * @return the string
	 */
	@RequestMapping(value = "/cart", method = RequestMethod.POST)
	public String addProduct(@RequestParam("prodId") ConcreteProduct concreteproduct,
			@RequestParam("dropDown") int number, @ModelAttribute Cart cart) {
		int amount = number;
		if (number < 0 || number > 5)
			amount = 1;
		cart.addOrUpdateItem(concreteproduct, Quantity.of(amount));
		// get first Category of product and redirect to associated catalog
		// search
		return "redirect:catalog/" + concreteproduct.getCategories().iterator().next() + "/name/1/5/1";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the cart.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String cartRedirect(ModelMap model, @ModelAttribute Cart cart, @LoggedIn Optional<UserAccount> userAccount) {
		DecimalFormat formatter = new DecimalFormat("0.00€");
		if(userAccount.isPresent()) {
			long points = usablePoints(cart,
					dataService.getConcreteUserAccountRepository().findByUserAccount(userAccount.get()).get());
			model.addAttribute("points", points);
			model.addAttribute("discount", formatter.format(((double)points)/100));
		} else {
			model.addAttribute("points", 0);
			model.addAttribute("discount", formatter.format(0));
		}
		
		return "cart";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page clears the cart.
	 *
	 * @param cart
	 *            the cart
	 * @return the string
	 */
	@RequestMapping(value = "/clearCart", method = RequestMethod.POST)
	public String clearCart(@ModelAttribute Cart cart) {
		cart.clear();
		return "redirect:/cart";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page removes one item from the cart.
	 *
	 * @param cart
	 *            the cart
	 * @param identifier
	 *            the identifier
	 * @return the string
	 */
	@RequestMapping(value = "/deleteItem", method = RequestMethod.POST)
	public String deleteItem(@ModelAttribute Cart cart, @RequestParam("cid") String identifier) {
		cart.removeItem(identifier);
		return "redirect:/cart";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page changes the amount of items of one product in the cart.
	 *
	 * @param cart
	 *            the cart
	 * @param identifier
	 *            the identifier
	 * @param amount
	 *            the amount
	 * @return the string
	 */
	@RequestMapping(value = "/changeAmount", method = RequestMethod.POST)
	public String changeAmount(@ModelAttribute Cart cart, @RequestParam("cid") String identifier,
			@RequestParam("amount") int amount) {
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
	 *            the option
	 * @param userAccount
	 *            the user account
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/orderdata/{option}", method = RequestMethod.POST)
	public String orderdata(@PathVariable("option") int option, @LoggedIn Optional<UserAccount> userAccount,
			ModelMap model) {
		return userAccount.map(account -> {
			model.addAttribute("option", option);
			return "orderdata";
		}).orElse("redirect:/#login-modal");
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param option
	 *            the option
	 * @param userAccount
	 *            the user account
	 * @param model
	 *            the model
	 * @return the string
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value = "/orderdata/{option}", method = RequestMethod.GET)
	public String orderdataredirect(@PathVariable("option") int option, @LoggedIn Optional<UserAccount> userAccount,
			ModelMap model) {
		return userAccount.map(account -> {
			model.addAttribute("option", option);
			return "orderdata";
		}).orElse("redirect:/#login-modal");
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param cart
	 *            the cart
	 * @param paymentForm
	 *            the payment form
	 * @param result
	 *            the result
	 * @param userAccount
	 *            the user account
	 * @param redir
	 *            the redir
	 * @return the string
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value = "/payed", method = RequestMethod.POST)
	public String payed(@ModelAttribute Cart cart, @ModelAttribute("paymentForm") @Valid PaymentForm paymentForm,
			BindingResult result, @LoggedIn Optional<UserAccount> userAccount, RedirectAttributes redir) {
		if (result.hasErrors()) {
			redir.addFlashAttribute("message1", result.getAllErrors());
			return "redirect:/orderdata/1";
		}
		return userAccount.map(account -> {
			org.javamoney.moneta.Money dailyWithdrawalLimit = Money.of(1000000000, EURO);
			org.javamoney.moneta.Money creditLimit = Money.of(1000000000, EURO);
			LocalDateTime validFrom = LocalDateTime.MIN;

			ConcreteUserAccount caccount = dataService.getConcreteUserAccountRepository().findByUserAccount(account)
					.get();
			ConcreteOrder order = new ConcreteOrder(caccount, Cash.CASH);

			cart.addItemsTo(order);

			String billingAddress = paymentForm.getBillingFirstName() + " " + paymentForm.getBillingLastName() + "\n"
					+ paymentForm.getBillingStreet() + " " + paymentForm.getBillingHouseNumber() + "\n"
					+ paymentForm.getBillingAddressLine2() + "\n" + paymentForm.getBillingZipCode() + ""
					+ paymentForm.getBillingTown();

			CreditCard paymentMethod = new CreditCard(paymentForm.getCardName(), paymentForm.getCardAssociationName(),
					paymentForm.getCardNumber(), paymentForm.getNameOnCard(), billingAddress, validFrom,
					paymentForm.getExpiryDateLocalDateTime(), paymentForm.getCardVerificationCode(),
					dailyWithdrawalLimit, creditLimit);

			order.setPaymentMethod(paymentMethod);

			order.setBillingAddress(paymentForm.getBillingAddress());

			order.setShippingAddress(paymentForm.getShippingAddress());

			order.setDateOrdered(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(1)));
			
			order.setUsedDiscountPoints(usablePoints(cart, caccount));

			order.setStatus(OrderStatus.PAID);
			dataService.getConcreteOrderRepository().save(order);

			String articles = "";
			Iterator<OrderLine> i = order.getOrderLines().iterator();
			OrderLine current;
			while (i.hasNext()) {
				current = i.next();
				articles += "\n" + current.getQuantity().toString() + "x " + current.getProductName() + " für gesamt "
						+ current.getPrice().toString();
			}
			articles += "\nGesamtpreis: " + order.getTotalPrice().toString();

			cart.clear();

			sender.sendMail(account.getEmail(),
					"Sehr geehrte(r) " + account.getFirstname() + " " + account.getLastname()
							+ "!\nIhre Bestellung ist soeben bei uns eingetroffen und wird nun bearbeitet!\nIhre Bestellung umfasst folgende Artikel:"
							+ articles,
					"nobody@nothing.com", "Bestellung eingetroffen!");

			return "redirect:/";
		}).orElse("redirect:/#login");
	}

	private long usablePoints(Cart cart, ConcreteUserAccount caccount) {
		Creditmanager credit = new Creditmanager(dataService);
		credit.updateCreditpointsByUser(caccount);
		
		if (cart.getPrice().divide(5).isGreaterThanOrEqualTo(Money.of(caccount.getCredits(), "EUR").divide(100))) {
			return caccount.getCredits();
		} else {
			return Math.round(cart.getPrice().multiply(20).getNumberStripped().doubleValue());
		}
	}

}
