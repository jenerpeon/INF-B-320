package internetkaufhaus.controller;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.web.LoggedIn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.salespointframework.useraccount.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import internetkaufhaus.forms.CreateUserForm;
import internetkaufhaus.forms.EditUserForm;
import internetkaufhaus.model.ConcreteUserAccount;
import internetkaufhaus.model.ConcreteUserAccountRepository;
import internetkaufhaus.model.UserManager;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController{
	private final ConcreteUserAccountRepository manager;
	private final UserAccountManager umanager;
	private final UserManager usermanager;

	@Autowired
	public AdminController(ConcreteUserAccountRepository manager, UserAccountManager umanager, UserManager user){
		this.manager=manager;	
		this.umanager = umanager;
		this.usermanager = user;

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
		usermanager.deleteUser(id);
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
	public String addedUser(@ModelAttribute("EditUserForm") @Valid CreateUserForm createuserform, BindingResult result)
	{
		if (result.hasErrors()) {
			return "redirect:/admin/changeuser/";
		}
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
		usermanager.changeUser(edituserform.getId(), edituserform.getRolename(), edituserform.getPassword());
		return "redirect:/admin/changeuser/";
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
