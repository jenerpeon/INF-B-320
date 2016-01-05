package internetkaufhaus.model;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchTest.
 */
public class SearchTest {

	/** The search. */
	private Search search;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		this.search = new Search();
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		this.search.getComments();
	}

}
