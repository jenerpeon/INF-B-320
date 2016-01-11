package internetkaufhaus.services;

import static org.junit.Assert.assertEquals;
import static org.salespointframework.core.Currencies.EURO;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.order.OrderLine;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import internetkaufhaus.Application;
import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.entities.ConcreteUserAccount;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductManagementServiceTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class ProductManagementServiceTest {

	/** The service. */
	private ProductManagementService service;

	/** The user account. */
	private ConcreteUserAccount userAcc;

	private DataService data;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		service = new ProductManagementService();
		this.userAcc = new ConcreteUserAccount("Username1", "Username1", Role.of("ROLE_CUSTOMER"),
				data.getUserAccountManager());
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		ConcreteOrder order = new ConcreteOrder(this.userAcc, Cash.CASH);
		assertEquals("getBuyingPrice0", Money.of(0, EURO), service.getBuyingPrice(order));

		order.add(new OrderLine(new ConcreteProduct("Name", Money.of(5, EURO), Money.of(4, EURO), "Grillen",
				"Beschreeeibung", "WAPlink", "IMAETSCHFEIL"), Quantity.of(3)));
		order.add(new OrderLine(new ConcreteProduct("NAAME", Money.of(20, EURO), Money.of(10, EURO), "KATEGORIE",
				"BESCHREIBUNG", "NETZVERKNUEPFUNG", "BILDDATEI"), Quantity.of(2)));
		// assertEquals("getBuyingPrice1", Money.of(22, EURO),
		// service.getBuyingPrice(order));
		// TODO: fix this error.
	}
}
