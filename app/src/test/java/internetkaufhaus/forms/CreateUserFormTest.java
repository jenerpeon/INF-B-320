package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateUserFormTest.
 */
public class CreateUserFormTest {
	
	/** The model. */
	private CreateUserForm model = new CreateUserForm();
	
	/**
	 * Rolename test.
	 */
	@Test
	public void rolenameTest() {
		model.setRolename("admin");
		assertTrue("Rollenname erhalten", model.getRolename().equals("admin"));
	}
	
}