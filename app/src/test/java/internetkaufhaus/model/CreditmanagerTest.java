package internetkaufhaus.model;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.useraccount.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import internetkaufhaus.Application;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.services.DataService;

// TODO: Auto-generated Javadoc
/**
 * The Class CreditmanagerTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class CreditmanagerTest {

	/** The acc. */
	private ConcreteUserAccount acc;

	/** The user. */
	private ConcreteUserAccount user;

	/** The manager. */
	private Creditmanager manager;

	@Autowired
	private DataService data;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		this.manager = new Creditmanager(data);
		this.user = new ConcreteUserAccount("Username1", "Username1", Role.of("ROLE_CUSTOMER"),
				data.getUserAccountManager());
		this.acc = new ConcreteUserAccount("test@mail.com", "Username2", "Firstname", "Lastname", "Adress", "ZipCode",
				"City", "Password", Role.of("ROLE_EMPLOYEE"), data.getUserAccountManager());

		user.setRecruits(acc);

	}

	/**
	 * Testupdate creditpoints by user.
	 */
	@Test
	public void testupdateCreditpointsByUser() {
		acc.setCredits(14);
		manager.updateCreditpointsByUser(acc);
		assertEquals("UpdatePoints", acc.getCredits(), 14);
	}
}