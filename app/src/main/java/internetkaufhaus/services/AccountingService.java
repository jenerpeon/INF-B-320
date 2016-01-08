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
import internetkaufhaus.forms.CreateUserForm;
import internetkaufhaus.forms.RegistrationForm;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountingService.
 */
@Service
public class AccountingService {

	/** The random. */
	private SecureRandom random;

	/** The key2email. */
	// Maps key to email. Used in Password reset and registration
	private Map<String, String> key2email;

	/** The email2pass. */
	// Maps emails to passwords. Used in Password reset
	private Map<String, String> email2pass;

	/** The recruit2invite. */
	// Maps recruits to invitators. Used in Registration with recruitation link
	private Map<String, String> recruit2invite;

	/** The mailsender. */
	@Autowired
	private ConcreteMailService mailsender;

	/** The data service. */
	@Autowired
	private DataService dataService;

	/**
	 * Instantiates a new accounting service.
	 */
	public AccountingService() {
		this.random = new SecureRandom();
		this.key2email = new HashMap<String, String>();
		this.email2pass = new HashMap<String, String>();
		this.recruit2invite = new HashMap<String, String>();
	}

	/**
	 * Adds the user.
	 *
	 * @param user
	 *            the user
	 * @return true, if successful
	 */
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

	/**
	 * Delete user.
	 *
	 * @param id
	 *            the id
	 * @return true, if successful
	 */
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

	/**
	 * Update user.
	 *
	 * @return true, if successful
	 */
	public boolean updateUser() {
		return true;
	}

	/**
	 * Register new user.
	 *
	 * @param regform
	 *            the regform
	 * @return true, if successful
	 */
	public boolean registerNew(RegistrationForm regform) {
		boolean success = true;
		ConcreteUserAccount user = null;
		if (regform == null) {
			success = false;
		}
		// Catch if Mail already registered
		if (success && dataService.getConcreteUserAccoutnRepository().findByEmail(regform.getEmail()) != null) {
			System.out.println("debug: Mail already registered");
			success = false;
		}
		if (success) {
			try {
				user = new ConcreteUserAccount(regform.getEmail(), regform.getName(), regform.getFirstname(),
						regform.getLastname(), regform.getAddress(), regform.getZipCode(), regform.getCity(),
						regform.getPassword(), Role.of("ROLE_CUSTOMER"), dataService.getUserAccountManager());
				this.addUser(user);
	
			} catch (Exception e) {
				System.out.println("AccountCreation failed: npe: " + e.toString());
				e.printStackTrace();
				success = false;
			}
			// Check Recrution
			if (success) {
				try {
					if (this.isRecruit(regform.getEmail())) {
						ConcreteUserAccount invitator = this.dataService.getConcreteUserAccoutnRepository()
								.findByEmail(recruit2invite.get(regform.getEmail()));
						invitator.setRecruits(user);
					}
				} catch (Exception e) {
					System.out.println("Recruitiong method failed: " + e.toString());
					e.printStackTrace();
					success = false;
				}
			}
		}
		// Register Customer
		if (success && !RegisterCustomer(regform.getEmail()))
			success = false;

		return success;
	}

	/**
	 * Register customer.
	 *
	 * @param email
	 *            the email
	 * @return true, if successful
	 */
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

	/**
	 * Recruit customer.
	 *
	 * @param invitator
	 *            the invitator
	 * @param recruit
	 *            the recruit
	 * @return the string
	 */
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

	/**
	 * Checks if is recruit.
	 *
	 * @param email
	 *            the email
	 * @return true, if is recruit
	 */
	public boolean isRecruit(String email) {
		if (this.recruit2invite.containsKey(email))
			return true;
		return false;
	}

	/**
	 * Checks if is registered.
	 *
	 * @param email
	 *            the email
	 * @return true, if is registered
	 */
	public boolean isRegistered(String email) {
		if (this.dataService.getConcreteUserAccoutnRepository().findByEmail(email) != null)
			return true;
		return false;
	}

	/**
	 * Request key.
	 *
	 * @param email
	 *            the email
	 * @return the string
	 */
	public String requestKey(String email) {
		String key = new BigInteger(120, random).toString(32);
		key2email.put(key, email);
		return key;
	}

	/**
	 * Pass validation.
	 *
	 * @param key
	 *            the key
	 */
	public void PassValidation(String key) {
		String validLink = "http://localhost:8080/NewPass/".concat(key);
		this.mailsender.sendMail(key2email.get(key), validLink, "unnecessaryfield", "Verify your changes");
	}

	/**
	 * Gets the recruit2invite.
	 *
	 * @return the recruit2invite
	 */
	public Map<String, String> getRecruit2invite() {
		return recruit2invite;
	}

	/**
	 * Sets the recruit2invite.
	 *
	 * @param recruit2invite
	 *            the recruit2invite
	 */
	public void setRecruit2invite(Map<String, String> recruit2invite) {
		this.recruit2invite = recruit2invite;
	}

	/**
	 * Checks if is valid key.
	 *
	 * @param key
	 *            the key
	 * @return true, if is valid key
	 */
	public boolean isValidKey(String key) {
		if (this.key2email.containsKey(key) && isRegistered(key2email.get(key)))
			return true;
		return false;
	}

	/**
	 * Removes the key.
	 *
	 * @param key
	 *            the key
	 */
	public void removeKey(String key) {
		this.email2pass.remove(this.key2email.get(key));
		this.key2email.remove(key);
	}

	/**
	 * Request pass.
	 *
	 * @param pass
	 *            the pass
	 * @param email
	 *            the email
	 * @return the string
	 */
	public String requestPass(String pass, String email) {
		this.email2pass.put(email, pass);
		return this.requestKey(email);
	}

	/**
	 * Verify pass.
	 *
	 * @param key
	 *            the key
	 */
	public void verifyPass(String key) {
		String email = this.key2email.get(key);
		this.dataService.getUserAccountManager().changePassword(
				dataService.getConcreteUserAccoutnRepository().findByEmail(email).getUserAccount(),
				email2pass.get(email));
		this.key2email.remove(key);
		this.email2pass.remove(email);

	}
}
