package internetkaufhaus.controller;

import org.springframework.stereotype.Controller;
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
import org.springframework.mail.MailSender;

@Controller
public class AuthController extends SalespointSecurityConfiguration {

	private static final String LOGIN_ROUTE = "/login";
	private final UserAccountManager userAccountManager;
	private final ConcreteUserAccountRepository concreteUserAccountManager;
	private final AccountAdministration accountAdministration;

	@Autowired
	public AuthController(AccountAdministration accountAdministration, UserAccountManager userAccountManager,
			ConcreteUserAccountRepository concreteUserAccountManager, MailSender sender) {
		this.userAccountManager = userAccountManager;
		this.concreteUserAccountManager = concreteUserAccountManager;
		this.accountAdministration = accountAdministration;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/**").permitAll().and().//
				formLogin().loginPage(LOGIN_ROUTE).loginProcessingUrl(LOGIN_ROUTE).and(). //
				logout().logoutUrl("/logout").logoutSuccessUrl("/");
	}

	@RequestMapping(value = "/passreset")
	public String resetPassword() {
		return "passreset";
	}

	@RequestMapping(value = "/NewPass", method = RequestMethod.POST)
	public String newPass(@RequestParam("email") String email, @RequestParam("password") String pass, @LoggedIn Optional<UserAccount> userAccount) {
		String key = this.accountAdministration.requestPass(pass, userAccount.get());
		this.accountAdministration.PassValidation(key);
		return "redirect:/login";
	}

	@RequestMapping(value = "/NewPass/{key}")
	public String changepassword(@PathVariable("key") String key) {
		boolean valid = this.accountAdministration.VerifyKey(key);
		if (!valid)
			System.out.println("Your Key is not Valid");
		this.accountAdministration.verifyPass(key);

		return "redirect:/login";
	}

	@RequestMapping(value = { "/register" })
	public String register() {
		return "register";
	}

	@RequestMapping("/registerNew")
	public String registerNew(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm,
			BindingResult result) {
		if (result.hasErrors()) {
			return "register";
		}

		ConcreteUserAccount user = new ConcreteUserAccount(registrationForm.getName(), registrationForm.getPassword(),
				Role.of("ROLE_CUSTOMER"), userAccountManager);
		concreteUserAccountManager.save(user);
		userAccountManager.save(user.getUserAccount());

		return "redirect:/";
	}
}
