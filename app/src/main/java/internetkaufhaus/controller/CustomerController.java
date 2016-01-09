package internetkaufhaus.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.forms.EditCustomerForm;
import internetkaufhaus.model.Creditmanager;
import internetkaufhaus.model.NavItem;
import internetkaufhaus.services.DataService;
import internetkaufhaus.services.HumanResourceService;

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

	@Autowired
	private HumanResourceService humanResourceService;

	@Autowired
	private DataService dataService;

	// private final Map<String, String> recruits;

	/**
	 * This is the constructor. It's neither used nor does it contain any
	 * functionality other than storing function arguments as class attribute,
	 * what do you expect me to write here?
	 *
	 */
	public CustomerController() {

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
		model.addAttribute("account",
				dataService.getConcreteUserAccountRepository().findByUserAccount(userAccount.get()).get());
		return "customerdata";
	}

	@RequestMapping(value = "/customer/data/changed", method = RequestMethod.POST)
	public String customerDataChanged(@LoggedIn Optional<UserAccount> userAccount,
			@ModelAttribute("editCustomerForm") @Valid EditCustomerForm editForm) {
		humanResourceService.changeCustomer(editForm, userAccount);
		return "redirect:/customer/data";
	}

	@RequestMapping("/customer/orders")
	public String customerOrders(@LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		Sort sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "dateOrdered", Sort.NullHandling.NATIVE));
		model.addAttribute("orders", dataService.getConcreteOrderRepository().findByUser(
				dataService.getConcreteUserAccountRepository().findByUserAccount(userAccount.get()).get(), sorting));
		return "customerorders";
	}

	@RequestMapping(value = "/customer/orders/return", method = RequestMethod.POST)
	public String customerOrdersReturn(@LoggedIn Optional<UserAccount> userAccount,
			@RequestParam("orderId") Long orderId, @RequestParam("reason") String reason) {
		ConcreteOrder order = dataService.getConcreteOrderRepository().findById(orderId);
		if (order.getUser().getUserAccount().equals(userAccount.get())) {
			order.setReturned(true);
			order.setReturnReason(reason);
		}
		dataService.getConcreteOrderRepository().save(order);
		return "redirect:/customer/orders";
	}

	@RequestMapping("/customer/points")
	public String customerPoints(@LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		Creditmanager credit = new Creditmanager(dataService);
		ConcreteUserAccount caccount = dataService.getConcreteUserAccountRepository()
				.findByUserAccount(userAccount.get()).get();
		credit.updateCreditpointsByUser(caccount);
		model.addAttribute("account", caccount);
		// model.addAttribute("recruiter",
		// userRepo.findByRecruits(accountList));
		return "customerpoints";
	}
}