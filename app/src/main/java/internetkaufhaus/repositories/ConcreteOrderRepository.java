package internetkaufhaus.repositories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.salespointframework.order.Order;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.time.Interval;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteUserAccount;

/**
 * The Interface ConcreteOrderRepository.
 */
public interface ConcreteOrderRepository extends PagingAndSortingRepository<ConcreteOrder, Long> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#findAll()
	 */
	Iterable<ConcreteOrder> findAll();
	
	Iterable<ConcreteOrder> findAll(Sort sort);

	/**
	 * Find by status.
	 *
	 * @param state
	 *            the state
	 * @return the iterable
	 */
	Iterable<ConcreteOrder> findByStatus(OrderStatus state);

	/**
	 * Find by id.
	 *
	 * @param id
	 *            the id
	 * @return the concrete order
	 */
	ConcreteOrder findById(Long id);

	/**
	 * Find by user.
	 *
	 * @param user
	 *            the user
	 * @return the iterable
	 */
	Iterable<ConcreteOrder> findByUser(ConcreteUserAccount user, Sort sort);

	/**
	 * Find by order.
	 *
	 * @param order
	 *            the order
	 * @return the iterable
	 */
	ConcreteOrder findByOrder(Order order);
	
	Iterable<ConcreteOrder> findByDateOrdered(LocalDateTime time);
	
	default public Iterable<ConcreteOrder> findByInterval(Interval interval) {
		LocalDateTime end = interval.getEnd();
		LocalDateTime begin = interval.getStart();
		Collection<ConcreteOrder> collect = new ArrayList<ConcreteOrder>();
		Iterable<ConcreteOrder> allOrders = this.findAll();
		for (ConcreteOrder order : allOrders) {
			LocalDateTime orderTime = order.getDateOrdered();
			if ((orderTime.isBefore(end) || orderTime.isEqual(end)) && (orderTime.isAfter(begin) || orderTime.isEqual(begin))) {
				collect.add(order);
			}
		}
		Iterable<ConcreteOrder> iter = (Iterable<ConcreteOrder>)collect;
		return iter;
	}
	
	Iterable<ConcreteOrder> findByReturnedTrue();

}