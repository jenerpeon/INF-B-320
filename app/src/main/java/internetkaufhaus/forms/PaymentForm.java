package internetkaufhaus.forms;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentForm.
 */
public class PaymentForm {

	/** The card name. */
	@NotEmpty(message = "cardname is mandatory")
	// @Pattern(regexp = "([A-Za-z])+", message = "cardname contains illegal
	// characters")
	private String cardName;

	/** The card association name. */
	@NotEmpty(message = "cardAssociationName field is mandatory")
	@Pattern(regexp = "([A-Za-z ])+", message = "cardAssociationName contains illegal characters")
	private String cardAssociationName;

	/** The card number. */
	@NotEmpty(message = "cardNumber field is mandatory")
	@Pattern(regexp = "([0-9])+", message = "cardNumber contains illegal characters")
	private String cardNumber;

	/** The name on card. */
	@NotEmpty(message = "nameOnCard field is mandatory")
	@Pattern(regexp = "([A-Za-z ])+", message = "nameOnCard contains illegal characters")
	private String nameOnCard;

	/** The expiry date. */
	@NotEmpty(message = "expiryDate field is mandatory")
	// @Pattern(regexp = "([A-Z0-9.:-])+", message = "expiryDate contains
	// illegal characters")
	private String expiryDate;

	/** The card verification code. */
	@NotEmpty(message = "cardVerificationCode field is mandatory")
	@Pattern(regexp = "([0-9])+", message = "cardVerificationCode contains illegal characters")
	private String cardVerificationCode;

	/**
	 * Gets the card name.
	 *
	 * @return the card name
	 */
	public String getCardName() {
		return this.cardName;
	}

	/**
	 * Sets the card name.
	 *
	 * @param cardName the new card name
	 */
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	/**
	 * Gets the card association name.
	 *
	 * @return the card association name
	 */
	public String getCardAssociationName() {
		return this.cardAssociationName;
	}

	/**
	 * Sets the card association name.
	 *
	 * @param cardAssociationName the new card association name
	 */
	public void setCardAssociationName(String cardAssociationName) {
		this.cardAssociationName = cardAssociationName;
	}

	/**
	 * Gets the card number.
	 *
	 * @return the card number
	 */
	public String getCardNumber() {
		return this.cardNumber;
	}

	/**
	 * Sets the card number.
	 *
	 * @param cardNumber the new card number
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * Gets the name on card.
	 *
	 * @return the name on card
	 */
	public String getNameOnCard() {
		return this.nameOnCard;
	}

	/**
	 * Sets the name on card.
	 *
	 * @param nameOnCard the new name on card
	 */
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	/**
	 * Gets the expiry date.
	 *
	 * @return the expiry date
	 */
	public String getExpiryDate() {
		return this.expiryDate;
	}

	/**
	 * Sets the expiry date.
	 *
	 * @param expiryDate the new expiry date
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * Gets the card verification code.
	 *
	 * @return the card verification code
	 */
	public String getCardVerificationCode() {
		return this.cardVerificationCode;
	}

	/**
	 * Sets the card verification code.
	 *
	 * @param cardVerificationCode the new card verification code
	 */
	public void setCardVerificationCode(String cardVerificationCode) {
		this.cardVerificationCode = cardVerificationCode;
	}

