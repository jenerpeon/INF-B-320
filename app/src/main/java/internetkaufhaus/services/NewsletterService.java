package internetkaufhaus.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsletterService {

	@Autowired
	private ConcreteMailService mailsender;

	private Map<String, String> map = new HashMap<String, String>(); // Map with
																		// User
																		// and
																		// Email
	private Map<String, Map<Date, String>> oldAbos = new HashMap<String, Map<Date, String>>();// Map
																								// with
																								// subject
																								// and
																								// text
																								// of
																								// email

	public NewsletterService() {

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
		for (String email : this.getMap().values()) {
			mailsender.sendMail(email, text, "zu@googlemail.com", "Newsletter Woods Super Dooper Shop");
		}

	}

	public Map<String, String> getMap() {

		return map;
	}

}
