package internetkaufhaus.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "NEWSLETTER")
public class Newsletter {
	
	@EmbeddedId
    @AttributeOverride(name="id", column=@Column(name="NEWSLETTER_ID"))
    private NewsletterIdentifier newsletterIdentifier = new NewsletterIdentifier();
	
	private LocalDate dateCreated;
	
	
	private List<List<ConcreteProduct>> productSelection;
	
	private String htmlContent;
	
	private String template;

	protected Newsletter() {
		
	}
	
	public Newsletter(String template, List<List<ConcreteProduct>> productSelection, LocalDate dateCreated) {
		this.htmlContent = template;
		this.productSelection = productSelection;
		this.dateCreated = dateCreated;
	}
	
	public void setTemplate(String template) {
		this.template = template;
	}
	
	public String getTemplate() {
		return this.template;
	}
	
	public void setProductSelection(List<List<ConcreteProduct>> productSelection) {
		this.productSelection = productSelection;
	}
	
	public List<List<ConcreteProduct>> getProductSelection() {
		return this.productSelection;
	}
	
	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}
	
	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	
}
