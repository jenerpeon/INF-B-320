package internetkaufhaus.controller;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

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
	
	@RequestMapping(value="/admin")
	public String admin() {
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

			

		return "redirect:/admin/changeuser";
	}
	
	@RequestMapping("/admin/changeuser/addUser")
	public String addArticle(Optional<UserAccount> userAccount) {
		return "changeusernewuser";
	}
	
	@RequestMapping(value="/admin/changeuser/detail/{id}")
	public String detailUser(@PathVariable("id") ConcreteUserAccount acc, ModelMap model) {
		model.addAttribute("account",acc);
		return "changeuserdetailuser";
	}
	
	@RequestMapping(value ="/admin/changeuser/addedUser", method=RequestMethod.POST)
	public String addedUser(@RequestParam(value="name") String username, @RequestParam(value="password") String password,
			@RequestParam(value="role") String role)
	{
		System.out.println(username);
		System.out.println(password);
		System.out.println(role);
		ConcreteUserAccount acc = new ConcreteUserAccount(username, password, Role.of(role), umanager ); 
		manager.save(acc);
		return "redirect:/admin/changeuser/";
	}
	
	@RequestMapping(value="/admin/changeuser/editUser/{id}")
	public String editUser(@PathVariable("id") ConcreteUserAccount acc, ModelMap model) {
		model.addAttribute("account",acc);
		return "changeuseredituser";
	}
	
	@RequestMapping(value="/admin/changeuser/editedUser/{id}", method=RequestMethod.POST)
	public String editedUserUser(@PathVariable("id") ConcreteUserAccount acc, @RequestParam(value="password") String password,
			@RequestParam(value="role") String role)
	{
		
		UserAccount usacc = acc.getUserAccount();
		usacc.remove(Role.of("ROLE_ADMIN"));
		usacc.remove(Role.of("ROLE_EMPLOYEE"));
		usacc.remove(Role.of("ROLE_CUSTOMER"));
		usacc.add(Role.of(role));
		umanager.save(usacc);
		acc.setUserAccount(usacc);
		acc.setRole(Role.of(role));
		umanager.changePassword(usacc, password);
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
