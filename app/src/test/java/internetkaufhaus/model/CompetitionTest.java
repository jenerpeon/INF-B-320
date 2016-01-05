package internetkaufhaus.model;

import static org.junit.Assert.assertTrue;
import static org.salespointframework.core.Currencies.EURO;

import java.util.ArrayList;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import internetkaufhaus.AbstractIntegrationTests;
import internetkaufhaus.Application;
import internetkaufhaus.entities.ConcreteUserAccount;

// TODO: Auto-generated Javadoc
/**
 * The Class CompetitionTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class CompetitionTest extends AbstractIntegrationTests {

	/** The manager. */
	@Autowired
	private UserAccountManager manager;

	/** The creditmanager. */
	@Autowired
	private Creditmanager creditmanager;

	/** The accs. */
	private ArrayList<ConcreteUserAccount> accs = new ArrayList<ConcreteUserAccount>();

	/** The customer role. */
	private Role customerRole = Role.of("ROLE_CUSTOMER");

	/** The comp. */
	private Competition comp;

	/**
	 * Inits the test.
	 */
	@Before
	public void initTest() {
		int i = 0;
		for (i = 0; i < 30; i++) {

			ConcreteUserAccount acc = new ConcreteUserAccount("kunde" + i + "@todesstern.ru", "mockup" + i, "Kunde" + i,
					"Kundenname" + i, "Kundenstraße" + i, "12345", "Definitiv nicht Dresden", "kunde" + i, customerRole,
					manager);
			acc.setCredits(Money.of(i, EURO));
			this.accs.add(acc);
		}
		comp = new Competition(accs, creditmanager);
	}

	/**
	 * Test get winners test.
	 */
	@Test
	public void testGetWinnersTest() {
		assertTrue("jop", comp.getWinners().size() == (this.accs.size() / 10));
	}

}
