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
	 * The compare method compares the credit amount of two existing user accounts.
	 * 
	 * @param first ConcreteUserAccount to compare with second ConcreteUserAccount
	 *  @param second ConcreteUserAccount to compare with first ConcreteUserAccount
	 * 
	 * @return integer, which account has more credits
	 *
	 */
	@Override
	public int compare(ConcreteUserAccount o1, ConcreteUserAccount o2) {
		return Integer.valueOf(o1.getCredits()).compareTo(Integer.valueOf(o2.getCredits()));
	}

}
