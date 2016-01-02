package internetkaufhaus.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.services.ConcreteMailService;
/**
 * Competition in which the Customers who 
 * gained the most CreditPoints are rewarded with a prize. 
 * 
 * @author Wilhelm Mundt
 *
 */
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
	/**
	 * Returns first 10% of participants
	 * @return
	 */
	public ArrayList<ConcreteUserAccount> getWinners() {
		winners.clear();
		for (ConcreteUserAccount acc : accs) {
			creditmanager.updateCreditpointsByUser(acc);
		}
		int accsize = this.accs.size();
		int numberofwinners = accsize / 10;
		int i = 0;
		Collections.sort(accs, new CreditComparator());
		Collections.reverse(accs);
		Iterator<ConcreteUserAccount> iter = this.accs.iterator();
		if (this.accs.size() == 0)
			return winners;
		if (this.accs.size() < 10 && iter.hasNext()) {
			winners.add(iter.next());
			return winners;
		}
		while (iter.hasNext() && i < numberofwinners) {
			winners.add(iter.next());
			i++;
		}

		return winners;
	}
	/**
	 * Notifies winners via Email
	 * @param sender 
	 */
	public void notifyWinners(ConcreteMailService sender) {
		for (ConcreteUserAccount acc : winners) {
			sender.sendMail(acc.getEmail(), "Herzlichen Glückwunsch", "wood@shop.de", "Gewonnen");
		}
	}
	/**
	 * Returns list of participants
	 * @return
	 */
	public ArrayList<ConcreteUserAccount> getAccs() {
		return accs;
	}
	/**
	 * Sets list of participants
	 * @param accs ConcreteUserAccounts for the competition
	 */
	public void setAccs(ArrayList<ConcreteUserAccount> accs) {
		this.accs = accs;
	}
}
