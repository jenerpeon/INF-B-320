package internetkaufhaus.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.salespointframework.useraccount.UserAccount;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.repositories.ConcreteOrderRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class ReturnManager.
 */
public class ReturnManager {
	
	/**
	 * The ReturnManager class is made for the return of orders.
	 *
	 * @param concreteOrderRepo the concrete order repo
	 * @param userAccount the user account
	 * @return the concrete order during last two weeks
	 */
	
	/**
	 * The getConcreteOrderDuringLastTwoWeeks method is responsible for the return of a list containing all orders of an user completed during the last two weeks.
	 *
	 *@param concreteOrderRepo the order repository. All orders are saved here.
	 *@param userAccount the user account 
	 *
	 *@return List of Orders completed during the last two weeks 
	 *
	 */
	public static List<ConcreteOrder> getConcreteOrderDuringLastTwoWeeks(ConcreteOrderRepository concreteOrderRepo, Optional<UserAccount> userAccount) {

		LocalDateTime timeStart = LocalDateTime.now().minusWeeks(2);

		Iterable<ConcreteOrder> ordersCompletedInReturnedTimeOne = concreteOrderRepo.findByUser(userAccount.get());

		List<ConcreteOrder> ordersCompletedInReturnedTime = new ArrayList<ConcreteOrder>();

		for (ConcreteOrder order : ordersCompletedInReturnedTimeOne) {
			if (order.getDateOrdered().isBefore(timeStart) || order.getReturned()) {
				System.out.print("");
			} else {
				ordersCompletedInReturnedTime.add(order);
			}
		}

		return ordersCompletedInReturnedTime;
	}
}
