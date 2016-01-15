package internetkaufhaus.entities;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ElementCollection;

@Entity
@Table(name = "NEWSLETTER")
public class Newsletter {
	
	@EmbeddedId
    @AttributeOverride(name="id", column=@Column(name="NEWSLETTER_ID"))
    private NewsletterIdentifier newsletterIdentifier = new NewsletterIdentifier();
	
	private LocalDate dateCreated;
	
	@ElementCollection
	private List<ConcreteProduct> productSelection;
	
	@Column(name = "HTMLCONTENT", length = 1000000)
	private String htmlContent;
	
	@Column(name = "HTMLPREVIEWCONTENT", length = 1000000)
	private String htmlPreviewContent;
	
	private String template;

	protected Newsletter() {
		
	}
	
	public Newsletter(String template, String htmlContent, String htmlPreviewContent, List<ConcreteProduct> productSelection, LocalDate dateCreated) {
		this.template = template;
		this.htmlContent = htmlContent;
		this.htmlPreviewContent = htmlPreviewContent;
		this.productSelection = productSelection;
		this.dateCreated = dateCreated;
	}
	
	public NewsletterIdentifier etNewsletterIdentifier() {
		return newsletterIdentifier;
	}

	public void setNewsletterIdentifier(NewsletterIdentifier newsletterIdentifier) {
		this.newsletterIdentifier = newsletterIdentifier;
	}

	public String getHtmlPreviewContent() {
		return htmlPreviewContent;
	}

	public void setHtmlPreviewContent(String htmlPreviewContent) {
		this.htmlPreviewContent = htmlPreviewContent;
	}

	public NewsletterIdentifier getId() {
		return newsletterIdentifier;
	}

	public void setId(NewsletterIdentifier newsletterIdentifier) {
		this.newsletterIdentifier = newsletterIdentifier;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public String getTemplate() {
		return this.template;
	}
	
	public void setProductSelection(List<ConcreteProduct> productSelection) {
		this.productSelection = productSelection;
	}
	
	public List<ConcreteProduct> getProductSelection() {
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
