package internetkaufhaus.model;

import org.salespointframework.support.ConsoleWritingMailSender;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

public class mailSender {

	private ConsoleWritingMailSender sender = new ConsoleWritingMailSender();

	private SimpleMailMessage msg;

	public mailSender(String sendTo, String text, String from, String subject) {

		msg = new SimpleMailMessage();
		msg.setTo(sendTo);
		msg.setFrom(from);
		msg.setSubject(subject);
		msg.setText(text);

	}

	public void sendMail() {

		try {
			sender.send(msg);
			System.out.println("Mail versendet");
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}

	}

}
