package internetkaufhaus.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.salespointframework.SalespointSecurityConfiguration;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import internetkaufhaus.forms.StandardUserForm;
import internetkaufhaus.services.AccountingService;
import internetkaufhaus.services.DataService;

// TODO: Auto-generated Javadoc
/**
 * This is the authentication controller. It controls the authentication
 * process. Or maybe it authenticates the controls? You never know... In this
 * class you may find the controllers for the authentication interfaces, should
 * you choose to look for them.
 * 
 * @author max
 *
 */
@Controller
@SessionAttributes("cart")
public class AuthController extends SalespointSecurityConfiguration {

	/** The Constant LOGIN_ROUTE. */
	private static final String LOGIN_ROUTE = "/login";
	
	/** The accounting service. */
	@Autowired
	private AccountingService accountingService;
	
	@Autowired
	private DataService data;
	
	/** The model and view. */
	private ModelAndView modelAndView = new ModelAndView();

	/* (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	// TODO: Javadoc
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/**").permitAll().and().//
				formLogin().loginPage(LOGIN_ROUTE).loginProcessingUrl(LOGIN_ROUTE).and(). //
				logout().logoutUrl("/logout").logoutSuccessUrl("/");
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page may redirect you if you aren't logged in but should be. Spring
	 * redirects you to /login so we redirect you to our login modal from there.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "/login")
	public String loginRedirection() {
		return "redirect:/#login-modal";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the password reset page.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "/passreset")
	public String resetPassword() {
		return "passreset";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page sends out an email with a link to reset your password.
	 *
	 * @param email            the email associated to the account which password should be
	 *            changed, is expected to be passed as a post parameter.
	 * @param pass            the new password the user wants to have
	 * @return the string
	 */
	@RequestMapping(value = "/NewPass", method = RequestMethod.POST)
	public String newPass(@RequestParam("email") String email, @RequestParam("password") String pass) {
		String key = this.accountingService.requestPass(pass, email);
		this.accountingService.PassValidation(key);
		return "redirect:/#login-modal";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page finalizes the password reset process. Its link gets passed via
	 * E-Mail to the user.
	 *
	 * @param key            a personal key which was generated earlier and should be
	 *            stored in db.
	 * @return the string
	 */
	@RequestMapping(value = "/NewPass/{key}")
	public String changepassword(@PathVariable("key") String key) {
		this.accountingService.verifyPass(key);
		return "redirect:/#login-modal";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page redirects users that may want to register and called the
	 * /register page to the registration modal.
	 *
	 * @return the string
	 */
	@RequestMapping(value = { "/register" })
	public String register() {
		return "redirect:/#registration-modal";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page accepts the registration form and registers a new user based on
	 * the entered credentials.
	 *
	 * @param registrationForm            the form object containing the user to register
	 * @param result            the result object (in)validating the form
	 * @param redir the redir
	 * @return the string
	 */
	@RequestMapping("/registerNew")
	public String registerNew(@ModelAttribute("registrationForm") @Valid StandardUserForm registrationForm,
			BindingResult result, RedirectAttributes redir) {
		if (data.getUserAccountManager().findByUsername(registrationForm.getName()).isPresent()) {
			ObjectError usernameError = new ObjectError("name", "Der Benutzername existiert bereits.");
			result.addError(usernameError);
		}
		if (data.getConcreteUserAccountRepository().findByEmail(registrationForm.getEmail()).isPresent()) {
			ObjectError emailError = new ObjectError("email", "Die E-Mail Adresse wird bereits verwendet.");
			result.addError(emailError);
		}
		if (result.hasErrors()) {
			modelAndView.setViewName("redirect:/#registration-modal");
			redir.addFlashAttribute("message", result.getAllErrors());
			return "redirect:/#registration-modal";
		}
		this.accountingService.registerNew(registrationForm);
		return "redirect:/";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the options to generate an invite link.
	 *
	 * @param modelmap the modelmap
	 * @param recruit the recruit
	 * @param userAccount the user account
	 * @return the string
	 */
	@RequestMapping(value = { "/recruit" })
	public String recruitUser(ModelMap modelmap, @RequestParam(value = "email") String recruit,
			@LoggedIn Optional<UserAccount> userAccount) {
		modelmap.addAttribute("info", this.accountingService.recruitCustomer(userAccount, recruit));
		return "redirect:/";
	}
}