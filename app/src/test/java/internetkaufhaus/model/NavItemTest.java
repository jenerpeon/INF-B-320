package internetkaufhaus.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// TODO: Auto-generated Javadoc
/**
 * The Class NavItemTest.
 */
public class NavItemTest {

	/** The nav. */
	private NavItem nav;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		this.nav = new NavItem("Name", "Link", "Typ");
	}
	
	@Test
	public void constructorTest() {
		assertTrue("Konstruktor funktioniert", nav.getName().equals("Name"));
		assertTrue("Konstruktor funktioniert", nav.getLink().equals("Link"));
		assertTrue("Konstruktor funktioniert", nav.getType().equals("Typ"));
	}

	/**
	 * Test get name.
	 */
	@Test
	public void nameTest() {
		nav.setName("Schmuck");
		assertEquals("Schmuck", nav.getName());
	}

	/**
	 * Test get link.
	 */
	@Test
	public void linkTest() {
		nav.setLink("wikipedia.de");
		assertEquals("wikipedia.de", nav.getLink());
	}

	/**
	 * Test get type.
	 */
	@Test
	public void typeTest() {
		nav.setType("Kategorie");
		assertEquals("Kategorie", nav.getType());
	}

}
