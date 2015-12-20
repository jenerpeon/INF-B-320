package internetkaufhaus.forms;

public class CreateUserForm extends StandardUserForm {

	private String rolename;

	/**
	 * This comment is just here because sonarcube is a little bitch.
	 */
	public CreateUserForm() {

	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
}
