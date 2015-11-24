package internetkaufhaus.model;

import java.io.Serializable;

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
@Table(name="CACCOUNT")
public class ConcreteUserAccount implements Serializable{

	private static final long serialVersionUID = 3L;

	@Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    private Long id;

	@OneToOne(cascade=CascadeType.ALL)
	private UserAccount userAccount;
    
	private String address;
    private String email;
    
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
	}

	public ConcreteUserAccount(String email, String username, String lastname, String address, String password,
			Role role, UserAccountManager u) {
		this.userAccount = u.create(username, password, role);
		this.userAccount.setLastname(lastname);
		this.address = address;
		this.userAccount.setEmail(email);
		this.email = email;
	}
	
		public UserAccount getUserAccount() {
		return this.userAccount;
	}
		
    public String getEmail(){
    	return this.email;
    }

}
