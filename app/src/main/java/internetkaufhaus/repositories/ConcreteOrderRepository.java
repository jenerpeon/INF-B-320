package internetkaufhaus.repositories;

import org.salespointframework.order.Order;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;

import internetkaufhaus.entities.ConcreteOrder;

public interface ConcreteOrderRepository extends CrudRepository<ConcreteOrder, Long> {
	Iterable<ConcreteOrder> findAll();

	Iterable<ConcreteOrder> findByStatus(OrderStatus state);

    ConcreteOrder findByOrder(Order order);	
	ConcreteOrder findById(Long id);

	Iterable<ConcreteOrder> findByUser(UserAccount user);

}