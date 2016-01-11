package internetkaufhaus.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.javamoney.moneta.Money;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Iterators;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.services.DataService;

public class Statistic {
	/**
	 * The Statistic class is responsible for all financial transactions. It
	 * provides different filter functions to get financial numbers of different
	 * intervals.
	 */

	private final DataService dataService;

	private Interval interval;

	private String unit;

	private Map<LocalDate, Money> turnover = new HashMap<LocalDate, Money>();
	private Map<LocalDate, Long> orders = new HashMap<LocalDate, Long>();
	private Map<LocalDate, Long> returns = new HashMap<LocalDate, Long>();
	private Map<LocalDate, Money> expenses = new HashMap<LocalDate, Money>();
	private Map<LocalDate, Money> profit = new HashMap<LocalDate, Money>();
	
	@Autowired
	public Statistic(DataService dataService, Interval interval, String unit) {
		this.dataService = dataService;
		this.interval = interval;
		this.unit = unit;
		calculateStatistics();
	}

	public Interval getInterval() {
		return this.interval;
	}

	public String getUnit() {
		return this.unit;
	}

	public Map<LocalDate, Money> getTurnover() {
		return this.turnover;
	}

	public Map<LocalDate, Long> getOrders() {
		return this.orders;
	}

	public Map<LocalDate, Long> getReturns() {
		return this.returns;
	}

	public Map<LocalDate, Money> getExpenses() {
		return this.expenses;
	}

	public Map<LocalDate, Money> getProfit() {
		return this.profit;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	private boolean calculateStatistics() {
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate start = interval.getStart().toLocalDate();
		LocalDate end = interval.getEnd().toLocalDate();
		switch (unit) {
		default:
			return false;
		case "day":
			for (LocalDate j = end; j.isAfter(start) || j.isEqual(start); j = j.minusDays(1)) {
				
				Iterable<ConcreteOrder> completedOrders = dataService.getConcreteOrderRepository().findByIntervalAndStatusAndNotReturned(
						LocalDateTime.of(j, midnight), LocalDateTime.of(j, midnight.minusSeconds(1)), OrderStatus.COMPLETED);
				Iterable<ConcreteOrder> openOrders = dataService.getConcreteOrderRepository().findByIntervalAndStatusAndNotReturned(
						LocalDateTime.of(j, midnight), LocalDateTime.of(j, midnight.minusSeconds(1)), OrderStatus.OPEN);
				Iterable<ConcreteOrder> returnedOrders = dataService.getConcreteOrderRepository().findByIntervalAndStatusAndReturned(
						LocalDateTime.of(j, midnight), LocalDateTime.of(j, midnight.minusSeconds(1)), OrderStatus.COMPLETED);
				
				Money intervalMoney = Money.of(0, "EUR");
				Long intervalOrders = (long) Iterators.size(completedOrders.iterator());
				Long intervalReturns = (long) Iterators.size(returnedOrders.iterator());
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
			return true;
		case "week":
			for (LocalDate j = end; j.isAfter(start) || j.isEqual(start); j = j.minusWeeks(1)) {
				
				Iterable<ConcreteOrder> completedOrders = dataService.getConcreteOrderRepository().findByIntervalAndStatusAndNotReturned(
						LocalDateTime.of(j, midnight), LocalDateTime.of(j, midnight.minusSeconds(1)), OrderStatus.COMPLETED);
				Iterable<ConcreteOrder> openOrders = dataService.getConcreteOrderRepository().findByIntervalAndStatusAndNotReturned(
						LocalDateTime.of(j, midnight), LocalDateTime.of(j, midnight.minusSeconds(1)), OrderStatus.OPEN);
				Iterable<ConcreteOrder> returnedOrders = dataService.getConcreteOrderRepository().findByIntervalAndStatusAndReturned(
						LocalDateTime.of(j, midnight), LocalDateTime.of(j, midnight.minusSeconds(1)), OrderStatus.COMPLETED);
				
				Money intervalMoney = Money.of(0, "EUR");
				Long intervalOrders = (long) Iterators.size(completedOrders.iterator());
				Long intervalReturns = (long) Iterators.size(returnedOrders.iterator());
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
			return true;
		case "month":
			for (LocalDate j = end; j.isAfter(start) || j.isEqual(start); j = j.minusMonths(1)) {
				
				Iterable<ConcreteOrder> completedOrders = dataService.getConcreteOrderRepository().findByIntervalAndStatusAndNotReturned(
						LocalDateTime.of(j, midnight), LocalDateTime.of(j, midnight.minusSeconds(1)), OrderStatus.COMPLETED);
				Iterable<ConcreteOrder> openOrders = dataService.getConcreteOrderRepository().findByIntervalAndStatusAndNotReturned(
						LocalDateTime.of(j, midnight), LocalDateTime.of(j, midnight.minusSeconds(1)), OrderStatus.OPEN);
				Iterable<ConcreteOrder> returnedOrders = dataService.getConcreteOrderRepository().findByIntervalAndStatusAndReturned(
						LocalDateTime.of(j, midnight), LocalDateTime.of(j, midnight.minusSeconds(1)), OrderStatus.COMPLETED);
				
				Money intervalMoney = Money.of(0, "EUR");
				Long intervalOrders = (long) Iterators.size(completedOrders.iterator());
				Long intervalReturns = (long) Iterators.size(returnedOrders.iterator());
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
			return true;
		case "year":
			for (LocalDate j = end; j.isAfter(start) || j.isEqual(start); j = j.minusYears(1)) {
				
				Iterable<ConcreteOrder> completedOrders = dataService.getConcreteOrderRepository().findByIntervalAndStatusAndNotReturned(
						LocalDateTime.of(j, midnight), LocalDateTime.of(j, midnight.minusSeconds(1)), OrderStatus.COMPLETED);
				Iterable<ConcreteOrder> openOrders = dataService.getConcreteOrderRepository().findByIntervalAndStatusAndNotReturned(
						LocalDateTime.of(j, midnight), LocalDateTime.of(j, midnight.minusSeconds(1)), OrderStatus.OPEN);
				Iterable<ConcreteOrder> returnedOrders = dataService.getConcreteOrderRepository().findByIntervalAndStatusAndReturned(
						LocalDateTime.of(j, midnight), LocalDateTime.of(j, midnight.minusSeconds(1)), OrderStatus.COMPLETED);
				
				Money intervalMoney = Money.of(0, "EUR");
				Long intervalOrders = (long) Iterators.size(completedOrders.iterator());
				Long intervalReturns = (long) Iterators.size(returnedOrders.iterator());
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
			return true;
		}
	}
}