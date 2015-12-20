package internetkaufhaus.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

@Component
public class NewsletterManager {

	MailSender sender;

	Map<String, String> map = new HashMap<String, String>(); // Map with User and Email
	Map<String, Map<Date, String>> oldAbos = new HashMap<String, Map<Date, String>>();// Map with subject and text of email

	@Autowired
	public NewsletterManager(MailSender sender) {
		this.sender = sender;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Map<String, Map<Date, String>> getOldAbos() {
		return oldAbos;
	}

	public void setOldAbos(Map<String, Map<Date, String>> oldAbos) {
		this.oldAbos = oldAbos;
	}

	public void sendNewsletters(String text) {
		ConcreteMailSender concreteMailSender = new ConcreteMailSender(sender);
		for (String email : this.getMap().values()) {
			concreteMailSender.sendMail(email, text, "zu@googlemail.com", "Newsletter Woods Super Dooper Shop");
		}

	}

	public Map<String, String> getMap() {

		return map;
	}

}