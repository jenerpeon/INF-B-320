package internetkaufhaus.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.salespointframework.catalog.Product;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;

// TODO: Auto-generated Javadoc
/**
 * The Class ConcreteOrderUnitTest.
 */
public class ConcreteOrderUnitTest {

	/** The corder. */
	private ConcreteOrder corder;

	/** The acc. */
	private UserAccount acc;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		this.acc = mock(UserAccount.class);
		this.corder = new ConcreteOrder(acc, Cash.CASH);
	}

	/**
	 * Testget billing gender.
	 */
	@Test
	public void testgetBillingGender() {
		corder.setBillingGender("male");
		assertEquals("male", corder.getBillingGender());
	}

	/**
	 * Testset billing gender.
	 */
	@Test
	public void testsetBillingGender() {
		corder.setBillingGender("male");
		assertEquals("male", corder.getBillingGender());
	}

	/**
	 * Testget order.
	 */
	@Test
	public void testgetOrder() {
		assertNotNull(corder.getOrder());
	}

	/**
	 * Testget billing first name.
	 */
	@Test
	public void testgetBillingFirstName() {
		corder.setBillingFirstName("Hans");
		assertEquals("Hans", corder.getBillingFirstName());
	}

	/**
	 * Test get billing last name.
	 */
	@Test
	public void testGetBillingLastName() {
		corder.setBillingLastName("Wurst");
		assertEquals("Wurst", corder.getBillingLastName());
	}

	/**
	 * Test get billing street.
	 */
	@Test
	public void testGetBillingStreet() {
		corder.setBillingStreet("Straße");
		assertEquals("Straße", corder.getBillingStreet());
	}

	/**
	 * Test get billing house number.
	 */
	@Test
	public void testGetBillingHouseNumber() {
		corder.setBillingHouseNumber("5");
		assertEquals("5", corder.getBillingHouseNumber());
	}

	/**
	 * Testget billing address line2.
	 */
	@Test
	public void testgetBillingAddressLine2() {
		corder.setBillingAddressLine2("Zeile2");
		assertEquals("Zeile2", corder.getBillingAddressLine2());
	}

	/**
	 * Test get billing zip code.
	 */
	@Test
	public void testGetBillingZipCode() {
		corder.setBillingZipCode("12345");
		assertEquals("12345", corder.getBillingZipCode());
	}

	/**
	 * Test get billing town.
	 */
	@Test
	public void testGetBillingTown() {
		corder.setBillingTown("Stadt");
		assertEquals("Stadt", corder.getBillingTown());
	}

	/**
	 * Test get shipping gender.
	 */
	@Test
	public void testGetShippingGender() {
		corder.setShippingGender("male");
		assertEquals("male", corder.getShippingGender());
	}

	/**
	 * Test set shipping gender.
	 */
	@Test
	public void testSetShippingGender() {
		corder.setShippingGender("male");
		assertEquals("male", corder.getShippingGender());
	}

	/**
	 * Test get shipping first name.
	 */
	@Test
	public void testGetShippingFirstName() {
		corder.setShippingFirstName("Hans");
		assertEquals("Hans", corder.getShippingFirstName());
	}

	/**
	 * Test get shipping last name.
	 */
	@Test
	public void testGetShippingLastName() {
		corder.setShippingLastName("Wurst");
		assertEquals("Wurst", corder.getShippingLastName());
	}

	/**
	 * Test get shipping street.
	 */
	@Test
	public void testGetShippingStreet() {
		corder.setShippingStreet("Straße");
		assertEquals("Straße", corder.getShippingStreet());
	}

	/**
	 * Test get shipping house number.
	 */
	@Test
	public void testGetShippingHouseNumber() {
		corder.setShippingHouseNumber("5");
		assertEquals("5", corder.getShippingHouseNumber());
	}

	/**
	 * Testget shipping address line2.
	 */
	@Test
	public void testgetShippingAddressLine2() {
		corder.setShippingAddressLine2("Zeile2");
		assertEquals("Zeile2", corder.getShippingAddressLine2());
	}

	/**
	 * Testget shipping zip code.
	 */
	@Test
	public void testgetShippingZipCode() {
		corder.setShippingZipCode("12345");
		assertEquals("12345", corder.getShippingZipCode());
	}

	/**
	 * Testget shipping town.
	 */
	@Test
	public void testgetShippingTown() {
		corder.setShippingTown("Stadt");
		assertEquals("Stadt", corder.getShippingTown());
	}

	/**
	 * Test get order lines size.
	 */
	@Test
	public void testGetOrderLinesSize() {
		OrderLine ol = mock(OrderLine.class);
		OrderLine ol2 = mock(OrderLine.class);
		Order order = this.corder.getOrder();
		order.add(ol);
		order.add(ol2);
		assertTrue("getOrderlineSize defekt", corder.getOrderLinesSize() == 2);
	}

	/**
	 * Test set date ordered.
	 */
	@Test
	public void testSetDateOrdered() {
		LocalDateTime time = LocalDateTime.now();
		corder.setDateOrdered(time);
		assertEquals(time, corder.getDateOrdered());
	}

	/**
	 * Test get total product number.
	 */
	@Test
	public void testGetTotalProductNumber() {
		Product prod = new Product("Produkt", Money.of(10, EURO));
		OrderLine ol = new OrderLine(prod, Quantity.of(2));
		OrderLine ol2 = new OrderLine(prod, Quantity.of(3));
		Order order = this.corder.getOrder();
		order.add(ol);
		order.add(ol2);
		assertTrue("getTotalProductNumber defekt", corder.getTotalProductNumber() == 5);
	}

	/**
	 * Test get date ordered.
	 */
	@Test
	public void testGetDateOrdered() {
		LocalDateTime time = LocalDateTime.now();
		corder.setDateOrdered(time);
		assertEquals(time, corder.getDateOrdered());
	}

	/**
	 * Test get returned.
	 */
	@Test
	public void testGetReturned() {
		corder.setReturned(true);
		assertTrue("getReturned defekt", corder.getReturned() == true);
	}

	/**
	 * Testset billing first name.
	 */
	@Test
	public void testsetBillingFirstName() {
		corder.setBillingFirstName("Hans");
		assertEquals("Hans", corder.getBillingFirstName());
	}

	/**
	 * Testset billing last name.
	 */
	@Test
	public void testsetBillingLastName() {
		corder.setBillingLastName("Wurst");
		assertEquals("Wurst", corder.getBillingLastName());
	}

	/**
	 * Testset billing street.
	 */
	@Test
	public void testsetBillingStreet() {
		corder.setBillingStreet("Straße");
		assertEquals("Straße", corder.getBillingStreet());
	}

	/**
	 * Testset billing house number.
	 */
	@Test
	public void testsetBillingHouseNumber() {
		corder.setBillingHouseNumber("5");
		assertEquals("5", corder.getBillingHouseNumber());
	}

	/**
	 * Testset billing address line2.
	 */
	@Test
	public void testsetBillingAddressLine2() {
		corder.setBillingAddressLine2("Zeile2");
		assertEquals("Zeile2", corder.getBillingAddressLine2());
	}

	/**
	 * Testset billing zip code.
	 */
	@Test
	public void testsetBillingZipCode() {
		corder.setBillingZipCode("12345");
		assertEquals("12345", corder.getBillingZipCode());
	}

	/**
	 * Testset billing town.
	 */
	@Test
	public void testsetBillingTown() {
		corder.setBillingTown("Stadt");
		assertEquals("Stadt", corder.getBillingTown());
	}

	/**
	 * Testset shipping first name.
	 */
	@Test
	public void testsetShippingFirstName() {
		corder.setShippingFirstName("Hans");
		assertEquals("Hans", corder.getShippingFirstName());
	}

	/**
	 * Testset shipping last name.
	 */
	@Test
	public void testsetShippingLastName() {
		corder.setShippingLastName("Wurst");
		assertEquals("Wurst", corder.getShippingLastName());
	}

	/**
	 * Testset shipping street.
	 */
	@Test
	public void testsetShippingStreet() {
		corder.setShippingStreet("Straße");
		assertEquals("Straße", corder.getShippingStreet());
	}

	/**
	 * Testset shipping house number.
	 */
	@Test
	public void testsetShippingHouseNumber() {
		corder.setShippingHouseNumber("5");
		assertEquals("5", corder.getShippingHouseNumber());
	}

	/**
	 * Testset shipping address line2.
	 */
	@Test
	public void testsetShippingAddressLine2() {
		corder.setShippingAddressLine2("Zeile2");
		assertEquals("Zeile2", corder.getShippingAddressLine2());
	}

	/**
	 * Testset shipping zip code.
	 */
	@Test
	public void testsetShippingZipCode() {
		corder.setShippingZipCode("12345");
		assertEquals("12345", corder.getShippingZipCode());
	}

	/**
	 * Testset shipping town.
	 */
	@Test
	public void testsetShippingTown() {
		corder.setShippingTown("Stadt");
		assertEquals("Stadt", corder.getShippingTown());
	}

	/**
	 * Test set id.
	 */
	@Test
	public void testSetId() {
		corder.setId(Long.valueOf(42));
		assertTrue("setId defekt", corder.getId().longValue() == 42);

	}

	/**
	 * Test get id.
	 */
	@Test
	public void testGetId() {
		corder.setId(Long.valueOf(42));
		assertTrue("setId defekt", corder.getId().longValue() == 42);

	}

	/**
	 * Test get user.
	 */
	@Test
	public void testGetUser() {
		assertEquals(this.acc, corder.getUser());
	}

	/**
	 * Test set return reason.
	 */
	@Test
	public void testSetReturnReason() {
		corder.setReturnReason("nein");
		assertEquals("nein", corder.getReturnReason());
	}

}
