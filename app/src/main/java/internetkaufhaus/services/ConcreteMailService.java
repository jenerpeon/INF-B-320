package internetkaufhaus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


// TODO: Auto-generated Javadoc
/**
 * This is the ConcreteMailService class. It is responsible for sending mails via
 * a connected mail provider.
 * 
 * @author heiner
 *
 */
@Service("mailService")
public class ConcreteMailService {

	/** The mail sender. */
	private final MailSender mailSender;

	/**
	 * Instantiates a new concrete mail service.
	 *
	 * @param mailSender the mail sender
	 */
	@Autowired
	public ConcreteMailService(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * The sendMail method will send a mail with the given mail attributes like
	 * the destination address or content.
	 *
	 * @author heiner
	 * @param sendTo            email destination address
	 * @param text            email text content
	 * @param from            sender name
	 * @param subject            email subject
	 * @return <tt>true</tt> if mail sending was successful
	 * @exception MailException the mail exception
	 */

	public boolean sendMail(String sendTo, String text, String from, String subject) {

		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setTo(sendTo);
		msg.setFrom(from);
		msg.setSubject(subject);
		msg.setText(text);

		try {
			mailSender.send(msg);
			return true;
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
			return false;
		}

	}

	/**
	 * Gets the mail sender.
	 *
	 * @return the mail sender
	 */
	public MailSender getMailSender() {
		return this.mailSender;
	}
}
