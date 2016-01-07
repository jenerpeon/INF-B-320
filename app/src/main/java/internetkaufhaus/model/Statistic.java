package internetkaufhaus.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javamoney.moneta.Money;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.repositories.ConcreteOrderRepository;

public class Statistic {
	/**
	 * The Statistic class is responsible for all financial transactions. It
	 * provides different filter functions to get financial numbers of different
	 * intervals.
	 */
	
	private final ConcreteOrderRepository concreteOrderRepo;
	
	private Interval interval;
	
	private String unit;
	
	private Map<LocalDate, Money> turnover = new HashMap<LocalDate, Money>();
	private Map<LocalDate, Long> orders = new HashMap<LocalDate, Long>();
	private Map<LocalDate, Long> returns = new HashMap<LocalDate, Long>();
	private Map<LocalDate, Money> expenses = new HashMap<LocalDate, Money>();
	private Map<LocalDate, Money> profit = new HashMap<LocalDate, Money>();

	@Autowired
	public Statistic(ConcreteOrderRepository concreteOrderRepo, Interval interval, String unit) {
		this.concreteOrderRepo = concreteOrderRepo;
		this.interval = interval;
		this.unit = unit;
		calculateStatistics();
	}
	
	/*
	private Money getTournover(Iterable<Order> orders) {
		Money turnover = Money.of(0, "EUR");
		for (Order o : orders) {
			if (o.getOrderStatus().equals(OrderStatus.COMPLETED))
				turnover.add(o.getTotalPrice());
		}
		return turnover;
	}*/

	/*
	 * private Money getTournover(Iterable<Order> orders) { Money turnover =
	 * Money.of(0, EURO); for (Order o : orders) { if
	 * (o.getOrderStatus().equals(OrderStatus.COMPLETED))
	 * turnover.add(o.getTotalPrice()); } return turnover; }
	 */


	/*
	 * The getTurnoverByInterval method is used to get all orders with the
	 * ordervalue and orderstatus each during a given time.
	 * 
	 * @param i
	 *            time interval
	 * @param unit
	 * 
	 * @return a list containing elements with orderstatus and ordervalue
	 */
	
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
	
