package internetkaufhaus.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import org.javamoney.moneta.Money;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.services.DataService;

// TODO: Auto-generated Javadoc
/**
 * The Class Statistic.
 */
public class Statistic {
	/**
	 * The Statistic class is responsible for all financial transactions. It
	 * provides different filter functions to get financial numbers of different
	 * intervals.
	 */

	private final DataService dataService;

	/** The interval. */
	private Interval interval;

	/** The unit. */
	private String unit;

	/** The turnover. */
	private Map<LocalDate, Money> turnover = new HashMap<LocalDate, Money>();
	
	/** The orders. */
	private Map<LocalDate, Long> orders = new HashMap<LocalDate, Long>();
	
	/** The returns. */
	private Map<LocalDate, Long> returns = new HashMap<LocalDate, Long>();
	
	/** The expenses. */
	private Map<LocalDate, Money> expenses = new HashMap<LocalDate, Money>();
	
	/** The profit. */
	private Map<LocalDate, Money> profit = new HashMap<LocalDate, Money>();

	/**
	 * Instantiates a new statistic.
	 *
	 * @param dataService the data service
	 * @param interval the interval
	 * @param unit the unit
	 */
	@Autowired
	public Statistic(DataService dataService, Interval interval, String unit) {
		this.dataService = dataService;
		this.interval = interval;
		this.unit = unit;
		calculateStatistics();
	}

