package internetkaufhaus.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class NewsletterService.
 */
@Service
public class NewsletterService {
	
	/** The mailsender. */
	@Autowired
	private ConcreteMailService mailsender;

	/** The map. */
	private Map<String, String> map = new HashMap<String, String>();
	
	/** The old abos. */
	// Map with User and Email
	private Map<String, Map<Date, String>> oldAbos = new HashMap<String, Map<Date, String>>();
	// Map with subject and text of email

	/**
	 * Instantiates a new newsletter service.
	 */
	public NewsletterService() {
		System.out.print("");
	}

	/**
	 * Sets the map.
	 *
	 * @param map the map
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	/**
	 * Gets the old abos.
	 *
	 * @return the old abos
	 */
	public Map<String, Map<Date, String>> getOldAbos() {
		return oldAbos;
	}

	/**
	 * Sets the old abos.
	 *
	 * @param oldAbos the old abos
	 */
	public void setOldAbos(Map<String, Map<Date, String>> oldAbos) {
		this.oldAbos = oldAbos;
	}

	/**
	 * The sendNewsletters method sends the given text as newsletter to all
	 * costumers which have subscribed the newsletter.
	 * 
	 * @param content
	 *            the newsletter content as String
	 *
	 */
	public void sendNewsletter(String subject, String content) {
		for (String email : this.getMap().values()) {
			mailsender.sendMail(email, content, "zu@googlemail.com", subject);
		}

	}

	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public Map<String, String> getMap() {

		return map;
	}

}
