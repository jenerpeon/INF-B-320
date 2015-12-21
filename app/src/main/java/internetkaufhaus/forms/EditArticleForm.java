package internetkaufhaus.forms;

import org.hibernate.validator.constraints.NotEmpty;

import internetkaufhaus.entities.ConcreteProduct;

public class EditArticleForm {

	private ConcreteProduct prodId;

	@NotEmpty(message = "{EditArticleForm.category.NotEmpty}")
	private String category;

	@NotEmpty(message = "{EditArticleForm.name.NotEmpty}")
	private String name;
	
	private float buyingPrice;

	private float price;

	@NotEmpty(message = "{EditArticleForm.description.NotEmpty}")
	private String description;

	public ConcreteProduct getProdId() {
		return prodId;
	}

	public void setProdId(ConcreteProduct prodId) {
		this.prodId = prodId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public float getBuyingPrice() {
		return this.buyingPrice;
	}
	
	public void setBuyingPrice(float buyingPrice) {
		this.buyingPrice = buyingPrice;
	}

}
