package internetkaufhaus.controller;

import javax.validation.Valid;

import org.salespointframework.SalespointSecurityConfiguration;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import internetkaufhaus.forms.RegistrationForm;
import internetkaufhaus.model.AccountAdministration;
import internetkaufhaus.model.ConcreteUserAccount;
import internetkaufhaus.model.ConcreteUserAccountRepository;

@Controller
public class AuthController extends SalespointSecurityConfiguration {

	private static final String LOGIN_ROUTE = "/login";
	private final UserAccountManager userAccountManager;
	private final ConcreteUserAccountRepository concreteUserAccountManager;
	private final AccountAdministration accountAdministration;
	ModelAndView modelAndView = new ModelAndView();

	@Autowired
	public AuthController(AccountAdministration accountAdministration, UserAccountManager userAccountManager, ConcreteUserAccountRepository concreteUserAccountManager, MailSender sender) {
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
	public String newPass(@RequestParam("email") String email, @RequestParam("password") String pass) {
		String key = this.accountAdministration.requestPass(pass, email);
		this.accountAdministration.PassValidation(key);
		return "redirect:/passreset";
	}

	@RequestMapping(value = "/NewPass/{key}")
	public String changepassword(@PathVariable("key") String key) {
		// boolean valid = this.accountAdministration.isValidKey(key);
		// if (!valid)
		// System.out.println("Your Key is not Valid");
		this.accountAdministration.verifyPass(key);// ByEmail("behrens_lars@gmx.de"
		// System.out.println(this.concreteUserAccountManager.findAll().toString());
		return "redirect:/#login-modal";
	}

	@RequestMapping(value = { "/register" })
	public String register() {
		return "redirect:/#registration-modal";
	}

	@RequestMapping("/registerNew")
	public ModelAndView registerNew(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm, BindingResult result, RedirectAttributes redir) {
		if (result.hasErrors()) {
			modelAndView.setViewName("redirect:/#registration-modal");
			redir.addFlashAttribute("message", result.getAllErrors());
			return modelAndView;
		}

		ConcreteUserAccount user = new ConcreteUserAccount(registrationForm.getEmail(), registrationForm.getName(), registrationForm.getFirstname(), registrationForm.getLastname(), registrationForm.getAddress(), registrationForm.getZipCode(), registrationForm.getCity(), registrationForm.getPassword(), Role.of("ROLE_CUSTOMER"), this.userAccountManager);
		concreteUserAccountManager.save(user);
		userAccountManager.save(user.getUserAccount());
		
		modelAndView.setViewName("redirect:/");
		return modelAndView;
	}
}
