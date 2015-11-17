package internetkaufhaus.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.salespointframework.SalespointSecurityConfiguration;
import org.salespointframework.SalespointWebConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.validation.*;
import internetkaufhaus.forms.*;
import internetkaufhaus.model.*;
import org.salespointframework.useraccount.*; 
import javax.validation.*;
import org.springframework.beans.factory.annotation.*;

@Controller
public class AuthController {

    private static final String LOGIN_ROUTE = "/login";
	private final UserAccountManager userAccountManager;
	
	@Autowired
    public AuthController(UserAccountManager userAccountManager){
		this.userAccountManager = userAccountManager;
	}

	@Configuration
	static class PeonWebConfiguration extends SalespointWebConfiguration {

		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController(LOGIN_ROUTE).setViewName("login");
		}
	}
	@Configuration
	static class WebSecurityConfiguration extends SalespointSecurityConfiguration {

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.csrf().disable();

			http.authorizeRequests().antMatchers("/**").permitAll().and().//
					formLogin().loginPage(LOGIN_ROUTE).loginProcessingUrl(LOGIN_ROUTE).and(). //
					logout().logoutUrl("/logout").logoutSuccessUrl("/");
		}
	}

	@RequestMapping(value={"/register"})
	public String register(){
	    return "register";
	}

	@RequestMapping("/registerNew")
	public String registerNew(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm,	BindingResult result) {
		if (result.hasErrors()) {
			return "register";
		}

		UserAccount customer = userAccountManager.create(registrationForm.getName(), registrationForm.getPassword(), Role.of("CUSTOMER"));
		userAccountManager.save(customer);

		return "redirect:/";
    }
}
