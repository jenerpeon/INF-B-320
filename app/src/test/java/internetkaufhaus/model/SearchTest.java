package internetkaufhaus.model;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class SearchTest {

	private Search search;

	@Before
	public void init() {
		this.search = new Search();
	}

	@Test
	public void test() {
		this.search.getComments();
	}

}
