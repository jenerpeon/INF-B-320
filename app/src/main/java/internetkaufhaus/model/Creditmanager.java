package internetkaufhaus.model;

import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;
import java.util.List;

import org.javamoney.moneta.Money;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.time.Interval;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.repositories.ConcreteOrderRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class Creditmanager.
 */
@Component
public class Creditmanager {

	/**
	 * The Creditmanager class is responsible for increasing the credits of the invitator everytime the recruiter buy products.
	 *
	 */
	
	private ConcreteOrderRepository concreteOrderRepo;

	/**
	 * Instantiates a new creditmanager.
	 *
	 * @param concreteOrderRepo the concrete order repo
	 */
	@Autowired
	public Creditmanager(ConcreteOrderRepository concreteOrderRepo) {

		this.concreteOrderRepo = concreteOrderRepo;
	}

	// Method to update the credit amount of the given ConcreteUserAccount
	// to get the credits of the User, use the method User.getCredits of ConcreteUserAccount--Class
	
	/**
	 * The updateCreditpointsByUser is responsible for increasing the credit amount by 20% of the sales value for completed orders after one month.
	 * 
	 * @param recruiter ConcreteUserAccount which get the credits and recruited the buyer.

	 *
	 */
	public void updateCreditpointsByUser(ConcreteUserAccount recruiter) {
		List<UserAccount> recruits = recruiter.getRecruits();

		Money credits = Money.of(0, EURO);
		for (UserAccount user : recruits) {

			for (ConcreteOrder order : concreteOrderRepo.findByUser(user)) {
				if (Interval.from(order.getDateOrdered()).to(LocalDateTime.now()).getDuration().toDays() >= 30 && order.getStatus().equals(OrderStatus.COMPLETED)) {
					credits = credits.add(order.getOrder().getTotalPrice().multiply(20).divide(100));
				}
			}
			recruiter.setCredits(credits);
		}
	}
}
