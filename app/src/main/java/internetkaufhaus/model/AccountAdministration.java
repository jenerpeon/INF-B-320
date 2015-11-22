package internetkaufhaus.model;

import java.math.BigInteger;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.OneToOne;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

@Component
public class AccountAdministration {
	private ConcreteMailSender concreteSender;
    private SecureRandom random;
    Map<String,String> key2email; 
    Map<String, String> email2pass; 

    @OneToOne private ConcreteUserAccountRepository ConcreteUserAccountManager;
    @OneToOne private UserAccountManager userAccountManager; 

    public AccountAdministration(){
        this.random = new SecureRandom();
        this.key2email = new HashMap<String, String>();
        this.email2pass = new HashMap<String, String>();
    }
    public boolean isRegistered(String email){
    	if(ConcreteUserAccountManager.findByEmail(email)!=null)
    		return true;
    	return false;
    }
    
    public String requestKey(String email){
        String key = new BigInteger(120, random).toString(32);
        key2email.put(key, email);
        return key;
    }
    public void PassValidation(String key){
        String validLink = "http://localhost:8080/NewPass/".concat(key);
        this.concreteSender.sendMail(key2email.get(key),validLink,"tolltolltoll@einfachtoll","Verify your changes");
    }
    
    public void setConcreteUserAccountManager(ConcreteUserAccountRepository concreteUserAccountManager){
       this.ConcreteUserAccountManager = concreteUserAccountManager; 
    }
    
    public void setUserAccountManager(UserAccountManager userAccountManager){
    	this.userAccountManager = userAccountManager;
    }

    
    public boolean isValidKey(String key){
        if(this.key2email.containsKey(key) && isRegistered(key2email.get(key)))
           return true;
        return false;
    }

    public void removeKey(String key){
        this.email2pass.remove(this.key2email.get(key));
        this.key2email.remove(key); 
    }
    
    public String requestPass(String pass, String email){
        this.email2pass.put(email, pass);
        return this.requestKey(email);
    }
    public void verifyPass(String key){
        String email = this.key2email.get(key);
        this.userAccountManager.changePassword(ConcreteUserAccountManager.findByEmail(email).getUserAccount(), email2pass.get(email)); 
        this.key2email.remove(key);
        this.email2pass.remove(email);

    }
    public void setMailSender(MailSender sender){
        this.concreteSender = new ConcreteMailSender(sender); 
    }
}
