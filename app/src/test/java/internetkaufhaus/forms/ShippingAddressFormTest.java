package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ShippingAddressFormTest {
	
	private ShippingAddressForm model = new ShippingAddressForm();
	private String gender = "Herr";
	private String firstName = "Martin";
	private String lastName = "Bens";
	private String street = "Wundtstrasse";
	private String houseNumber = "3";
	private String addressLine2 = "Hinterhaus";
	private String zipCode = "01217";
	private String town = "Dresden";
	
	@Test
	public void testShippingGender() {
		model.setShippingGender(gender);
		assertTrue("Geschlecht erhalten", model.getShippingGender().equals(gender));
	}
	
	@Test
	public void testShippingFirstName() {
		model.setShippingFirstName(firstName);
		assertTrue("Vorname erhalten", model.getShippingFirstName().equals(firstName));
	}

	@Test
	public void testShippingLastName() {
		model.setShippingLastName(lastName);
		assertTrue("Nachname erhalten", model.getShippingLastName().equals(lastName));
	}

	@Test
	public void testShippingStreet() {
		model.setShippingStreet(street);
		assertTrue("Straßenname erhalten", model.getShippingStreet().equals(street));
	}
	
	@Test
	public void testShippingHouseNumber() {
		model.setShippingHouseNumber("3");
		assertTrue("Hausnummer erhalten", model.getShippingHouseNumber().equals("3"));
	}
	
	@Test
	public void testShippingAddressLine2() {
		model.setShippingAddressLine2(addressLine2);
		assertTrue("Adresszusatz erhalten", model.getShippingAddressLine2().equals(addressLine2));
	}

	@Test
	public void testShippingZipCode() {
		model.setShippingZipCode(zipCode);
		assertTrue("Postleitzahl erhalten", model.getShippingZipCode().equals(zipCode));
	}
	
	@Test
	public void testShippingTown() {
		model.setShippingTown(town);
		assertTrue("Stadt erhalten", model.getShippingTown().equals(town));
	}
	
	@Test
	public void testShippingAddress() {
		List<String> shippingAddress = new ArrayList<String>();
		shippingAddress.add(gender);
		shippingAddress.add(firstName);
		shippingAddress.add(lastName);
		shippingAddress.add(street);
		shippingAddress.add(houseNumber);
		shippingAddress.add(addressLine2);
		shippingAddress.add(zipCode);
		shippingAddress.add(town);
		
		ShippingAddressForm model = new ShippingAddressForm(gender, firstName, lastName, street, houseNumber, addressLine2, zipCode, town);
		
		assertTrue("Zahlungsadresse erhalten", model.getShippingAddress().equals(shippingAddress));
	}
	
}

