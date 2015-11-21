package internetkaufhaus.model;

import org.springframework.beans.factory.annotation.Autowired;
import javax.crypto.KeyGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.*;

@Entity
public class ConcreteUserAccount{
    private @Id @GeneratedValue long id;
    private String address;
    private String email;
 
    @OneToOne private UserAccount userAccount;

    public ConcreteUserAccount(String username, String password, Role role, UserAccountManager u){
        this.userAccount = u.create(username, password, role);
    }

    public ConcreteUserAccount(String email, String username, String lastname, String address, String password, Role role, UserAccountManager u){
        this.userAccount = u.create(username, password, role);
        this.userAccount.setLastname(lastname);
        this.address=address;
        this.userAccount.setEmail(email);
        this.email = email;
    }
   
    public UserAccount getUserAccount(){
        return this.userAccount; 
    }

    public String getEmail(){
    	return this.userAccount.getEmail();
    }

    

}
