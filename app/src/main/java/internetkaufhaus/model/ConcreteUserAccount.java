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
@Table(name = "ConcreteAccounts")
public class ConcreteUserAccount {


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@OneToOne(cascade=CascadeType.ALL)
	private UserAccount userAccount;
    
	private String address;
    private String email;
    private Role role;
    
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@SuppressWarnings("deprecation")
	public ConcreteUserAccount(){
		
	}


	public ConcreteUserAccount(String username, String password, Role role, UserAccountManager u) {
		this.userAccount = u.create(username, password, role);
		this.role = role;
	}

	public ConcreteUserAccount(String email, String username, String lastname, String address, String password,
			Role role, UserAccountManager u) {
		this.userAccount = u.create(username, password, role);
		this.userAccount.setLastname(lastname);
		this.address = address;
		this.userAccount.setEmail(email);
		this.email = email;
		this.role=role;
	}
	
		public UserAccount getUserAccount() {
		return this.userAccount;
	}
		
    public String getEmail(){
    	return this.email;
    }

    public Role getRole(){
    	return role;
    }
    public boolean setRole(Role role)
    {
    	this.role = role;
    	return (this.role.equals(role));
    	
    }
    public void setUserAccount(UserAccount acc)
    {
    	this.userAccount=acc;
    }

    public Long getId()
    {
    	return id;
    }
}
