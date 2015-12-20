package internetkaufhaus.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.salespointframework.SalespointSecurityConfiguration;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.forms.RegistrationForm;
import internetkaufhaus.model.AccountAdministration;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

@Controller
public class AuthController extends SalespointSecurityConfiguration {

	private static final String LOGIN_ROUTE = "/login";
	private final UserAccountManager userAccountManager;
	private final ConcreteUserAccountRepository concreteUserAccountRepo;

	private final AccountAdministration accountAdministration;
	ModelAndView modelAndView = new ModelAndView();

	@Autowired
	public AuthController(AccountAdministration accountAdministration, UserAccountManager userAccountManager, ConcreteUserAccountRepository concreteUserAccountManager, MailSender sender) {
		this.userAccountManager = userAccountManager;
		this.concreteUserAccountRepo = concreteUserAccountManager;
		this.accountAdministration = accountAdministration;

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/**").permitAll().and().//
				formLogin().loginPage(LOGIN_ROUTE).loginProcessingUrl(LOGIN_ROUTE).and(). //
				logout().logoutUrl("/logout").logoutSuccessUrl("/");
	}

	@RequestMapping(value = "/login")
	public String loginRedirection() {
		return "redirect:/#login-modal";
	}

	@RequestMapping(value = "/passreset")
	public String resetPassword() {
		return "passreset";
	}

	@RequestMapping(value = "/NewPass", method = RequestMethod.POST)
	public String newPass(@RequestParam("email") String email, @RequestParam("password") String pass) {
		String key = this.accountAdministration.requestPass(pass, email);
		this.accountAdministration.PassValidation(key);
		return "redirect/#login-modal";// "redirect:/passreset";
	}

	@RequestMapping(value = "/NewPass/{key}")
	public String changepassword(@PathVariable("key") String key) {
		this.accountAdministration.verifyPass(key);
		return "redirect:/#login-modal";
	}

	@RequestMapping(value = { "/register" })
	public String register() {
		return "redirect:/#registration-modal";
	}

	@RequestMapping("/registerNew")
	public String registerNew(ModelMap modelmap, @ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm, BindingResult result, RedirectAttributes redir) {
		if (result.hasErrors()) {
			modelAndView.setViewName("redirect:/#registration-modal");
			redir.addFlashAttribute("message", result.getAllErrors());
			return "redirect:/#registration-modal";
		}

		/* catch if mail address allready taken */
		if (concreteUserAccountRepo.findByEmail(registrationForm.getEmail()) != null)
			return "redirect:/#login-modal";

		ConcreteUserAccount user = new ConcreteUserAccount(registrationForm.getEmail(), registrationForm.getName(), registrationForm.getFirstname(), registrationForm.getLastname(), registrationForm.getAddress(), registrationForm.getZipCode(), registrationForm.getCity(), registrationForm.getPassword(), Role.of("ROLE_CUSTOMER"), this.userAccountManager);
		userAccountManager.save(user.getUserAccount());
		concreteUserAccountRepo.save(user);
		// userAccountManager.save(user.getUserAccount());

		if (this.accountAdministration.isRecruit(registrationForm.getEmail())) {
			ConcreteUserAccount invitator = concreteUserAccountRepo.findByEmail(this.accountAdministration.getRecruit2invite().get(user.getEmail()));
			invitator.setRecruits(user);
		}

		this.accountAdministration.RegisterCustomer(user.getEmail());
		modelmap.addAttribute("info", "account has been generate. Check your Email to validate");

		return "index";
	}

	@RequestMapping(value = { "/recruit" })
	public String recruitUser(ModelMap modelmap, @RequestParam(value = "email") String recruit, @LoggedIn Optional<UserAccount> userAccount) {
		String invitator = "none@gmx.de";
		if (!(userAccount.get().getEmail() == null))
			invitator = userAccount.get().getEmail();
		String msg = this.accountAdministration.RecruitCustomer(recruit, invitator);

		modelmap.addAttribute("info", msg);
		return "/index";
	}

}
