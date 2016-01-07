package internetkaufhaus.forms;

// TODO: Auto-generated Javadoc
/**
 * The Class EditUserForm.
 */
public class EditCustomerForm {
	
	private String email;
	
	private String firstname;
	
	private String lastname;
	
	private String address;
	
	private String city;
	
	private String zipCode;
	
	/** The password. */
	private String password;
	
	private String passwordRepitition;
	
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstname() {
		return this.firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return this.lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getZipCode() {
		return this.zipCode;
	}
	
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
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordRepitition() {
		return this.passwordRepitition;
	}
	
	public void setPasswordRepitition(String passwordRepitition) {
		this.passwordRepitition = passwordRepitition;
	}

}
