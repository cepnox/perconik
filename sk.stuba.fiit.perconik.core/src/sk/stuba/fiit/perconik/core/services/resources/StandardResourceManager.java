package sk.stuba.fiit.perconik.core.services.resources;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;

import sk.stuba.fiit.perconik.core.Listener;
import sk.stuba.fiit.perconik.core.Resource;
import sk.stuba.fiit.perconik.core.ResourceUnregistrationException;
import sk.stuba.fiit.perconik.utilities.MoreThrowables;
import sk.stuba.fiit.perconik.utilities.reflect.Reflections;

final class StandardResourceManager extends AbstractResourceManager
{
	private final SetMultimap<Class<? extends Listener>, Resource<?>> multimap;
	
	StandardResourceManager()
	{
		this.multimap = HashMultimap.create();
	}
	
	@Override
	protected final SetMultimap<Class<? extends Listener>, Resource<?>> multimap()
	{
		return this.multimap;
	}
	
	public final <L extends Listener> void unregisterAll(final Class<L> type)
	{
		List<Exception> failures = Lists.newLinkedList();
		
		for (Entry<Class<? extends L>, Resource<? extends L>> entry: this.assignablesAsSetMultimap(type).entries())
		{
			Resource<L> resource = Unsafe.cast(type, entry.getValue());
			
			try
			{
				this.unregister(entry.getKey(), resource);
			}
			catch (Exception failure)
			{
				failures.add(failure);
			}
		}
		
		if (!failures.isEmpty())
		{
			throw MoreThrowables.initializeSuppressor(new ResourceUnregistrationException(), failures);
		}
	}
	
	public final <L extends Listener> Set<Resource<? extends L>> assignables(final Class<L> type)
	{
		return Sets.newHashSet(this.assignablesAsSetMultimap(type).values());
	}

	private final <L extends Listener> SetMultimap<Class<? extends L>, Resource<? extends L>> assignablesAsSetMultimap(final Class<L> type)
	{
		SetMultimap<Class<? extends L>, Resource<? extends L>> result = HashMultimap.create();
		
		for (Entry<Class<? extends Listener>, Resource<?>> entry: this.multimap.entries())
		{
			if (type.isAssignableFrom(entry.getKey()))
			{
				result.put((Class<? extends L>) entry.getKey(), (Resource<? extends L>) entry.getValue());
			}
		}
		
		return result;
	}
	
	public final <L extends Listener> Set<Resource<? super L>> registrables(final Class<L> type)
	{
		Set<Resource<? super L>> result = Sets.newHashSet();
		
		for (Entry<Class<? extends Listener>, Resource<?>> entry: this.multimap.entries())
		{
			boolean matched = type == entry.getKey();
			
			if (!matched)
			{
				for (Class<?> supertype: Reflections.collectInterfaces(type))
				{
					if (supertype == entry.getKey())
					{
						matched = true;
						
						break;
					}
				}
			}
			
			if (matched)
			{
				result.add((Resource<? super L>) entry.getValue());
			}
		}
		
		return result;
	}

	public final SetMultimap<Class<? extends Listener>, Resource<?>> registrations()
	{
		return HashMultimap.create(this.multimap);
	}

	public final boolean registered(final Class<? extends Listener> type, final Resource<?> resource)
	{
		return this.multimap.containsEntry(type, resource);
	}
}
