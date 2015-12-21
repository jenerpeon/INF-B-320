package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CreateUserFormTest {
	
	private CreateUserForm model = new CreateUserForm();
	
	@Test
	public void rolenameTest() {
		model.setRolename("admin");
		assertTrue("Rollenname erhalten", model.getRolename().equals("admin"));
	}
	
}