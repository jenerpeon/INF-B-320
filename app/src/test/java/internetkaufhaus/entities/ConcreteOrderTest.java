package internetkaufhaus.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.order.Cart;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import internetkaufhaus.AbstractIntegrationTests;
import internetkaufhaus.Application;
import internetkaufhaus.services.DataService;

// TODO: Auto-generated Javadoc
/**
 * The Class ConcreteOrderTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class ConcreteOrderTest extends AbstractIntegrationTests {

	/** The o. */
	private ConcreteOrder o;
	
	private ConcreteUserAccount acc; 
	
	private ConcreteProduct prod1;
	
	private ConcreteProduct prod2;
	
	/** The u. */
	@Autowired
	private DataService data;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		Cart cart = new Cart();
		this.acc = new ConcreteUserAccount("Username1", "Username1", Role.of("ROLE_CUSTOMER"), data.getUserAccountManager());
		this.prod1 = new ConcreteProduct(
				"CIMAROSA Sauvignon Blanc Marlborough Estate Selection, Weißwein 2014", Money.of(5.49, EURO),
				Money.of(5.00, EURO), "Wein",
				"„Nahezu mühelos produziert Neuseeland helle, aromatische Sauvignons, die eine Geschmackstiefe erreichen, ohne dieselbe Heftigkeit am Gaumen zu zeigen, die diese beliebte Rebsorte oft charakterisiert. Frisch und nicht zu trocken ist dieser Wein leicht zu trinken – alleine oder zu Salaten, Fisch und weißem Fleisch.\" RICHARD BAMPFIELD.",
				"https://eng.wikipedia.org/wiki/Fuzz",
				"cimarosa-sauvignon-blanc-marlborough-estate-selection-weisswein-2014.jpg");
		this.prod2 = new ConcreteProduct("Allini Pinot Chardonnay, Schaumwein", Money.of(2.49, EURO),
				Money.of(0, EURO), "Wein",
				"Mit diesem prickelnd sanften Spumante, von Pinot & Chardonnay steht dem endlosen Trinkgenuss nichts mehr im Wege. Zart strohgelb im Glas, mit einer Aromatik von Marille und weißer Mandelblüte, einer belebenden Säure und einem spritzig fruchtigen Mundgefühl, schmeckt dieser prickelnde Gefährte hervorragend gut gekühlt bei 8-10°C als Aperitif oder zu knackigen Salaten.",
				"https://eng.wikipedia.org/wiki/Fuzz", "allini-pinot-chardonnay-schaumwein--1.jpg");
		cart.addOrUpdateItem(prod1, Quantity.of(10));
		cart.addOrUpdateItem(prod2, Quantity.of(5));
		this.o = new ConcreteOrder(this.acc, Cash.CASH);
		cart.addItemsTo(o.getOrder());
		// o.getOrderLinesSize();

	}

	/**
	 * Testget order lines size.
	 */
	@Test
	public void testgetOrderLinesSize() {
		assertTrue("Anzahl der Orderline", o.getOrderLinesSize() == 2);
	}

	/**
	 * Testget total product number.
	 */
	@Test
	public void testgetTotalProductNumber() {
		assertTrue("Anzahl der Produkte", o.getTotalProductNumber() == 15);
	}

	/**
	 * Testset billing gender.
	 */
	@Test
	public void testBillingGender() {
		o.setBillingGender("male");
		assertEquals("male", o.getBillingGender());
	}

	/**
	 * Testget order.
	 */
	@Test
	public void testOrder() {
		assertNotNull(o.getOrder());
	}

	/**
	 * Testget billing first name.
	 */
	@Test
	public void testBillingFirstName() {
		o.setBillingFirstName("Hans");
		assertEquals("Hans", o.getBillingFirstName());
	}

	/**
	 * Test get billing last name.
	 */
	@Test
	public void testBillingLastName() {
		o.setBillingLastName("Wurst");
		assertEquals("Wurst", o.getBillingLastName());
	}

	/**
	 * Test get billing street.
	 */
	@Test
	public void testBillingStreet() {
		o.setBillingStreet("Straße");
		assertEquals("Straße", o.getBillingStreet());
	}

	/**
	 * Test get billing house number.
	 */
	@Test
	public void testBillingHouseNumber() {
		o.setBillingHouseNumber("5");
		assertEquals("5", o.getBillingHouseNumber());
	}

	/**
	 * Testget billing address line2.
	 */
	@Test
	public void testBillingAddressLine2() {
		o.setBillingAddressLine2("Zeile2");
		assertEquals("Zeile2", o.getBillingAddressLine2());
	}

	/**
	 * Test get billing zip code.
	 */
	@Test
	public void testBillingZipCode() {
		o.setBillingZipCode("12345");
		assertEquals("12345", o.getBillingZipCode());
	}

	/**
	 * Test get billing town.
	 */
	@Test
	public void testBillingTown() {
		o.setBillingTown("Stadt");
		assertEquals("Stadt", o.getBillingTown());
	}

	/**
	 * Test get shipping gender.
	 */
	@Test
	public void testShippingGender() {
		o.setShippingGender("male");
		assertEquals("male", o.getShippingGender());
	}

	/**
	 * Test get shipping first name.
	 */
	@Test
	public void testShippingFirstName() {
		o.setShippingFirstName("Hans");
		assertEquals("Hans", o.getShippingFirstName());
	}

	/**
	 * Test get shipping last name.
	 */
	@Test
	public void testShippingLastName() {
		o.setShippingLastName("Wurst");
		assertEquals("Wurst", o.getShippingLastName());
	}

	/**
	 * Test get shipping street.
	 */
	@Test
	public void testShippingStreet() {
		o.setShippingStreet("Straße");
		assertEquals("Straße", o.getShippingStreet());
	}

	/**
	 * Test get shipping house number.
	 */
	@Test
	public void testShippingHouseNumber() {
		o.setShippingHouseNumber("5");
		assertEquals("5", o.getShippingHouseNumber());
	}

	/**
	 * Testget shipping address line2.
	 */
	@Test
	public void testShippingAddressLine2() {
		o.setShippingAddressLine2("Zeile2");
		assertEquals("Zeile2", o.getShippingAddressLine2());
	}

	/**
	 * Testget shipping zip code.
	 */
	@Test
	public void testShippingZipCode() {
		o.setShippingZipCode("12345");
		assertEquals("12345", o.getShippingZipCode());
	}

	/**
	 * Testget shipping town.
	 */
	@Test
	public void testShippingTown() {
		o.setShippingTown("Stadt");
		assertEquals("Stadt", o.getShippingTown());
	}

	/**
	 * Test get order lines size.
	 */
	@Test
	public void testOrderLinesSize() {
		System.out.println("orderLinesSize:"+o.getOrderLinesSize());
		assertTrue("getOrderlineSize defekt", o.getOrderLinesSize() == 2);
	}

	/**
	 * Test set date ordered.
	 */
	@Test
	public void testDateOrdered() {
		LocalDateTime time = LocalDateTime.now();
		o.setDateOrdered(time);
		assertEquals(time, o.getDateOrdered());
	}

	/**
	 * Test get total product number.
	 */
	@Test
	public void testTotalProductNumber() {
		assertTrue("getTotalProductNumber defekt", o.getTotalProductNumber() == 15);
	}

	/**
	 * Test get returned.
	 */
	@Test
	public void testReturned() {
		o.setReturned(true);
		assertTrue("getReturned defekt", o.getReturned() == true);
	}
	
	/**
	 * Test set id.
	 */
	@Test
	public void testId() {
		o.setId(Long.valueOf(42));
		assertTrue("setId defekt", o.getId().longValue() == 42);

	}

	/**
	 * Test get user.
	 */
	@Test
	public void testUser() {
		assertEquals(this.acc, o.getUser());
	}

	/**
	 * Test set return reason.
	 */
	@Test
	public void testReturnReason() {
		o.setReturnReason("nein");
		assertEquals("nein", o.getReturnReason());
	}

}