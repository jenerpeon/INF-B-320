package internetkaufhaus.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;



import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.catalog.Product;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;

public class ConcreteOrderUnitTest {

	private ConcreteOrder corder;
	private UserAccount acc;
	
	@Before
	public void init()
	{
		this.acc = mock(UserAccount.class);
		this.corder = new ConcreteOrder(acc, Cash.CASH); 
	}
	@Test
	public void testgetBillingGender()
	{
		corder.setBillingGender("male");
		assertEquals("male", corder.getBillingGender());
	}
	@Test
	public void testsetBillingGender()
	{
		corder.setBillingGender("male");
		assertEquals("male", corder.getBillingGender());
	}
	@Test
	public void testgetOrder()
	{
		assertNotNull(corder.getOrder());
	}
	@Test
	public void testgetBillingFirstName()
	{
		corder.setBillingFirstName("Hans");
		assertEquals("Hans", corder.getBillingFirstName());
	}
	@Test
	public void testGetBillingLastName()
	{
		corder.setBillingLastName("Wurst");
		assertEquals("Wurst", corder.getBillingLastName());
	}
	@Test
	public void testGetBillingStreet()
	{
		corder.setBillingStreet("Straße");
		assertEquals("Straße", corder.getBillingStreet());
	}
	@Test
	public void testGetBillingHouseNumber()
	{
		corder.setBillingHouseNumber("5");
		assertEquals("5", corder.getBillingHouseNumber());
	}
	@Test
	public void testgetBillingAddressLine2()
	{
		corder.setBillingAddressLine2("Zeile2");
		assertEquals("Zeile2", corder.getBillingAddressLine2());
	}
	@Test
	public void getBillingZipCode()
	{
		corder.setBillingZipCode("12345");
		assertEquals("12345", corder.getBillingZipCode());
	}
	@Test
	public void getBillingTown()
	{
		corder.setBillingTown("Stadt");
		assertEquals("Stadt", corder.getBillingTown());
	}
	@Test
	public void testgetShippingGender()
	{
		corder.setShippingGender("male");
		assertEquals("male", corder.getShippingGender());
	}
	@Test
	public void testsetShippingGender()
	{
		corder.setShippingGender("male");
		assertEquals("male", corder.getShippingGender());
	}
	@Test
	public void testgetShippingFirstName()
	{
		corder.setShippingFirstName("Hans");
		assertEquals("Hans", corder.getShippingFirstName());
	}
	@Test
	public void testGetShippingLastName()
	{
		corder.setShippingLastName("Wurst");
		assertEquals("Wurst", corder.getShippingLastName());
	}
	@Test
	public void testGetShippingStreet()
	{
		corder.setShippingStreet("Straße");
		assertEquals("Straße", corder.getShippingStreet());
	}
	@Test
	public void testGetShippingHouseNumber()
	{
		corder.setShippingHouseNumber("5");
		assertEquals("5", corder.getShippingHouseNumber());
	}
	@Test
	public void testgetShippingAddressLine2()
	{
		corder.setShippingAddressLine2("Zeile2");
		assertEquals("Zeile2", corder.getShippingAddressLine2());
	}
	@Test
	public void testgetShippingZipCode()
	{
		corder.setShippingZipCode("12345");
		assertEquals("12345", corder.getShippingZipCode());
	}
	@Test
	public void testgetShippingTown()
	{
		corder.setShippingTown("Stadt");
		assertEquals("Stadt", corder.getShippingTown());
	} 
	@Test
	public void testGetOrderLinesSize()
	{
		OrderLine ol = mock(OrderLine.class);
		OrderLine ol2 = mock(OrderLine.class);
		Order order = this.corder.getOrder();
		order.add(ol);
		order.add(ol2);
		assertTrue("getOrderlineSize defekt", corder.getOrderLinesSize()==2);
	}
	@Test
	public void testSetDateOrdered()
	{
		LocalDateTime time = LocalDateTime.now();
		corder.setDateOrdered(time);
		assertEquals(time, corder.getDateOrdered());
	}
	@Test
	public void testGetTotalProductNumber()
	{
		Product prod = new Product("Produkt", Money.of(10, EURO));
		OrderLine ol = new OrderLine(prod, Quantity.of(2));
		OrderLine ol2 = new OrderLine(prod, Quantity.of(3));
		Order order = this.corder.getOrder();
		order.add(ol);
		order.add(ol2);
		assertTrue("getTotalProductNumber defekt", corder.getTotalProductNumber()==5);
	}
	@Test
	public void testGetDateOrdered()
	{
		LocalDateTime time = LocalDateTime.now();
		corder.setDateOrdered(time);
		assertEquals(time, corder.getDateOrdered());
	}
	@Test
	public void testGetReturned()
	{
		corder.setReturned(true);
		assertTrue("getReturned defekt", corder.getReturned()==true);
	}
//	@Test
//	public void testsetBillingFirstName()
//	{
//		corder.setBillingFirstName("Hans");
//		assertEquals("Hans", corder.getBillingFirstName());
//	}
//	@Test
//	public void testsetBillingLastName()
//	{
//		corder.setBillingLastName("Wurst");
//		assertEquals("Wurst", corder.getBillingLastName());
//	}
//	@Test
//	public void testsetBillingStreet()
//	{
//		corder.setBillingStreet("Straße");
//		assertEquals("Straße", corder.getBillingStreet());
//	}
//	@Test
//	public void testsetBillingHouseNumber()
//	{
//		corder.setBillingHouseNumber("5");
//		assertEquals("5", corder.getBillingHouseNumber());
//	}
//	@Test
//	public void testsetBillingAddressLine2()
//	{
//		corder.setBillingAddressLine2("Zeile2");
//		assertEquals("Zeile2", corder.getBillingAddressLine2());
//	}
//	@Test
//	public void testsetBillingZipCode()
//	{
//		corder.setBillingZipCode("12345");
//		assertEquals("12345", corder.getBillingZipCode());
//	}
//	@Test
//	public void testsetBillingTown()
//	{
//		corder.setBillingTown("Stadt");
//		assertEquals("Stadt", corder.getBillingTown());
//	}
//	@Test
//	public void testsetShippingFirstName()
//	{
//		corder.setShippingFirstName("Hans");
//		assertEquals("Hans", corder.getShippingFirstName());
//	}
//	@Test
//	public void testsetShippingLastName()
//	{
//		corder.setShippingLastName("Wurst");
//		assertEquals("Wurst", corder.getShippingLastName());
//	}
//	@Test
//	public void testsetShippingStreet()
//	{
//		corder.setShippingStreet("Straße");
//		assertEquals("Straße", corder.getShippingStreet());
//	}
//	@Test
//	public void testsetShippingHouseNumber()
//	{
//		corder.setShippingHouseNumber("5");
//		assertEquals("5", corder.getShippingHouseNumber());
//	}
//	@Test
//	public void testsetShippingAddressLine2()
//	{
//		corder.setShippingAddressLine2("Zeile2");
//		assertEquals("Zeile2", corder.getShippingAddressLine2());
//	}
//	@Test
//	public void testsetShippingZipCode()
//	{
//		corder.setShippingZipCode("12345");
//		assertEquals("12345", corder.getShippingZipCode());
//	}
//	@Test
//	public void testsetShippingTown()
//	{
//		corder.setShippingTown("Stadt");
//		assertEquals("Stadt", corder.getShippingTown());
//	}
//	@Test
//	public void testSetId()
//	{
//		corder.setId(Long.valueOf(42));
//		assertTrue("setId defekt", corder.getId().longValue()==42);
//		
//	}
	@Test
	public void testGetId()
	{
		corder.setId(Long.valueOf(42));
		assertTrue("setId defekt", corder.getId().longValue()==42);
		
	}
	@Test
	public void testGetUser()
	{
		assertEquals(this.acc, corder.getUser());
	}
	@Test
	public void testSetReturnReason()
	{
		corder.setReturnReason("nein");
		assertEquals("nein", corder.getReturnReason());
	}
	
	
}
