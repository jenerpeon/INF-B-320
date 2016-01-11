package internetkaufhaus.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javamoney.moneta.Money;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.repositories.ConcreteOrderRepository;

@Service
public class StatisticService {

	private final DataService dataService;
	private final OrderManager orderManager;
	private final ConcreteOrderRepository concreteOrderRepository;

	@Autowired
	public StatisticService(DataService dataService) {

		this.dataService = dataService;
		this.orderManager = dataService.getOrderManager();
		this.concreteOrderRepository = dataService.getConcreteOrderRepository();

	}

	private List<Interval> intervalBuilder(Interval i, int days) {

		List<Interval> groups = new LinkedList<Interval>();
		LocalDateTime start = i.getStart();
		LocalDateTime end = i.getEnd();

		for (LocalDateTime j = start; j.isBefore(end); j = j.plusDays(days)) {
			groups.add(Interval.from(j).to(j.plusDays(days)));

		}
		return groups;
	}

	private Map<Integer, Money> turnoverHelper(Interval i, int metric) {
		Map<Integer, Money> turnover = new HashMap<Integer, Money>();

		int count = 0;
		for (Interval j : intervalBuilder(i, metric)) {
			count++;
			Money sum = Money.of(0, "EUR");
			for (Object order : orderManager.findBy(j)) {
				sum = sum.add(((Order) order).getTotalPrice());
			}
			turnover.put(count, sum);
		}

		return turnover;
	}

	public Map<Integer, Money> getTurnoverByInterval(Interval i, String unit) {

		Map<Integer, Money> turnover = new HashMap<Integer, Money>();

		switch (unit) {
		
		case "day":
			return turnoverHelper(i, 1);
		case "week":
			return turnoverHelper(i, 7);
		case "month":
			return turnoverHelper(i, 30);
		case "year":
			return turnoverHelper(i, 365);
		}

		return null;

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

	public List<Integer> getPurchasesByInterval(Interval i, int q) {
		return null; // TODO: implement this
	}

	public List<Money> getProfitByInterval(Interval i, int q) {
		return null; // TODO: implement this
	}

	public List<Integer> getRetoursByInterval(Interval i, int q) {
		return null; // TODO: implement this
	}

}
