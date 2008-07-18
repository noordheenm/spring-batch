package org.springframework.batch.item.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.sample.Foo;
import org.springframework.batch.item.sample.FooService;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * Tests for {@link ItemReaderAdapter}.
 * 
 * @author Robert Kasanicky
 */
public class ItemReaderAdapterTests extends AbstractDependencyInjectionSpringContextTests {

	private ItemReaderAdapter<Foo> provider;

	private FooService fooService;

	protected String getConfigPath() {
		return "delegating-item-provider.xml";
	}

	/**
	 * Regular usage scenario - items are retrieved from the service injected invoker points to.
	 */
	public void testNext() throws Exception {
		List<Object> returnedItems = new ArrayList<Object>();
		Object item;
		while ((item = provider.read()) != null) {
			returnedItems.add(item);
		}

		List<Foo> input = fooService.getGeneratedFoos();
		assertEquals(input.size(), returnedItems.size());
		assertFalse(returnedItems.isEmpty());

		for (int i = 0; i < input.size(); i++) {
			assertSame(input.get(i), returnedItems.get(i));
		}
	}

	public void setProvider(ItemReaderAdapter<Foo> provider) {
		this.provider = provider;
	}

	public void setFooService(FooService fooService) {
		this.fooService = fooService;
	}

}
