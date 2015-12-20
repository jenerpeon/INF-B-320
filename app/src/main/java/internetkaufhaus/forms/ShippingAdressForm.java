package internetkaufhaus.forms;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class ShippingAdressForm {

	@NotEmpty(message = "shipping gender is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping gender contains illegal characters")
	private String shippingGender;

	@NotEmpty(message = "shipping fist name field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping first name contains illegal characters")
	private String shippingFirstName;

	@NotEmpty(message = "shipping last name field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping last name contains illegal characters")
	private String shippingLastName;

	@NotEmpty(message = "shipping street field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping street contains illegal characters")
	private String shippingStreet;

	@NotEmpty(message = "shipping house number field is mandatory")
	@Pattern(regexp = "([0-9])+", message = "shipping house number contains illegal characters")
	private String shippingHouseNumber;

	@Pattern(regexp = "([A-Za-z])+", message = "shipping adress line 2 contains illegal characters")
	private String shippingAdressLine2;

	@NotEmpty(message = "shipping zip code field is mandatory")
	@Pattern(regexp = "([0-9])+", message = "shipping zip code contains illegal characters")
	private String shippingZipCode;

	@NotEmpty(message = "shipping town field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping town contains illegal characters")
	private String shippingTown;

	public String getShippingGender() {
		return shippingGender;
	}

	public String getShippingFirstName() {
		return shippingFirstName;
	}

	public String getShippingLastName() {
		return shippingLastName;
	}

	public String getShippingStreet() {
		return shippingStreet;
	}

	public String getShippingHouseNumber() {
		return shippingHouseNumber;
	}

	public String getShippingAdressLine2() {
		return shippingAdressLine2;
	}

	public String getShippingZipCode() {
		return shippingZipCode;
	}

	public String getShippingTown() {
		return shippingTown;
	}

	public void setShippingGender(String shippingGender) {
		this.shippingGender = shippingGender;
	}

	public void setShippingFirstName(String shippingFirstName) {
		this.shippingFirstName = shippingFirstName;
	}

	public void setShippingLastName(String shippingLastName) {
		this.shippingLastName = shippingLastName;
	}

	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}

	public void setShippingHouseNumber(String shippingHouseNumber) {
		this.shippingHouseNumber = shippingHouseNumber;
	}

	public void setShippingAdressLine2(String shippingAdressLine2) {
		this.shippingAdressLine2 = shippingAdressLine2;
	}

	public void setShippingZipCode(String shippingZipCode) {
		this.shippingZipCode = shippingZipCode;
	}

	public void setShippingTown(String shippingTown) {
		this.shippingTown = shippingTown;
	}

	public List<String> getShippingAdress() {
		List<String> shippingAdress = new ArrayList<String>();
		shippingAdress.add(shippingGender);
		shippingAdress.add(shippingFirstName);
		shippingAdress.add(shippingLastName);
		shippingAdress.add(shippingStreet);
		shippingAdress.add(shippingHouseNumber);
		shippingAdress.add(shippingAdressLine2);
		shippingAdress.add(shippingZipCode);
		shippingAdress.add(shippingTown);

		return shippingAdress;
	}

}