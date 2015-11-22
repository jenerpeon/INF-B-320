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
    Map<String,UserAccount> key2account; 
    Map<UserAccount, String> user2pass; 
    @OneToOne private UserAccountManager userAccountManager;
 
    public AccountAdministration(){
        this.random = new SecureRandom();
        this.key2account = new HashMap<String, UserAccount>();
        this.user2pass = new HashMap<UserAccount, String>();
    }
    
    public String requestKey(UserAccount user){
        String key = new BigInteger(120, random).toString(32);
        key2account.put(key, user);
        return key;
    }
    public void PassValidation(String key){
        String validLink = "http://localhost:8080/NewPass/".concat(key);
        this.concreteSender.sendMail(key2account.get(key).getEmail(),validLink,"tolltolltoll@einfachtoll","Verify your changes");
    }
    
    public void setUserAccountManager(UserAccountManager userAccountManager){
       this.userAccountManager = userAccountManager; 
    }
    
    public boolean VerifyKey(String key){
        if(this.key2account.containsKey(key))
           return true;
        return false;
    }

    public void removeKey(String key){
        this.key2account.remove(key); 
    }
    
    public String requestPass(String pass, UserAccount userAccount){
        this.user2pass.put(userAccount, pass);
        return this.requestKey(userAccount);
    }
    public void verifyPass(String key){
        UserAccount user = this.key2account.get(key);
        this.userAccountManager.changePassword(user, user2pass.get(user)); 
        this.key2account.remove(key);
        this.user2pass.remove(user);

    }
    public void setMailSender(MailSender sender){
        this.concreteSender = new ConcreteMailSender(sender); 
    }
}
