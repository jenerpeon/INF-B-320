package internetkaufhaus.model;

import java.util.Comparator;

import internetkaufhaus.entities.ConcreteUserAccount;

public class CreditComparator implements Comparator<ConcreteUserAccount>{

	@Override
	public int compare(ConcreteUserAccount o1, ConcreteUserAccount o2) {
		// TODO Auto-generated method stub
		return Integer.valueOf(o1.getCredits()).compareTo(Integer.valueOf(o2.getCredits()));
	}

	
}
