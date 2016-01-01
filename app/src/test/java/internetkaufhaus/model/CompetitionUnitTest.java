package internetkaufhaus.model;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.services.ConcreteMailService;

public class CompetitionUnitTest {
	private ArrayList<ConcreteUserAccount> accs = new ArrayList<ConcreteUserAccount>();
	private Creditmanager credit;
	@Before
	public void init()
	{
		int i = 0;
		for(i=0;i<20;i++)
		{
			ConcreteUserAccount mockacc = mock(ConcreteUserAccount.class);
			when(mockacc.getCredits()).thenReturn(i);
			when(mockacc.getEmail()).thenReturn("kunde"+i+"@mock.de");
			accs.add(mockacc);
		}
		this.credit = mock(Creditmanager.class);
	}
	@Test
	public void getWinnersTest()
	{
		Competition com = new Competition(this.accs, this.credit);
		assertTrue("Es wurden "+com.getWinners().size()+" Gewinner ermittelt (falsch)", com.getWinners().size()==2);
	}
	@Test
	public void notifyWinnersTest()
	{
		ConcreteMailService sender = mock(ConcreteMailService.class);
		Competition com = new Competition(this.accs, this.credit);
		com.getWinners();
		com.notifyWinners(sender);
		verify(sender, times(2)).sendMail(anyString(),eq("Herzlichen Glückwunsch"),eq("wood@shop.de"),eq("Gewonnen"));
	}
	@Test
	public void getAccsTest()
	{
		Competition com = new Competition(this.accs, this.credit);
		assertTrue("ConcreteUserAccounts stimmen nicht überein", com.getAccs().equals(this.accs));
	}
	@Test
	public void setAccsTest()
	{
		ConcreteUserAccount mocki = mock(ConcreteUserAccount.class);
		ArrayList<ConcreteUserAccount> acci = new ArrayList<ConcreteUserAccount>();
		acci.add(mocki);
		Competition com = new Competition(this.accs, this.credit);
		com.setAccs(acci);
		assertTrue("Accounts stimmen nicht überein", com.getAccs().equals(acci));
	}
}
