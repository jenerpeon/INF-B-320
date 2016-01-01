package internetkaufhaus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class ConcreteMailService {
	private final MailSender mailSender;
	
	@Autowired
	public ConcreteMailService(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String sendTo, String text, String from, String subject) {

		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setTo(sendTo);
		msg.setFrom(from);
		msg.setSubject(subject);
		msg.setText(text);

		try {
			mailSender.send(msg);
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}

	}
	public MailSender getMailSender(){
		return this.mailSender;
	}
}
