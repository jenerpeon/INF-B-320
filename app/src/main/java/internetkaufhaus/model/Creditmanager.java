package internetkaufhaus.model;

import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;
import java.util.List;

import org.javamoney.moneta.Money;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.time.Interval;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;


@Component
public class Creditmanager {
	
	OrderManager<Order> orderManager;
	UserAccountManager userManager;
	ConcreteUserAccountRepository userRepo;
	ConcreteOrderRepository concreteOrderRepo;
	
	@Autowired
	public Creditmanager(OrderManager<Order> orderManager, UserAccountManager userManager, ConcreteUserAccountRepository userRepo,ConcreteOrderRepository concreteOrderRepo){
		this.orderManager=orderManager;
		this.userManager=userManager;
		this.userRepo=userRepo;
		this.concreteOrderRepo=concreteOrderRepo;
	}


//Method to update the credit amount of the given ConcreteUserAccount 
//to get the credits of the User, use the method User.getCredits of ConcreteUserAccount--Class
	public void udateCreditpointsByUser(ConcreteUserAccount recruiter){		
		List<UserAccount>recruits= recruiter.getRecruits();
		
		
		Money credits=Money.of(0, EURO);
		System.out.println(recruits);
		for( UserAccount user : recruits){
			
			for(ConcreteOrder order : concreteOrderRepo.findByUser(user)){
				System.out.println(order);
//				System.out.println("Step 1");
//				System.out.println("Periode"+Interval.from(order.getDateOrdered()).to(LocalDateTime.now()).getDuration().toDays());
				if(Interval.from(order.getDateOrdered()).to(LocalDateTime.now()).getDuration().toDays()>=30 && order.getStatus().equals(OrderStatus.COMPLETED)){
					credits = credits.add(order.getOrder().getTotalPrice().multiply(20).divide(100));
//					System.out.println(order.getOrder().getTotalPrice().toString());
				}
			}
		
		

		System.out.println(credits);
		recruiter.setCredits(credits);
		}
	}
}
