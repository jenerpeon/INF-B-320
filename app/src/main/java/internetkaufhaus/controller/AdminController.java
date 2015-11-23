package internetkaufhaus.controller;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import internetkaufhaus.model.ConcreteUserAccount;
import internetkaufhaus.model.ConcreteUserAccountRepository;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController{
	private final ConcreteUserAccountRepository manager;

	@Autowired
	public AdminController(ConcreteUserAccountRepository manager){
		this.manager=manager;	

	}

	@RequestMapping(value="/employee")
	public String employees(ModelMap model){
		Role role = Role.of("ROLE_EMPLOYEE");
		model.addAttribute("employees",manager.findByRole(role));
		return "changeemployee";
		
	}
	@RequestMapping(value="/management/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") ConcreteUserAccount acc )
	{
//		UserAccount u = acc.getUserAccount();
//		manager.delete(acc);
		
		return "redirect:/changeemployee";
	}
	
	@RequestMapping(value="/customer")
	public String customers(ModelMap model){
		Role role= Role.of("ROLE_COSTUMER");
		model.addAttribute("customers", manager.findByRole(role));
	
		return "changecustomer";
	}
	
	
	
	
	/*@RequestMapping(value="/userManagement")
	public String userManagement(ModelMap model){
		//model.addAttribute("customers", );
		//model.addAttribute("admins",);
		//model.addAttribute("employees",);
	    return "index";*/
}
