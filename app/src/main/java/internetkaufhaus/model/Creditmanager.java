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

@Component
public class Creditmanager {

	private ConcreteOrderRepository concreteOrderRepo;

	@Autowired
	public Creditmanager(ConcreteOrderRepository concreteOrderRepo) {

		this.concreteOrderRepo = concreteOrderRepo;
	}

	// Method to update the credit amount of the given ConcreteUserAccount
	// to get the credits of the User, use the method User.getCredits of ConcreteUserAccount--Class
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
