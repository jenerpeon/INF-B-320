package internetkaufhaus.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.salespointframework.useraccount.UserAccount;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.repositories.ConcreteOrderRepository;

public class ReturnManager {
	
	public static List<ConcreteOrder> getConcreteOrderDuringLastTwoWeeks(ConcreteOrderRepository concreteOrderRepo, Optional<UserAccount> userAccount){
	
		LocalDateTime timeStart = LocalDateTime.now().minusWeeks(2);
	
		Iterable<ConcreteOrder> ordersCompletedInReturnedTimeOne = concreteOrderRepo.findByUser(userAccount.get());
		
		List<ConcreteOrder> ordersCompletedInReturnedTime = new ArrayList<ConcreteOrder>();
		
		for( ConcreteOrder order : ordersCompletedInReturnedTimeOne){
			if(order.getDateOrdered().isBefore(timeStart)||order.getReturned()){
			}
			else {
				ordersCompletedInReturnedTime.add(order);
			}
		}
		
		return ordersCompletedInReturnedTime;
	}
}
