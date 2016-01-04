package internetkaufhaus.services;

import static org.salespointframework.core.Currencies.EURO;

import org.javamoney.moneta.Money;
import org.salespointframework.order.OrderLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import internetkaufhaus.entities.ConcreteOrder;

@Service
public class ProductManagementService {

	@Autowired
	private DataService dataService;

	public ProductManagementService() {
	}

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
