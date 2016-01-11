package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class StandardUserFormTest.
 */
public class StandardUserFormTest {
	
	/** The model. */
	private StandardUserForm model = new StandardUserForm();
	
	/**
	 * Name test.
	 */
	@Test
	public void nameTest() {
		model.setName("spigo");
		assertTrue("Benutzername erhalten", model.getName().equals("spigo"));
	}
	
}