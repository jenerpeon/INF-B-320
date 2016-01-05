package internetkaufhaus.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.services.ConcreteMailService;

// TODO: Auto-generated Javadoc
/**
 * Competition in which the Customers who gained the most CreditPoints are
 * rewarded with a prize.
 * 
 * @author Wilhelm Mundt
 *
 */
public class Competition {

	/**
	 * The Competition class is responsible for the recruit competition between
	 * all invitators. The the best invitators are the winners.
	 *
	 */

	private ArrayList<ConcreteUserAccount> accs = new ArrayList<ConcreteUserAccount>();

	/** The winners. */
	private ArrayList<ConcreteUserAccount> winners = new ArrayList<ConcreteUserAccount>();

	/** The creditmanager. */
	private Creditmanager creditmanager;

	/**
	 * Instantiates a new competition.
	 *
	 * @param accs
	 *            the accs
	 * @param creditmanager
	 *            the creditmanager
	 */
	public Competition(Iterable<ConcreteUserAccount> accs, Creditmanager creditmanager) {
		for (ConcreteUserAccount acc : accs) {
			this.accs.add(acc);
		}
		this.creditmanager = creditmanager;
	}

	/**
	 * The getWinners method finds out the ten best invitators.
	 *
	 * @author heiner
	 * @return ArrayList with
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
	 * The notifyWinners method sends a congratulation email to the winners.
	 *
	 * @author heiner
	 * @param sender
	 *            the sender
	 */
	public void notifyWinners(ConcreteMailService sender) {
		for (ConcreteUserAccount acc : winners) {
			sender.sendMail(acc.getEmail(), "Herzlichen Glückwunsch", "wood@shop.de", "Gewonnen");
		}
	}

	/**
	 * Returns list of participants.
	 *
	 * @return the accs
	 */
	public ArrayList<ConcreteUserAccount> getAccs() {
		return accs;
	}

	/**
	 * Sets list of participants.
	 *
	 * @param accs
	 *            the new accs
	 */
	public void setAccs(ArrayList<ConcreteUserAccount> accs) {
		this.accs = accs;
	}
}
