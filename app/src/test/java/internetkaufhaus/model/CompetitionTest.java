package internetkaufhaus.model;

import static org.junit.Assert.assertTrue;
import static org.salespointframework.core.Currencies.EURO;

import java.util.ArrayList;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.useraccount.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import internetkaufhaus.AbstractIntegrationTests;
import internetkaufhaus.Application;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.services.ConcreteMailService;
import internetkaufhaus.services.DataService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompetitionTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class CompetitionTest extends AbstractIntegrationTests {

	/** The manager. */
	@Autowired
	private DataService data;

	/** The creditmanager. */
	@Autowired
	private Creditmanager creditmanager;

	/** The accs. */
	private ArrayList<ConcreteUserAccount> accs = new ArrayList<ConcreteUserAccount>();

	/** The customer role. */
	private Role customerRole = Role.of("ROLE_CUSTOMER");

	/** The comp. */
	private Competition comp;
	
	@Autowired
	private ConcreteMailService mailServer;

	/**
	 * Inits the test.
	 */
	@Before
	public void initTest() {
		int i = 0;
		for (i = 0; i < 30; i++) {

			ConcreteUserAccount acc = new ConcreteUserAccount("kunde" + i + "@todesstern.ru", "mockup" + i, "Kunde" + i,
					"Kundenname" + i, "Kundenstraße" + i, "12345", "Definitiv nicht Dresden", "kunde" + i, customerRole,
					data.getUserAccountManager());
			acc.setCredits(Money.of(i, EURO));
			this.accs.add(acc);
		}
		this.comp = new Competition(accs, creditmanager);
	}

	/**
	 * Test get winners.
	 */
	@Test
	public void testGetWinners() {
		assertTrue("Es wurden " + comp.getWinners().size() + " Gewinner ermittelt (falsch)",
				comp.getWinners().size() == 3);
	}

	/**
	 * Test notify winners.
	 */
	@Test
	public void testNotifyWinners() {
		Competition com = new Competition(this.accs, this.creditmanager);
		com.getWinners();
		com.notifyWinners(mailServer);
		//verify(mailServer, times(2)).sendMail(anyString(), eq("Herzlichen Glückwunsch"), eq("wood@shop.de"),
		//		eq("Gewonnen"));
	}

	/**
	 * Test get accs.
	 */
	@Test
	public void testGetAccs() {
		Competition com = new Competition(this.accs, this.creditmanager);
		assertTrue("ConcreteUserAccounts stimmen nicht überein", com.getAccs().equals(this.accs));
	}

	/**
	 * Test set accs.
	 */
	@Test
	public void testSetAccs() {
		ConcreteUserAccount mocki = new ConcreteUserAccount("Username1", "Username1", Role.of("ROLE_CUSTOMER"),
				data.getUserAccountManager());
		ArrayList<ConcreteUserAccount> acci = new ArrayList<ConcreteUserAccount>();
		acci.add(mocki);
		Competition com = new Competition(this.accs, this.creditmanager);
		com.setAccs(acci);
		assertTrue("Accounts stimmen nicht überein", com.getAccs().equals(acci));
	}

}
