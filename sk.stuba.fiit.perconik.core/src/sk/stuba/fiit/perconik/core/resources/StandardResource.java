package sk.stuba.fiit.perconik.core.resources;

import java.util.Collection;

import com.google.common.collect.Lists;

import sk.stuba.fiit.perconik.core.Listener;
import sk.stuba.fiit.perconik.core.Resource;
import sk.stuba.fiit.perconik.core.annotations.Unsupported;

class StandardResource<L extends Listener> extends AbstractResource<L>
{
	final Pool<L> pool;
	
	StandardResource(final Pool<L> pool)
	{
		super(pool.toString().replace("Pool", "Resource"));
		
		this.pool = pool;
	}
	
	static final <L extends Listener> Resource<L> newInstance(final Pool<L> pool, final boolean unsupported)
	{
		return unsupported ? new UnsupportedStandardResource<>(pool) : new StandardResource<>(pool);
	}
	
	public final void register(final L listener)
	{
		this.pool.add(listener);
	}
	
	public final void unregister(final L listener)
	{
		this.pool.remove(listener);
	}
	
	public final <U extends Listener> Collection<U> registered(final Class<U> type)
	{
		Collection<L> listeners = this.pool.toCollection();
		Collection<U> filtered  = Lists.newArrayListWithCapacity(listeners.size());
		
		for (L listener: listeners)
		{
			if (type.isInstance(listener))
			{
				filtered.add(type.cast(listener));
			}
		}
		
		return filtered;
	}
	
	public final boolean registered(final Listener listener)
	{
		return this.pool.contains(listener);
	}
}

@Unsupported
class UnsupportedStandardResource<L extends Listener> extends StandardResource<L>
{
	UnsupportedStandardResource(Pool<L> pool)
	{
		super(pool);
	}
}
