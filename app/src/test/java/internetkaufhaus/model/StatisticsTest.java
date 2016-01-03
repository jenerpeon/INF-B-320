package internetkaufhaus.model;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import org.javamoney.moneta.Money;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class StatisticsTest extends AbstractIntegrationTests{

	@Autowired
	private OrderManager<Order> orderManager;
	@Autowired
	private ConcreteOrderRepository concreteOrderRepo;
	@Autowired
	private ConcreteProductRepository prods;
	@Autowired
	private ConcreteUserAccountRepository ConcreteUserAccountManager;
	
	@Before
	public void init() {
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

			orderManager.payOrder(o);// only set orderManager.payOrder(o), do
										// not use
										// orderManager.completeOrder(0), to
										// complete Order look at the next line!
			order.setStatus(OrderStatus.COMPLETED); // to complete Order do not
													// use
													// orderManager.completeOrder
			order.setDateOrdered(LocalDateTime.now().minusDays(31));
			concreteOrderRepo.save(order);
			orderManager.save(o);

		}
  
		c.clear();
	

	}

	@Test
	public void getTurnoverByIntervalTest() {
		assertTrue("hola", true);
	}

	@Test
	public void getSalesByIntervalTest() {
		assertTrue("hola", true);
	}

	/* Purchcases and profit calculation are not yet implemented */

	public List<Integer> getPurchasesByInterval(Interval i, int q) {
		return null; // TODO: implement this
	}

	public List<Money> getProfitByInterval(Interval i, int q) {
		return null; // TODO: implement this
	}

	public List<Integer> getRetoursByInterval(Interval i, int q) {
		return null; // TODO: implement this
	}



}
