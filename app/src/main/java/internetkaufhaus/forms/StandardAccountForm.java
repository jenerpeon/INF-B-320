package internetkaufhaus.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import cz.jirutka.validator.spring.SpELAssert;

// TODO: Auto-generated Javadoc
/**
 * The Class EditUserForm.
 */
@SpELAssert(value = "password.equals(passwordrepeat)", message = "Die Passwörter stimmen nicht überein")
public class StandardAccountForm {

	/** The email. */
	@NotEmpty(message = "Bitte geben Sie eine E-Mail Adresse an.")
	@Email(message = "Die E-Mail Adresse is ungültig.")
	private String email;

	/** The firstname. */
	@NotEmpty(message = "Bitte geben Sie einen Vornamen an.")
	@Pattern(regexp = "([A-Za-z -äöüß])+", message = "Der Vorname enthält ungültige Zeichen.")
	private String firstname;

	/** The lastname. */
	@NotEmpty(message = "Bitte geben Sie einen Nachnamen an.")
	@Pattern(regexp = "([A-Za-z -äöüß])+", message = "Der Nachname enthält ungültige Zeichen.")
	private String lastname;

	/** The street. */
	@NotEmpty(message = "Bitte geben Sie eine Straße an.")
	@Pattern(regexp = "([A-Za-z ,.-äöüß])+", message = "Die Straße enthält unzulässige Zeichen.")
	private String street;

	/** The housenumber. */
	@NotEmpty(message = "Bitte geben Sie eine Hausnummer an.")
	@Pattern(regexp = "([0-9])+", message = "Die Hausnummer enthält unzulässige Zeichen.")
	private String houseNumber;

	/** The city. */
	@NotEmpty(message = "Bitte geben Sie eine Stadt an.")
	@Pattern(regexp = "([A-Za-z0-9 ,.-äöüß])+", message = "Die Stadt enthält ungültige Zeichen.")
	private String city;

	/** The zip code. */
	@NotEmpty(message = "Bitte geben Sie eine Postleitzahl an.")
	@Length(min = 5, max = 5, message = "Die Postleitzahl muss genau fünf Ziffern lang sein.")
	@Pattern(regexp = "([0-9])+", message = "Die Postleitzahl enthält unzulässige Zeichen.")
	private String zipCode;

	/** The password. */
	@NotEmpty(message = "Bitte geben Sie ein Passwort an.")
	@Length(min = 8, message = "Das Passwort ist zu kurz.")
	private String password;

	/** The passwordrepeat. */
	@NotEmpty(message = "Bitte geben Sie ihr Passwort zu Überprüfung ein zweites mal an.")
	private String passwordrepeat;

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
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * Sets the firstname.
	 *
	 * @param firstname
	 *            the new firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
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
	 * Sets the lastname.
	 *
	 * @param lastname
	 *            the new lastname
	 */
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
	 * @param street
	 *            the new street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the house number.
	 *
	 * @return the house number
	 */
	public String getHouseNumber() {
		return this.houseNumber;
	}

	/**
	 * Sets the house number.
	 *
	 * @param houseNumber
	 *            the new house number
	 */
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city
	 *            the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the zip code.
	 *
	 * @return the zip code
	 */
	public String getZipCode() {
		return this.zipCode;
	}

	/**
	 * Sets the zip code.
	 *
	 * @param zipCode
	 *            the new zip code
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
		return this.passwordrepeat;
	}

	/**
	 * Sets the passwordrepeat.
	 *
	 * @param passwordRepitition
	 *            the new passwordrepeat
	 */
	public void setPasswordrepeat(String passwordRepitition) {
		this.passwordrepeat = passwordRepitition;
	}

}
