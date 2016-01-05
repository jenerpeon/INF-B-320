package internetkaufhaus.services;

import static org.salespointframework.core.Currencies.EURO;

import org.javamoney.moneta.Money;
import org.salespointframework.order.OrderLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import internetkaufhaus.entities.ConcreteOrder;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductManagementService.
 */
@Service
public class ProductManagementService {

	/** The data service. */
	@Autowired
	private DataService dataService;

	/**
	 * Instantiates a new product management service.
	 */
	public ProductManagementService() {
		System.out.print("");
	}

	/**
	 * Gets the buying price.
	 *
	 * @param order the order
	 * @return the buying price
	 */
	public Money getBuyingPrice(ConcreteOrder order) {
		Money result = Money.of(0, EURO);
		for (OrderLine i : order.getOrder().getOrderLines()) {
			result = result.add(
					this.dataService.getConcreteProductRepository().findByProductIdentifier(i.getProductIdentifier())
							.getBuyingPrice().multiply(i.getQuantity().getAmount()));
		}
		return result;
	}
}
