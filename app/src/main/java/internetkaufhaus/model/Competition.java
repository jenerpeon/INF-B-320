package internetkaufhaus.model;

import java.util.List;

import org.salespointframework.useraccount.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.google.common.collect.Iterators;

import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.services.ConcreteMailService;
import internetkaufhaus.services.DataService;

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
	private DataService data;

	/** The winners. */
	private List<ConcreteUserAccount> winners;

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
	@Autowired
	public Competition(DataService data) {
		this.data = data;
		this.creditmanager = new Creditmanager(data);
	}

	/**
	 * The getWinners method finds out the ten best invitators.
	 *
	 * @author heiner
	 * @return ArrayList with
	 */
	public List<ConcreteUserAccount> getWinners() {

		for (ConcreteUserAccount acc : data.getConcreteUserAccountRepository().findAll()) {
			creditmanager.updateCreditpointsByUser(acc);
			System.out.println(acc.getEmail());

		}

		Page<ConcreteUserAccount> page = data.getConcreteUserAccountRepository()
				.findByRole(Role.of("ROLE_CUSTOMER"),
						new PageRequest(0,
								Iterators.size(data.getConcreteUserAccountRepository()
										.findByRole(Role.of("ROLE_CUSTOMER")).iterator()) / 10,
						new Sort(new Sort.Order(Sort.Direction.DESC, "credits", Sort.NullHandling.NATIVE))));
		
		winners = page.getContent();

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

}
