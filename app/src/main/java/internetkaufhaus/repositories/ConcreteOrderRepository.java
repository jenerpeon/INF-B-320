package internetkaufhaus.repositories;

import org.salespointframework.order.OrderIdentifier;
import org.salespointframework.order.OrderStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteUserAccount;

// TODO: Auto-generated Javadoc
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.repository.PagingAndSortingRepository#findAll(
	 * org.springframework.data.domain.Sort)
	 */
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
	 * Find one.
	 *
	 * @param id
	 *            the id
	 * @return the concrete order
	 */
	@Query("SELECT c FROM ConcreteOrder c WHERE c.id = :id")
	ConcreteOrder findOne(@Param("id") OrderIdentifier id);

	/**
	 * Find by user.
	 *
	 * @param user
	 *            the user
	 * @param sort
	 *            the sort
	 * @return the iterable
	 */
	Iterable<ConcreteOrder> findByUser(ConcreteUserAccount user, Sort sort);

	/**
	 * Find by interval and status and not returned.
	 *
	 * @param begin
	 *            the begin
	 * @param end
	 *            the end
	 * @param status
	 *            the status
	 * @return the iterable
	 */
	@Query("SELECT c FROM ConcreteOrder c WHERE c.status = :status AND c.returned = false AND c.dateOrdered >= :begin AND c.dateOrdered <= :end")
	Iterable<ConcreteOrder> findByIntervalAndStatusAndNotReturned(@Param("begin") Long begin, @Param("end") Long end,
			@Param("status") OrderStatus status);

	/**
	 * Number of find by interval and status and not returned.
	 *
	 * @param begin
	 *            the begin
	 * @param end
	 *            the end
	 * @param status
	 *            the status
	 * @return the long
	 */
	@Query("SELECT COUNT(*) FROM ConcreteOrder c WHERE c.status = :status AND c.returned = false AND c.dateOrdered >= :begin AND c.dateOrdered <= :end")
	Long numberOfFindByIntervalAndStatusAndNotReturned(@Param("begin") Long begin, @Param("end") Long end,
			@Param("status") OrderStatus status);

	/**
	 * Find by interval and status and returned.
	 *
	 * @param begin
	 *            the begin
	 * @param end
	 *            the end
	 * @param status
	 *            the status
	 * @return the iterable
	 */
	@Query("SELECT c FROM ConcreteOrder c WHERE c.status = :status AND c.returned = true AND c.dateOrdered >= :begin AND c.dateOrdered <= :end")
	Iterable<ConcreteOrder> findByIntervalAndStatusAndReturned(@Param("begin") Long begin, @Param("end") Long end,
			@Param("status") OrderStatus status);

	/**
	 * Number of find by interval and status and returned.
	 *
	 * @param begin
	 *            the begin
	 * @param end
	 *            the end
	 * @param status
	 *            the status
	 * @return the long
	 */
	@Query("SELECT COUNT(*) FROM ConcreteOrder c WHERE c.status = :status AND c.returned = true AND c.dateOrdered >= :begin AND c.dateOrdered <= :end")
	Long numberOfFindByIntervalAndStatusAndReturned(@Param("begin") Long begin, @Param("end") Long end,
			@Param("status") OrderStatus status);

	/**
	 * Find by returned true.
	 *
	 * @return the iterable
	 */
	Iterable<ConcreteOrder> findByReturnedTrue();

	/**
	 * Find by user and discount.
	 *
	 * @param user
	 *            the user
	 * @return the iterable
	 */
	@Query("SELECT c FROM ConcreteOrder c WHERE c.user = :user AND c.usedDiscountPoints > 0")
	Iterable<ConcreteOrder> findByUserAndDiscount(@Param("user") ConcreteUserAccount user);

	/**
	 * Find by user and returned.
	 *
	 * @param user
	 *            the user
	 * @param returned
	 *            the returned
	 * @return the iterable
	 */
	@Query("SELECT c FROM ConcreteOrder c WHERE c.user = :user AND c.returned = :returned")
	Iterable<ConcreteOrder> findByUserAndReturned(@Param("user") ConcreteUserAccount user,
			@Param("returned") boolean returned);

}