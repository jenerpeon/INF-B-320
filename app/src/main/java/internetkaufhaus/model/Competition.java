package internetkaufhaus.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static org.salespointframework.core.Currencies.EURO;

import org.javamoney.moneta.Money;

import internetkaufhaus.entities.ConcreteUserAccount;

public class Competition {

	private ArrayList<ConcreteUserAccount> winners = new ArrayList<ConcreteUserAccount>();
	private ArrayList<ConcreteUserAccount> accs = new ArrayList<ConcreteUserAccount>(); 
	public Competition(Iterable<ConcreteUserAccount> accs)
	{
		for(ConcreteUserAccount acc:accs)
		{
			this.accs.add(acc);
		}
	}
	
	public ArrayList<ConcreteUserAccount> getWinners()
	{
		accs.forEach(x->x.setCredits(Money.of(100-x.getId(), EURO)));
		Collections.sort(accs, new Comparator<ConcreteUserAccount>()
				{
					@Override
					public int compare(ConcreteUserAccount acc1, ConcreteUserAccount acc2)
					{
						
						return Integer.valueOf(acc2.getCredits()).compareTo(Integer.valueOf(acc1.getCredits()));
					}
				});
		accs.forEach(x->System.out.println(x.getUserAccount().getUsername()+" "+x.getCredits()));
		return winners;
	}
}
