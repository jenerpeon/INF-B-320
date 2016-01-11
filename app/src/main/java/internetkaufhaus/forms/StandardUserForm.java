package internetkaufhaus.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

// TODO: Auto-generated Javadoc
/**
 * The Class StandardUserForm.
 */
public class StandardUserForm extends StandardAccountForm{

	/** The name. */
	@NotEmpty(message = "Bitte geben Sie einen Benutzernamen an.")
	@Pattern(regexp = "([A-Za-z0-9])+", message = "Der Benutzername enthält unzulässige Zeichen.")
	private String name;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
