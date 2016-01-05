package internetkaufhaus.entities;

import static org.salespointframework.core.Currencies.EURO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.collections.IteratorUtils;
import org.javamoney.moneta.Money;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.useraccount.UserAccount;

import internetkaufhaus.repositories.ConcreteProductRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class ConcreteOrder.
 */
@Entity
@Table(name = "CORDER")
public class ConcreteOrder implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status. */
	// private OrderManager<Order> orderManager;
	private OrderStatus status;

	/** The user. */
	@OneToOne
	private UserAccount user;

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The billing gender. */
	private String billingGender;

	/** The billing first name. */
	private String billingFirstName;

	/** The billing last name. */
	private String billingLastName;

	/** The billing street. */
	private String billingStreet;

	/** The billing house number. */
	private String billingHouseNumber;

	/** The billing address line2. */
	private String billingAddressLine2;

	/** The billing zip code. */
	private String billingZipCode;

	/** The billing town. */
	private String billingTown;

	/** The shipping gender. */
	private String shippingGender;

	/** The shipping first name. */
	private String shippingFirstName;

	/** The shipping last name. */
	private String shippingLastName;

	/** The shipping street. */
	private String shippingStreet;

	/** The shipping house number. */
	private String shippingHouseNumber;

	/** The shipping address line2. */
	private String shippingAddressLine2;

	/** The shipping zip code. */
	private String shippingZipCode;

	/** The shipping town. */
	private String shippingTown;

	/** The date ordered. */
	private LocalDateTime dateOrdered;

	/** The order. */
	@OneToOne
	private Order order;

	/** The returned. */
	private boolean returned = false;

	/** The return reason. */
	private String returnReason;

	/**
	 * Instantiates a new concrete order.
	 */
	@SuppressWarnings({ "unused" })
	private ConcreteOrder() {

	}

	/**
	 * Instantiates a new concrete order.
	 *
	 * @param account
	 *            the account
	 * @param cash
	 *            the cash
	 */
	public ConcreteOrder(UserAccount account, Cash cash) {
		this.order = new Order(account, cash);
		this.status = this.order.getOrderStatus();
		this.user = account;
	}

	/**
	 * Instantiates a new concrete order.
	 *
	 * @param billingGender
	 *            the billing gender
	 * @param billingFirstName
	 *            the billing first name
	 * @param billingLastName
	 *            the billing last name
	 * @param billingStreet
	 *            the billing street
	 * @param billingHouseNumber
	 *            the billing house number
	 * @param billingAddressLine2
	 *            the billing address line2
	 * @param billingZipCode
	 *            the billing zip code
	 * @param billingTown
	 *            the billing town
	 * @param shippingGender
	 *            the shipping gender
	 * @param shippingFirstName
	 *            the shipping first name
	 * @param shippingLastName
	 *            the shipping last name
	 * @param shippingStreet
	 *            the shipping street
	 * @param shippingHouseNumber
	 *            the shipping house number
	 * @param shippingAddressLine2
	 *            the shipping address line2
	 * @param shippingZipCode
	 *            the shipping zip code
	 * @param shippingTown
	 *            the shipping town
	 * @param dateOrdered
	 *            the date ordered
	 * @param order
	 *            the order
	 */
	public ConcreteOrder(String billingGender, String billingFirstName, String billingLastName, String billingStreet,
			String billingHouseNumber, String billingAddressLine2, String billingZipCode, String billingTown,
			String shippingGender, String shippingFirstName, String shippingLastName, String shippingStreet,
			String shippingHouseNumber, String shippingAddressLine2, String shippingZipCode, String shippingTown,
			LocalDateTime dateOrdered, Order order) {
		this.billingGender = billingGender;
		this.billingFirstName = billingFirstName;
		this.billingLastName = billingLastName;
		this.billingStreet = billingStreet;
		this.billingHouseNumber = billingHouseNumber;
		this.billingAddressLine2 = billingAddressLine2;
		this.billingZipCode = billingZipCode;
		this.billingTown = billingTown;

		this.shippingGender = shippingGender;
		this.shippingFirstName = shippingFirstName;
		this.shippingLastName = shippingLastName;
		this.shippingStreet = shippingStreet;
		this.shippingHouseNumber = shippingHouseNumber;
		this.shippingAddressLine2 = shippingAddressLine2;
		this.shippingZipCode = shippingZipCode;
		this.shippingTown = shippingTown;

		this.dateOrdered = dateOrdered;
		this.order = order;
		this.status = order.getOrderStatus();

	}

	/**
	 * Gets the billing gender.
	 *
	 * @return the billing gender
	 */
	public String getBillingGender() {
		return billingGender;
	}

	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	public Order getOrder() {
		return this.order;
	}

	/**
	 * Gets the billing first name.
	 *
	 * @return the billing first name
	 */
	public String getBillingFirstName() {
		return billingFirstName;
	}

	/**
	 * Gets the billing last name.
	 *
	 * @return the billing last name
	 */
	public String getBillingLastName() {
		return billingLastName;
	}

	/**
	 * Gets the billing street.
	 *
	 * @return the billing street
	 */
	public String getBillingStreet() {
		return billingStreet;
	}

	/**
	 * Gets the billing house number.
	 *
	 * @return the billing house number
	 */
	public String getBillingHouseNumber() {
		return billingHouseNumber;
	}

	/**
	 * Gets the billing address line2.
	 *
	 * @return the billing address line2
	 */
	public String getBillingAddressLine2() {
		return billingAddressLine2;
	}

	/**
	 * Gets the billing zip code.
	 *
	 * @return the billing zip code
	 */
	public String getBillingZipCode() {
		return billingZipCode;
	}

	/**
	 * Gets the billing town.
	 *
	 * @return the billing town
	 */
	public String getBillingTown() {
		return billingTown;
	}

	/**
	 * Gets the shipping gender.
	 *
	 * @return the shipping gender
	 */
	public String getShippingGender() {
		return shippingGender;
	}

	/**
	 * Gets the shipping first name.
	 *
	 * @return the shipping first name
	 */
	public String getShippingFirstName() {
		return shippingFirstName;
	}

	/**
	 * Gets the shipping last name.
	 *
	 * @return the shipping last name
	 */
	public String getShippingLastName() {
		return shippingLastName;
	}

	/**
	 * Gets the shipping street.
	 *
	 * @return the shipping street
	 */
	public String getShippingStreet() {
		return shippingStreet;
	}

	/**
	 * Gets the shipping house number.
	 *
	 * @return the shipping house number
	 */
	public String getShippingHouseNumber() {
		return shippingHouseNumber;
	}

	/**
	 * Gets the shipping address line2.
	 *
	 * @return the shipping address line2
	 */
	public String getShippingAddressLine2() {
		return shippingAddressLine2;
	}

	/**
	 * Gets the shipping zip code.
	 *
	 * @return the shipping zip code
	 */
	public String getShippingZipCode() {
		return shippingZipCode;
	}

	/**
	 * Gets the shipping town.
	 *
	 * @return the shipping town
	 */
	public String getShippingTown() {
		return shippingTown;
	}

	/**
	 * Sets the date ordered.
	 *
	 * @param dateOrdered
	 *            the new date ordered
	 */
	public void setDateOrdered(LocalDateTime dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	/**
	 * Gets the order lines size.
	 *
	 * @return the order lines size
	 */
	public int getOrderLinesSize() {
		Collection<OrderLine> orderLines = IteratorUtils.toList(this.order.getOrderLines().iterator());
		return orderLines.size();
	}

	/**
	 * Gets the total product number.
	 *
	 * @return the total product number
	 */
	public int getTotalProductNumber() {
		int total = 0;
		Iterable<OrderLine> orderLines = this.order.getOrderLines();
		for (OrderLine orderLine : orderLines) {
			total += Integer.parseInt(orderLine.getQuantity().toString());
		}
		return total;
	}

	/**
	 * Gets the date ordered.
	 *
	 * @return the date ordered
	 */
	public LocalDateTime getDateOrdered() {
		return this.dateOrdered;
	}

	/**
	 * Gets the returned.
	 *
	 * @return the returned
	 */
	public boolean getReturned() {
		return this.returned;
	}

	/**
	 * Sets the billing gender.
	 *
	 * @param billingGender
	 *            the new billing gender
	 */
	public void setBillingGender(String billingGender) {
		this.billingGender = billingGender;
	}

	/**
	 * Sets the billing first name.
	 *
	 * @param billingFirstName
	 *            the new billing first name
	 */
	public void setBillingFirstName(String billingFirstName) {
		this.billingFirstName = billingFirstName;
	}

	/**
	 * Sets the billing last name.
	 *
	 * @param billingLastName
	 *            the new billing last name
	 */
	public void setBillingLastName(String billingLastName) {
		this.billingLastName = billingLastName;
	}

	/**
	 * Sets the billing street.
	 *
	 * @param billingStreet
	 *            the new billing street
	 */
	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}

	/**
	 * Sets the billing house number.
	 *
	 * @param billingHouseNumber
	 *            the new billing house number
	 */
	public void setBillingHouseNumber(String billingHouseNumber) {
		this.billingHouseNumber = billingHouseNumber;
	}

	/**
	 * Sets the billing address line2.
	 *
	 * @param billingAddressLine2
	 *            the new billing address line2
	 */
	public void setBillingAddressLine2(String billingAddressLine2) {
		this.billingAddressLine2 = billingAddressLine2;
	}

	/**
	 * Sets the billing zip code.
	 *
	 * @param billingZipCode
	 *            the new billing zip code
	 */
	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}

	/**
	 * Sets the billing town.
	 *
	 * @param billingTown
	 *            the new billing town
	 */
	public void setBillingTown(String billingTown) {
		this.billingTown = billingTown;
	}

	/**
	 * Sets the shipping gender.
	 *
	 * @param shippingGender
	 *            the new shipping gender
	 */
	public void setShippingGender(String shippingGender) {
		this.shippingGender = shippingGender;
	}

	/**
	 * Sets the shipping first name.
	 *
	 * @param shippingFirstName
	 *            the new shipping first name
	 */
	public void setShippingFirstName(String shippingFirstName) {
		this.shippingFirstName = shippingFirstName;
	}

	/**
	 * Sets the shipping last name.
	 *
	 * @param shippingLastName
	 *            the new shipping last name
	 */
	public void setShippingLastName(String shippingLastName) {
		this.shippingLastName = shippingLastName;
	}

	/**
	 * Sets the shipping street.
	 *
	 * @param shippingStreet
	 *            the new shipping street
	 */
	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}

	/**
	 * Sets the shipping house number.
	 *
	 * @param shippingHouseNumber
	 *            the new shipping house number
	 */
	public void setShippingHouseNumber(String shippingHouseNumber) {
		this.shippingHouseNumber = shippingHouseNumber;
	}

	/**
	 * Sets the shipping address line2.
	 *
	 * @param shippingAddressLine2
	 *            the new shipping address line2
	 */
	public void setShippingAddressLine2(String shippingAddressLine2) {
		this.shippingAddressLine2 = shippingAddressLine2;
	}

	/**
	 * Sets the shipping zip code.
	 *
	 * @param shippingZipCode
	 *            the new shipping zip code
	 */
	public void setShippingZipCode(String shippingZipCode) {
		this.shippingZipCode = shippingZipCode;
	}

	/**
	 * Sets the shipping town.
	 *
	 * @param shippingTown
	 *            the new shipping town
	 */
	public void setShippingTown(String shippingTown) {
		this.shippingTown = shippingTown;
	}

	/**
	 * Sets the user account.
	 *
	 * @param account
	 *            the new user account
	 */
	public void setUserAccount(UserAccount account) {
		this.setUserAccount(account);
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Sets the billing address.
	 *
	 * @param billingAddress
	 *            the new billing address
	 */
	public void setBillingAddress(List<String> billingAddress) {
		this.billingGender = billingAddress.get(0);
		this.billingFirstName = billingAddress.get(1);
		this.billingLastName = billingAddress.get(2);
		this.billingStreet = billingAddress.get(3);
		this.billingHouseNumber = billingAddress.get(4);
		this.billingAddressLine2 = billingAddress.get(5);
		this.billingZipCode = billingAddress.get(6);
		this.billingTown = billingAddress.get(7);
	}

	/**
	 * Sets the shipping address.
	 *
	 * @param shippingAddress
	 *            the new shipping address
	 */
	public void setShippingAddress(List<String> shippingAddress) {
		this.shippingGender = shippingAddress.get(0);
		this.shippingFirstName = shippingAddress.get(1);
		this.shippingLastName = shippingAddress.get(2);
		this.shippingStreet = shippingAddress.get(3);
		this.shippingHouseNumber = shippingAddress.get(4);
		this.shippingAddressLine2 = shippingAddress.get(5);
		this.shippingZipCode = shippingAddress.get(6);
		this.shippingTown = shippingAddress.get(7);
	}

	/**
	 * Sets the returned.
	 *
	 * @param returned
	 *            the new returned
	 */
	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public UserAccount getUser() {
		return user;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	/*
	 * public void setOrderManager(OrderManager<Order> orderManager){
	 * this.orderManager = orderManager; } public OrderManager<Order>
	 * getOrderManager(){ return this.orderManager; }
	 */
	public OrderStatus getStatus() {
		return this.status;
	}

	/**
	 * Sets the status.
	 *
	 * @param state
	 *            the new status
	 */
	public void setStatus(OrderStatus state) {
		this.status = state;
	}

	/**
	 * Gets the return reason.
	 *
	 * @return the return reason
	 */
	public String getReturnReason() {
		return returnReason;
	}

	/**
	 * Sets the return reason.
	 *
	 * @param returnReason
	 *            the new return reason
	 */
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
}