	private void calculateStatistics() {
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate start = interval.getStart().toLocalDate();
		LocalDate end = interval.getEnd().toLocalDate();
		switch (unit) {
		case "day":
			for (LocalDate j = end; j.isAfter(start) || j.isEqual(start); j = j.minusDays(1)) {
				Iterable<ConcreteOrder> allOrders = concreteOrderRepo.findByInterval(Interval.from(LocalDateTime.of(j, midnight)).to(LocalDateTime.of(j, midnight.minusSeconds(1))));
				Money intervalMoney = Money.of(0, "EUR");
				Long intervalOrders = (long) 0;
				Long intervalReturns = (long) 0;
				Money intervalStockMoney = Money.of(0, "EUR");
				for (ConcreteOrder order : allOrders) {
					if (order.getStatus().equals(OrderStatus.COMPLETED) && !order.getReturned()) {
						intervalMoney = intervalMoney.add(order.getOrder().getTotalPrice());
						intervalOrders++;
					}
					
					if (order.getReturned()) {
						intervalReturns++;
					}
					
					if (order.getStatus().equals(OrderStatus.OPEN)) {
						intervalStockMoney = intervalStockMoney.add(order.getOrder().getTotalPrice());
					}
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
				Iterable<ConcreteOrder> allOrders = concreteOrderRepo.findByInterval(
						Interval.from(LocalDateTime.of(j.minusDays(6), midnight)).to(LocalDateTime.of(j, midnight)));
				Money intervalMoney = Money.of(0, "EUR");
				Long intervalOrders = (long) 0;
				Long intervalReturns = (long) 0;
				Money intervalStockMoney = Money.of(0, "EUR");
				for (ConcreteOrder order : allOrders) {
					if (order.getStatus().equals(OrderStatus.COMPLETED) && !order.getReturned()) {
						intervalMoney = intervalMoney.add(order.getOrder().getTotalPrice());
						intervalOrders++;
					}
					
					if (order.getReturned()) {
						intervalReturns++;
					}
					
					if (order.getStatus().equals(OrderStatus.OPEN)) {
						intervalStockMoney = intervalStockMoney.add(order.getOrder().getTotalPrice());
					}
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
				Iterable<ConcreteOrder> allOrders = concreteOrderRepo.findByInterval(
						Interval.from(LocalDateTime.of(j.minusMonths(1).plusDays(1), midnight))
								.to(LocalDateTime.of(j, midnight)));
				Money intervalMoney = Money.of(0, "EUR");
				Long intervalOrders = (long) 0;
				Long intervalReturns = (long) 0;
				Money intervalStockMoney = Money.of(0, "EUR");
				for (ConcreteOrder order : allOrders) {
					if (order.getStatus().equals(OrderStatus.COMPLETED) && !order.getReturned()) {
						intervalMoney = intervalMoney.add(order.getOrder().getTotalPrice());
						intervalOrders++;
					}
					
					if (order.getReturned()) {
						intervalReturns++;
					}
					
					if (order.getStatus().equals(OrderStatus.OPEN)) {
						intervalStockMoney = intervalStockMoney.add(order.getOrder().getTotalPrice());
					}
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
				Iterable<ConcreteOrder> allOrders = concreteOrderRepo.findByInterval(
						Interval.from(LocalDateTime.of(j.minusYears(1).plusDays(1), midnight))
								.to(LocalDateTime.of(j, midnight)));
				Money intervalMoney = Money.of(0, "EUR");
				Long intervalOrders = (long) 0;
				Long intervalReturns = (long) 0;
				Money intervalStockMoney = Money.of(0, "EUR");
				for (ConcreteOrder order : allOrders) {
					if (order.getStatus().equals(OrderStatus.COMPLETED) && !order.getReturned()) {
						intervalMoney = intervalMoney.add(order.getOrder().getTotalPrice());
						intervalOrders++;
					}
					
					if (order.getReturned()) {
						intervalReturns++;
					}
					
					if (order.getStatus().equals(OrderStatus.OPEN)) {
						intervalStockMoney = intervalStockMoney.add(order.getOrder().getTotalPrice());
					}
				}
				orders.put(j, intervalOrders);
				returns.put(j, intervalReturns);
				turnover.put(j, intervalMoney);
				expenses.put(j, intervalStockMoney);
				profit.put(j, intervalMoney.subtract(intervalStockMoney));
			}
			break;
		}
		/*
		List<Money> turnover = new ArrayList<Money>();
		long duration = i.getDuration().toDays();
		int steps = (int) (duration / q);
		LocalDateTime start = i.getStart();
		for (int j = 0; j < steps; j += q) {
			Set<Order> orders = new HashSet<Order>();
			LocalDateTime from = start.plusDays((long) j * q);
			LocalDateTime to = start.plusDays((long) j * (q + 1));
			orders = (Set<Order>) orderManager.findBy(Interval.from(from).to(to));
			turnover.add(getTournover(orders));
		}*/

		/*
		 List<Money> turnover = new ArrayList<Money>(); long duration =
		 i.getDuration().toDays(); int steps = (int) (duration / q);
		 LocalDateTime start = i.getStart(); for (int j = 0; j < steps; j +=
		 Set<Order> orders = new HashSet<Order>(); LocalDateTime from =
		 start.plusDays((long) j * q); LocalDateTime to =
		 start.plusDays((long) j * (q + 1)); orders = (Set<Order>)
		 orderManager.findBy(Interval.from(from).to(to));
		 turnover.add(getTournover(orders)); }*/
	}
	
	/**
	 * The getSales method finds out how much completed orders are existing
	 * inside a given Iterable
	 * 
	 * @param orders
	 *            Iterable with orders
	 *

	 * @return an Integer showing how much orders part of the given Iterable are
	 *         completed
	 */
	/*
	private Integer getSales(Iterable<Order> orders) {
		int sum = 0;
		for (Order o : orders) {
			if (o.getOrderStatus().equals(OrderStatus.COMPLETED))
				sum += 1;
		}
		return sum;
	}

	public List<Integer> getSalesByInterval(Interval i, int q) {
		List<Integer> sales = new ArrayList<Integer>();
		long duration = i.getDuration().toDays();
		int steps = (int) (duration / q);
		LocalDateTime start = i.getStart();
		for (int j = 0; j < steps; j += q) {
			Set<Order> orders = new HashSet<Order>();
			LocalDateTime from = start.plusDays((long) j * q);
			LocalDateTime to = start.plusDays((long) j * (q + 1));
			orders = (Set<Order>) orderManager.findBy(Interval.from(from).to(to));
			sales.add(getSales(orders));
		}
		return sales;
	}

	/* Purchcases and profit calculation are not yet implemented */
	/*
	public List<Integer> getPurchasesByInterval(Interval i, int q) {
		return null; // TODO: implement this
	}

	public List<Money> getProfitByInterval(Interval i, int q) {
		return null; // TODO: implement this
	}

	public List<Integer> getRetoursByInterval(Interval i, int q) {
		return null; // TODO: implement this
	}*/
}