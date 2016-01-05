package internetkaufhaus.model;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.Interval;
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
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteProductRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class StatisticTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class StatisticTest extends AbstractIntegrationTests {

	/** The order manager. */
	@Autowired
	private OrderManager<Order> orderManager;

	/** The concrete order repo. */
	@Autowired
	private ConcreteOrderRepository concreteOrderRepo;

	/** The prods. */
	@Autowired
	private ConcreteProductRepository prods;

	/** The Concrete user account manager. */
	@Autowired
	private ConcreteUserAccountRepository ConcreteUserAccountManager;

	/**
	 * Inits the test.
	 */
	@Before
	public void initTest() {
		Cart c = new Cart();
		for (ConcreteProduct p : prods.findAll()) {
			c.addOrUpdateItem(p, Quantity.of(1));
		}
		for (ConcreteUserAccount u : ConcreteUserAccountManager.findByRole(Role.of("ROLE_CUSTOMER"))) {

			ConcreteOrder order = new ConcreteOrder(u.getUserAccount(), Cash.CASH);
			c.addItemsTo(order.getOrder());

			Order o = order.getOrder();
			c.addItemsTo(o);

			order.setBillingGender("Herr");
			order.setBillingFirstName(u.getUserAccount().getFirstname());
			order.setBillingLastName(u.getUserAccount().getLastname());
			order.setBillingStreet(u.getAddress());
			order.setBillingHouseNumber("2");
			order.setBillingTown(u.getCity());
			order.setBillingZipCode(u.getZipCode());

			order.setShippingGender("Herr");
			order.setShippingFirstName(u.getUserAccount().getFirstname());
			order.setShippingLastName(u.getUserAccount().getLastname());
			order.setShippingStreet(u.getAddress());
			order.setShippingHouseNumber("2");
			order.setShippingTown(u.getCity());
			order.setShippingZipCode(u.getZipCode());

			orderManager.payOrder(o);
			// only set orderManager.payOrder(o), do not use
			// orderManager.completeOrder(0), to complete Order look at the next
			// line!
			order.setStatus(OrderStatus.COMPLETED);
			// to complete Order do not use orderManager.completeOrder
			order.setDateOrdered(LocalDateTime.now().minusDays(31));
			concreteOrderRepo.save(order);
			orderManager.save(o);
		}
		c.clear();
	}

	/**
	 * Test get turnover by interval.
	 */
	@Test
	public void testGetTurnoverByInterval() {
		assertTrue("hola", true);
	}

	/**
	 * Test get sales by interval.
	 */
	@Test
	public void testGetSalesByInterval() {
		assertTrue("hola", true);
	}

	/**
	 * Test get purchases by interval.
	 *
	 * @param i the i
	 * @param q the q
	 */
	public void testGetPurchasesByInterval(Interval i, int q) {
	}

	/**
	 * Test get profit by interval.
	 *
	 * @param i the i
	 * @param q the q
	 */
	public void testGetProfitByInterval(Interval i, int q) {
	}

	/**
	 * Test get retours by interval.
	 *
	 * @param i the i
	 * @param q the q
	 */
	public void testGetRetoursByInterval(Interval i, int q) {
	}
}
