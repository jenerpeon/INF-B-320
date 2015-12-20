package internetkaufhaus.controller;

import java.util.Optional;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import internetkaufhaus.model.Creditmanager;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

@Controller
@PreAuthorize("hasRole('ROLE_CUSTOMER')")

public class CustomerController {
	private final Creditmanager creditmanager;
	private final ConcreteUserAccountRepository userRepo;

	@Autowired
	public CustomerController(Creditmanager creditmanager, ConcreteUserAccountRepository userRepo) {
		this.creditmanager = creditmanager;

		this.userRepo = userRepo;
	}

	@RequestMapping("/customer")
	public String customer(@LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		creditmanager.udateCreditpointsByUser(userRepo.findByUserAccount(userAccount.get()));
		model.addAttribute("points", userRepo.findByUserAccount(userAccount.get()).getCredits());
		model.addAttribute("account", userAccount.get());
		return "points";
	}
}