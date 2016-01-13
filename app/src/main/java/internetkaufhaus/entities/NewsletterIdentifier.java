package internetkaufhaus.entities;

import javax.persistence.Embeddable;

import org.salespointframework.core.SalespointIdentifier;

@Embeddable
public class NewsletterIdentifier extends SalespointIdentifier{

	private static final long serialVersionUID = 6625075538614910470L;

	NewsletterIdentifier() {
    }

	NewsletterIdentifier(String newsletterIdentifier) {
        super(newsletterIdentifier);
    }
}
