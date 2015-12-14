package internetkaufhaus.model;

import static org.junit.Assert.*;
import static org.salespointframework.core.Currencies.EURO;

import java.util.ArrayList;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import internetkaufhaus.Application;
import internetkaufhaus.entities.ConcreteUserAccount;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CompetitionTest {

	
	@Autowired private UserAccountManager manager;
	@Autowired private Creditmanager creditmanager;
	private ArrayList<ConcreteUserAccount> accs = new ArrayList<ConcreteUserAccount>();
	private Role customerRole = Role.of("ROLE_CUSTOMER");
	private Competition comp;
	
	
	@Before
	public void init()
	{
		int i = 0;
		for(i=0;i<30;i++)
		{
			
			ConcreteUserAccount acc = new ConcreteUserAccount("kunde"+i+"@todesstern.ru", "mockup"+i, "Kunde"+i, "Kundenname"+i, "Kundenstraße"+i, "12345", "Definitiv nicht Dresden", "kunde"+i, customerRole, manager);
			acc.setCredits(Money.of(i, EURO));
			this.accs.add(acc);
		}
		comp = new Competition(accs, creditmanager);
	}
	@Test
	public void getWinnersTest()
	{
		assertTrue("jop", comp.getWinners().size()==(this.accs.size()/10));
	}
	
}
