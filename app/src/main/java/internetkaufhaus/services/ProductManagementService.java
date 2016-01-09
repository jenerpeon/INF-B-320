package internetkaufhaus.services;

import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;
import java.util.Optional;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Iterables;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.forms.EditArticleForm;
import internetkaufhaus.forms.StockForm;

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

	public boolean addProduct(EditArticleForm form, MultipartFile img) {
		if (!Iterables.isEmpty(dataService.getConcreteProductRepository().findByName(form.getName()))) {
			return false;
		}

		ConcreteProduct prod = new ConcreteProduct(form.getName(), Money.of(form.getPrice(), EURO),
				Money.of(form.getBuyingPrice(), EURO), form.getCategory(), form.getDescription(), "",
				img.getOriginalFilename());
		dataService.getConcreteProductRepository().save(prod);

		InventoryItem item = new InventoryItem(prod, Quantity.of(0));
		dataService.getInventory().save(item);

		return true;
	}
	
	public boolean editProduct(EditArticleForm form, MultipartFile img) {
		if(dataService.getConcreteProductRepository().findOne(form.getProdId().getId())==null) {
			return false;
		}
		ConcreteProduct prod = form.getProdId();

		prod.setCategory(form.getCategory());
		prod.addCategory(form.getCategory());
		prod.setName(form.getName());
		prod.setPrice(Money.of(form.getPrice(), EURO));
		prod.setBuyingPrice(Money.of(form.getBuyingPrice(), EURO));
		prod.setDescription(form.getDescription());

		if (!(img.getOriginalFilename().isEmpty())) {
			prod.setImagefile(img.getOriginalFilename());
		}

		dataService.getConcreteProductRepository().save(prod);
		return true;
	}

	public boolean deleteProduct(ProductIdentifier id) {
		try {
			dataService.getConcreteProductRepository().delete(id);
			dataService.getInventory().delete(dataService.getInventory().findByProductIdentifier(id).get());
		} catch (Exception e) {
			System.out.println("Das löschen des Artikels ist gescheitert:" + e.toString());
			return false;
		}
		return true;
	}

	public boolean orderProduct(StockForm form, Optional<UserAccount> userAccount) {
		if (!userAccount.isPresent() || dataService.getConcreteUserAccountRepository()
				.findByUserAccount(userAccount.get()).get().getRole().equals(Role.of("CUSTOMER"))) {
			return false;
		}
		if(dataService.getConcreteProductRepository().findOne(form.getProdId()) == null) {
			return false;
		}
		ConcreteOrder order = new ConcreteOrder(dataService.getConcreteUserAccountRepository().findByUserAccount(userAccount.get()).get(), Cash.CASH);
		OrderLine orderLine = new OrderLine(dataService.getConcreteProductRepository().findOne(form.getProdId()),
				Quantity.of(form.getQuantity()));
		order.getOrder().add(orderLine);
		order.setDateOrdered(LocalDateTime.now());
		order.setStatus(OrderStatus.OPEN);
		dataService.getOrderManager().save(order.getOrder());
		dataService.getConcreteOrderRepository().save(order);
		dataService.getConcreteInventory().findByProductIdentifier(form.getProdId()).ifPresent(x -> {
			x.increaseQuantity(Quantity.of(form.getQuantity()));
			dataService.getConcreteInventory().save(x);
		});
		return true;
	}

	public boolean destroyProduct(StockForm form, Optional<UserAccount> userAccount) {
		if (!userAccount.isPresent() || dataService.getConcreteUserAccountRepository()
				.findByUserAccount(userAccount.get()).get().getRole().equals(Role.of("CUSTOMER"))) {
			return false;
		}
		if(dataService.getConcreteProductRepository().findOne(form.getProdId()) == null) {
			return false;
		}
		dataService.getConcreteInventory().findByProductIdentifier(form.getProdId()).ifPresent(x -> {
			if (x.hasSufficientQuantity(Quantity.of(form.getQuantity()))) {
				x.decreaseQuantity(Quantity.of(form.getQuantity()));
				dataService.getConcreteInventory().save(x);
			}
		});
		return true;
	}

	/**
	 * Gets the buying price.
	 *
	 * @param order
	 *            the order
	 * @return the buying price
	 */
	public Money getBuyingPrice(ConcreteOrder order) {
		Money result = Money.of(0, EURO);
		for (OrderLine i : order.getOrder().getOrderLines()) {
			result = result.add(this.dataService.getConcreteProductRepository().findOne(i.getProductIdentifier())
					.getBuyingPrice().multiply(i.getQuantity().getAmount()));
		}
		return result;
	}
}
