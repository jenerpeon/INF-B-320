package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StandardUserFormTest {
	
	private StandardUserForm model = new StandardUserForm();
	
	@Test
	public void firsNameTest() {
		model.setFirstname("Martin");
		assertTrue("Vorname erhalten", model.getFirstname().equals("Martin"));
	}
	
	@Test
	public void lastNameTest() {
		model.setLastname("Bens");
		assertTrue("Nachname erhalten", model.getLastname().equals("Bens"));
	}
	
	@Test
	public void nameTest() {
		model.setName("spigo");
		assertTrue("Benutzername erhalten", model.getName().equals("spigo"));
	}
	
	@Test
	public void passwordTest() {
		model.setPassword("12345678");
		assertTrue("Passwort erhalten", model.getPassword().equals("12345678"));
	}
	
	@Test
	public void passwordRepeatTest() {
		model.setPasswordrepeat("12345678");
		assertTrue("Passwortwiederholung erhalten", model.getPasswordrepeat().equals("12345678"));
	}
	
	@Test
	public void emailTest() {
		model.setEmail("spigo@gmail.com");
		assertTrue("EMail-Adresse erhalten", model.getEmail().equals("spigo@gmail.com"));
	}
	
	@Test
	public void addressTest() {
		model.setAddress("Meine Adresse");
		assertTrue("Adresse erhalten", model.getAddress().equals("Meine Adresse"));
	}
	
	@Test
	public void zipCodeTest() {
		model.setZipCode("01217");
		assertTrue("Postleitzahl erhalten", model.getZipCode().equals("01217"));
	}
	
	@Test
	public void cityTest() {
		model.setCity("Dresden");
		assertTrue("Stadt erhalten", model.getCity().equals("Dresden"));
	}
	
	
}