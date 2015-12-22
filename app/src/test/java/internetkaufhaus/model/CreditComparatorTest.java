package internetkaufhaus.model;

import org.junit.Before;
import org.junit.Test;

import internetkaufhaus.entities.ConcreteUserAccount;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CreditComparatorTest {

	private ConcreteUserAccount acc1, acc2;
	@Before
	public void init()
	{
		this.acc1 = mock(ConcreteUserAccount.class);
		this.acc2 = mock(ConcreteUserAccount.class);
		when(acc1.getCredits()).thenReturn(50);
		when(acc2.getCredits()).thenReturn(40);
	}
	@Test
	public void compareTest()
	{
		CreditComparator comp = new CreditComparator();
		assertTrue("klappt nicht", comp.compare(this.acc1, this.acc2)>0);
	}
}
