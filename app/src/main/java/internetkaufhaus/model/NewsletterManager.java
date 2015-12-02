package internetkaufhaus.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

@Component
public class NewsletterManager{
	
	MailSender sender;
	List<String> newsletterAbo= new ArrayList<String>();
	//List<ConcreteUserAccount> newsUser= new ArrayList<ConcreteUserAccount>();
	Map<String,String> map = new HashMap<String,String>();
	Map<Date,String> oldAbos= new HashMap<Date,String>();
	
	@Autowired
	public NewsletterManager(MailSender sender){
		this.sender=sender;
	}

	
	public List<String> getNewsletterAbo() {
		return newsletterAbo;
	}

	public void addToNewsletterAbo(String email){
		newsletterAbo.add(email);
		
	}


	public void setMap(Map<String, String> map) {
		this.map = map;
	}


	public boolean deleteNewsletterAbo(String email){
		return newsletterAbo.remove(email);
	}
	
	/*public List<ConcreteUserAccount> getNewsUser() {
		return newsUser;
	}
	
	public void addnewsUser(ConcreteUserAccount user){
		newsUser.add(user);
		
	}*/
	public void sendNewsletters(String text){
		ConcreteMailSender concreteMailSender = new ConcreteMailSender(sender);
		for(String email : this.newsletterAbo){
			concreteMailSender.sendMail(email, text, "zu@googlemail.com", "Newsletter Woods Super Dooper Shop");
		}
		
	}
	public void addOldAbos(Date date, String mailBody){
		oldAbos.put(date, mailBody);
		
	}


	public Map<String, String> getMap() {
		
		return map;
	}


	

	
}