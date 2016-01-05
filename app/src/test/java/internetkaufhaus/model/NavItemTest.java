package internetkaufhaus.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
	public void init()
	{
		this.nav = new NavItem("Name","Link","Typ");
	}
	
	/**
	 * Test get name.
	 */
	@Test
	public void testGetName()
	{
		assertEquals("Name", this.nav.getName());
	}
	
	/**
	 * Test get link.
	 */
	@Test
	public void testGetLink()
	{
		assertEquals("Link", this.nav.getLink());
	}
	
	/**
	 * Test get type.
	 */
	@Test
	public void testGetType()
	{
		assertEquals("Typ", this.nav.getType());
	}
	
	/**
	 * Test set name.
	 */
	@Test
	public void testSetName()
	{
		this.nav.setName("Hans");
		assertEquals("Hans", this.nav.getName());
	}
	
	/**
	 * Test set link.
	 */
	@Test
	public void testSetLink()
	{
		this.nav.setLink("wikipedia.de");
		assertEquals("wikipedia.de", this.nav.getLink());
	}
	
	/**
	 * Test set type.
	 */
	@Test
	public void testSetType()
	{
		this.nav.setType("Schmuck");
		assertEquals("Schmuck", this.nav.getType());
	}
}
