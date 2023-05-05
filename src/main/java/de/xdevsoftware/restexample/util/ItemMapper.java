
package de.xdevsoftware.restexample.util;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;


public class ItemMapper
{
	public static <T, R> R fromItem(final T sourceItem, final Class<R> target)
	{
		final ModelMapper mapper = new ModelMapper();
		return mapper.map(sourceItem, target);
	}
	
	public static <T, R> List<R> fromList(final List<T> sourceItems, final Class<R> target)
	{
		final List<R> items = new ArrayList<>();
		
		final ModelMapper mapper = new ModelMapper();
		sourceItems.forEach(item -> items.add(mapper.map(item, target)));
		
		return items;
	}
}
