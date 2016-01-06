package internetkaufhaus.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

// TODO: Auto-generated Javadoc
/**
 * The Class EditUserForm2.
 */
public class EditUserForm2 {


	/** The firstname.*/
	@NotEmpty(message = "firstname is mandatory")
	@Pattern(regexp = "([A-Za-z0-9])+", message = "firstname contains illegal characters")
	private String firstname;
	
	/** The lastname.*/
	@NotEmpty(message = "lastname is mandatory")
	@Pattern(regexp = "([A-Za-z0-9])+", message = "lastname contains illegal characters")
	private String lastname;
	
	/** The address.*/
	@NotEmpty(message = "address field is mandatory")
	@Pattern(regexp = "([A-Za-z0-9 ,.-])+", message = "address contains illegal characters")
	private String address;
	
	/** The city. */
	@NotEmpty(message = "city field is mandatory")
	@Pattern(regexp = "([A-Za-z0-9,.-])+", message = "city contains illegal characters")
	private String city;
	
	/** The zipCode.*/
	@NotEmpty(message = "zipCode field is mandatory")
	@Length(min = 5, max = 5, message = "zip-code is not 5 characters long, but should be")
	@Pattern(regexp = "([0-9])+", message = "zip-code contains illegal characters")
	private String zipCode;
	
	/** The Email.*/
	@NotEmpty(message = "email field is mandatory")
	@Email(message = "email is not a valid email")
	private String email;
	
	/** The password. */
	@NotEmpty(message = "password field is mandatory")
	@Length(min = 8, message = "password is too short")
	private String password;
	
	/** The id. */
	private Long id;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
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
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * Gets the firstname.
	 *
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
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
	 * Gets the lastname.
	 *
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
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
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
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
	/**
	 * Gets the zipCode.
	 *
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * Sets the zipCode.
	 *
	 * @param zipCode the new zipCode
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Sets the email.
	 *
	 * @param email the new remail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
