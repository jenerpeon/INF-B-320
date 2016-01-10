package internetkaufhaus.repositories;

import org.salespointframework.catalog.ProductIdentifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import internetkaufhaus.entities.ConcreteProduct;

/**
 * The Interface ConcreteProductRepository.
 */
public interface ConcreteProductRepository extends PagingAndSortingRepository<ConcreteProduct, ProductIdentifier> {

	/**
	 * Find by category.
	 *
	 * @param category
	 *            the category
	 * @param sort
	 *            the sort
	 * @return the iterable
	 */
	Iterable<ConcreteProduct> findByCategory(String category, Sort sort);

	/**
	 * Find by category.
	 *
	 * @param category
	 *            the category
	 * @param pageable
	 *            the pageable
	 * @return the page
	 */
	Page<ConcreteProduct> findByCategory(String category, Pageable pageable);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.repository.PagingAndSortingRepository#findAll(
	 * org.springframework.data.domain.Sort)
	 */
	Iterable<ConcreteProduct> findAll(Sort sort);

	/**
	 * Find all by order by name.
	 *
	 * @return the iterable
	 */
	Iterable<ConcreteProduct> findAllByOrderByName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.repository.PagingAndSortingRepository#findAll(
	 * org.springframework.data.domain.Pageable)
	 */
	Page<ConcreteProduct> findAll(Pageable pageable);

	/**
	 * Find by product identifier.
	 *
	 * @param productIdentifier
	 *            the product identifier
	 * @return the concrete product
	 */
	ConcreteProduct findOne(ProductIdentifier productIdentifier);
	
	@Query("SELECT c FROM ConcreteProduct c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%',:name,'%'))")
	Iterable<ConcreteProduct> findByName(@Param("name") String name);
	
	@Query("SELECT DISTINCT c.category FROM ConcreteProduct c")
	Iterable<String> getCategories();

}
