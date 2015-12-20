package internetkaufhaus.model;

import java.math.BigInteger;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.OneToOne;

import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

import internetkaufhaus.repositories.ConcreteUserAccountRepository;

@Component
public class AccountAdministration {
	private ConcreteMailSender concreteSender;
	private SecureRandom random;
	// Maps key to email. Used in Password reset and registration
	private Map<String, String> key2email;
	// Maps emails to passwords. Used in Password reset
	private Map<String, String> email2pass;
	// Maps recruits to invitators. Used in Registration with recruitation link
	private Map<String, String> recruit2invite;

	@OneToOne
	private ConcreteUserAccountRepository ConcreteUserAccountManager;
	@OneToOne
	private UserAccountManager userAccountManager;

	public AccountAdministration() {
		this.random = new SecureRandom();
		this.key2email = new HashMap<String, String>();
		this.email2pass = new HashMap<String, String>();
		this.recruit2invite = new HashMap<String, String>();
	}

	public void RegisterCustomer(String email) {
		if (this.isRegistered(email)) {
			System.out.print("");
		}
		String invitation = "http://localhost:8080/login";
		this.concreteSender.sendMail(email, "You have been sucessful registered. Click here" + invitation + " to login.", "unnecessaryfield", "Shop Now!");
	}

	public String RecruitCustomer(String recruit, String invitator) {
		if (this.isRegistered(recruit)) {
			return "your friend is already a member";
		}
		String invitation = "http://localhost:8080/register";
		this.concreteSender.sendMail(recruit, invitator + " recruited you. Join now! " + invitation, "unnecessaryfield", "Click to join");
		this.recruit2invite.put(recruit, invitator);
		return "your friend got an invitation link";
	}

	public boolean isRecruit(String email) {
		if (this.recruit2invite.containsKey(email))
			return true;
		return false;
	}

	public boolean isRegistered(String email) {
		if (ConcreteUserAccountManager.findByEmail(email) != null)
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
		this.concreteSender.sendMail(key2email.get(key), validLink, "unnecessaryfield", "Verify your changes");
	}

	public Map<String, String> getRecruit2invite() {
		return recruit2invite;
	}

	public void setRecruit2invite(Map<String, String> recruit2invite) {
		this.recruit2invite = recruit2invite;
	}

	public void setConcreteUserAccountManager(ConcreteUserAccountRepository concreteUserAccountManager) {
		this.ConcreteUserAccountManager = concreteUserAccountManager;
	}

	public void setUserAccountManager(UserAccountManager userAccountManager) {
		this.userAccountManager = userAccountManager;
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
		this.userAccountManager.changePassword(ConcreteUserAccountManager.findByEmail(email).getUserAccount(), email2pass.get(email));
		this.key2email.remove(key);
		this.email2pass.remove(email);

	}

	public void setMailSender(MailSender sender) {
		this.concreteSender = new ConcreteMailSender(sender);
	}

}
