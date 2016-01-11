package internetkaufhaus.model;

import java.util.Comparator;

import internetkaufhaus.entities.ConcreteUserAccount;

// TODO: Auto-generated Javadoc
/**
 * The Class CreditComparator.
 *
 * @author Wilhelm Mundt
 * 
 *         A CreditComparator for sorting UserAccounts by credits.
 */
public class CreditComparator implements Comparator<ConcreteUserAccount> {

	/**
	 * The compare method compares the credit amount of two existing user
	 * accounts.
	 *
	 * @param o1
	 *            the o1
	 * @param o2
	 *            the o2
	 * @return integer, which account has more credits
	 */
	@Override
	public int compare(ConcreteUserAccount o1, ConcreteUserAccount o2) {
		return new Long(o1.getCredits()).compareTo(new Long(o2.getCredits()));
	}

}
