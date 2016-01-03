package internetkaufhaus.services;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.forms.RegistrationForm;

@Service
public class AccountingService {

	private SecureRandom random;
	// Maps key to email. Used in Password reset and registration
	private Map<String, String> key2email;
	// Maps emails to passwords. Used in Password reset
	private Map<String, String> email2pass;
	// Maps recruits to invitators. Used in Registration with recruitation link
	private Map<String, String> recruit2invite;

	@Autowired
	private ConcreteMailService mailsender;
	@Autowired
	private DataService dataService;

	public AccountingService() {
		this.random = new SecureRandom();
		this.key2email = new HashMap<String, String>();
		this.email2pass = new HashMap<String, String>();
		this.recruit2invite = new HashMap<String, String>();
	}

	public boolean addUser(ConcreteUserAccount user) {
		try {
			dataService.getUserAccountManager().save(user.getUserAccount());
			dataService.getConcreteUserAccoutnRepository().save(user);
		} catch (Exception e) {
			System.out.println("Adding userAccount failed." + e.toString());
			return false;
		}
		return true;
	}

	public boolean deleteUser(Long id) {
		try {
			dataService.getUserAccountManager().disable(
					dataService.getConcreteUserAccoutnRepository().findOne(id).getUserAccount().getIdentifier());
			dataService.getConcreteUserAccoutnRepository().delete(id);
		} catch (Exception e) {
			System.out.println("deleting User Failed:" + e.toString());
			return false;
		}
		return true;
	}

	public boolean updateUser() {
		return true;
	}

	public boolean registerNew(RegistrationForm regform) {
		ConcreteUserAccount user;
		if (regform == null) {
			return false;
		}
		// Catch if Mail allready registered
		if (dataService.getConcreteUserAccoutnRepository().findByEmail(regform.getEmail()) != null) {
			System.out.println("debug: Mail allredy registered");
			return false;
		}
		try {
			user = new ConcreteUserAccount(regform.getEmail(), regform.getName(), regform.getFirstname(),
					regform.getLastname(), regform.getAddress(), regform.getZipCode(), regform.getCity(),
					regform.getPassword(), Role.of("ROLE_CUSTOMER"), dataService.getUserAccountManager());
			this.addUser(user);

		} catch (Exception e) {
			System.out.println("AccountCreation failed: npe: " + e.toString());
			return false;
		}
		// Check Recrution
		try {
			if (this.isRecruit(regform.getEmail())) {
				ConcreteUserAccount invitator = this.dataService.getConcreteUserAccoutnRepository()
						.findByEmail(recruit2invite.get(regform.getEmail()));
				invitator.setRecruits(user);
			}
		} catch (Exception e) {
			System.out.println("Recruitiong method failed" + e.toString());
			return false;
		}
		// Register Customer
		if (!RegisterCustomer(regform.getEmail()))
			return false;

		return true;
	}

	public boolean RegisterCustomer(String email) {
		if (dataService.getConcreteUserAccoutnRepository().findByEmail(email) == null) {
			System.out.println("Customer Allready registered with this mail");
			return false;
		}
		String invitation = "http://localhost:8080/login";
		this.mailsender.sendMail(email, "You have been sucessful registered. Click here" + invitation + " to login.",
				"unnecessaryfield", "Shop Now!");
		return true;
	}

	public String RecruitCustomer(Optional<UserAccount> invitator, String recruit) {
		if (invitator.get() == null) {
			return "invalid invitator";
		}
		String invitatorEmail = invitator.get().getEmail();
		if (this.isRegistered(recruit)) {
			return "your friend is already a member";
		}
		String invitation = "http://localhost:8080/register";
		this.mailsender.sendMail(recruit, invitator + " recruited you. Join now! " + invitation, "unnecessaryfield",
				"Click to join");
		this.recruit2invite.put(recruit, invitatorEmail);
		return "your friend got an invitation link";
	}

	public boolean isRecruit(String email) {
		if (this.recruit2invite.containsKey(email))
			return true;
		return false;
	}

	public boolean isRegistered(String email) {
		if (this.dataService.getConcreteUserAccoutnRepository().findByEmail(email) != null)
			return true;
		return false;
	}

	public String requestKey(String email) {
		String key = new BigInteger(120, random).toString(32);
		key2email.put(key, email);
		return key;
	}

	public void PassValidation(String key) {
		String validLink = "http://localhost:8080/NewPass/".concat(key);
		this.mailsender.sendMail(key2email.get(key), validLink, "unnecessaryfield", "Verify your changes");
	}

	public Map<String, String> getRecruit2invite() {
		return recruit2invite;
	}

	public void setRecruit2invite(Map<String, String> recruit2invite) {
		this.recruit2invite = recruit2invite;
	}

	public boolean isValidKey(String key) {
		if (this.key2email.containsKey(key) && isRegistered(key2email.get(key)))
			return true;
		return false;
	}

	public void removeKey(String key) {
		this.email2pass.remove(this.key2email.get(key));
		this.key2email.remove(key);
	}

	public String requestPass(String pass, String email) {
		this.email2pass.put(email, pass);
		return this.requestKey(email);
	}

	public void verifyPass(String key) {
		String email = this.key2email.get(key);
		this.dataService.getUserAccountManager().changePassword(
				dataService.getConcreteUserAccoutnRepository().findByEmail(email).getUserAccount(),
				email2pass.get(email));
		this.key2email.remove(key);
		this.email2pass.remove(email);

	}
}
