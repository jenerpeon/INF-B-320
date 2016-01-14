package internetkaufhaus.model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

import javax.transaction.Transactional;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Iterators;

import internetkaufhaus.AbstractIntegrationTests;
import internetkaufhaus.Application;
import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.services.ConcreteMailService;
import internetkaufhaus.services.DataService;

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
	private DataService data;

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

		for (int i = 1; i < 31; i++) {

			ConcreteUserAccount acc = new ConcreteUserAccount("kunde" + i + "@todesstern.ru", "mockup" + i, "Kunde" + i,
					"Kundenname" + i, "Kundenstraße" + i, "3", "12345", "Definitiv nicht Dresden", "kunde" + i, customerRole,
					data.getUserAccountManager());
			if (i > 1) {
				acc.setRecruits(data.getConcreteUserAccountRepository()
						.findByEmail("kunde" + (i - 1) + "@todesstern.ru").get());
			}
			
			data.getUserAccountManager().save(acc.getUserAccount());
			data.getConcreteUserAccountRepository().save(acc);
			
			Random random = new Random();
			for (int j = 0; j < random.nextInt(5); j++) {
				ConcreteProduct prod = new ConcreteProduct("Test", Money.of(5, "EUR"), Money.of(5, "EUR"), "Test",
						"Test", "Test", "Test");
				ConcreteOrder order = new ConcreteOrder(acc, Cash.CASH);
				order.add(new OrderLine(prod, Quantity.of(random.nextInt(20) + 2)));
				order.setDateOrdered(LocalDateTime.now().minusDays(31).toEpochSecond(ZoneOffset.ofHours(1)));
				order.setStatus(OrderStatus.COMPLETED);
				data.getConcreteOrderRepository().save(order);
			}

		}
		this.comp = new Competition(data);
	}

	/**
	 * Test get winners.
	 */
	@Test
	public void testGetWinners() {
		assertEquals("Es wurden Gewinner ermittelt",
				comp.getWinners().size(), Iterators.size(data.getConcreteUserAccountRepository().findByRole(Role.of("ROLE_CUSTOMER")).iterator())/10);
	}

	/**
	 * Test notify winners.
	 */
	@Test
	public void testNotifyWinners() {
		comp.getWinners();
		//comp.notifyWinners(mailServer);
		// verify(mailServer, times(2)).sendMail(anyString(), eq("Herzlichen
		// Glückwunsch"), eq("wood@shop.de"),
		// eq("Gewonnen"));
	}

}
