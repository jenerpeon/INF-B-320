package internetkaufhaus.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.salespointframework.catalog.ProductIdentifier;

public class StockForm {

	@NotNull(message = "leer")
	@Min(1)
	private int quantity;

	private ProductIdentifier prodId;

	public StockForm() {

	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ProductIdentifier getProdId() {
		return prodId;
	}

	public void setProdId(ProductIdentifier prodId) {
		this.prodId = prodId;
	}

}
