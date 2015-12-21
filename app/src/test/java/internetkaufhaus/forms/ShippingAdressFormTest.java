package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ShippingAdressFormTest {
	
	private ShippingAdressForm model = new ShippingAdressForm();
	private String gender = "Herr";
	private String firstName = "Martin";
	private String lastName = "Bens";
	private String street = "Wundtstrasse";
	private String houseNumber = "3";
	private String adressLine2 = "Hinterhaus";
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
	public void testShippingAdressLine2() {
		model.setShippingAdressLine2(adressLine2);
		assertTrue("Adresszusatz erhalten", model.getShippingAdressLine2().equals(adressLine2));
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
	public void testShippingAdress() {
		List<String> ShippingAdress = new ArrayList<String>();
		ShippingAdress.add(gender);
		ShippingAdress.add(firstName);
		ShippingAdress.add(lastName);
		ShippingAdress.add(street);
		ShippingAdress.add(houseNumber);
		ShippingAdress.add(adressLine2);
		ShippingAdress.add(zipCode);
		ShippingAdress.add(town);
		
		ShippingAdressForm model = new ShippingAdressForm(gender, firstName, lastName, street, houseNumber, adressLine2, zipCode, town);
		
		assertTrue("Zahlungsadresse erhalten", model.getShippingAdress().equals(ShippingAdress));
	}
	
}

