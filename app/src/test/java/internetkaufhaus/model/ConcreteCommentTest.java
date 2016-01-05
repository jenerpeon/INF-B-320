package internetkaufhaus.model;

import static org.junit.Assert.assertTrue;
import static org.salespointframework.core.Currencies.EURO;

import java.util.Date;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;

import internetkaufhaus.entities.Comment;

// TODO: Auto-generated Javadoc
/**
 * The Class ConcreteCommentTest.
 */
public class ConcreteCommentTest {

	/** The model. */
	private Comment model;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		Date now = new Date();
		model = new Comment("Das hier ist ein Kommentar", 4, now, "12");
	}

	/**
	 * Test get formated date.
	 */
	@Test
	public void testGetFormatedDate() {

		assertTrue("Datum erhalten", model.getFormatedDate().equals("12"));
	}
	/*
	 * 
	 * @Test public void testSetFormatedDate() {
	 * 
	 * Date date = new Date("Jan 10, 1970"); date.setTime(10000); model.setFormatedDate(date);
	 * 
	 * assertTrue("Datum geändert", model.getFormatedDate().equals("Thu, 01 Jan 1970 02:00")); }
	 */

}