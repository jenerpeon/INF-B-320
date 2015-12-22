package internetkaufhaus.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import java.util.*;

public class BillingAddressForm {

	@NotEmpty(message = "shipping gender is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping gender contains illegal characters")
	private String billingGender;

	@NotEmpty(message = "shipping fist name field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping first name contains illegal characters")
	private String billingFirstName;

	@NotEmpty(message = "shipping last name field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping last name contains illegal characters")
	private String billingLastName;

	@NotEmpty(message = "shipping street field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping street contains illegal characters")
	private String billingStreet;

	@NotEmpty(message = "shipping house number field is mandatory")
	@Pattern(regexp = "([0-9])+", message = "shipping house number contains illegal characters")
	private String billingHouseNumber;

	@Pattern(regexp = "([A-Za-z])+", message = "shipping address line 2 contains illegal characters")
	private String billingAddressLine2;

	@NotEmpty(message = "shipping zip code field is mandatory")
	@Pattern(regexp = "([0-9])+", message = "shipping zip code contains illegal characters")
	private String billingZipCode;

	@NotEmpty(message = "shipping town field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping town contains illegal characters")
	private String billingTown;

	public BillingAddressForm(){};
	
	public BillingAddressForm(String gender, String firstName, String lastName, String street, String houseNumber, String addressLine2, String zipCode, String town) {
		this.billingGender = gender;
		this.billingFirstName = firstName;
		this.billingLastName = lastName;
		this.billingStreet = street;
		this.billingHouseNumber = houseNumber;
		this.billingAddressLine2 = addressLine2;
		this.billingZipCode = zipCode;
		this.billingTown = town;
	}
	
	public String getBillingGender() {
		return this.billingGender;
	}

	public String getBillingFirstName() {
		return this.billingFirstName;
	}

	public String getBillingLastName() {
		return this.billingLastName;
	}

	public String getBillingStreet() {
		return this.billingStreet;
	}

	public String getBillingHouseNumber() {
		return this.billingHouseNumber;
	}

	public String getBillingAddressLine2() {
		return this.billingAddressLine2;
	}

	public String getBillingZipCode() {
		return this.billingZipCode;
	}

	public String getBillingTown() {
		return this.billingTown;
	}

	public void setBillingGender(String billingGender) {
		this.billingGender = billingGender;
	}

	public void setBillingFirstName(String billingFirstName) {
		this.billingFirstName = billingFirstName;
	}

	public void setBillingLastName(String billingLastName) {
		this.billingLastName = billingLastName;
	}

	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}

	public void setBillingHouseNumber(String billingHouseNumber) {
		this.billingHouseNumber = billingHouseNumber;
	}

	public void setBillingAddressLine2(String billingAddressLine2) {
		this.billingAddressLine2 = billingAddressLine2;
	}

	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}

	public void setBillingTown(String billingTown) {
		this.billingTown = billingTown;
	}

	public List<String> getBillingAddress() {
		List<String> billingAddress = new ArrayList<String>();
		billingAddress.add(this.billingGender);
		billingAddress.add(this.billingFirstName);
		billingAddress.add(this.billingLastName);
		billingAddress.add(this.billingStreet);
		billingAddress.add(this.billingHouseNumber);
		billingAddress.add(this.billingAddressLine2);
		billingAddress.add(this.billingZipCode);
		billingAddress.add(this.billingTown);
		
		System.out.println(this.billingGender);

		return billingAddress;
	}

}