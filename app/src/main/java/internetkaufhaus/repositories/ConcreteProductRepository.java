package internetkaufhaus.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import internetkaufhaus.entities.ConcreteProduct;

public interface ConcreteProductRepository extends PagingAndSortingRepository<ConcreteProduct, Long> {

	Iterable<ConcreteProduct> findByCategory(String category, Sort sort);

	Page<ConcreteProduct> findByCategory(String category, Pageable pageable);

	Iterable<ConcreteProduct> findAll(Sort sort);

	Iterable<ConcreteProduct> findAllByOrderByName();

	Page<ConcreteProduct> findAll(Pageable pageable);

}
