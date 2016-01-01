package internetkaufhaus.controller;

import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.forms.CreateUserForm;
import internetkaufhaus.forms.EditUserForm;
import internetkaufhaus.forms.NewUserAccountForm;
import internetkaufhaus.model.Competition;
import internetkaufhaus.model.Creditmanager;
import internetkaufhaus.model.NavItem;
import internetkaufhaus.services.ConcreteMailService;
import internetkaufhaus.services.DataService;

/**
 * This is the admin controller. It controls the admin. Or maybe it admins the controls? You never know... In this class you may find the controllers for the admin interfaces, should you choose to look for them.
 * 
 * @author max
 *
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
	
	@Autowired
	private DataService dataService;
	@Autowired
	private ConcreteMailService mailService;

	private final NewUserAccountForm form;
	private final Creditmanager creditmanager;

	/**
	 * This is the constructor. It's neither used nor does it contain any functionality other than storing function arguments as class attribute, what do you expect me to write here?
	 * 
	 * @param concreteOrderRepo
	 * @param manager
	 * @param umanager
	 * @param creditmanager
	 * @param form
	 */
	@Autowired
	public AdminController(Creditmanager creditmanager, NewUserAccountForm form) {
			
		this.form = form;
		this.creditmanager = creditmanager;

	}
	
	@ModelAttribute("adminNaviagtion")
	public List<NavItem> addAdminNavigation() {
		String adminNavigationName[] = {"Userverwaltung","Bilanzen","Statistiken","Gewinnspiel"};
		String adminNavigationLink[] = {"/admin/changeuser","/admin/balance","/admin/statistics","/admin/lottery"};
		List<NavItem> navigation = new ArrayList<NavItem>();
		for (int i=0; i < adminNavigationName.length; i++) {
			NavItem nav = new NavItem(adminNavigationName[i],adminNavigationLink[i],"non-category");
			navigation.add(nav);
		}
		return navigation;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param userAccount
	 * @param model
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
	 * @return changeUserPage
	 */
	@RequestMapping(value = "/admin/changeuser")
	public String changeUser(ModelMap model) {
		Role roleCustomer = Role.of("ROLE_CUSTOMER");
		Role roleAdmin = Role.of("ROLE_ADMIN");
		Role roleEmployee = Role.of("ROLE_EMPLOYEE");
		model.addAttribute("employees", dataService.getConcreteUserAccoutnRepository().findByRole(roleEmployee));
		model.addAttribute("customers", dataService.getConcreteUserAccoutnRepository().findByRole(roleCustomer));
		model.addAttribute("admins", dataService.getConcreteUserAccoutnRepository().findByRole(roleAdmin));
		return "changeuser";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param id
	 * @return redirectToChangeUserPage
	 */
	@RequestMapping(value = "/admin/changeuser/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
        dataService.getUserAccountManager().disable(
        		dataService.getConcreteUserAccoutnRepository().
        	    	findOne(id).
        	    	getUserAccount().
        	    	getId());
        dataService.getConcreteUserAccoutnRepository().delete(id);

		return "redirect:/admin/changeuser";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param acc
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/changeuser/detail/{id}")
	public String detailUser(@PathVariable("id") ConcreteUserAccount acc, ModelMap model) {
		model.addAttribute("account", acc);
		return "changeuserdetailuser";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @return
	 */
	@RequestMapping("/admin/changeuser/addUser")
	public String addArticle() {
		return "changeusernewuser";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param createuserform
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/changeuser/addedUser", method = RequestMethod.POST)
	public String addedUser(@ModelAttribute("CreateUserForm") @Valid CreateUserForm createuserform, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("message", result.getAllErrors());
			return "changeusernewuser";
		}
		form.createUser(createuserform);
		return "redirect:/admin/changeuser/";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param acc
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/changeuser/editUser/{id}")
	public String editUser(@PathVariable("id") ConcreteUserAccount acc, ModelMap model) {
		model.addAttribute("account", acc);
		return "changeuseredituser";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param edituserform
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/admin/changeuser/editedUser", method = RequestMethod.POST)
	public String editedUserUser(@ModelAttribute("EditUserForm") @Valid EditUserForm edituserform, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/changeuser/";
		}
		form.changeUser(edituserform.getId(), edituserform.getRolename(), edituserform.getPassword());
		return "redirect:/admin/changeuser/";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param acc
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/changeuser/displayUser/{id}")
	public String displayUser(@PathVariable("id") ConcreteUserAccount acc, ModelMap model) {
		model.addAttribute("account", acc);
		return "changeUserDisplay";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/balance")
	public String balance(ModelMap model) {
		Iterable<ConcreteOrder> ordersCompleted = dataService.getConcreteOrderRepository().findByStatus(OrderStatus.COMPLETED); 
		Iterable<ConcreteOrder> ordersOpen = dataService.getConcreteOrderRepository().findByStatus(OrderStatus.OPEN);

		double totalPaid = 0;
		for (ConcreteOrder order : ordersCompleted) {
			if (order.getReturned() == false) {
				totalPaid += order.getOrder().getTotalPrice().getNumberStripped().doubleValue();
			}
		}

		double totalOpen = 0;
		for (ConcreteOrder order : ordersOpen) {
			totalOpen += order.getOrder().getTotalPrice().getNumberStripped().doubleValue();
		}

		double balance = Math.round((totalPaid - totalOpen) * 100.00) / 100.00;

		model.addAttribute("customerOrders", ordersCompleted);
		model.addAttribute("StockOrders", ordersOpen);
		model.addAttribute("customerOrdersTotal", Money.of(Math.round(totalPaid * 100) / 100, EURO));
		model.addAttribute("StockOrdersTotal", Money.of(Math.round(totalOpen), EURO));
		model.addAttribute("balance", Money.of(balance, EURO));
		return "balance";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/statistics")
	public String getStatistics() {
		//Statistic stat = new Statistic(orderManager);
		LocalDateTime to = LocalDateTime.now();
		LocalDateTime from7Days = to.minusDays(7);
		LocalDateTime from1Month = to.minusMonths(1);
		LocalDateTime from3Month = to.minusMonths(3);
		LocalDateTime from1Year = to.minusYears(1);
		LocalDateTime from3Year = to.minusYears(3);
		LocalDateTime from5Year = to.minusYears(5);
		LocalDateTime from10Year = to.minusYears(10);
		
		Map<Interval, String> intervals = new HashMap<Interval, String>();
		
		intervals.put(Interval.from(from7Days).to(to), "day");
		intervals.put(Interval.from(from1Month).to(to), "week");
		intervals.put(Interval.from(from3Month).to(to), "month");
		intervals.put(Interval.from(from1Year).to(to), "month");
		intervals.put(Interval.from(from3Year).to(to), "year");
		intervals.put(Interval.from(from5Year).to(to), "year");
		intervals.put(Interval.from(from10Year).to(to), "year");
		
		/*List<Map<LocalDate, Money>> turnovers= new ArrayList<Map<LocalDate, Money>>();
		for (Map.Entry<Interval, String> entry : intervals.entrySet()) {
			turnovers.add(stat.getTurnoverByInterval(entry.getKey(), entry.getValue()));
		}
		
		model.addAttribute("turnover", turnovers);*/
		
		/*model.addAttribute("sales", stat.getSalesByInterval(i, quantize));
		model.addAttribute("purchases", null);
		model.addAttribute("profit", null);*/
		return "statistics";
	}

	/*
	 * @RequestMapping(value="/userManagement") public String userManagement(ModelMap model){ //model.addAttribute("customers", ); //model.addAttribute("admins",); //model.addAttribute("employees",); return "index";
	 */
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

		Competition com = new Competition(dataService.getConcreteUserAccoutnRepository().findByRole(Role.of("ROLE_CUSTOMER")), creditmanager);

		model.addAttribute("winners", com.getWinners());
		com.getWinners().forEach(x->System.out.println(x.getUserAccount().getUsername()+" "+x.getCredits()));
		com.notifyWinners(mailService);
		HashMap<String, String> msg = new HashMap<String, String>();
		msg.put("success", "Die folgenden Gewinner wurden benachrichtigt");
		msg.put("name", "Name");
		msg.put("credits", "Punktestand");
		model.addAttribute("competitionmessage", msg);
		return "competition";
	}

}