package internetkaufhaus.model;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.MailSender;

import org.springframework.beans.factory.annotation.Autowired;


public class ConcreteMailSender{
	
	private final MailSender mailSender;

	@Autowired
	public ConcreteMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendMail(String sendTo, String text, String from, String subject){

		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setTo(sendTo);
		msg.setFrom(from);
		msg.setSubject(subject);
		msg.setText(text);
		
		try{
			mailSender.send(msg);
			System.out.println("Mail versendet");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }

	}


}
