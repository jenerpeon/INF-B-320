package internetkaufhaus.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import internetkaufhaus.AbstractIntegrationTests;
import internetkaufhaus.Application;
import internetkaufhaus.services.DataService;

// TODO: Auto-generated Javadoc
/**
 * The Class StatisticTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

@Transactional
public class StatisticTest extends AbstractIntegrationTests {

	@Autowired
	private DataService data;

	private Statistic statistic;

	/**
	 * Inits the test.
	 */
	@Before
	public void initTest() {
		Interval interval = Interval.from(LocalDateTime.now().minusDays(7)).to(LocalDateTime.now());
		statistic = new Statistic(data, interval, "week");
		statistic.setInterval(interval);
		statistic.setUnit("week");
	}

	/**
	 * Test get interval.
	 */
	@Test
	public void testGetInterval() {
		Interval interval = Interval.from(LocalDateTime.now().minusDays(7)).to(LocalDateTime.now());
		statistic.setInterval(interval);
		assertEquals("testGetInterval", statistic.getInterval(), interval);
	}
	
	@Test
	public void testGetUnit() {
		assertEquals("testGetUnit", statistic.getUnit(), "week");
	}

	@Test
	public void testGetTurnover() {
		System.out.println(statistic.getTurnover().size());
		System.out.println(statistic.getExpenses().size());
		System.out.println(statistic.getProfit().size());
		System.out.println(statistic.getReturns().size());
		System.out.println(statistic.getOrders().size());
	}
}
