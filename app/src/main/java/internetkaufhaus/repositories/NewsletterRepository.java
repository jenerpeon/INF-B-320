package internetkaufhaus.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import internetkaufhaus.entities.Newsletter;
import internetkaufhaus.entities.NewsletterIdentifier;

public interface NewsletterRepository extends PagingAndSortingRepository<Newsletter, NewsletterIdentifier>{
	
	@Override
	@Query("SELECT n FROM Newsletter n WHERE n.newsletterIdentifier = :id")
	Newsletter findOne(@Param("id") NewsletterIdentifier id);

}
