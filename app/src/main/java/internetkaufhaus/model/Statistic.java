package internetkaufhaus.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.salespointframework.core.Currencies.EURO;

import org.javamoney.moneta.Money;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;

import internetkaufhaus.entities.ConcreteOrder;

public class Statistic {

	private final OrderManager<ConcreteOrder> orderManager;

	@Autowired
	public Statistic(OrderManager<ConcreteOrder> orderManager) {
		this.orderManager = orderManager;
	}

	public Money getTournover(Iterable<ConcreteOrder> orders) {
		Money tournover = Money.of(0, EURO);
		for (Order o : orders) {
			tournover.add(o.getTotalPrice());
		}
		return tournover;
	}

	public List<Money> getTurnoverByInterval(Interval i, int q) {
		List<Money> turnover = new ArrayList<Money>();
		long duration = i.getDuration().toDays();
		int steps = (int) (duration / q);
		LocalDateTime start = i.getStart();
		for (int j = 0; j < steps; j += q) {
			Set<ConcreteOrder> orders = new HashSet<ConcreteOrder>();
			LocalDateTime from = start.plusDays(j * q);
			LocalDateTime to = start.plusDays(j * (q + 1));
			orders = (Set<ConcreteOrder>) orderManager.findBy(Interval.from(from).to(to));
			turnover.add(getTournover(orders));
		}
		return turnover;
	}

	public List<Integer> getSalesByInterval(Interval i, int q) {
		List<Integer> sales = new ArrayList<Integer>();
		long duration = i.getDuration().toDays();
		int steps = (int) (duration / q);
		LocalDateTime start = i.getStart();
		for (int j = 0; j < steps; j += q) {
			Set<ConcreteOrder> orders = new HashSet<ConcreteOrder>();
			LocalDateTime from = start.plusDays(j * q);
			LocalDateTime to = start.plusDays(j * (q + 1));
			orders = (Set<ConcreteOrder>) orderManager.findBy(Interval.from(from).to(to));
			sales.add(orders.size());
		}
		return sales;
	}

	/* Purchcases and profit calculation are not yet implemented */

	public List<Integer> getPurchasesByInterval(Interval i, int q) {
		return null;
	}

	public List<Money> getProfitByInterval(Interval i, int q) {
		return null;
	}

	public List<Integer> getRetoursByInterval(Interval i, int q) {
		return null;
	}
}
