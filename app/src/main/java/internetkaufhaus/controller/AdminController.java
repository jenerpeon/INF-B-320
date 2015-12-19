package internetkaufhaus.controller;
import static org.salespointframework.core.Currencies.EURO;

import java.util.HashMap;
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
import internetkaufhaus.model.ConcreteMailSender;
import internetkaufhaus.model.Creditmanager;
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;


@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController{
	private final ConcreteUserAccountRepository manager;

	private final ConcreteOrderRepository concreteOrderRepo;
	private final UserAccountManager umanager;
	private final NewUserAccountForm form;


	private final Creditmanager creditmanager;


	private final ConcreteMailSender sender;
	@Autowired

	public AdminController(ConcreteOrderRepository concreteOrderRepo, ConcreteUserAccountRepository manager, UserAccountManager umanager, OrderManager<Order> orderManager, ConcreteMailSender sender,
							Creditmanager creditmanager, NewUserAccountForm form){

		this.manager=manager;
		this.umanager=umanager;
		this.concreteOrderRepo = concreteOrderRepo;

		this.form=form;

		this.sender = sender;
		this.creditmanager = creditmanager;

	}

	
	@RequestMapping("/admin")
	public String adminStart(@LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		model.addAttribute("account", userAccount.get());
		return "admin";
	}
	
	@RequestMapping(value="/admin/changeuser")
	public String changeUser(ModelMap model){
		Role roleCustomer= Role.of("ROLE_CUSTOMER");
		Role roleAdmin = Role.of("ROLE_ADMIN");
		Role roleEmployee = Role.of("ROLE_EMPLOYEE");
		model.addAttribute("employees",manager.findByRole(roleEmployee));
		model.addAttribute("customers",manager.findByRole(roleCustomer));
		model.addAttribute("admins",manager.findByRole(roleAdmin));
		return "changeuser";	
	}
	
	@RequestMapping(value="/admin/changeuser/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") Long id)
	{	

		umanager.disable((manager.findOne(id).getUserAccount().getId()));
		manager.delete(id);
		
		
		return "redirect:/admin/changeuser";
	}
	
	@RequestMapping(value="/admin/changeuser/detail/{id}")
	public String detailUser(@PathVariable("id") ConcreteUserAccount acc, ModelMap model) {
		model.addAttribute("account",acc);
		return "changeuserdetailuser";
	}
	
	@RequestMapping("/admin/changeuser/addUser")
	public String addArticle(Optional<UserAccount> userAccount) {
		return "changeusernewuser";
	}
	
	@RequestMapping(value ="/admin/changeuser/addedUser", method=RequestMethod.POST)
	public String addedUser(@ModelAttribute("CreateUserForm") @Valid CreateUserForm createuserform, BindingResult result, ModelMap model)
	{
		if (result.hasErrors()) {
			model.addAttribute("message", result.getAllErrors());
			return "changeusernewuser";
		}
		form.createUser(createuserform);
		return "redirect:/admin/changeuser/";
	}
	
	@RequestMapping(value="/admin/changeuser/editUser/{id}")
	public String editUser(@PathVariable("id") ConcreteUserAccount acc, ModelMap model) {
		model.addAttribute("account",acc);
		return "changeuseredituser";
	}
	
	@RequestMapping(value="/admin/changeuser/editedUser", method=RequestMethod.POST)
	public String editedUserUser(@ModelAttribute("EditUserForm") @Valid EditUserForm edituserform, BindingResult result)
	{
		if (result.hasErrors()) {
			return "redirect:/admin/changeuser/";
		}
		form.changeUser(edituserform.getId(), edituserform.getRolename(), edituserform.getPassword());
		return "redirect:/admin/changeuser/";
	}
	@RequestMapping(value="/admin/changeuser/displayUser/{id}")
	public String displayUser(@PathVariable("id") ConcreteUserAccount acc, ModelMap model) {
		model.addAttribute("account",acc);
		return "changeUserDisplay";
	}
	
	@RequestMapping(value="/admin/balance")
	public String balance(ModelMap model)

	{
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
		
		model.addAttribute("customerOrders",ordersCompleted);
		model.addAttribute("StockOrders",ordersOpen);
		model.addAttribute("customerOrdersTotal",Money.of(Math.round(totalPaid*100)/100, EURO));
		model.addAttribute("StockOrdersTotal",Money.of(Math.round(totalOpen), EURO));
		model.addAttribute("balance",Money.of(balance, EURO));
		return "balance";
	}
	

	@RequestMapping(value="/admin/statistics")
	public String statistics(ModelMap model)
	{
		return "statistics";
	}
	/*@RequestMapping(value="/userManagement")
	public String userManagement(ModelMap model){
		//model.addAttribute("customers", );
		//model.addAttribute("admins",);
		//model.addAttribute("employees",);
	    return "index";*/
	@RequestMapping(value="/admin/lottery")
	public String competition(ModelMap model)
	{
		
		return "competition";
	}
	@RequestMapping(value="/admin/competitionButton")
	public String getWinners(ModelMap model)
	{
		
		Competition com = new Competition(manager.findByRole(Role.of("ROLE_CUSTOMER")), creditmanager);
		
		model.addAttribute("winners", com.getWinners());
		com.getWinners().forEach(x->System.out.println(x.getUserAccount().getUsername()+" "+x.getCredits()));
		com.notifyWinners(sender);
		HashMap<String, String> msg = new HashMap<String, String>();
		msg.put("success", "Die folgenden Gewinner wurden benachrichtigt");
		msg.put("name", "Name");
		msg.put("credits", "Punktestand");
		model.addAttribute("competitionmessage", msg);
		return "competition";
	}

}