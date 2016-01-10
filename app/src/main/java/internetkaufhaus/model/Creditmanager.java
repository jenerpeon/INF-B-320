package internetkaufhaus.model;

import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;
import java.util.List;

import org.javamoney.moneta.Money;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.services.DataService;

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
	private final DataService dataService;

	/**
	 * Instantiates a new creditmanager.
	 */
	@Autowired
	public Creditmanager(DataService dataService) {
		this.dataService = dataService;
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
		List<ConcreteUserAccount> recruits = recruiter.getRecruits();
		Money credits = Money.of(0, EURO);
		for (ConcreteUserAccount user : recruits) {
			Sort sorting = new Sort(new Sort.Order(Sort.Direction.ASC, "dateOrdered", Sort.NullHandling.NATIVE));
			for (ConcreteOrder order : dataService.getConcreteOrderRepository().findByUser(user, sorting)) {
				if (Interval.from(order.getDateOrdered()).to(LocalDateTime.now()).getDuration().toDays() >= 30 && order.getStatus().equals(OrderStatus.COMPLETED)) {
					credits = credits.add(order.getTotalPrice().divide(100));
				}
			}
			recruiter.setCredits(credits);
		}
	}
}
