package internetkaufhaus.model;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import internetkaufhaus.Application;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ConcreteMailSenderTest {
	
	@Autowired
	private MailSender sender;
	
	
	@Test
	public void wiredTest () throws Exception{
		ConcreteMailSender concreteMailSender = new ConcreteMailSender(sender);
		concreteMailSender.sendMail("heinzerluds@googlemail.com", "Hallo Heinz", "me@web.de", "tescht");
	}
}
