package internetkaufhaus.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;

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
	private List<ConcreteUserAccount> recruits = new ArrayList<ConcreteUserAccount>();

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The user account. */
	@OneToOne
	private UserAccount userAccount;

	/** The email. */
	private String email;

	/** The street. */
	private String street;

	/** The house number. */
	private String houseNumber;

	/** The zip code. */
	private String zipCode;

	/** The city. */
	private String city;

	/** The credits. */
	private long credits;

	/** The role. */
	private Role role;

	/**
	 * Instantiates a new concrete user account.
	 */
	public ConcreteUserAccount() {
		System.out.print("");
	}

	/**
	 * Instantiates a new concrete user account.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param role
	 *            the role
	 * @param u
	 *            the u
	 */
	public ConcreteUserAccount(String username, String password, Role role, UserAccountManager u) {

		this.userAccount = u.create(username, password, role);
		this.role = role;
		this.credits = 0;
	}

	/**
	 * Instantiates a new concrete user account.
	 *
	 * @param email
	 *            the email
	 * @param username
	 *            the username
	 * @param firstname
	 *            the firstname
	 * @param lastname
	 *            the lastname
	 * @param street
	 *            the street
	 * @param houseNumber
	 *            the house number
	 * @param zipCode
	 *            the zip code
	 * @param city
	 *            the city
	 * @param password
	 *            the password
	 * @param role
	 *            the role
	 * @param u
	 *            the u
	 * @param credits
	 *            the credits
	 * @param recruits
	 *            the recruits
	 */
	public ConcreteUserAccount(String email, String username, String firstname, String lastname, String street,
			String houseNumber, String zipCode, String city, String password, Role role, UserAccountManager u,
			long credits, List<ConcreteUserAccount> recruits) {
		this.userAccount = u.create(username, password, role);
		this.recruits = recruits;
		this.userAccount.setFirstname(firstname);
		this.userAccount.setLastname(lastname);
		this.street = street;
		this.houseNumber = houseNumber;
		this.zipCode = zipCode;
		this.city = city;
		this.userAccount.setEmail(email);
		this.email = email;
		this.credits = credits;
		this.role = role;
	}

	/**
	 * Instantiates a new concrete user account.
	 *
	 * @param email
	 *            the email
	 * @param username
	 *            the username
	 * @param firstname
	 *            the firstname
	 * @param lastname
	 *            the lastname
	 * @param street
	 *            the street
	 * @param houseNumber
	 *            the house number
	 * @param zipCode
	 *            the zip code
	 * @param city
	 *            the city
	 * @param password
	 *            the password
	 * @param role
	 *            the role
	 * @param u
	 *            the u
	 */
	public ConcreteUserAccount(String email, String username, String firstname, String lastname, String street,
			String houseNumber, String zipCode, String city, String password, Role role, UserAccountManager u) {
		this.userAccount = u.create(username, password, role);
		this.userAccount.setFirstname(firstname);
		this.userAccount.setLastname(lastname);
		this.street = street;
		this.houseNumber = houseNumber;
		this.zipCode = zipCode;
		this.city = city;
		this.userAccount.setEmail(email);
		this.email = email;
		this.credits = 0;
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
	 * @param c
	 *            the c
	 */
	public void addComment(Comment c) {
		this.comments.add(c);
	}

	/**
	 * Removes the comment.
	 *
	 * @param c
	 *            the c
	 */
	public void removeComment(Comment c) {
		this.comments.remove(c);
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
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
	 * @param role
	 *            the new role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Sets the zip code.
	 *
	 * @param zipCode
	 *            the new zip code
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
	 * @param city
	 *            the new city
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
	 * @param userAccount
	 *            the new user account
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
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return this.street;
	}

	/**
	 * Sets the street.
	 *
	 * @param street
	 *            the new street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the house number.
	 *
	 * @return the house number
	 */
	public String getHouseNumber() {
		return this.houseNumber;
	}

	/**
	 * Sets the house number.
	 *
	 * @param houseNumber
	 *            the new house number
	 */
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	/**
	 * Gets the recruits.
	 *
	 * @return the recruits
	 */
	public List<ConcreteUserAccount> getRecruits() {
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
	public long getCredits() {
		return this.credits;
	}

	/**
	 * Sets the credits.
	 *
	 * @param credits
	 *            the new credits
	 */
	public void setCredits(long credits) {
		this.credits = credits;
	}

	/**
	 * Sets the recruits.
	 *
	 * @param user
	 *            the new recruits
	 */
	public void setRecruits(ConcreteUserAccount user) {
		if (recruits == null) {
			List<ConcreteUserAccount> recruit = new ArrayList<ConcreteUserAccount>();
			recruit.add(user);
			this.recruits = recruit;
		} else {
			this.recruits.add(user);
		}

	}

}
