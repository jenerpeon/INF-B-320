package internetkaufhaus.advices;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class HeaderAdviceTest {
	
	private HeaderAdvice advice;

	@Before
	public void init() {
		advice = new HeaderAdvice(null);
	}

	@Test
	public void testGetCart() {
		
	}

}
