package internetkaufhaus.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;

@Entity
@Table(name = "ConcreteAccounts")
public class ConcreteUserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private UserAccount userAccount;

	private String email;
	private String address;
	private String zipCode;
	private String city;

	public ConcreteUserAccount() {

	}

	public ConcreteUserAccount(String username, String password, Role role, UserAccountManager u) {
		this.userAccount = u.create(username, password, role);
	}

	public ConcreteUserAccount(String email, String username, String firstname, String lastname, String address, String zipCode, String city, String password, Role role, UserAccountManager u) {
		this.userAccount = u.create(username, password, role);
		this.userAccount.setFirstname(firstname);
		this.userAccount.setLastname(lastname);
		this.address = address;
		this.zipCode = zipCode;
		this.city = city;
		this.userAccount.setEmail(email);
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getZipCode() {
		return zipCode;
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

}
