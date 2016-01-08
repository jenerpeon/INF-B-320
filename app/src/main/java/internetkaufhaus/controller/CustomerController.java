package internetkaufhaus.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.salespointframework.order.OrderManager;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.salespointframework.order.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.forms.EditArticleForm;
import internetkaufhaus.forms.EditCustomerForm;
import internetkaufhaus.forms.NewUserAccountForm;
import internetkaufhaus.model.Creditmanager;
import internetkaufhaus.model.NavItem;
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

// TODO: Auto-generated Javadoc
/**
 * This is the customer controller. It controls the customer. Or does it
 * customize the controller? You never know... In this class you may find the
 * controllers for the profile page and similar stuff, should you choose to look
 * for it.
 * 
 * @author max
 *
 */
@Controller
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
@SessionAttributes("cart")
public class CustomerController {

	/** The creditmanager. */
	private final Creditmanager creditmanager;

	/** The user repo. */
	private final ConcreteUserAccountRepository userRepo;

	private final ConcreteOrderRepository concreteOrderRepo;

	private final NewUserAccountForm form;

	// private final Map<String, String> recruits;

	/**
	 * This is the constructor. It's neither used nor does it contain any
	 * functionality other than storing function arguments as class attribute,
	 * what do you expect me to write here?
	 *
	 * @param creditmanager
	 *            the creditmanager
	 * @param userRepo
	 *            the user repo
	 */
	@Autowired
	public CustomerController(Creditmanager creditmanager, ConcreteUserAccountRepository userRepo,
			ConcreteOrderRepository concreteOrderRepo, NewUserAccountForm form) {
		this.creditmanager = creditmanager;

		this.userRepo = userRepo;

		this.concreteOrderRepo = concreteOrderRepo;

		this.form = form;

		// this.recruits = recruits;
	}

	@ModelAttribute("customerNaviagtion")
	public List<NavItem> addCustomerNavigation() {
		String customerNavigationName[] = { "Meine Daten", "Meine Bestellungen", "Punktekonto" };
		String customerNavigationLink[] = { "/customer/data", "/customer/orders", "/customer/points" };
		List<NavItem> navigation = new ArrayList<NavItem>();
		for (int i = 0; i < customerNavigationName.length; i++) {
			NavItem nav = new NavItem(customerNavigationName[i], customerNavigationLink[i], "non-category");
			navigation.add(nav);
		}
		return navigation;
	}

	@RequestMapping("/customer")
	public String customer(@LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		model.addAttribute("account", userAccount.get());
		return "customer";
	}

	@RequestMapping("/customer/data")
	public String customerData(@LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		model.addAttribute("account", userRepo.findByUserAccount(userAccount.get()));
		return "customerdata";
	}

	@RequestMapping(value = "/customer/data/changed", method = RequestMethod.POST)
	public String customerDataChanged(@LoggedIn Optional<UserAccount> userAccount,
			@ModelAttribute("editCustomerForm") @Valid EditCustomerForm editForm) {
		ConcreteUserAccount caccount = userRepo.findByUserAccount(userAccount.get());
		caccount.setEmail(editForm.getEmail());
		caccount.setAddress(editForm.getAddress());
		caccount.setCity(editForm.getCity());
		caccount.setZipCode(editForm.getZipCode());
		caccount.getUserAccount().setFirstname(editForm.getFirstname());
		caccount.getUserAccount().setLastname(editForm.getLastname());
		userRepo.save(caccount);
		form.changeUser(caccount.getId(), editForm.getEmail(), caccount.getRole().toString(), editForm.getPassword());
		return "redirect:/customer/data";
	}

	@RequestMapping("/customer/orders")
	public String customerOrders(@LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		Sort sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "dateOrdered", Sort.NullHandling.NATIVE));
		model.addAttribute("orders", concreteOrderRepo.findByUser(userAccount.get(), sorting));
		return "customerorders";
	}

	@RequestMapping(value = "/customer/orders/return", method = RequestMethod.POST)
	public String customerOrdersReturn(@LoggedIn Optional<UserAccount> userAccount,
			@RequestParam("orderId") Long orderId, @RequestParam("reason") String reason) {
		ConcreteOrder order = concreteOrderRepo.findById(orderId);
		if (order.getUser().equals(userAccount.get())) {
			order.setReturned(true);
			order.setReturnReason(reason);
		}
		concreteOrderRepo.save(order);
		return "redirect:/customer/orders";
	}

	@RequestMapping("/customer/points")
	public String customerPoints(@LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		Creditmanager credit = new Creditmanager(concreteOrderRepo);
		ConcreteUserAccount caccount = userRepo.findByUserAccount(userAccount.get());
		credit.updateCreditpointsByUser(caccount);
		model.addAttribute("account", caccount);
		// model.addAttribute("recruiter",
		// userRepo.findByRecruits(accountList));
		return "customerpoints";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the account details of a given account.
	 *
	 * @param userAccount
	 *            the account to show details about
	 * @param model
	 *            the model
	 * @return the string
	 */
	/*
	 * @RequestMapping("/customer") public String customer(@LoggedIn
	 * Optional<UserAccount> userAccount, ModelMap model) {
	 * creditmanager.updateCreditpointsByUser(userRepo.findByUserAccount(
	 * userAccount.get())); model.addAttribute("points",
	 * userRepo.findByUserAccount(userAccount.get()).getCredits());
	 * model.addAttribute("account", userAccount.get()); return "points"; }
	 */
}