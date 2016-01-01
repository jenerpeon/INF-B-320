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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import internetkaufhaus.forms.RegistrationForm;
import internetkaufhaus.services.AccountingService;

@Controller
public class AuthController extends SalespointSecurityConfiguration {

	private static final String LOGIN_ROUTE = "/login";
	private final AccountingService accountingService;

	private ModelAndView modelAndView = new ModelAndView();

	@Autowired
	public AuthController(AccountingService accountingService) {
		this.accountingService = accountingService;

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
		String key = this.accountingService.requestPass(pass, email);
		this.accountingService.PassValidation(key);
		return "redirect:/#login-modal";
	}

	@RequestMapping(value = "/NewPass/{key}")
	public String changepassword(@PathVariable("key") String key) {
		this.accountingService.verifyPass(key);
		return "redirect:/#login-modal";
	}

	@RequestMapping(value = { "/register" })
	public String register() {
		return "redirect:/#registration-modal";
	}

	@RequestMapping("/registerNew")
	public String registerNew(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm,
			BindingResult result, RedirectAttributes redir) {
		if (result.hasErrors()) {
			modelAndView.setViewName("redirect:/#registration-modal");
			redir.addFlashAttribute("message", result.getAllErrors());
			return "redirect:/#registration-modal";
		}
		this.accountingService.registerNew(registrationForm);
		return "index";
	}

	@RequestMapping(value = { "/recruit" })
	public String recruitUser(ModelMap modelmap, @RequestParam(value = "email") String recruit,
			@LoggedIn Optional<UserAccount> userAccount) {
		modelmap.addAttribute("info",this.accountingService.RecruitCustomer(userAccount, recruit));
		return "/index";
	}

}