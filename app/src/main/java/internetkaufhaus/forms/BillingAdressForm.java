package internetkaufhaus.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import java.util.*;

public class BillingAdressForm {

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

	@Pattern(regexp = "([A-Za-z])+", message = "shipping adress line 2 contains illegal characters")
	private String billingAdressLine2;

	@NotEmpty(message = "shipping zip code field is mandatory")
	@Pattern(regexp = "([0-9])+", message = "shipping zip code contains illegal characters")
	private String billingZipCode;

	@NotEmpty(message = "shipping town field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping town contains illegal characters")
	private String billingTown;

	public BillingAdressForm(){};
	
	public BillingAdressForm(String gender, String firstName, String lastName, String street, String houseNumber, String adressLine2, String zipCode, String town) {
		this.billingGender = gender;
		this.billingFirstName = firstName;
		this.billingLastName = lastName;
		this.billingStreet = street;
		this.billingHouseNumber = houseNumber;
		this.billingAdressLine2 = adressLine2;
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

	public String getBillingAdressLine2() {
		return this.billingAdressLine2;
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

	public void setBillingAdressLine2(String billingAdressLine2) {
		this.billingAdressLine2 = billingAdressLine2;
	}

	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}

	public void setBillingTown(String billingTown) {
		this.billingTown = billingTown;
	}

	public List<String> getBillingAdress() {
		List<String> billingAdress = new ArrayList<String>();
		billingAdress.add(this.billingGender);
		billingAdress.add(this.billingFirstName);
		billingAdress.add(this.billingLastName);
		billingAdress.add(this.billingStreet);
		billingAdress.add(this.billingHouseNumber);
		billingAdress.add(this.billingAdressLine2);
		billingAdress.add(this.billingZipCode);
		billingAdress.add(this.billingTown);
		
		System.out.println(this.billingGender);

		return billingAdress;
	}

}