package internetkaufhaus.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

	/** The model. */
	private ConcreteOrder model;
	
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
		this.model = new ConcreteOrder(this.acc, Cash.CASH);
		cart.addItemsTo(model);

	}

	/**
	 * Testget order lines size.
	 */
	@Test
	public void testgetOrderLinesSize() {
		assertTrue("Anzahl der Orderline", model.getOrderLinesSize() == 2);
	}

	/**
	 * Testget total product number.
	 */
	@Test
	public void testgetTotalProductNumber() {
		assertTrue("Anzahl der Produkte", model.getTotalProductNumber() == 15);
	}

	/**
	 * Testset billing gender.
	 */
	@Test
	public void testBillingGender() {
		model.setBillingGender("male");
		assertEquals("male", model.getBillingGender());
	}

	/**
	 * Testget billing first name.
	 */
	@Test
	public void testBillingFirstName() {
		model.setBillingFirstName("Hans");
		assertEquals("Hans", model.getBillingFirstName());
	}

	/**
	 * Test get billing last name.
	 */
	@Test
	public void testBillingLastName() {
		model.setBillingLastName("Wurst");
		assertEquals("Wurst", model.getBillingLastName());
	}

	/**
	 * Test get billing street.
	 */
	@Test
	public void testBillingStreet() {
		model.setBillingStreet("Straße");
		assertEquals("Straße", model.getBillingStreet());
	}

	/**
	 * Test get billing house number.
	 */
	@Test
	public void testBillingHouseNumber() {
		model.setBillingHouseNumber("5");
		assertEquals("5", model.getBillingHouseNumber());
	}

	/**
	 * Testget billing address line2.
	 */
	@Test
	public void testBillingAddressLine2() {
		model.setBillingAddressLine2("Zeile2");
		assertEquals("Zeile2", model.getBillingAddressLine2());
	}

	/**
	 * Test get billing zip code.
	 */
	@Test
	public void testBillingZipCode() {
		model.setBillingZipCode("12345");
		assertEquals("12345", model.getBillingZipCode());
	}

	/**
	 * Test get billing town.
	 */
	@Test
	public void testBillingTown() {
		model.setBillingTown("Stadt");
		assertEquals("Stadt", model.getBillingTown());
	}

	/**
	 * Test get shipping gender.
	 */
	@Test
	public void testShippingGender() {
		model.setShippingGender("male");
		assertEquals("male", model.getShippingGender());
	}

	/**
	 * Test get shipping first name.
	 */
	@Test
	public void testShippingFirstName() {
		model.setShippingFirstName("Hans");
		assertEquals("Hans", model.getShippingFirstName());
	}

	/**
	 * Test get shipping last name.
	 */
	@Test
	public void testShippingLastName() {
		model.setShippingLastName("Wurst");
		assertEquals("Wurst", model.getShippingLastName());
	}

	/**
	 * Test get shipping street.
	 */
	@Test
	public void testShippingStreet() {
		model.setShippingStreet("Straße");
		assertEquals("Straße", model.getShippingStreet());
	}

	/**
	 * Test get shipping house number.
	 */
	@Test
	public void testShippingHouseNumber() {
		model.setShippingHouseNumber("5");
		assertEquals("5", model.getShippingHouseNumber());
	}

	/**
	 * Testget shipping address line2.
	 */
	@Test
	public void testShippingAddressLine2() {
		model.setShippingAddressLine2("Zeile2");
		assertEquals("Zeile2", model.getShippingAddressLine2());
	}

	/**
	 * Testget shipping zip code.
	 */
	@Test
	public void testShippingZipCode() {
		model.setShippingZipCode("12345");
		assertEquals("12345", model.getShippingZipCode());
	}

	/**
	 * Testget shipping town.
	 */
	@Test
	public void testShippingTown() {
		model.setShippingTown("Stadt");
		assertEquals("Stadt", model.getShippingTown());
	}

	/**
	 * Test get order lines size.
	 */
	@Test
	public void testOrderLinesSize() {
		assertTrue("getOrderlineSize defekt", model.getOrderLinesSize() == 2);
	}

	/**
	 * Test set date ordered.
	 */
	@Test
	public void testDateOrdered() {
		Long time = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(1));
		model.setDateOrdered(time);
		assertEquals(time, model.getDateOrdered());
	}

	/**
	 * Test get total product number.
	 */
	@Test
	public void testTotalProductNumber() {
		assertTrue("getTotalProductNumber defekt", model.getTotalProductNumber() == 15);
	}

	/**
	 * Test get returned.
	 */
	@Test
	public void testReturned() {
		model.setReturned(true);
		assertTrue("getReturned defekt", model.getReturned() == true);
	}

	/**
	 * Test get user.
	 */
	@Test
	public void testUser() {
		assertEquals(this.acc, model.getUser());
	}

	/**
	 * Test set return reason.
	 */
	@Test
	public void testReturnReason() {
		model.setReturnReason("nein");
		assertEquals("nein", model.getReturnReason());
	}

}