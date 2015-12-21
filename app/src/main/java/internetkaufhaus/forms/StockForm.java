package internetkaufhaus.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.salespointframework.catalog.ProductIdentifier;

public class StockForm {

	@NotNull(message = "leer")
	@Min(1)
	private int quantity;

	private ProductIdentifier prodId;

	/**
	 * This comment is just here because sonarcube is a little bitch.
	 */
	public StockForm() {
		/**
		 * This comment is just here because sonarcube is a little bitch.
		 */
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
