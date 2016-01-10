package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class EditUserFormTest.
 */
public class EditUserFormTest {
	
	/** The model. */
	EditUserForm model = new EditUserForm();
	
	/**
	 * Id test.
	 */
	@Test
	public void idTest() {
		model.setId((long) 53346);
		assertTrue("ID erhalten", model.getId().equals((long) 53346));
	}
	
	/**
	 * Rolename test.
	 */
	@Test
	public void rolenameTest() {
		model.setRolename("Sucher");
		assertTrue("Rollenname erhalten", model.getRolename().equals("Sucher"));
	}
	
	/**
	 * Password test.
	 */
	@Test
	public void passwordTest() {
		model.setPassword("Missetat begangen");
		assertTrue("Passwort erhalten", model.getPassword().equals("Missetat begangen"));
	}
	
	@Test
	public void passwortRepeatTest() {
		model.setPasswordrepeat("Missetat begangen");
		assertTrue("Passwort wiederholung erhalten", model.getPasswordrepeat().equals("Missetat begangen"));
	}
	
	@Test
	public void emailTest() {
		model.setEmail("test@test.de");
		assertTrue("E-Mail erhalten", model.getEmail().equals("test@test.de"));
	}
	
}