package internetkaufhaus.controller;

import static org.salespointframework.core.Currencies.EURO;

import java.util.Optional;

import javax.validation.Valid;

import org.javamoney.moneta.Money;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
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
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

/**
 * This is the admin controller. It controls the admin. Or maybe it admins the controls? You never know... In this class you may find the controllers for the admin interfaces, should you choose to look for them.
 * 
 * @author max
 *
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
	private final ConcreteUserAccountRepository manager;

	private final ConcreteOrderRepository concreteOrderRepo;
	private final UserAccountManager umanager;
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
	public AdminController(ConcreteOrderRepository concreteOrderRepo, ConcreteUserAccountRepository manager, UserAccountManager umanager, Creditmanager creditmanager, NewUserAccountForm form) {

		this.manager = manager;
		this.umanager = umanager;
		this.concreteOrderRepo = concreteOrderRepo;

		this.form = form;

		this.creditmanager = creditmanager;

	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param userAccount
	 * @param model
	 * @return
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
	 * @return
	 */
	@RequestMapping(value = "/admin/changeuser")
	public String changeUser(ModelMap model) {
		Role roleCustomer = Role.of("ROLE_CUSTOMER");
		Role roleAdmin = Role.of("ROLE_ADMIN");
		Role roleEmployee = Role.of("ROLE_EMPLOYEE");
		model.addAttribute("employees", manager.findByRole(roleEmployee));
		model.addAttribute("customers", manager.findByRole(roleCustomer));
		model.addAttribute("admins", manager.findByRole(roleAdmin));
		return "changeuser";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/changeuser/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") Long id) {

		umanager.disable((manager.findOne(id).getUserAccount().getId()));
		manager.delete(id);

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
		Iterable<ConcreteOrder> ordersCompleted = concreteOrderRepo.findByStatus(OrderStatus.COMPLETED);
		Iterable<ConcreteOrder> ordersOpen = concreteOrderRepo.findByStatus(OrderStatus.OPEN);

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
	public String statistics() {
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

		Competition com = new Competition(manager.findByRole(Role.of("ROLE_CUSTOMER")), creditmanager);

		model.addAttribute("winners", com.getWinners());
		return "competition";
	}

}