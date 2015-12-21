package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EditUserFormTest {
	
	EditUserForm model = new EditUserForm();
	
	@Test
	public void idTest() {
		model.setId((long) 53346);
		assertTrue("ID erhalten", model.getId().equals((long) 53346));
	}
	
	@Test
	public void rolenameTest() {
		model.setRolename("Sucher");
		assertTrue("Rollenname erhalten", model.getRolename().equals("Sucher"));
	}
	
	@Test
	public void passwordTest() {
		model.setPassword("Missetat begangen");
		assertTrue("Passwort erhalten", model.getPassword().equals("Missetat begangen"));
	}
	
}