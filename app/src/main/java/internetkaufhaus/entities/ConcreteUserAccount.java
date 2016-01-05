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

@Entity
@Table(name = "CACCOUNT")
public class ConcreteUserAccount implements Serializable {

	private static final long serialVersionUID = 3L;

	/** The comments. */
	@ManyToMany
	@JoinColumn(name = "COMMENT", nullable = false)
	private List<Comment> comments = new ArrayList<Comment>();
	@ManyToMany
	private List<UserAccount> recruits = new ArrayList<UserAccount>();
	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private UserAccount userAccount;

	private String email;
	private String address;
	private String zipCode;
	private String city;

	@Column(length = 2000)
	private Money credits;
	private Role role;

	/**
	 * This comment is just here because sonarcube is a little bitch.
	 */
	public ConcreteUserAccount() {
		/**
		 * This comment is just here because sonarcube is a little bitch.
		 */
	}

	public ConcreteUserAccount(String username, String password, Role role, UserAccountManager u) {

		this.userAccount = u.create(username, password, role);
		this.credits = Money.of(0, EURO);
	}

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

	public Long getId() {
		return id;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void addComment(Comment c) {
		this.comments.add(c);
	}
	
	public void removeComment(Comment c) {
		this.comments.remove(c);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<UserAccount> getRecruits() {
		return this.recruits;
	}

	public Role getRole() {
		return this.userAccount.getRoles().iterator().next();
	}

	public int getCredits() {
		return (int) (credits.getNumber().doubleValue() + 0.5);
	}

	public void setCredits(Money credits) {
		this.credits = credits;
	}

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
