package internetkaufhaus.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;

public class PaymentForm {

	@NotEmpty(message = "cardname is mandatory")
	// @Pattern(regexp = "([A-Za-z])+", message = "cardname contains illegal characters")
	private String cardName;

	@NotEmpty(message = "cardAssociationName field is mandatory")
	@Pattern(regexp = "([A-Za-z ])+", message = "cardAssociationName contains illegal characters")
	private String cardAssociationName;

	@NotEmpty(message = "cardNumber field is mandatory")
	@Pattern(regexp = "([0-9])+", message = "cardNumber contains illegal characters")
	private String cardNumber;

	@NotEmpty(message = "nameOnCard field is mandatory")
	@Pattern(regexp = "([A-Za-z ])+", message = "nameOnCard contains illegal characters")
	private String nameOnCard;

	@NotEmpty(message = "expiryDate field is mandatory")
	// @Pattern(regexp = "([A-Z0-9.:-])+", message = "expiryDate contains illegal characters")
	private String expiryDate;

	@NotEmpty(message = "cardVerificationCode field is mandatory")
	@Pattern(regexp = "([0-9])+", message = "cardVerificationCode contains illegal characters")
	private String cardVerificationCode;

	public String getCardName() {
		return this.cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardAssociationName() {
		return this.cardAssociationName;
	}

	public void setCardAssociationName(String cardAssociationName) {
		this.cardAssociationName = cardAssociationName;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getNameOnCard() {
		return this.nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCardVerificationCode() {
		return this.cardVerificationCode;
	}

	public void setCardVerificationCode(String cardVerificationCode) {
		this.cardVerificationCode = cardVerificationCode;
	}

	public LocalDateTime getExpiryDateLocalDateTime() {
		String[] time = this.expiryDate.split("-");
		return LocalDateTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]), 0, 0);

	}

}