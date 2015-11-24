package internetkaufhaus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.salespointframework.SalespointSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.validation.*;
import internetkaufhaus.forms.*;
import internetkaufhaus.model.*;
import org.salespointframework.useraccount.*;
import org.salespointframework.useraccount.web.LoggedIn;

import java.util.Optional;

import javax.validation.*;
import org.springframework.beans.factory.annotation.*;


public class AdminController{

//	@Autowired
	public AdminController(){
		
	}
	
	@RequestMapping(value="/userManagement")
	public String userManagement(ModelMap model){
		//model.addAttribute("customers", );
		//model.addAttribute("admins",);
		//model.addAttribute("employees",);
	    return "index";
	}
	
}