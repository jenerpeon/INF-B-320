package internetkaufhaus.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;

@Entity
@Table(name="CACCOUNT")
public class ConcreteUserAccount implements Serializable{

	private static final long serialVersionUID = 3L;

	@Id
    @GeneratedValue
    private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private UserAccount userAccount;
	private String email;
	private String address;
	private String zipCode;
	private String city;
    private Role role;
    


	public ConcreteUserAccount() {

	}

	public ConcreteUserAccount(String username, String password, Role role, UserAccountManager u) {
		this.userAccount = u.create(username, password, role);
		this.role = role;
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
		this.role=role;
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

    public Role getRole(){
    	return role;
    }
    public boolean setRole(Role role)
    {
    	this.role = role;
    	return (this.role.equals(role));
    	
    }

}
