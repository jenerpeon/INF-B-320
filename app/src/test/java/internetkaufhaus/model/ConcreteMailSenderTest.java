package internetkaufhaus.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mail.MailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import internetkaufhaus.Application;
import internetkaufhaus.services.ConcreteMailService;

// TODO: Auto-generated Javadoc
/**
 * The Class ConcreteMailSenderTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ConcreteMailSenderTest {

	/** The sender. */
	@Autowired
	private MailSender sender;

	/**
	 * Wired test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void wiredTest() throws Exception {
		ConcreteMailService concreteMailSender = new ConcreteMailService(sender);
		concreteMailSender.sendMail("heinzerluds@googlemail.com", "Hallo Heinz", "me@web.de", "tescht");
		assertTrue("empty test", true);
	}
}
