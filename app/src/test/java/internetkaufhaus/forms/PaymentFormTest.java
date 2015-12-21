package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

public class PaymentFormTest {
	
	private PaymentForm model = new PaymentForm();
	
	@Test
	public void cardNameTest() {
		model.setCardName("Kreditkarte");
		assertTrue("Kartenname erhalten", model.getCardName().equals("Kreditkarte"));
	}
	
	@Test
	public void cardAssosiationNameTest() {
		model.setCardAssociationName("Gringotts");
		assertTrue("Heraugebendes Kreditinstitut erhalten", model.getCardAssociationName().equals("Gringotts"));
	}
	
	@Test
	public void cardNumberTest() {
		model.setCardNumber("132344535");
		assertTrue("Kartennummer erhalten", model.getCardNumber().equals("132344535"));
	}
	
	@Test
	public void nameOnCardTest() {
		model.setNameOnCard("Wood");
		assertTrue("Karteninhaber erhalten", model.getNameOnCard().equals("Wood"));
	}
	
	@Test
	public void cardVerificationCodeTest() {
		model.setCardVerificationCode("213");
		assertTrue("Prüfziffern erhalten", model.getCardVerificationCode().equals("213"));
	}
	
	@Test
	public void expiryDateTest() {
		model.setExpiryDate("2016-03-03");
		assertTrue("Ablaufdatum erhalten", model.getExpiryDate().equals("2016-03-03"));
	}
	
	@Test
	public void expiryDateLocalDateTimeTest() {
		model.setExpiryDate("2016-03-03");
		assertTrue("Ablaufdatum erhalten", model.getExpiryDateLocalDateTime().isEqual(LocalDateTime.of(2016, 03, 03, 0, 0)));
	}
}