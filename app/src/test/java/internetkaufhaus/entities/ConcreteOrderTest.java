package internetkaufhaus.entities;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

public class ConcreteOrderTest {

	private ConcreteOrder mainObject;
	private Order order;
	private ConcreteUserAccount user;
	@Autowired
	private UserAccountManager userAccountManager;

	@Before
	public void init() {
		LocalDateTime now = LocalDateTime.now();
		user = new ConcreteUserAccount("username", "password", Role.of("ROLE_CUSTOMER"), userAccountManager);
		order = new Order(user.getUserAccount());
		mainObject = new ConcreteOrder("Herr", "Vorname", "Nachname", "Straße", "12a", "Wohnung 12b", "12345", "Stadt",
				"Frau", "Vor Name", "Nach Name", "Stra Aaße", "34f", "Unter der Treppe", "09876", "Nürgendwo", now,
				order);
	}

	@Test
	public void testSetBillingGender() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setBillingGender(test);
			assertEquals(
					"Die Methoden setBillingGender() und getBillingGender() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getBillingGender());
		}
	}

	@Test
	public void testSetBillingFirstName() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setBillingFirstName(test);
			assertEquals(
					"Die Methoden setBillingFirstName() und getBillingFirstName() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getBillingFirstName());
		}
	}

	@Test
	public void testSetBillingLastName() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setBillingLastName(test);
			assertEquals(
					"Die Methoden setBillingLastName() und getBillingLastName() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getBillingLastName());
		}
	}

	@Test
	public void testSetBillingStreet() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setBillingStreet(test);
			assertEquals(
					"Die Methoden setBillingStreet() und getBillingStreet() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getBillingStreet());
		}
	}

	@Test
	public void testSetBillingHouseNumber() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setBillingHouseNumber(test);
			assertEquals(
					"Die Methoden setBillingHouseNumber() und getBillingHouseNumber() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getBillingHouseNumber());
		}
	}

	@Test
	public void testSetBillingAddressLine2() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setBillingAddressLine2(test);
			assertEquals(
					"Die Methoden setBillingAddressLine2() und getBillingAddressLine2() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getBillingAddressLine2());
		}
	}

	@Test
	public void testSetBillingZipCode() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setBillingZipCode(test);
			assertEquals(
					"Die Methoden setBillingZipCode() und getBillingZipCode() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getBillingZipCode());
		}
	}

	@Test
	public void testSetBillingTown() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setBillingTown(test);
			assertEquals("Die Methoden setBillingTown() und getBillingTown() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getBillingTown());
		}
	}

	@Test
	public void testSetShippingGender() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setShippingGender(test);
			assertEquals(
					"Die Methoden setShippingGender() und getShippingGender() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getShippingGender());
		}
	}

	@Test
	public void testSetShippingFirstName() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setShippingFirstName(test);
			assertEquals(
					"Die Methoden setShippingFirstName() und getShippingFirstName() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getShippingFirstName());
		}
	}

	@Test
	public void testSetShippingLastName() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setShippingLastName(test);
			assertEquals(
					"Die Methoden setShippingLastName() und getShippingLastName() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getShippingLastName());
		}
	}

	@Test
	public void testSetShippingStreet() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setShippingStreet(test);
			assertEquals(
					"Die Methoden setShippingStreet() und getShippingStreet() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getShippingStreet());
		}
	}

	@Test
	public void testSetShippingHouseNumber() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setShippingHouseNumber(test);
			assertEquals(
					"Die Methoden setShippingHouseNumber() und getShippingHouseNumber() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getShippingHouseNumber());
		}
	}

	@Test
	public void testSetShippingAddressLine2() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setShippingAddressLine2(test);
			assertEquals(
					"Die Methoden setShippingAddressLine2() und getShippingAddressLine2() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getShippingAddressLine2());
		}
	}

	@Test
	public void testSetShippingZipCode() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setShippingZipCode(test);
			assertEquals(
					"Die Methoden setShippingZipCode() und getShippingZipCode() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getShippingZipCode());
		}
	}

	@Test
	public void testSetShippingTown() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setShippingTown(test);
			assertEquals(
					"Die Methoden setShippingTown() und getShippingTown() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getShippingTown());
		}
	}

	@Test
	public void testSetUser() {
		ConcreteUserAccount user = new ConcreteUserAccount();
		mainObject.setUserAccount(user.getUserAccount());
		assertEquals("Die Methoden setUserAccount() und getUserAccount() auf ConcreteOrder stimmen nicht ueberein.",
				user.getUserAccount(), mainObject.getUser());
	}

	@Test
	public void testSetReturned() {
		List<Boolean> tests = new ArrayList<Boolean>();
		tests.add(false);
		tests.add(true);
		for (Boolean test : tests) {
			mainObject.setReturned(test);
			assertEquals("Die Methoden setReturned() und getReturned() auf ConcreteOrder stimmen nicht ueberein.", test,
					mainObject.getReturned());
		}
	}

	@Test
	public void testSetStatus() {
		List<OrderStatus> tests = new ArrayList<OrderStatus>();
		tests.add(OrderStatus.CANCELLED);
		tests.add(OrderStatus.COMPLETED);
		tests.add(OrderStatus.OPEN);
		tests.add(OrderStatus.PAID);
		for (OrderStatus test : tests) {
			mainObject.setStatus(test);
			assertEquals("Die Methoden setStatus() und getStatus() auf ConcreteOrder stimmen nicht ueberein.", test,
					mainObject.getStatus());
		}
	}

	@Test
	public void testSetReturnReason() {
		List<String> tests = new ArrayList<String>();
		tests.add("Herr");
		tests.add("Frau");
		for (String test : tests) {
			mainObject.setReturnReason(test);
			assertEquals(
					"Die Methoden setReturnReason() und getReturnReason() auf ConcreteOrder stimmen nicht ueberein.",
					test, mainObject.getReturnReason());
		}
	}
}
