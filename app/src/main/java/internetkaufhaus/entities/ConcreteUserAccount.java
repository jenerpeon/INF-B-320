package internetkaufhaus.entities;

import static org.salespointframework.core.Currencies.EURO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;

// TODO: Auto-generated Javadoc
/**
 * The Class ConcreteUserAccount.
 */
@Entity
@Table(name = "CACCOUNT")
public class ConcreteUserAccount implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3L;

	/** The comments. */
	@ManyToMany
	@JoinColumn(name = "COMMENT", nullable = false)
	private List<Comment> comments = new ArrayList<Comment>();
	
	/** The recruits. */
	@ManyToMany
	private List<UserAccount> recruits = new ArrayList<UserAccount>();
	
	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The user account. */
	@OneToOne
	private UserAccount userAccount;

	/** The email. */
	private String email;
	
	/** The address. */
	private String address;
	
	/** The zip code. */
	private String zipCode;
	
	/** The city. */
	private String city;

	/** The credits. */
	@Column(length = 2000)
	private Money credits;
	
	/** The role. */
	private Role role;

	/**
	 * This comment is just here because sonarcube is a little bitch.
	 */
	public ConcreteUserAccount() {
		/**
		 * This comment is just here because sonarcube is a little bitch.
		 */
	}

	/**
	 * Instantiates a new concrete user account.
	 *
	 * @param username the username
	 * @param password the password
	 * @param role the role
	 * @param u the u
	 */
	public ConcreteUserAccount(String username, String password, Role role, UserAccountManager u) {

		this.userAccount = u.create(username, password, role);
		this.role = role;
		this.credits = Money.of(0, EURO);
	}

	/**
	 * Instantiates a new concrete user account.
	 *
	 * @param email the email
	 * @param username the username
	 * @param firstname the firstname
	 * @param lastname the lastname
	 * @param address the address
	 * @param zipCode the zip code
	 * @param city the city
	 * @param password the password
	 * @param role the role
	 * @param u the u
	 * @param credits the credits
	 * @param recruits the recruits
	 */
	public ConcreteUserAccount(String email, String username, String firstname, String lastname, String address,
			String zipCode, String city, String password, Role role, UserAccountManager u, int credits,
			List<UserAccount> recruits) {
		this.userAccount = u.create(username, password, role);
		this.recruits = recruits;
		this.userAccount.setFirstname(firstname);
		this.userAccount.setLastname(lastname);
		this.address = address;
		this.zipCode = zipCode;
		this.city = city;
		this.userAccount.setEmail(email);
		this.email = email;
		this.credits = Money.of(credits, EURO);
		this.role = role;
	}

	/**
	 * Instantiates a new concrete user account.
	 *
	 * @param email the email
	 * @param username the username
	 * @param firstname the firstname
	 * @param lastname the lastname
	 * @param address the address
	 * @param zipCode the zip code
	 * @param city the city
	 * @param password the password
	 * @param role the role
	 * @param u the u
	 */
	public ConcreteUserAccount(String email, String username, String firstname, String lastname, String address,
			String zipCode, String city, String password, Role role, UserAccountManager u) {
		this.userAccount = u.create(username, password, role);
		this.userAccount.setFirstname(firstname);
		this.userAccount.setLastname(lastname);
		this.address = address;
		this.zipCode = zipCode;
		this.city = city;
		this.userAccount.setEmail(email);
		this.email = email;
		this.credits = Money.of(0, EURO);
		this.role = role;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return this.comments;
	}

	/**
	 * Adds the comment.
	 *
	 * @param c the c
	 */
	public void addComment(Comment c) {
		this.comments.add(c);
	}
	
	/**
	 * Removes the comment.
	 *
	 * @param c the c
	 */
	public void removeComment(Comment c) {
		this.comments.remove(c);
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the zip code.
	 *
	 * @return the zip code
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Sets the zip code.
	 *
	 * @param zipCode the new zip code
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the user account.
	 *
	 * @return the user account
	 */
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	/**
	 * Sets the user account.
	 *
	 * @param userAccount the new user account
	 */
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the recruits.
	 *
	 * @return the recruits
	 */
	public List<UserAccount> getRecruits() {
		return this.recruits;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public Role getRole() {
		return this.role;
	}

	/**
	 * Gets the credits.
	 *
	 * @return the credits
	 */
	public int getCredits() {
		return (int) (credits.getNumber().doubleValue() + 0.5);
	}

	/**
	 * Sets the credits.
	 *
	 * @param credits the new credits
	 */
	public void setCredits(Money credits) {
		this.credits = credits;
	}

	/**
	 * Sets the recruits.
	 *
	 * @param user the new recruits
	 */
	public void setRecruits(ConcreteUserAccount user) {
		if (recruits == null) {
			List<UserAccount> recruit = new ArrayList<UserAccount>();
			recruit.add(user.getUserAccount());
			this.recruits = recruit;
		} else {
			this.recruits.add(user.getUserAccount());
		}

	}

}
