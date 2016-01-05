package internetkaufhaus.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class NavItemTest {

	private NavItem nav;
	@Before
	public void init()
	{
		this.nav = new NavItem("Name","Link","Typ");
	}
	@Test
	public void testGetName()
	{
		assertEquals("Name", this.nav.getName());
	}
	@Test
	public void testGetLink()
	{
		assertEquals("Link", this.nav.getLink());
	}
	@Test
	public void testGetType()
	{
		assertEquals("Typ", this.nav.getType());
	}
	@Test
	public void testSetName()
	{
		this.nav.setName("Hans");
		assertEquals("Hans", this.nav.getName());
	}
	@Test
	public void testSetLink()
	{
		this.nav.setLink("wikipedia.de");
		assertEquals("wikipedia.de", this.nav.getLink());
	}
	@Test
	public void testSetType()
	{
		this.nav.setType("Schmuck");
		assertEquals("Schmuck", this.nav.getType());
	}
}
