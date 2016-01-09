package internetkaufhaus.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aspectj.weaver.Iterators;
import org.salespointframework.catalog.ProductIdentifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

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
	
	default public Iterable<ConcreteProduct> findByName(String name) {
		String nameLowerCase = name.toLowerCase();
		Collection<ConcreteProduct> collect = new ArrayList<ConcreteProduct>();
		Iterable<ConcreteProduct> allProducts = this.findAll();
		for (ConcreteProduct prod : allProducts) {
			if (prod.getName().toLowerCase().contains(nameLowerCase)) {
				collect.add(prod);
			}
		}
		Iterable<ConcreteProduct> iter = (Iterable<ConcreteProduct>)collect;
		return iter;
	}
	
	default public Iterable<String> getCategories() {
		List<String> categories = new ArrayList<String>(); 
		for (ConcreteProduct prod : this.findAll()) {
			if (!categories.contains(prod.getCategory())) {
				categories.add(prod.getCategory());
			}
		}
		return categories;
	}

}
