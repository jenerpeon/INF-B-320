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

	public String getBillingGender() {
		return billingGender;
	}

	public String getBillingFirstName() {
		return billingFirstName;
	}

	public String getBillingLastName() {
		return billingLastName;
	}

	public String getBillingStreet() {
		return billingStreet;
	}

	public String getBillingHouseNumber() {
		return billingHouseNumber;
	}

	public String getBillingAdressLine2() {
		return billingAdressLine2;
	}

	public String getBillingZipCode() {
		return billingZipCode;
	}

	public String getBillingTown() {
		return billingTown;
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
		billingAdress.add(billingGender);
		billingAdress.add(billingFirstName);
		billingAdress.add(billingLastName);
		billingAdress.add(billingStreet);
		billingAdress.add(billingHouseNumber);
		billingAdress.add(billingAdressLine2);
		billingAdress.add(billingZipCode);
		billingAdress.add(billingTown);

		return billingAdress;
	}

}