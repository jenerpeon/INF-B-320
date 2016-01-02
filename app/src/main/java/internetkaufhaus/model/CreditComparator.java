package internetkaufhaus.model;

import java.util.Comparator;

import internetkaufhaus.entities.ConcreteUserAccount;

/**
 * 
 * @author Wilhelm Mundt
 *
 *A CreditComparator for sorting UserAccounts by credits.
 */
public class CreditComparator implements Comparator<ConcreteUserAccount> {

	/**
	 * Compares two ConcreteUserAccounts
	 * @param o1
	 * @param o2
	 * @return
	 */
	@Override
	public int compare(ConcreteUserAccount o1, ConcreteUserAccount o2) {
		return Integer.valueOf(o1.getCredits()).compareTo(Integer.valueOf(o2.getCredits()));
	}

}
