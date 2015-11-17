package internetkaufhaus.model;


import org.salespointframework.order.Cart;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.mail.MailException;
import org.salespointframework.support.ConsoleWritingMailSender;
import org.salespointframework.time.Interval;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.Optional;

import org.salespointframework.catalog.Product;
import org.salespointframework.core.AbstractEntity;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderCompletionResult;
import org.salespointframework.order.OrderIdentifier;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


public class mailSender{
	
	private ConsoleWritingMailSender sender = new ConsoleWritingMailSender();
	
	private SimpleMailMessage msg;
	
	public mailSender(String sendTo, String text, String from, String subject){

		msg = new SimpleMailMessage();
		msg.setTo(sendTo);
		msg.setFrom(from);
		msg.setSubject(subject);
		msg.setText(text);
		
	}
	
	public void sendMail() {
		
		try{
			sender.send(msg);
			System.out.println("Mail versendet");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }

	}

}
