package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentFormTest.
 */
public class PaymentFormTest {
	
	/** The model. */
	private PaymentForm model = new PaymentForm();
	
	/**
	 * Card name test.
	 */
	@Test
	public void cardNameTest() {
		model.setCardName("Kreditkarte");
		assertTrue("Kartenname erhalten", model.getCardName().equals("Kreditkarte"));
	}
	
	/**
	 * Card assosiation name test.
	 */
	@Test
	public void cardAssosiationNameTest() {
		model.setCardAssociationName("Gringotts");
		assertTrue("Heraugebendes Kreditinstitut erhalten", model.getCardAssociationName().equals("Gringotts"));
	}
	
	/**
	 * Card number test.
	 */
	@Test
	public void cardNumberTest() {
		model.setCardNumber("132344535");
		assertTrue("Kartennummer erhalten", model.getCardNumber().equals("132344535"));
	}
	
	/**
	 * Name on card test.
	 */
	@Test
	public void nameOnCardTest() {
		model.setNameOnCard("Wood");
		assertTrue("Karteninhaber erhalten", model.getNameOnCard().equals("Wood"));
	}
	
	/**
	 * Card verification code test.
	 */
	@Test
	public void cardVerificationCodeTest() {
		model.setCardVerificationCode("213");
		assertTrue("Prüfziffern erhalten", model.getCardVerificationCode().equals("213"));
	}
	
	/**
	 * Expiry date test.
	 */
	@Test
	public void expiryDateTest() {
		model.setExpiryDate("2016-03-03");
		assertTrue("Ablaufdatum erhalten", model.getExpiryDate().equals("2016-03-03"));
	}
	
	/**
	 * Expiry date local date time test.
	 */
	@Test
	public void expiryDateLocalDateTimeTest() {
		model.setExpiryDate("2016-03-03");
		assertTrue("Ablaufdatum erhalten", model.getExpiryDateLocalDateTime().isEqual(LocalDateTime.of(2016, 03, 03, 0, 0)));
	}
}