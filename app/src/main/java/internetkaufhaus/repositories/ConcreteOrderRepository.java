package internetkaufhaus.repositories;

import org.salespointframework.order.OrderStatus;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;

import internetkaufhaus.entities.ConcreteOrder;

// TODO: Auto-generated Javadoc
/**
 * The Interface ConcreteOrderRepository.
 */
public interface ConcreteOrderRepository extends CrudRepository<ConcreteOrder, Long> {
	
	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#findAll()
	 */
	Iterable<ConcreteOrder> findAll();

	/**
	 * Find by status.
	 *
	 * @param state the state
	 * @return the iterable
	 */
	Iterable<ConcreteOrder> findByStatus(OrderStatus state);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the concrete order
	 */
	ConcreteOrder findById(Long id);

	/**
	 * Find by user.
	 *
	 * @param user the user
	 * @return the iterable
	 */
	Iterable<ConcreteOrder> findByUser(UserAccount user);

}