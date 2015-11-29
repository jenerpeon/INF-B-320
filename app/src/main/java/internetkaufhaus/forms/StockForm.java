package internetkaufhaus.forms;

import org.hibernate.validator.constraints.NotEmpty;
import org.salespointframework.catalog.ProductIdentifier;

public class StockForm {

//	@NotEmpty(message="{StockForm.quantity.NotEmpty}")
	private int quantity;
//	@NotEmpty(message="{StockForm.prodId.NotEmpty}")
	private ProductIdentifier prodId;
	
	public StockForm()
	{
		
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
