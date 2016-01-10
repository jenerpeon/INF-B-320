package internetkaufhaus.controller;

import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.javamoney.moneta.Money;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.time.Interval;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.forms.CreateUserForm;
import internetkaufhaus.forms.EditUserForm;
import internetkaufhaus.model.Competition;
import internetkaufhaus.model.Creditmanager;
import internetkaufhaus.model.NavItem;
import internetkaufhaus.model.Statistic;
import internetkaufhaus.services.ConcreteMailService;
import internetkaufhaus.services.DataService;
import internetkaufhaus.services.HumanResourceService;
import internetkaufhaus.services.ProductManagementService;

// TODO: Auto-generated Javadoc
/**
 * This is the admin controller. It controls the admin. Or maybe it admins the
 * controls? You never know... In this class you may find the controllers for
 * the admin interfaces, should you choose to look for them.
 * 
 * @author max
 *
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@SessionAttributes("cart")
public class AdminController {

	/** The data service. */
	@Autowired
	private DataService dataService;

	/** The mail service. */
	@Autowired
	private ConcreteMailService mailService;

	/** The product management service. */
	@Autowired
	private ProductManagementService productManagementService;

	/** The HumanResourceService */
	@Autowired
	private HumanResourceService humanResourceService;

	/**
	 * This is the constructor. It's neither used nor does it contain any
	 * functionality other than storing function arguments as class attribute,
	 * what do you expect me to write here?
	 */
	public AdminController() {
	}

	/**
	 * Adds the admin navigation.
	 *
	 * @return the list
	 */
	@ModelAttribute("adminNaviagtion")
	public List<NavItem> addAdminNavigation() {
		String adminNavigationName[] = { "Userverwaltung", "Bilanzen", "Statistiken", "Gewinnspiel" };
		String adminNavigationLink[] = { "/admin/changeuser", "/admin/balance", "/admin/statistics", "/admin/lottery" };
		List<NavItem> navigation = new ArrayList<NavItem>();
		for (int i = 0; i < adminNavigationName.length; i++) {
			NavItem nav = new NavItem(adminNavigationName[i], adminNavigationLink[i], "non-category");
			navigation.add(nav);
		}
		return navigation;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param userAccount
	 *            the user account
	 * @param model
	 *            the model
	 * @return adminOverviewPage
	 */
	@RequestMapping("/admin")
	public String adminStart(@LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		model.addAttribute("account", userAccount.get());
		return "admin";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param model
	 *            the model
	 * @return changeUserPage
	 */
	@RequestMapping(value = "/admin/changeuser")
	public String changeUser(ModelMap model) {
		Role roleCustomer = Role.of("ROLE_CUSTOMER");
		Role roleAdmin = Role.of("ROLE_ADMIN");
		Role roleEmployee = Role.of("ROLE_EMPLOYEE");
		model.addAttribute("employees", dataService.getConcreteUserAccountRepository().findByRole(roleEmployee));
		model.addAttribute("customers", dataService.getConcreteUserAccountRepository().findByRole(roleCustomer));
		model.addAttribute("admins", dataService.getConcreteUserAccountRepository().findByRole(roleAdmin));
		return "changeuser";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param id
	 *            the id
	 * @return redirectToChangeUserPage
	 */
	@RequestMapping(value = "/admin/changeuser/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") Long id, @LoggedIn Optional<UserAccount> admin) {
		humanResourceService.fireEmployee(id, admin);
		return "redirect:/admin/changeuser";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param acc
	 *            the acc
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/admin/changeuser/detail/{id}")
	public String detailUser(@PathVariable("id") ConcreteUserAccount acc, ModelMap model) {
		model.addAttribute("account", acc);
		return "changeuserdetailuser";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @return the string
	 */
	@RequestMapping("/admin/changeuser/addUser")
	public String addUser() {
		return "changeusernewuser";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param createuserform
	 *            the createuserform
	 * @param result
	 *            the result
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/admin/changeuser/addedUser", method = RequestMethod.POST)
	public String addedUser(@ModelAttribute("CreateUserForm") @Valid CreateUserForm createuserform,
			BindingResult result, ModelMap model) {
		if (dataService.getUserAccountManager().findByUsername(createuserform.getName()).isPresent()) {
			ObjectError usernameError = new ObjectError("name", "Der Benutzername existiert bereits.");
			result.addError(usernameError);
		}
		if (dataService.getConcreteUserAccountRepository().findByEmail(createuserform.getEmail()).isPresent()) {
			ObjectError emailError = new ObjectError("email", "Die E-Mail Adresse wird bereits verwendet.");
			result.addError(emailError);
		}
		if (result.hasErrors()) {
			model.addAttribute("message", result.getAllErrors());
			return "changeusernewuser";
		}
		System.out.println("Test");
		humanResourceService.hireEmployee(createuserform);
		return "redirect:/admin/changeuser/";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param acc
	 *            the acc
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/admin/changeuser/editUser/{id}")
	public String editUser(@PathVariable("id") ConcreteUserAccount acc, ModelMap model) {
		Sort sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "dateOrdered", Sort.NullHandling.NATIVE));
		Creditmanager credit = new Creditmanager(dataService);
		credit.updateCreditpointsByUser(acc);
		model.addAttribute("account", acc);

		Iterable<ConcreteOrder> orders = dataService.getConcreteOrderRepository().findByUser(acc, sorting);
		Money turnover = Money.of(0, "EUR");
		for (ConcreteOrder order : orders) {
			turnover = turnover.add(order.getTotalPrice());
		}
		model.addAttribute("orders", orders);
		model.addAttribute("turnover", turnover);
		return "changeuseredituser";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param edituserform
	 *            the edituserform
	 * @param result
	 *            the result
	 * @return the string
	 */
	@RequestMapping(value = "/admin/changeuser/editedUser", method = RequestMethod.POST)
	public String editedUserUser(@ModelAttribute("EditUserForm") @Valid EditUserForm edituserform, BindingResult result,
			@LoggedIn Optional<UserAccount> admin, ModelMap model) {
		ConcreteUserAccount acc = dataService.getConcreteUserAccountRepository().findOne(edituserform.getId());
		System.out.println(acc.getEmail().equals(edituserform.getEmail()));
		if (dataService.getConcreteUserAccountRepository().findByEmail(edituserform.getEmail()).isPresent()
				&& !(acc.getEmail().equals(edituserform.getEmail()))) {
			ObjectError emailError = new ObjectError("email", "Die E-Mail Adresse wird bereits verwendet.");
			result.addError(emailError);
		}
		if (result.hasErrors()) {
			Sort sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "dateOrdered", Sort.NullHandling.NATIVE));
			Creditmanager credit = new Creditmanager(dataService);
			credit.updateCreditpointsByUser(acc);
			model.addAttribute("account", acc);

			Iterable<ConcreteOrder> orders = dataService.getConcreteOrderRepository().findByUser(acc, sorting);
			Money turnover = Money.of(0, "EUR");
			for (ConcreteOrder order : orders) {
				turnover = turnover.add(order.getTotalPrice());
			}
			model.addAttribute("orders", orders);
			model.addAttribute("turnover", turnover);
			model.addAttribute("message", result.getAllErrors());
			return "changeuseredituser";
		}
		humanResourceService.changeEmployee(edituserform, admin);
		return "redirect:/admin/changeuser/";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param acc
	 *            the acc
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/admin/changeuser/displayUser/{id}")
	public String displayUser(@PathVariable("id") ConcreteUserAccount acc, ModelMap model) {
		model.addAttribute("account", acc);
		return "redirect:/admin/changeuser/editUser/" + acc.getId();
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/admin/balance")
	public String balance(ModelMap model) {
		Iterable<ConcreteOrder> ordersCompleted = dataService.getConcreteOrderRepository()
				.findByStatus(OrderStatus.COMPLETED);
		Iterable<ConcreteOrder> ordersOpen = dataService.getConcreteOrderRepository().findByStatus(OrderStatus.OPEN);

		double totalPaid = 0;
		Map<ConcreteOrder, Double> customerOrders = new HashMap<ConcreteOrder, Double>();
		for (ConcreteOrder order : ordersCompleted) {
			if (order.getReturned() == false) {
				customerOrders.put(order, order.getTotalPrice().getNumberStripped().doubleValue());
				totalPaid += customerOrders.get(order);
			}
		}

		double totalOpen = 0;
		Map<ConcreteOrder, Double> stockOrders = new HashMap<ConcreteOrder, Double>();
		for (ConcreteOrder order : ordersOpen) {
			stockOrders.put(order,
					this.productManagementService.getBuyingPrice(order).getNumberStripped().doubleValue());
			totalOpen += stockOrders.get(order);
		}

		double balance = Math.round((totalPaid - totalOpen) * 100.00) / 100.00;

		model.addAttribute("customerOrders", customerOrders);
		model.addAttribute("StockOrders", stockOrders);
		model.addAttribute("customerOrdersTotal", Money.of(Math.round(totalPaid * 100) / 100, EURO));
		model.addAttribute("StockOrdersTotal", Money.of(Math.round(totalOpen * 100) / 100, EURO));
		model.addAttribute("balance", Money.of(balance, EURO));
		return "balance";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param model
	 *            the model
	 * @return the winners
	 */
	@RequestMapping(value = "/admin/statistics")
	public String getStatistics(ModelMap model) {

		LocalDateTime to = LocalDateTime.now();
		LocalDateTime from7Days = to.minusDays(7);
		LocalDateTime from1Month = to.minusMonths(1);
		LocalDateTime from3Month = to.minusMonths(3);
		LocalDateTime from1Year = to.minusYears(1);
		LocalDateTime from3Year = to.minusYears(3);
		LocalDateTime from5Year = to.minusYears(5);
		LocalDateTime from10Year = to.minusYears(10);

		Map<Interval, String> intervals = new LinkedHashMap<Interval, String>();

		intervals.put(Interval.from(from7Days).to(to), "day");
		intervals.put(Interval.from(from1Month).to(to), "week");
		intervals.put(Interval.from(from3Month).to(to), "month");
		intervals.put(Interval.from(from1Year).to(to), "month");
		intervals.put(Interval.from(from3Year).to(to), "year");
		intervals.put(Interval.from(from5Year).to(to), "year");
		intervals.put(Interval.from(from10Year).to(to), "year");

		List<Statistic> stats = new ArrayList<Statistic>();

		for (Interval key : intervals.keySet()) {
			System.out.println(key);
			Statistic stat = new Statistic(dataService, key, intervals.get(key));
			stats.add(stat);
			System.out.println("Bestellungen: "+stat.getOrders()+" Retouren: "+stat.getReturns()+" Umsatz: "+stat.getTurnover()+" Gewinn "+stat.getProfit());
		}

		model.addAttribute("stats", stats);
		return "statistics";
	}

	@RequestMapping(value = "/admin/lottery")
	public String competition() {
		return "competition"; // TODO: what does this even do?
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/competitionButton")
	public String getWinners(ModelMap model) {

		Competition com = new Competition(
				dataService.getConcreteUserAccountRepository().findByRole(Role.of("ROLE_CUSTOMER")),
				new Creditmanager(dataService));

		model.addAttribute("winners", com.getWinners());
		com.getWinners().forEach(x -> System.out.println(x.getUserAccount().getUsername() + " " + x.getCredits()));
		com.notifyWinners(mailService);
		HashMap<String, String> msg = new HashMap<String, String>();
		msg.put("success", "Die folgenden Gewinner wurden benachrichtigt");
		msg.put("name", "Name");
		msg.put("credits", "Punktestand");
		model.addAttribute("competitionmessage", msg);
		return "competition";
	}

}