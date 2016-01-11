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
	
}