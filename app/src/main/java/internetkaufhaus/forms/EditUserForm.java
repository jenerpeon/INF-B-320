package internetkaufhaus.forms;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

// TODO: Auto-generated Javadoc
/**
 * The Class EditUserForm.
 */
public class EditUserForm {

	/** The rolename. */
//	@NotEmpty(message = "rolename is mandatory")
	private String rolename;
	
	/** The password. */
	@NotEmpty(message = "password field is mandatory")
	@Length(min = 8, message = "password is too short")
	private String password;
	
	/** The passwordrepeat. */
	@NotEmpty(message = "passwordrepeat field is mandatory")
	@Length(min = 8, message = "password is too short")
	private String passwordrepeat;
	
	/** The id. */
	private Long id;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public EditUserForm()
	{
		System.out.println("");
	}
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
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

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Gets the passwordrepeat.
	 *
	 * @return the passwordrepeat
	 */
	public String getPasswordrepeat() {
		return passwordrepeat;
	}
	/**
	 * Sets the passwordrepeat.
	 *
	 */
	public void setPasswordrepeat(String passwordrepeat) {
		this.passwordrepeat = passwordrepeat;
	}

}
