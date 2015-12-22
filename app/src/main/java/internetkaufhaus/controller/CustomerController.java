package internetkaufhaus.controller;

import java.util.Optional;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import internetkaufhaus.model.Creditmanager;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

/**
 * This is the customer controller. It controlls the customer. Or does it customize the controller? You never know...
 * In this class you may find the controllers for the profile page and similar stuff, should you choose to look for it.
 * 
 * @author max
 *
 */
@Controller
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class CustomerController {
	private final Creditmanager creditmanager;
	private final ConcreteUserAccountRepository userRepo;

	/**
	 * This is the constructor. It's neither used nor does it contain any functionality other than storing function arguments as class attribute, what do you expect me to write here?
	 * 
	 * @param creditmanager
	 * @param userRepo
	 */
	@Autowired
	public CustomerController(Creditmanager creditmanager, ConcreteUserAccountRepository userRepo) {
		this.creditmanager = creditmanager;

		this.userRepo = userRepo;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param userAccount
	 * @param model
	 * @return
	 */
	@RequestMapping("/customer")
	public String customer(@LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		creditmanager.updateCreditpointsByUser(userRepo.findByUserAccount(userAccount.get()));
		model.addAttribute("points", userRepo.findByUserAccount(userAccount.get()).getCredits());
		model.addAttribute("account", userAccount.get());
		return "points";
	}
}