package internetkaufhaus.repositories;

import java.time.LocalDateTime;
import org.salespointframework.order.OrderIdentifier;
import org.salespointframework.order.OrderStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

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
	
	@Query("SELECT c FROM ConcreteOrder c WHERE c.id = :id")
	ConcreteOrder findOne(@Param("id") OrderIdentifier id);

	/**
	 * Find by user.
	 *
	 * @param user
	 *            the user
	 * @return the iterable
	 */
	Iterable<ConcreteOrder> findByUser(ConcreteUserAccount user, Sort sort);

	Iterable<ConcreteOrder> findByDateOrdered(LocalDateTime time);
	
	@Query("SELECT c FROM ConcreteOrder c WHERE c.status = :status AND c.returned = false AND c.dateOrdered >= :begin AND c.dateOrdered <= :end")
	Iterable<ConcreteOrder> findByIntervalAndStatusAndNotReturned(@Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end, @Param("status") OrderStatus status);
	
	@Query("SELECT c FROM ConcreteOrder c WHERE c.status = :status AND c.returned = true AND c.dateOrdered >= :begin AND c.dateOrdered <= :end")
	Iterable<ConcreteOrder> findByIntervalAndStatusAndReturned(@Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end, @Param("status") OrderStatus status);
	
	Iterable<ConcreteOrder> findByReturnedTrue();
	
	@Query("SELECT c FROM ConcreteOrder c WHERE c.user = :user AND c.usedDiscountPoints > 0")
	Iterable<ConcreteOrder> findByUserAndDiscount(@Param("user") ConcreteUserAccount user);
	
	@Query("SELECT c FROM ConcreteOrder c WHERE c.user = :user AND c.returned = :returned")
	Iterable<ConcreteOrder> findByUserAndReturned(@Param("user") ConcreteUserAccount user, @Param("returned") boolean returned);

}