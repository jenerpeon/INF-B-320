package internetkaufhaus.forms;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateUserForm.
 */
public class CreateUserForm extends StandardUserForm {

	/** The rolename. */
	private String rolename;

	/**
	 * Instantiates a new creates the user form.
	 */
	public CreateUserForm() {
		System.out.print("");
	}

	/**
	 * Gets the rolename.
	 *
	 * @return the rolename
	 */
	public String getRolename() {
		return rolename;
	}

	/**
	 * Sets the rolename.
	 *
	 * @param rolename the new rolename
	 */
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
}
