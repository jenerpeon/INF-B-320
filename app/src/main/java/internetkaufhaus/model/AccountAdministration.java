package internetkaufhaus.model;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.OneToOne;

import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

import internetkaufhaus.repositories.ConcreteUserAccountRepository;
import internetkaufhaus.services.ConcreteMailService;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountAdministration.
 */
@Component
public class AccountAdministration {

	/**
	 * The AccountAdministration class is responsible for creating new costumer
	 * accounts.
	 *
	 */
	private ConcreteMailService concreteMailService;

	/** The random. */
	private SecureRandom random;

	/** Maps key to email. Used in Password reset and registration */
	private Map<String, String> key2email;

	/** Maps emails to passwords. Used in Password reset */
	private Map<String, String> email2pass;

	/**
	 * Maps recruits to invitators. Used in Registration with recruitation link
	 */
	private Map<String, String> recruit2invite;

	/** The Concrete user account manager. */
	@OneToOne
	private ConcreteUserAccountRepository ConcreteUserAccountManager;

	/** The user account manager. */
	@OneToOne
	private UserAccountManager userAccountManager;

	/**
	 * Instantiates a new account administration.
	 */
	public AccountAdministration() {
		this.random = new SecureRandom();
		this.key2email = new HashMap<String, String>();
		this.email2pass = new HashMap<String, String>();
		this.recruit2invite = new HashMap<String, String>();
	}

	/**
	 * The RegisterCustomer method will send a mail to new registered costumers
	 * with an activation link.
	 *
	 * @param email
	 *            email address of costumer
	 * @exception MailException
	 *                the mail exception
	 */

	public void RegisterCustomer(String email) {
		if (this.isRegistered(email)) {
			System.out.print("");
		}
		String invitation = "http://localhost:8080/login";
		this.concreteMailService.sendMail(email,
				"You have been sucessful registered. Click here" + invitation + " to login.", "unnecessaryfield",
				"Shop Now!");
	}

	/**
	 * The RecruitCustomer method will send a mail to a recruit costumer. Names
	 * of recruit and invitator will be saved at the invitator account.
	 *
	 * @param recruit
	 *            name of invitator
	 * @param invitator
	 *            the invitator
	 * @return the string
	 */
	public String RecruitCustomer(String recruit, String invitator) {
		if (this.isRegistered(recruit)) {
			return "your friend is already a member";
		}
		String invitation = "http://localhost:8080/register";
		this.concreteMailService.sendMail(recruit, invitator + " recruited you. Join now! " + invitation,
				"unnecessaryfield", "Click to join");
		this.recruit2invite.put(recruit, invitator);
		return "your friend got an invitation link";
	}

	/**
	 * The isRecruit method checks if an user is already a recruit.
	 * 
	 * @param email
	 *            email of user
	 * 
	 * @return <tt>true</tt> if user is already a recruit
	 *
	 */
	public boolean isRecruit(String email) {
		if (this.recruit2invite.containsKey(email))
			return true;
		return false;
	}

	/**
	 * The isRegistered method checks if an user is already registered.
	 * 
	 * @param email
	 *            email of user
	 * 
	 * @return <tt>true</tt> if user is already registered.
	 *
	 */
	public boolean isRegistered(String email) {
		if (ConcreteUserAccountManager.findByEmail(email) != null)
			return true;
		return false;
	}

	/**
	 * The requestKey method generates a random number and save it with given
	 * email in a HashMap.
	 * 
	 * @param email
	 *            email of user
	 * 
	 * @return key the randomly generated number as String
	 *
	 */
	public String requestKey(String email) {
		String key = new BigInteger(120, random).toString(32);
		key2email.put(key, email);
		return key;
	}

	/**
	 * The PassValidation method sends an account activation mail to the user
	 * mail which is connected to the given key.
	 * 
	 * @param key
	 *            randomly generated activation key
	 *
	 */
	public void PassValidation(String key) {
		String validLink = "http://localhost:8080/NewPass/".concat(key);
		this.concreteMailService.sendMail(key2email.get(key), validLink, "unnecessaryfield", "Verify your changes");
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
	 * Sets the concrete user account manager.
	 *
	 * @param concreteUserAccountManager
	 *            the new concrete user account manager
	 */
	public void setConcreteUserAccountManager(ConcreteUserAccountRepository concreteUserAccountManager) {
		this.ConcreteUserAccountManager = concreteUserAccountManager;
	}

	/**
	 * Sets the user account manager.
	 *
	 * @param userAccountManager
	 *            the new user account manager
	 */
	public void setUserAccountManager(UserAccountManager userAccountManager) {
		this.userAccountManager = userAccountManager;
	}

	/**
	 * The isValidKey method checks if a key is valid/ already created and the
	 * user account is registered.
	 * 
	 * @param key
	 *            randomly generated activation key of existing user account
	 * 
	 * @return <tt>true</tt> if key is valid.
	 *
	 */
	public boolean isValidKey(String key) {
		if (this.key2email.containsKey(key) && isRegistered(key2email.get(key)))
			return true;
		return false;
	}

	/**
	 * The removeKey method removes the randomly generated key from all HashMaps
	 * if it exists.
	 * 
	 * @param key
	 *            randomly generated activation key
	 *
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
	 * The verifyPass method changes the password of an existing user account.
	 * 
	 * @param key
	 *            randomly generated activation key
	 *
	 */
	public void verifyPass(String key) {
		String email = this.key2email.get(key);
		this.userAccountManager.changePassword(ConcreteUserAccountManager.findByEmail(email).get().getUserAccount(),
				email2pass.get(email));
		this.key2email.remove(key);
		this.email2pass.remove(email);

	}

	/**
	 * Sets the mail sender.
	 *
	 * @param sender
	 *            the new mail sender
	 */
	public void setMailSender(MailSender sender) {
		this.concreteMailService = new ConcreteMailService(sender);
	}

}
