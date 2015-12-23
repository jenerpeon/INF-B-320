package internetkaufhaus.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.ArgumentMatcher;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;

import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.repositories.ConcreteInventory;

import static org.mockito.Mockito.*;

import static org.salespointframework.core.Currencies.EURO;

import java.util.Optional;

import org.javamoney.moneta.Money;

@RunWith(MockitoJUnitRunner.class)
public class StockManagerUnitTest {
	@Mock 
	private ConcreteInventory<InventoryItem> inventory;
	private Optional<InventoryItem> item;
	private ConcreteProduct prod;
	private class ItemMatcher extends ArgumentMatcher<InventoryItem>
	{
		private Quantity q;
		public ItemMatcher(int q)
		{
			super();
			this.q = Quantity.of(q);
		}
		@Override
		public boolean matches(Object item) {
			if(((InventoryItem)item).getQuantity().equals(q))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	@Before
	public void init()
	{
		this.prod = mock(ConcreteProduct.class);
		this.item = Optional.of(new InventoryItem(this.prod, Quantity.of(10)));
		when(inventory.findByProductIdentifier(any())).thenReturn(this.item);
	}
	@Test
	public void orderArticleTest()
	{
		StockManager stock = new StockManager(inventory);
		stock.orderArticle(this.prod.getIdentifier(), Quantity.of(10));
		verify(inventory).save(argThat(new ItemMatcher(20)));
	}
	@Test
	public void removeArticleTest()
	{
		StockManager stock = new StockManager(inventory);
		stock.removeArticle(this.prod.getIdentifier(), Quantity.of(5));
		verify(inventory).save(argThat(new ItemMatcher(5)));
	}
}
