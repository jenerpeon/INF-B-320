package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
		assertTrue("Ablaufdatum erhalten",
				model.getExpiryDateLocalDateTime().isEqual(LocalDateTime.of(2016, 03, 03, 0, 0)));
	}

	@Test
	public void billingGenderTest() {
		model.setBillingGender("Herr");
		assertTrue("Rechnung Geschlecht erhalten", model.getBillingGender().equals("Herr"));
	}

	@Test
	public void billingFirstName() {
		model.setBillingFirstName("Harry");
		assertTrue("Rechnung Vorname erhalten", model.getBillingFirstName().equals("Harry"));
	}

	@Test
	public void billingLastName() {
		model.setBillingLastName("Potter");
		assertTrue("Rechnung Nachname erhalten", model.getBillingLastName().equals("Potter"));
	}

	@Test
	public void billingStreet() {
		model.setBillingStreet("Ligusterweg");
		assertTrue("Rechnung Straße erhalten", model.getBillingStreet().equals("Ligusterweg"));
	}

	@Test
	public void billingHouseNumber() {
		model.setBillingHouseNumber("4");
		assertTrue("Rechnung Hausnummer erhalten", model.getBillingHouseNumber().equals("4"));
	}

	@Test
	public void billingAdressLine2() {
		model.setBillingAddressLine2("Unter der Treppe");
		assertTrue("Rechnung Adresszusatz erhalten", model.getBillingAddressLine2().equals("Unter der Treppe"));
	}

	@Test
	public void billingZipCode() {
		model.setBillingZipCode("12345");
		assertTrue("Rechnung Postleitzahl erhalten", model.getBillingZipCode().equals("12345"));
	}

	@Test
	public void billingTown() {
		model.setBillingTown("London");
		assertTrue("Rechnung Stadt erhalten", model.getBillingTown().equals("London"));
	}

	@Test
	public void billingAddressTest() {
		List<String> test = new ArrayList<String>();
		model.setBillingGender("Herr");
		test.add("Herr");
		model.setBillingFirstName("Harry");
		test.add("Harry");
		model.setBillingLastName("Potter");
		test.add("Potter");
		model.setBillingStreet("Ligusterweg");
		test.add("Ligusterweg");
		model.setBillingHouseNumber("4");
		test.add("4");
		model.setBillingAddressLine2("Unter der Treppe");
		test.add("Unter der Treppe");
		model.setBillingZipCode("12345");
		test.add("12345");
		model.setBillingTown("London");
		test.add("London");
		assertTrue("Rechnung erhalten", model.getBillingAddress().containsAll(test));
	}

	@Test
	public void shippingGenderTest() {
		model.setShippingGender("Herr");
		assertTrue("Versand Geschlecht erhalten", model.getShippingGender().equals("Herr"));
	}

	@Test
	public void shippingFirstName() {
		model.setShippingFirstName("Harry");
		assertTrue("Versand Vorname erhalten", model.getShippingFirstName().equals("Harry"));
	}

	@Test
	public void shippingLastName() {
		model.setShippingLastName("Potter");
		assertTrue("Versand Nachname erhalten", model.getShippingLastName().equals("Potter"));
	}

	@Test
	public void shippingStreet() {
		model.setShippingStreet("Ligusterweg");
		assertTrue("Versand Straße erhalten", model.getShippingStreet().equals("Ligusterweg"));
	}

	@Test
	public void shippingHouseNumber() {
		model.setShippingHouseNumber("4");
		assertTrue("Versand Hausnummer erhalten", model.getShippingHouseNumber().equals("4"));
	}

	@Test
	public void shippingAdressLine2() {
		model.setShippingAddressLine2("Unter der Treppe");
		assertTrue("Versand Adresszusatz erhalten", model.getShippingAddressLine2().equals("Unter der Treppe"));
	}

	@Test
	public void shippingZipCode() {
		model.setShippingZipCode("12345");
		assertTrue("Versand Postleitzahl erhalten", model.getShippingZipCode().equals("12345"));
	}

	@Test
	public void shippingTown() {
		model.setShippingTown("London");
		assertTrue("Versand Stadt erhalten", model.getShippingTown().equals("London"));
	}

	@Test
	public void shippingAddressTest() {
		List<String> test = new ArrayList<String>();
		model.setShippingGender("Herr");
		test.add("Herr");
		model.setShippingFirstName("Harry");
		test.add("Harry");
		model.setShippingLastName("Potter");
		test.add("Potter");
		model.setShippingStreet("Ligusterweg");
		test.add("Ligusterweg");
		model.setShippingHouseNumber("4");
		test.add("4");
		model.setShippingAddressLine2("Unter der Treppe");
		test.add("Unter der Treppe");
		model.setShippingZipCode("12345");
		test.add("12345");
		model.setShippingTown("London");
		test.add("London");
		assertTrue("Versand erhalten", model.getShippingAddress().containsAll(test));
	}

	@Test
	public void constructorTest() {
		PaymentForm form = new PaymentForm("Herr", "Harry", "Potter", "Ligusterweg", "4", "Unter der Treppe", "12345",
				"London", "Herr", "Harry", "Potter", "Ligusterweg", "4", "Unter der Treppe", "12345", "London");
		List<String> test = new ArrayList<String>();
		test.add("Herr");
		test.add("Harry");
		test.add("Potter");
		test.add("Ligusterweg");
		test.add("4");
		test.add("Unter der Treppe");
		test.add("12345");
		test.add("London");
		assertTrue("Konstruktor funktioniert", form.getBillingAddress().containsAll(test) && form.getShippingAddress().contains(test));
	}
}