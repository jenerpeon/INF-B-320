package internetkaufhaus.model;

import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.javamoney.moneta.Money;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;

import internetkaufhaus.entities.ConcreteOrder;

public class Statistic {

	private final OrderManager<ConcreteOrder> orderManager;

	@Autowired
	public Statistic(OrderManager<ConcreteOrder> orderManager) {
		this.orderManager = orderManager;
	}

	private Money getTournover(Iterable<ConcreteOrder> orders) {
		Money turnover = Money.of(0, EURO);
		for (Order o : orders) {
			if (o.getOrderStatus().equals(OrderStatus.COMPLETED))
				turnover.add(o.getTotalPrice());
		}
		return turnover;
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

	private Integer getSales(Iterable<ConcreteOrder> orders) {
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
			Set<ConcreteOrder> orders = new HashSet<ConcreteOrder>();
			LocalDateTime from = start.plusDays(j * q);
			LocalDateTime to = start.plusDays(j * (q + 1));
			orders = (Set<ConcreteOrder>) orderManager.findBy(Interval.from(from).to(to));
			sales.add(getSales(orders));
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
