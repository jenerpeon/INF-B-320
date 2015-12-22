package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BillingAddressFormTest {
	
	private BillingAddressForm model = new BillingAddressForm();
	private String gender = "Herr";
	private String firstName = "Martin";
	private String lastName = "Bens";
	private String street = "Wundtstrasse";
	private String houseNumber = "3";
	private String addressLine2 = "Hinterhaus";
	private String zipCode = "01217";
	private String town = "Dresden";
	
	@Test
	public void testBillingGender() {
		model.setBillingGender(gender);
		assertTrue("Geschlecht erhalten", model.getBillingGender().equals(gender));
	}
	
	@Test
	public void testBillingFirstName() {
		model.setBillingFirstName(firstName);
		assertTrue("Vorname erhalten", model.getBillingFirstName().equals(firstName));
	}

	@Test
	public void testBillingLastName() {
		model.setBillingLastName(lastName);
		assertTrue("Nachname erhalten", model.getBillingLastName().equals(lastName));
	}

	@Test
	public void testBillingStreet() {
		model.setBillingStreet(street);
		assertTrue("Straßenname erhalten", model.getBillingStreet().equals(street));
	}
	
	@Test
	public void testBillingHouseNumber() {
		model.setBillingHouseNumber("3");
		assertTrue("Hausnummer erhalten", model.getBillingHouseNumber().equals("3"));
	}
	
	@Test
	public void testBillingAddressLine2() {
		model.setBillingAddressLine2(addressLine2);
		assertTrue("Adresszusatz erhalten", model.getBillingAddressLine2().equals(addressLine2));
	}

	@Test
	public void testBillingZipCode() {
		model.setBillingZipCode(zipCode);
		assertTrue("Postleitzahl erhalten", model.getBillingZipCode().equals(zipCode));
	}
	
	@Test
	public void testBillingTown() {
		model.setBillingTown(town);
		assertTrue("Stadt erhalten", model.getBillingTown().equals(town));
	}
	
	@Test
	public void testBillingAddress() {
		List<String> billingAddress = new ArrayList<String>();
		billingAddress.add(gender);
		billingAddress.add(firstName);
		billingAddress.add(lastName);
		billingAddress.add(street);
		billingAddress.add(houseNumber);
		billingAddress.add(addressLine2);
		billingAddress.add(zipCode);
		billingAddress.add(town);
		
		BillingAddressForm model = new BillingAddressForm(gender, firstName, lastName, street, houseNumber, addressLine2, zipCode, town);
		
		assertTrue("Zahlungsadresse erhalten", model.getBillingAddress().equals(billingAddress));
	}
	
}

