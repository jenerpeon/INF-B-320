package internetkaufhaus.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import cz.jirutka.validator.spring.SpELAssert;

/**
 * The Class EditUserForm.
 */
@SpELAssert(value = "password.equals(passwordrepeat)", message = "Die Passwörter stimmen nicht überein")
public class EditUserForm {

	/** The email. */
	@NotEmpty(message = "Bitte geben Sie eine E-Mail Adresse an.")
	@Email(message = "Die E-Mail Adresse is ungültig.")
	private String email;

	/** The rolename. */
	@NotEmpty(message = "Bitte geben Sie eine Rolle an.")
	private String rolename;

	/** The password. */
	@NotEmpty(message = "Bitte geben Sie ein Passwort an.")
	@Length(min = 8, message = "Das Passwort ist zu kurz.")
	private String password;

	/** The passwordrepeat. */
	@NotEmpty(message = "Bitte geben Sie ihr Passwort zu Überprüfung ein zweites mal an.")
	private String passwordrepeat;

	/** The firstname. */
	@NotEmpty(message = "Bitte geben Sie einen Vornamen an.")
	@Pattern(regexp = "([A-Za-z])+", message = "Der Vorname enthält ungültige Zeichen.")
	private String firstname;

	/** The lastname. */
	@NotEmpty(message = "Bitte geben Sie einen Nachnamen an.")
	@Pattern(regexp = "([A-Za-z])+", message = "Der Nachname enthält ungültige Zeichen.")
	private String lastname;

	/** The street. */
	@NotEmpty(message = "Bitte geben Sie eine Straße an.")
	@Pattern(regexp = "([A-Za-z ,.-])+", message = "Die Straße enthält unzulässige Zeichen.")
	private String street;
	
	/** The housenumber. */
	@NotEmpty(message = "Bitte geben Sie eine Hausnummer an.")
	@Pattern(regexp = "([0-9])+", message = "Die Hausnummer enthält unzulässige Zeichen.")
	private String houseNumber;

	/** The city. */
	@NotEmpty(message = "Bitte geben Sie eine Stadt an.")
	@Pattern(regexp = "([A-Za-z0-9,.-])+", message = "Die Stadt enthält ungültige Zeichen.")
	private String city;

	/** The zip code. */
	@NotEmpty(message = "Bitte geben Sie eine Postleitzahl an.")
	@Length(min = 5, max = 5, message = "Die Postleitzahl muss genau fünf Ziffern lang sein.")
	@Pattern(regexp = "([0-9])+", message = "Die Postleitzahl enthält unzulässige Zeichen.")
	private String zipCode;

	/** The id. */
	private Long id;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
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
	 * @param rolename
	 *            the new rolename
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
	 * @param password
	 *            the new password
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getStreet() {
		return this.street;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getHouseNumber() {
		return this.houseNumber;
	}
	
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