	/**
	 * Gets the interval.
	 *
	 * @return the interval
	 */
	public Interval getInterval() {
		return this.interval;
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public String getUnit() {
		return this.unit;
	}

	/**
	 * Gets the turnover.
	 *
	 * @return the turnover
	 */
	public Map<LocalDate, Money> getTurnover() {
		return this.turnover;
	}

	/**
	 * Gets the orders.
	 *
	 * @return the orders
	 */
	public Map<LocalDate, Long> getOrders() {
		return this.orders;
	}

	/**
	 * Gets the returns.
	 *
	 * @return the returns
	 */
	public Map<LocalDate, Long> getReturns() {
		return this.returns;
	}

	/**
	 * Gets the expenses.
	 *
	 * @return the expenses
	 */
	public Map<LocalDate, Money> getExpenses() {
		return this.expenses;
	}

	/**
	 * Gets the profit.
	 *
	 * @return the profit
	 */
	public Map<LocalDate, Money> getProfit() {
		return this.profit;
	}

	/**
	 * Sets the interval.
	 *
	 * @param interval the new interval
	 */
	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit the new unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Calculate statistics.
	 *
	 * @return true, if successful
	 */
	private boolean calculateStatistics() {
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate start = interval.getStart().toLocalDate();
		LocalDate end = interval.getEnd().toLocalDate();
		switch (unit) {
		case "day":
			for (LocalDate j = end; j.isAfter(start) || j.isEqual(start); j = j.minusDays(1)) {

				Iterable<ConcreteOrder> completedOrders = dataService.getConcreteOrderRepository()
						.findByIntervalAndStatusAndNotReturned(
								LocalDateTime.of(j, midnight).minusDays(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.COMPLETED);
				Iterable<ConcreteOrder> openOrders = dataService.getConcreteOrderRepository()
						.findByIntervalAndStatusAndNotReturned(
								LocalDateTime.of(j, midnight).minusDays(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.OPEN);

				Money intervalMoney = Money.of(0, "EUR");
				Long intervalOrders = dataService.getConcreteOrderRepository()
						.numberOfFindByIntervalAndStatusAndNotReturned(
								LocalDateTime.of(j, midnight).minusDays(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.COMPLETED);
				Long intervalReturns = dataService.getConcreteOrderRepository()
						.numberOfFindByIntervalAndStatusAndReturned(
								LocalDateTime.of(j, midnight).minusDays(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.COMPLETED);
				Money intervalStockMoney = Money.of(0, "EUR");
				for (ConcreteOrder order : completedOrders) {
					intervalMoney = intervalMoney.add(order.getTotalPrice());
				}
				for (ConcreteOrder order : openOrders) {
					intervalStockMoney = intervalStockMoney.add(order.getTotalPrice());
				}

				orders.put(j, intervalOrders);
				returns.put(j, intervalReturns);
				turnover.put(j, intervalMoney);
				expenses.put(j, intervalStockMoney);
				profit.put(j, intervalMoney.subtract(intervalStockMoney));
			}
			break;
		case "week":
			for (LocalDate j = end; j.isAfter(start) || j.isEqual(start); j = j.minusWeeks(1)) {

				Iterable<ConcreteOrder> completedOrders = dataService.getConcreteOrderRepository()
						.findByIntervalAndStatusAndNotReturned(
								LocalDateTime.of(j, midnight).minusWeeks(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.COMPLETED);
				Iterable<ConcreteOrder> openOrders = dataService.getConcreteOrderRepository()
						.findByIntervalAndStatusAndNotReturned(
								LocalDateTime.of(j, midnight).minusWeeks(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.OPEN);

				Money intervalMoney = Money.of(0, "EUR");
				Long intervalOrders = dataService.getConcreteOrderRepository()
						.numberOfFindByIntervalAndStatusAndNotReturned(
								LocalDateTime.of(j, midnight).minusWeeks(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.COMPLETED);
				Long intervalReturns = dataService.getConcreteOrderRepository()
						.numberOfFindByIntervalAndStatusAndReturned(
								LocalDateTime.of(j, midnight).minusWeeks(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.COMPLETED);
				Money intervalStockMoney = Money.of(0, "EUR");
				for (ConcreteOrder order : completedOrders) {
					intervalMoney = intervalMoney.add(order.getTotalPrice());
				}
				for (ConcreteOrder order : openOrders) {
					intervalStockMoney = intervalStockMoney.add(order.getTotalPrice());
				}

				orders.put(j, intervalOrders);
				returns.put(j, intervalReturns);
				turnover.put(j, intervalMoney);
				expenses.put(j, intervalStockMoney);
				profit.put(j, intervalMoney.subtract(intervalStockMoney));
			}
			break;
		case "month":
			for (LocalDate j = end; j.isAfter(start) || j.isEqual(start); j = j.minusMonths(1)) {

				Iterable<ConcreteOrder> completedOrders = dataService.getConcreteOrderRepository()
						.findByIntervalAndStatusAndNotReturned(
								LocalDateTime.of(j, midnight).minusMonths(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.COMPLETED);
				Iterable<ConcreteOrder> openOrders = dataService.getConcreteOrderRepository()
						.findByIntervalAndStatusAndNotReturned(
								LocalDateTime.of(j, midnight).minusMonths(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.OPEN);

				Money intervalMoney = Money.of(0, "EUR");
				Long intervalOrders = dataService.getConcreteOrderRepository()
						.numberOfFindByIntervalAndStatusAndNotReturned(
								LocalDateTime.of(j, midnight).minusMonths(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.COMPLETED);
				Long intervalReturns = dataService.getConcreteOrderRepository()
						.numberOfFindByIntervalAndStatusAndReturned(
								LocalDateTime.of(j, midnight).minusMonths(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.COMPLETED);
				Money intervalStockMoney = Money.of(0, "EUR");
				for (ConcreteOrder order : completedOrders) {
					intervalMoney = intervalMoney.add(order.getTotalPrice());
				}
				for (ConcreteOrder order : openOrders) {
					intervalStockMoney = intervalStockMoney.add(order.getTotalPrice());
				}

				orders.put(j, intervalOrders);
				returns.put(j, intervalReturns);
				turnover.put(j, intervalMoney);
				expenses.put(j, intervalStockMoney);
				profit.put(j, intervalMoney.subtract(intervalStockMoney));
			}
			break;
		case "year":
			for (LocalDate j = end; j.isAfter(start) || j.isEqual(start); j = j.minusYears(1)) {

				Iterable<ConcreteOrder> completedOrders = dataService.getConcreteOrderRepository()
						.findByIntervalAndStatusAndNotReturned(
								LocalDateTime.of(j, midnight).minusYears(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.COMPLETED);
				Iterable<ConcreteOrder> openOrders = dataService.getConcreteOrderRepository()
						.findByIntervalAndStatusAndNotReturned(
								LocalDateTime.of(j, midnight).minusYears(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.OPEN);

				Money intervalMoney = Money.of(0, "EUR");
				Long intervalOrders = dataService.getConcreteOrderRepository()
						.numberOfFindByIntervalAndStatusAndNotReturned(
								LocalDateTime.of(j, midnight).minusYears(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.COMPLETED);
				Long intervalReturns = dataService.getConcreteOrderRepository()
						.numberOfFindByIntervalAndStatusAndReturned(
								LocalDateTime.of(j, midnight).minusYears(1).toEpochSecond(ZoneOffset.ofHours(1)),
								LocalDateTime.of(j, midnight.minusSeconds(1)).toEpochSecond(ZoneOffset.ofHours(1)),
								OrderStatus.COMPLETED);
				Money intervalStockMoney = Money.of(0, "EUR");
				for (ConcreteOrder order : completedOrders) {
					intervalMoney = intervalMoney.add(order.getTotalPrice());
				}
				for (ConcreteOrder order : openOrders) {
					intervalStockMoney = intervalStockMoney.add(order.getTotalPrice());
				}

				orders.put(j, intervalOrders);
				returns.put(j, intervalReturns);
				turnover.put(j, intervalMoney);
				expenses.put(j, intervalStockMoney);
				profit.put(j, intervalMoney.subtract(intervalStockMoney));
			}
			break;
		default:
			return false;
		}
		return true;
	}
}