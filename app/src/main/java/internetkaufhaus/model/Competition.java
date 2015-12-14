package internetkaufhaus.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import static org.salespointframework.core.Currencies.EURO;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;

import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

public class Competition {

	
	private ArrayList<ConcreteUserAccount> accs = new ArrayList<ConcreteUserAccount>();
	private ArrayList<ConcreteUserAccount> winners = new ArrayList<ConcreteUserAccount>();
	private Creditmanager creditmanager;
	
	
	public Competition(Iterable<ConcreteUserAccount> accs, Creditmanager creditmanager)
	{
		for(ConcreteUserAccount acc:accs)
		{
			this.accs.add(acc);
		}
		this.creditmanager = creditmanager;
	}
	
	public ArrayList<ConcreteUserAccount> getWinners()
	{		
		winners.clear();
		for(ConcreteUserAccount acc: accs)
		{
			creditmanager.udateCreditpointsByUser(acc);
		}
		int accsize = this.accs.size();
		int numberofwinners = accsize/10;
		int i = 0;
		Collections.sort(accs, new CreditComparator());
		Collections.reverse(accs);
		Iterator<ConcreteUserAccount> iter = this.accs.iterator();
		if(this.accs.size()==0)
			return winners;
		
		while(iter.hasNext() && i<numberofwinners)
		{
			winners.add(iter.next());
			i++;
		}
		
		return winners;
	}
	public void notifyWinners(ConcreteMailSender sender)
	{
		for(ConcreteUserAccount acc: winners)
		{
			sender.sendMail(acc.getEmail(), "Herzlichen Glückwunsch", "wood@shop.de", "Gewonnen");
		}
	}
}
