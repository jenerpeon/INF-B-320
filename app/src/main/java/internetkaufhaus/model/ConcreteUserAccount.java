package internetkaufhaus.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;

@Entity
public class ConcreteUserAccount {
	private @Id @GeneratedValue long id;
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@OneToOne
	private UserAccount userAccount;

	public ConcreteUserAccount(String username, String password, Role role, UserAccountManager u) {
		this.userAccount = u.create(username, password, role);
	}

	public ConcreteUserAccount(String email, String username, String lastname, String address, String password,
			Role role, UserAccountManager u) {
		this.userAccount = u.create(username, password, role);
		this.userAccount.setLastname(lastname);
		this.address = address;
		this.userAccount.setEmail(email);
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

}
