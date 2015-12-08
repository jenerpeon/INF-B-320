package internetkaufhaus.controller;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.web.LoggedIn;

import static org.salespointframework.core.Currencies.EURO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.IteratorUtils;
import org.javamoney.moneta.Money;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.useraccount.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController{
	private final ConcreteUserAccountRepository manager;
	private final UserAccountManager umanager;
	private final OrderManager<ConcreteOrder> orderManager;

	@Autowired
	public AdminController(ConcreteUserAccountRepository manager, UserAccountManager umanager, OrderManager<ConcreteOrder> orderManager){
		this.manager=manager;	
		this.umanager = umanager;
		this.orderManager = orderManager;

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
	public String deleteUser(@PathVariable("id") ConcreteUserAccount acc )
	{
		int remaining = 0;
		if(acc!=null)
		{
			System.out.println("ja");
		}
			
		if(acc!=null)
		{
			Iterator <ConcreteUserAccount> iter = manager.findByRole(Role.of("ROLE_ADMIN")).iterator();
//			ArrayList<ConcreteUserAccount> accli = new ArrayList<ConcreteUserAccount>();
			if(acc.getUserAccount().getRoles().iterator().next().equals(Role.of("ROLE_ADMIN")))
			{
				while(iter.hasNext())
				{
				iter.next();
				remaining++ ;
				System.out.println(remaining);
				}
				if(remaining > 1)
				{
					manager.delete(acc);
				}
				else
				{
					System.out.println("kein admin");
				}
			}
			else
			{
				manager.delete(acc);
			}
		}

			

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
	public String addedUser(@RequestParam(value="name") String username, @RequestParam(value="password") String password,
			@RequestParam(value="role") String role)
	{
		ConcreteUserAccount acc = new ConcreteUserAccount(username, password, Role.of(role), umanager ); 
		manager.save(acc);
		umanager.save(acc.getUserAccount());
		return "redirect:/admin/changeuser/";
	}
	
	@RequestMapping(value="/admin/changeuser/editUser/{id}")
	public String editUser(@PathVariable("id") ConcreteUserAccount acc, ModelMap model) {
		model.addAttribute("account",acc);
		return "changeuseredituser";
	}
	
	@RequestMapping(value="/admin/changeuser/editedUser", method=RequestMethod.POST)
	public String editedUserUser(@RequestParam(value="id") ConcreteUserAccount acc, @RequestParam(value="password") String password,
			@RequestParam(value="role") String role)
	{
		
		UserAccount usacc = acc.getUserAccount();
		usacc.remove(Role.of("ROLE_ADMIN"));
		usacc.remove(Role.of("ROLE_EMPLOYEE"));
		usacc.remove(Role.of("ROLE_CUSTOMER"));
		usacc.add(Role.of(role));
		umanager.save(usacc);
		acc.setUserAccount(usacc);
		//acc.getUserAccount().add(Role.of(role));
		//acc.setRole(Role.of(role));
		umanager.changePassword(usacc, password);
		return "redirect:/admin/changeuser/";
	}
	
	@RequestMapping(value="/admin/balance")
	public String balance(ModelMap model)
	{
		
		Collection<ConcreteOrder> ordersCompleted = IteratorUtils.toList(orderManager.findBy(OrderStatus.COMPLETED).iterator());
		Collection<ConcreteOrder> ordersOpen = IteratorUtils.toList(orderManager.findBy(OrderStatus.OPEN).iterator());
		
		double totalPaid = 0;
		for (ConcreteOrder order : ordersCompleted) {
			if (order.getReturned() == false) {
				totalPaid += order.getTotalPrice().getNumberStripped().doubleValue();
			}
		}
		
		double totalOpen = 0;
		for (ConcreteOrder order : ordersOpen) {
			totalOpen += order.getTotalPrice().getNumberStripped().doubleValue();
		}
		
		double balance = Math.round((totalPaid - totalOpen) * 100.00) / 100.00;
		
		model.addAttribute("customerOrders",ordersCompleted);
		model.addAttribute("StockOrders",ordersOpen);
		model.addAttribute("customerOrdersTotal",Money.of(totalPaid, EURO));
		model.addAttribute("StockOrdersTotal",Money.of(totalOpen, EURO));
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
}
