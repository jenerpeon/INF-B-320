package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EditCustomerFormTest {
	
	private EditCustomerForm model = new EditCustomerForm();
	
	@Test
	public void firstNameTest() {
		model.setFirstname("Martin");
		assertTrue("Vorname erhalten", model.getFirstname().equals("Martin"));
	}
	
	/**
	 * Last name test.
	 */
	@Test
	public void lastNameTest() {
		model.setLastname("Bens");
		assertTrue("Nachname erhalten", model.getLastname().equals("Bens"));
	}
	
	/**
	 * Password test.
	 */
	@Test
	public void passwordTest() {
		model.setPassword("12345678");
		assertTrue("Passwort erhalten", model.getPassword().equals("12345678"));
	}
	
	/**
	 * Password repeat test.
	 */
	@Test
	public void passwordRepeatTest() {
		model.setPasswordrepeat("12345678");
		assertTrue("Passwortwiederholung erhalten", model.getPasswordrepeat().equals("12345678"));
	}
	
	/**
	 * Email test.
	 */
	@Test
	public void emailTest() {
		model.setEmail("spigo@gmail.com");
		assertTrue("EMail-Adresse erhalten", model.getEmail().equals("spigo@gmail.com"));
	}
	
	/**
	 * Address test.
	 */
	@Test
	public void streetTest() {
		model.setStreet("Meine Adresse");
		assertTrue("Adresse erhalten", model.getStreet().equals("Meine Adresse"));
	}
	
	@Test
	public void houseNumberTest() {
		model.setHouseNumber("3");
		assertTrue("Hausnummer erhalten", model.getHouseNumber().equals("3"));
	}
	
	/**
	 * Zip code test.
	 */
	@Test
	public void zipCodeTest() {
		model.setZipCode("01217");
		assertTrue("Postleitzahl erhalten", model.getZipCode().equals("01217"));
	}
	
	/**
	 * City test.
	 */
	@Test
	public void cityTest() {
		model.setCity("Dresden");
		assertTrue("Stadt erhalten", model.getCity().equals("Dresden"));
	}

}
