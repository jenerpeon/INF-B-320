package internetkaufhaus.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

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
import org.springframework.transaction.annotation.Transactional;

import internetkaufhaus.AbstractIntegrationTests;
import internetkaufhaus.Application;
import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.services.DataService;

// TODO: Auto-generated Javadoc
/**
 * The Class CreditmanagerTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

@Transactional
public class CreditmanagerTest extends AbstractIntegrationTests {

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
		ConcreteUserAccount user = new ConcreteUserAccount("test2@mail.com", "Username1", "Firstname", "Lastname",
				"Straße", "3", "ZipCode", "City", "Password", Role.of("ROLE_CUSTOMER"), data.getUserAccountManager());
		ConcreteUserAccount acc = new ConcreteUserAccount("test@mail.com", "Username2", "Firstname", "Lastname",
				"Straße", "3", "ZipCode", "City", "Password", Role.of("ROLE_EMPLOYEE"), data.getUserAccountManager());
		user.setRecruits(acc);
		data.getConcreteUserAccountRepository().save(user);
		data.getUserAccountManager().save(user.getUserAccount());
		data.getConcreteUserAccountRepository().save(acc);
		data.getUserAccountManager().save(acc.getUserAccount());

		ConcreteProduct prod = new ConcreteProduct("Test", Money.of(5, "EUR"), Money.of(5, "EUR"), "Test", "Test",
				"Test", "Test");
		ConcreteOrder order = new ConcreteOrder(acc, Cash.CASH);
		order.add(new OrderLine(prod, Quantity.of(5)));
		order.setDateOrdered(LocalDateTime.now().minusDays(31));
		order.setStatus(OrderStatus.COMPLETED);
		data.getConcreteOrderRepository().save(order);
	}

	/**
	 * Testupdate creditpoints by user.
	 */
	@Test
	public void testupdateCreditpointsByUser() {
		manager.updateCreditpointsByUser(data.getConcreteUserAccountRepository().findByEmail("test2@mail.com").get());
		assertEquals("UpdatePoints",
				data.getConcreteUserAccountRepository().findByEmail("test2@mail.com").get().getCredits(), 25);
	}
}