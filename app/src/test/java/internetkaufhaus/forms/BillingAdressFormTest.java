package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BillingAdressFormTest {
	
	private BillingAdressForm model = new BillingAdressForm();
	private String gender = "Herr";
	private String firstName = "Martin";
	private String lastName = "Bens";
	private String street = "Wundtstrasse";
	private String houseNumber = "3";
	private String adressLine2 = "Hinterhaus";
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
	public void testBillingAdressLine2() {
		model.setBillingAdressLine2(adressLine2);
		assertTrue("Adresszusatz erhalten", model.getBillingAdressLine2().equals(adressLine2));
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
	public void testBillingAdress() {
		List<String> billingAdress = new ArrayList<String>();
		billingAdress.add(gender);
		billingAdress.add(firstName);
		billingAdress.add(lastName);
		billingAdress.add(street);
		billingAdress.add(houseNumber);
		billingAdress.add(adressLine2);
		billingAdress.add(zipCode);
		billingAdress.add(town);
		
		BillingAdressForm model = new BillingAdressForm(gender, firstName, lastName, street, houseNumber, adressLine2, zipCode, town);
		
		assertTrue("Zahlungsadresse erhalten", model.getBillingAdress().equals(billingAdress));
	}
	
}

