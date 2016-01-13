package internetkaufhaus.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import internetkaufhaus.entities.Newsletter;
import internetkaufhaus.entities.NewsletterIdentifier;

public interface NewsletterRepository extends PagingAndSortingRepository<Newsletter, NewsletterIdentifier>{

}