	/**
	 * Gets the expiry date local date time.
	 *
	 * @return the expiry date local date time
	 */
	public LocalDateTime getExpiryDateLocalDateTime() {
		String[] time = this.expiryDate.split("-");
		return LocalDateTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]), 0, 0);

	}

	/** The billing gender. */
	@NotEmpty(message = "shipping gender is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping gender contains illegal characters")
	private String billingGender;

	/** The billing first name. */
	@NotEmpty(message = "shipping fist name field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping first name contains illegal characters")
	private String billingFirstName;

	/** The billing last name. */
	@NotEmpty(message = "shipping last name field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping last name contains illegal characters")
	private String billingLastName;

	/** The billing street. */
	@NotEmpty(message = "shipping street field is mandatory")
	@Pattern(regexp = "([A-Za-z ß,.-])+", message = "shipping street contains illegal characters")
	private String billingStreet;

	/** The billing house number. */
	@NotEmpty(message = "shipping house number field is mandatory")
	@Pattern(regexp = "([0-9])+", message = "shipping house number contains illegal characters")
	private String billingHouseNumber;

	/** The billing address line2. */
	@Pattern(regexp = "([A-Za-z])+", message = "shipping address line 2 contains illegal characters")
	private String billingAddressLine2;

	/** The billing zip code. */
	@NotEmpty(message = "shipping zip code field is mandatory")
	@Pattern(regexp = "([0-9])+", message = "shipping zip code contains illegal characters")
	private String billingZipCode;

	/** The billing town. */
	@NotEmpty(message = "shipping town field is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping town contains illegal characters")
	private String billingTown;

	/**
	 * Gets the billing gender.
	 *
	 * @return the billing gender
	 */
	public String getBillingGender() {
		return this.billingGender;
	}

	/**
	 * Gets the billing first name.
	 *
	 * @return the billing first name
	 */
	public String getBillingFirstName() {
		return this.billingFirstName;
	}

	/**
	 * Gets the billing last name.
	 *
	 * @return the billing last name
	 */
	public String getBillingLastName() {
		return this.billingLastName;
	}

	/**
	 * Gets the billing street.
	 *
	 * @return the billing street
	 */
	public String getBillingStreet() {
		return this.billingStreet;
	}

	/**
	 * Gets the billing house number.
	 *
	 * @return the billing house number
	 */
	public String getBillingHouseNumber() {
		return this.billingHouseNumber;
	}

	/**
	 * Gets the billing address line2.
	 *
	 * @return the billing address line2
	 */
	public String getBillingAddressLine2() {
		return this.billingAddressLine2;
	}

	/**
	 * Gets the billing zip code.
	 *
	 * @return the billing zip code
	 */
	public String getBillingZipCode() {
		return this.billingZipCode;
	}

	/**
	 * Gets the billing town.
	 *
	 * @return the billing town
	 */
	public String getBillingTown() {
		return this.billingTown;
	}

	/**
	 * Sets the billing gender.
	 *
	 * @param billingGender the new billing gender
	 */
	public void setBillingGender(String billingGender) {
		this.billingGender = billingGender;
	}

	/**
	 * Sets the billing first name.
	 *
	 * @param billingFirstName the new billing first name
	 */
	public void setBillingFirstName(String billingFirstName) {
		this.billingFirstName = billingFirstName;
	}

	/**
	 * Sets the billing last name.
	 *
	 * @param billingLastName the new billing last name
	 */
	public void setBillingLastName(String billingLastName) {
		this.billingLastName = billingLastName;
	}

	/**
	 * Sets the billing street.
	 *
	 * @param billingStreet the new billing street
	 */
	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}

	/**
	 * Sets the billing house number.
	 *
	 * @param billingHouseNumber the new billing house number
	 */
	public void setBillingHouseNumber(String billingHouseNumber) {
		this.billingHouseNumber = billingHouseNumber;
	}

	/**
	 * Sets the billing address line2.
	 *
	 * @param billingAddressLine2 the new billing address line2
	 */
	public void setBillingAddressLine2(String billingAddressLine2) {
		this.billingAddressLine2 = billingAddressLine2;
	}

	/**
	 * Sets the billing zip code.
	 *
	 * @param billingZipCode the new billing zip code
	 */
	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}

	/**
	 * Sets the billing town.
	 *
	 * @param billingTown the new billing town
	 */
	public void setBillingTown(String billingTown) {
		this.billingTown = billingTown;
	}

	/**
	 * Gets the billing address.
	 *
	 * @return the billing address
	 */
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

	/** The shipping gender. */
	@NotEmpty(message = "shipping gender is mandatory")
	@Pattern(regexp = "([A-Za-z])+", message = "shipping gender contains illegal characters")
	private String shippingGender;

	/** The shipping first name. */
	@NotEmpty(message = "shipping fist name field is mandatory")
	@Pattern(regexp = "([A-Za-z -])+", message = "shipping first name contains illegal characters")
	private String shippingFirstName;

	/** The shipping last name. */
	@NotEmpty(message = "shipping last name field is mandatory")
	@Pattern(regexp = "([A-Za-z -])+", message = "shipping last name contains illegal characters")
	private String shippingLastName;

	/** The shipping street. */
	@NotEmpty(message = "shipping street field is mandatory")
	@Pattern(regexp = "([A-Za-zß ,.-])+", message = "shipping street contains illegal characters")
	private String shippingStreet;

	/** The shipping house number. */
	@NotEmpty(message = "shipping house number field is mandatory")
	@Pattern(regexp = "([0-9-])+(A-Za-z)*", message = "shipping house number contains illegal characters")
	private String shippingHouseNumber;

	/** The shipping address line2. */
	@Pattern(regexp = "([0-9A-Za-z ,.-])+", message = "shipping address line 2 contains illegal characters")
	private String shippingAddressLine2;

	/** The shipping zip code. */
	@NotEmpty(message = "shipping zip code field is mandatory")
	@Pattern(regexp = "([0-9])+", message = "shipping zip code contains illegal characters")
	private String shippingZipCode;

	/** The shipping town. */
	@NotEmpty(message = "shipping town field is mandatory")
	@Pattern(regexp = "([A-Za-z ,.-])+", message = "shipping town contains illegal characters")
	private String shippingTown;

	/**
	 * Gets the shipping gender.
	 *
	 * @return the shipping gender
	 */
	public String getShippingGender() {
		return shippingGender;
	}

	/**
	 * Gets the shipping first name.
	 *
	 * @return the shipping first name
	 */
	public String getShippingFirstName() {
		return shippingFirstName;
	}

	/**
	 * Gets the shipping last name.
	 *
	 * @return the shipping last name
	 */
	public String getShippingLastName() {
		return shippingLastName;
	}

	/**
	 * Gets the shipping street.
	 *
	 * @return the shipping street
	 */
	public String getShippingStreet() {
		return shippingStreet;
	}

	/**
	 * Gets the shipping house number.
	 *
	 * @return the shipping house number
	 */
	public String getShippingHouseNumber() {
		return shippingHouseNumber;
	}

	/**
	 * Gets the shipping address line2.
	 *
	 * @return the shipping address line2
	 */
	public String getShippingAddressLine2() {
		return shippingAddressLine2;
	}

	/**
	 * Gets the shipping zip code.
	 *
	 * @return the shipping zip code
	 */
	public String getShippingZipCode() {
		return shippingZipCode;
	}

	/**
	 * Gets the shipping town.
	 *
	 * @return the shipping town
	 */
	public String getShippingTown() {
		return shippingTown;
	}

	/**
	 * Sets the shipping gender.
	 *
	 * @param shippingGender the new shipping gender
	 */
	public void setShippingGender(String shippingGender) {
		this.shippingGender = shippingGender;
	}

	/**
	 * Sets the shipping first name.
	 *
	 * @param shippingFirstName the new shipping first name
	 */
	public void setShippingFirstName(String shippingFirstName) {
		this.shippingFirstName = shippingFirstName;
	}

	/**
	 * Sets the shipping last name.
	 *
	 * @param shippingLastName the new shipping last name
	 */
	public void setShippingLastName(String shippingLastName) {
		this.shippingLastName = shippingLastName;
	}

	/**
	 * Sets the shipping street.
	 *
	 * @param shippingStreet the new shipping street
	 */
	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}

	/**
	 * Sets the shipping house number.
	 *
	 * @param shippingHouseNumber the new shipping house number
	 */
	public void setShippingHouseNumber(String shippingHouseNumber) {
		this.shippingHouseNumber = shippingHouseNumber;
	}

	/**
	 * Sets the shipping address line2.
	 *
	 * @param shippingAddressLine2 the new shipping address line2
	 */
	public void setShippingAddressLine2(String shippingAddressLine2) {
		this.shippingAddressLine2 = shippingAddressLine2;
	}

	/**
	 * Sets the shipping zip code.
	 *
	 * @param shippingZipCode the new shipping zip code
	 */
	public void setShippingZipCode(String shippingZipCode) {
		this.shippingZipCode = shippingZipCode;
	}

	/**
	 * Sets the shipping town.
	 *
	 * @param shippingTown the new shipping town
	 */
	public void setShippingTown(String shippingTown) {
		this.shippingTown = shippingTown;
	}

	/**
	 * Gets the shipping address.
	 *
	 * @return the shipping address
	 */
	public List<String> getShippingAddress() {
		List<String> shippingAddress = new ArrayList<String>();
		shippingAddress.add(this.shippingGender);
		shippingAddress.add(this.shippingFirstName);
		shippingAddress.add(this.shippingLastName);
		shippingAddress.add(this.shippingStreet);
		shippingAddress.add(this.shippingHouseNumber);
		shippingAddress.add(this.shippingAddressLine2);
		shippingAddress.add(this.shippingZipCode);
		shippingAddress.add(this.shippingTown);

		return shippingAddress;
	}

}