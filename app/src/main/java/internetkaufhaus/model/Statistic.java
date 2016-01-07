/*package internetkaufhaus.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;f
import java.util.Set;

import org.javamoney.moneta.Money;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;

public class Statistic {
	/**
	 * The Statistic class is responsible for all financial transactions. It
	 * provides different filter functions to get financial numbers of different
	 * intervals.
	 */
	/*
	private final OrderManager<Order> orderManager;
	// private final ConcreteOrderRepository concreteOrderRepo;

	@Autowired
	public Statistic(OrderManager<Order> orderManager) {
		this.orderManager = orderManager;
		// this.concreteOrderRepo = concreteOrderRepo;
	}

	private Money getTournover(Iterable<Order> orders) {
		Money turnover = Money.of(0, EURO);
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
	/*
	public Map<LocalDate, Money> getTurnoverByInterval(Interval i, String unit) {
		Map<LocalDate, Money> turnover = new HashMap<LocalDate, Money>();
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate start = i.getStart().toLocalDate();
		LocalDate end = i.getEnd().toLocalDate();
		switch (unit) {
		case "day":
			for (LocalDate j = end; j.isAfter(start) || j.isEqual(start); j.minusDays(1)) {
				Iterable<Order> allOrders = orderManager.findBy(
						Interval.from(LocalDateTime.of(j, midnight)).to(LocalDateTime.of(j, midnight.minusSeconds(1))));
				Money intervalMoney = Money.of(0, "EUR");
				for (Order order : allOrders) {
					if (order.getOrderStatus() == OrderStatus.COMPLETED) {
						intervalMoney.add(order.getTotalPrice());
					}
				}
				turnover.put(j, intervalMoney);
			}
			break;
		case "week":
			for (LocalDate j = end; j.isAfter(start) || j.isEqual(start); j.minusWeeks(1)) {
				Iterable<Order> allOrders = orderManager.findBy(
						Interval.from(LocalDateTime.of(j.minusDays(6), midnight)).to(LocalDateTime.of(j, midnight)));
				Money intervalMoney = Money.of(0, "EUR");
				for (Order order : allOrders) {
					if (order.getOrderStatus() == OrderStatus.COMPLETED) {
						intervalMoney.add(order.getTotalPrice());
					}
				}
				turnover.put(j, intervalMoney);
			}
			break;
		case "month":
			for (LocalDate j = end; j.isAfter(start) || j.isEqual(start); j.minusMonths(1)) {
				Iterable<Order> allOrders = orderManager
						.findBy(Interval.from(LocalDateTime.of(j.minusMonths(1).plusDays(1), midnight))
								.to(LocalDateTime.of(j, midnight)));
				Money intervalMoney = Money.of(0, "EUR");
				for (Order order : allOrders) {
					if (order.getOrderStatus() == OrderStatus.COMPLETED) {
						intervalMoney.add(order.getTotalPrice());
					}
				}
				turnover.put(j, intervalMoney);
			}
			break;
		case "year":
			for (LocalDate j = end; j.isAfter(start) || j.isEqual(start); j.minusYears(1)) {
				Iterable<Order> allOrders = orderManager
						.findBy(Interval.from(LocalDateTime.of(j.minusYears(1).plusDays(1), midnight))
								.to(LocalDateTime.of(j, midnight)));
				Money intervalMoney = Money.of(0, "EUR");
				for (Order order : allOrders) {
					if (order.getOrderStatus() == OrderStatus.COMPLETED) {
						intervalMoney.add(order.getTotalPrice());
					}
				}
				turnover.put(j, intervalMoney);
			}
			break;
		}
		
		/*List<Money> turnover = new ArrayList<Money>();
		long duration = i.getDuration().toDays();
		int steps = (int) (duration / q);
		LocalDateTime start = i.getStart();
		for (int j = 0; j < steps; j += q) {
			Set<Order> orders = new HashSet<Order>();
			LocalDateTime from = start.plusDays((long) j * q);
			LocalDateTime to = start.plusDays((long) j * (q + 1));
			orders = (Set<Order>) orderManager.findBy(Interval.from(from).to(to));
			turnover.add(getTournover(orders));
		}

		/*
		 * List<Money> turnover = new ArrayList<Money>(); long duration =
		 * i.getDuration().toDays(); int steps = (int) (duration / q);
		 * LocalDateTime start = i.getStart(); for (int j = 0; j < steps; j +=
		 * q) { Set<Order> orders = new HashSet<Order>(); LocalDateTime from =
		 * start.plusDays((long) j * q); LocalDateTime to =
		 * start.plusDays((long) j * (q + 1)); orders = (Set<Order>)
		 * orderManager.findBy(Interval.from(from).to(to));
		 * turnover.add(getTournover(orders)); }
		 
		return turnover;
	}*/
	/*
	/**
	 * The getSales method finds out how much completed orders are existing
	 * inside a given Iterable
	 * 
	 * @param orders
	 *            Iterable with orders
	 *
<<<<<<< HEAD
	 * @return an Integer showing how much orders part of the given Iterable are completed
	 *//*
=======
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
	}
}*/