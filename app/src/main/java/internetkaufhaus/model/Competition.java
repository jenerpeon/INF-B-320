package internetkaufhaus.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import internetkaufhaus.entities.ConcreteUserAccount;

public class Competition {

	private ArrayList<ConcreteUserAccount> accs = new ArrayList<ConcreteUserAccount>();
	private ArrayList<ConcreteUserAccount> winners = new ArrayList<ConcreteUserAccount>();
	private Creditmanager creditmanager;

	public Competition(Iterable<ConcreteUserAccount> accs, Creditmanager creditmanager) {
		for (ConcreteUserAccount acc : accs) {
			this.accs.add(acc);
		}
		this.creditmanager = creditmanager;
	}

	public ArrayList<ConcreteUserAccount> getWinners() {
		winners.clear();
		for (ConcreteUserAccount acc : accs) {
			creditmanager.udateCreditpointsByUser(acc);
		}
		int accsize = this.accs.size();
		int numberofwinners = accsize / 10;
		int i = 0;
		Collections.sort(accs, new CreditComparator());
		Collections.reverse(accs);
		Iterator<ConcreteUserAccount> iter = this.accs.iterator();
		if (this.accs.size() == 0)
			return winners;
		if(this.accs.size()<10 && iter.hasNext())
		{
			winners.add(iter.next());
			return winners;
		}
		while(iter.hasNext() && i<numberofwinners)
		{
			winners.add(iter.next());
			i++;
		}

		return winners;
	}

	public void notifyWinners(ConcreteMailSender sender) {
		for (ConcreteUserAccount acc : winners) {
			sender.sendMail(acc.getEmail(), "Herzlichen Glückwunsch", "wood@shop.de", "Gewonnen");
		}
	}

	public ArrayList<ConcreteUserAccount> getAccs() {
		return accs;
	}

	public void setAccs(ArrayList<ConcreteUserAccount> accs) {
		this.accs = accs;
	}
}
