package internetkaufhaus.forms;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import internetkaufhaus.entities.ConcreteProduct;

// TODO: Auto-generated Javadoc
/**
 * The Class EditArticleForm.
 */
public class EditArticleForm {

	/** The prod id. */
	private ConcreteProduct prodId;

	/** The category. */
	@NotEmpty(message = "Bitte geben Sie eine Kategorie an.")
	@Pattern(regexp = "([A-Za-zß])+", message = "Die Kategorie enthält unzulässige Zeichen")
	private String category;

	/** The name. */
	@NotEmpty(message = "Bitte geben Sie einen Artikelnamen an.")
	private String name;
	
	/** The buying price. */
	@DecimalMin(message = "Bitte geben Sie einen Einkaufspreis an.", value = "0")
	private float buyingPrice;

	/** The price. */
	@DecimalMin(message = "Bitte geben Sie einen Preis an.", value = "0")
	private float price;

	/** The description. */
	@NotEmpty(message = "Bitte geben Sie eine Beschreibung an.")
	private String description;

	/**
	 * Gets the prod id.
	 *
	 * @return the prod id
	 */
	public ConcreteProduct getProdId() {
		return prodId;
	}

	/**
	 * Sets the prod id.
	 *
	 * @param prodId the new prod id
	 */
	public void setProdId(ConcreteProduct prodId) {
		this.prodId = prodId;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the buying price.
	 *
	 * @return the buying price
	 */
	public float getBuyingPrice() {
		return this.buyingPrice;
	}
	
	/**
	 * Sets the buying price.
	 *
	 * @param buyingPrice the new buying price
	 */
	public void setBuyingPrice(float buyingPrice) {
		this.buyingPrice = buyingPrice;
	}

}
