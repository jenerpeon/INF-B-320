package internetkaufhaus.forms;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

// TODO: Auto-generated Javadoc
/**
 * The Class StandardUserForm.
 */
public class StandardUserForm {

	/** The name. */
	@NotEmpty(message = "username is mandatory")
	@Pattern(regexp = "([A-Za-z0-9])+", message = "username contains illegal characters")
	private String name;

	/** The password. */
	@NotEmpty(message = "password field is mandatory")
	@Length(min = 8, message = "password is too short")
	private String password;

	/** The passwordrepeat. */
	@NotEmpty(message = "password-repeat field is mandatory")
	private String passwordrepeat;

	/** The email. */
	@NotEmpty(message = "email field is mandatory")
	@Email(message = "email is not a valid email")
	private String email;

	/** The address. */
	@NotEmpty(message = "address field is mandatory")
	@Pattern(regexp = "([A-Za-z0-9 ,.-])+", message = "address contains illegal characters")
	private String address;

	/** The zip code. */
	@NotEmpty(message = "zipCode field is mandatory")
	@Length(min = 5, max = 5, message = "zip-code is not 5 characters long, but should be")
	@Pattern(regexp = "([0-9])+", message = "zip-code contains illegal characters")
	private String zipCode;

	/** The city. */
	@NotEmpty(message = "city field is mandatory")
	@Pattern(regexp = "([A-Za-z0-9,.-])+", message = "city contains illegal characters")
	private String city;

	/** The firstname. */
	@NotEmpty(message = "first-name field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "first name contains illegal characters")
	private String firstname;

	/** The lastname. */
	@NotEmpty(message = "last-name field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "last name contains illegal characters")
	private String lastname;

	/**
	 * Checks if is valid.
	 *
	 * @return true, if is valid
	 */
	@AssertTrue(message = "password-repeat field should be equal to password field")
	private boolean isValid() {
		return this.password != null && this.passwordrepeat != null && this.password.equals(this.passwordrepeat);
	}

	/**
	 * Gets the firstname.
	 *
	 * @return the firstname
	 */
	public String getFirstname() {
		return this.firstname;
	}

	/**
	 * Gets the lastname.
	 *
	 * @return the lastname
	 */
	public String getLastname() {
		return this.lastname;
	}

	/**
	 * Sets the firstname.
	 *
	 * @param firstname the new firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Sets the lastname.
	 *
	 * @param lastname the new lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

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
	 * @param passwordrepeat the new passwordrepeat
	 */
	public void setPasswordrepeat(String passwordrepeat) {
		this.passwordrepeat = passwordrepeat;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the zip code.
	 *
	 * @return the zip code
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Sets the zip code.
	 *
	 * @param zipCode the new zip code
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

}
