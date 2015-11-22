package internetkaufhaus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.salespointframework.SalespointSecurityConfiguration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.validation.*;
import internetkaufhaus.forms.*;
import internetkaufhaus.model.*;
import videoshop.model.CustomerRepository;

import org.salespointframework.useraccount.*;
import org.salespointframework.useraccount.web.LoggedIn;

import java.util.Optional;

import javax.validation.*;
import org.springframework.beans.factory.annotation.*;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController{
	private final ConcreteUserAccountManager manager;

	@Autowired
	public AdminController(ConcreteUserAccountManager manager){
		this.manager=manager;	
	}

	@RequestMapping(value="/employee")
	public String employees(ModelMap model){
		model.addAttribute(manager.)
		
	}
	
	
	/*@RequestMapping(value="/userManagement")
	public String userManagement(ModelMap model){
		//model.addAttribute("customers", );
		//model.addAttribute("admins",);
		//model.addAttribute("employees",);
	    return "index";*/
}