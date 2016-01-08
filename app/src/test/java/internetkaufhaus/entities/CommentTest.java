package internetkaufhaus.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.salespointframework.core.Currencies.EURO;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class CommentTest.
 */
public class CommentTest {

	/** The model. */
	Comment model;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		model = new Comment("Das hier ist ein Titel", "Das hier ist ein Kommentar", 5, LocalDateTime.now(), "t");
	}

	/**
	 * Test get comment id.
	 */
	@Test
	public void testGetCommentId() {
		long number = new Long(232424);
		model.setCommentid(number);
		assertTrue("Id wird zurückgegeben", model.getCommentid() == number);
	}

	/**
	 * Test product.
	 */
	@Test
	public void testProduct() {
		ConcreteProduct prod = new ConcreteProduct("Zigarre", Money.of(1.99, EURO), Money.of(1.49, EURO), "Tabakwaren",
				"Eine gute alte Zigarre", "www.tabak.ru", "zigarre.png");
		model.setProduct(prod);
		assertTrue("Product wird zurückgegeben", model.getProduct() == prod);
	}

	/**
	 * Test user.
	 */
	@Test
	public void testUser() {
		ConcreteUserAccount account = new ConcreteUserAccount();
		model.setUser(account);
		assertTrue("Account gesetzt", model.getUserAccount() == account);
	}

	/**
	 * Test text.
	 */
	@Test
	public void testText() {
		String text = "Dieser Text soll angezeigt werden";
		model.setText(text);
		assertTrue("Text gesetzt", model.getText() == text);
		assertTrue("Text gesetzt", model.toString() == text);
	}

	/**
	 * Test rating.
	 */
	@Test
	public void testRating() {
		int rate = 5;
		model.setRating(rate);
		assertTrue("Rating gesetzt", model.getRating() == rate);
	}

	/**
	 * Test date.
	 */
	@Test
	public void testDate() {
		LocalDateTime date = LocalDateTime.now();
		model.setDate(date);
		assertTrue("Datum gesetzt", model.getDate() == date);
	}

	/**
	 * Test accept.
	 */
	@Test
	public void testAccept() {
		model.accept();
		assertTrue("Kommentar ist auf accept gesetzt", model.isAccepted() == true);
		model.setAccepted(false);
		assertTrue("Kommentar ist nicht auf accept gesetzt", model.isAccepted() == false);
	}

	/**
	 * Test formatted date.
	 */
	@Test
	public void testFormattedDate() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm", Locale.GERMANY);
		String formattedDate = formatter.format(date);
		model.setFormatedDate(date);
		assertEquals("FormattedDate gesetzt", model.getFormatedDate(), formattedDate);

	}
	
	/**
	 * Test to string.
	 */
	@Test 
	public void testToString()
	{
		assertEquals("Das hier ist ein Kommentar", model.toString());
	}

}
