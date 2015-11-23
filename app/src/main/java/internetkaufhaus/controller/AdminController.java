package internetkaufhaus.controller;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;

import java.util.ArrayList;
import java.util.Iterator;

import org.salespointframework.useraccount.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import internetkaufhaus.model.ConcreteUserAccount;
import internetkaufhaus.model.ConcreteUserAccountRepository;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController{
	private final ConcreteUserAccountRepository manager;
	private final UserAccountManager umanager;

	@Autowired
	public AdminController(ConcreteUserAccountRepository manager, UserAccountManager umanager){
		this.manager=manager;	
		this.umanager = umanager;

	}

	@RequestMapping(value="/employee")
	public String employees(ModelMap model){
		Role role = Role.of("ROLE_EMPLOYEE");
		model.addAttribute("employees",manager.findByRole(role));
		return "changeemployee";	
	}
	@RequestMapping(value="/admin")
	public String admins(ModelMap model){
		Role role = Role.of("ROLE_ADMIN");
		model.addAttribute("employees",manager.findByRole(role));
		return "changeemployee";
		
	}
	@RequestMapping(value="/customer")
	public String customers(ModelMap model){
		Role role= Role.of("ROLE_CUSTOMER");
		model.addAttribute("employees", manager.findByRole(role));
		return "changeemployee";
	}
	
	@RequestMapping(value="/management/deleteUser/{id}")
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
			if(acc.getRole().equals(Role.of("ROLE_ADMIN")))
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

			

		return "redirect:/employee";
	}
	

	@RequestMapping(value ="/management/addUser1", method=RequestMethod.POST)
	public String addUser(@RequestParam(value="name") String username, @RequestParam(value="password") String password,
			@RequestParam(value="role") String role)
	{
		ConcreteUserAccount acc = new ConcreteUserAccount(username, password, Role.of(role), umanager ); 
		manager.save(acc);
		return "redirect:/employee";
	}
	@RequestMapping(value ="/management/changeUserDisplay/{id}")
	public String changeUserDisplay(ModelMap model, @PathVariable("id") ConcreteUserAccount acc)
	{
		model.addAttribute("user", acc);
		return "/changeUserDisplay";
	}
	@RequestMapping(value="/management/changeUser", method=RequestMethod.POST)
	public String changeUser(@RequestParam(value="password") String password,
			@RequestParam(value="role") String roleName, @RequestParam(value="id") ConcreteUserAccount acc)
	{
		
		UserAccount usacc = acc.getUserAccount();
		usacc.remove(Role.of("ROLE_ADMIN"));
		usacc.remove(Role.of("ROLE_EMPLOYEE"));
		usacc.remove(Role.of("ROLE_CUSTOMER"));
		acc.getUserAccount().add(Role.of(roleName));
		umanager.save(usacc);
		acc.setUserAccount(usacc);
		acc.setRole(Role.of(roleName));
		umanager.changePassword(acc.getUserAccount(), password);
		return "redirect:/employee";
	}
	@RequestMapping(value="/management/addUser")
	public String addU()
	{
		return "addUser";
	}
	
	
	
	/*@RequestMapping(value="/userManagement")
	public String userManagement(ModelMap model){
		//model.addAttribute("customers", );
		//model.addAttribute("admins",);
		//model.addAttribute("employees",);
	    return "index";*/
}
