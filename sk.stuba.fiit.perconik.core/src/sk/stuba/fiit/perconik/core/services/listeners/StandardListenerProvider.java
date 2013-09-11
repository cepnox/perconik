package sk.stuba.fiit.perconik.core.services.listeners;

import java.util.Map;
import java.util.Set;
import sk.stuba.fiit.perconik.core.Listener;
import sk.stuba.fiit.perconik.core.ListenerInstantiationException;
import sk.stuba.fiit.perconik.utilities.MoreSets;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

final class StandardListenerProvider extends AbstractListenerProvider
{
	private final BiMap<String, Class<? extends Listener>> map;
	
	private final Map<Class<? extends Listener>, Listener> cache;
	
	private final ListenerProvider parent;
	
	StandardListenerProvider(final Builder builder)
	{
		this.map    = HashBiMap.create(builder.map);
		this.cache  = Maps.newConcurrentMap();
		this.parent = builder.parent.or(ListenerProviders.getSystemProvider());

		for (Class<? extends Listener> type: this.map.values())
		{
			cast(type);
		}
	}
	
	public static final class Builder implements ListenerProvider.Builder
	{
		BiMap<String, Class<? extends Listener>> map;
		
		Optional<ListenerProvider> parent;

		public Builder()
		{
			this.map    = HashBiMap.create();
			this.parent = Optional.absent();
		}
		
		public final Builder add(final Class<? extends Listener> implementation)
		{
			Preconditions.checkNotNull(implementation);
			
			this.map.put(implementation.getName(), implementation);
			
			return this;
		}

		public final Builder addAll(final Iterable<Class<? extends Listener>> implementations)
		{
			for (Class<? extends Listener> type: implementations)
			{
				this.add(type);
			}
			
			return this;
		}
		
		public final Builder parent(final ListenerProvider parent)
		{
			Preconditions.checkState(!this.parent.isPresent());

			this.parent = Optional.of(Preconditions.checkNotNull(parent));
			
			return this;
		}
		
		public final StandardListenerProvider build()
		{
			return new StandardListenerProvider(this);
		}
	}
	
	public static final Builder builder()
	{
		return new Builder();
	}

	@Override
	protected final ClassLoader loader()
	{
		return this.getClass().getClassLoader();
	}
	
	public final <L extends Listener> L forClass(final Class<L> type)
	{
		Listener listener = this.cache.get(type);
		
		if (listener != null)
		{
			return type.cast(listener);
		}
		
		try
		{
			if (!this.map.containsValue(type))
			{
				this.map.put(type.getName(), cast(type));
			}
			
			L instance = type.newInstance();
			
			this.cache.put(type, instance);
			
			return instance;
		}
		catch (Exception cause)
		{
			// TODO also throw ListenerNotFoundException on ClassNotFoundException
			// TODO also throw IllegalListenerClassException on malformed class
			// TODO do so with resources too + support instantaible resources
			return this.parentForClass(type, new ListenerInstantiationException(cause));
		}
	}

	public final Class<? extends Listener> loadClass(final String name) throws ClassNotFoundException
	{
		Class<? extends Listener> type = this.map.get(name);

		if (type != null)
		{
			return type;
		}

		try
		{
			type = this.load(name);
			
			this.map.put(name, type);
			
			return type;
		}
		catch (Exception cause)
		{
			return this.parentLoadClass(name, cause);
		}
	}
	
	public final Set<Class<? extends Listener>> classes()
	{
		return MoreSets.newHashSet(this.map.values(), this.parent.classes());
	}

	public final ListenerProvider parent()
	{
		return this.parent;
	}
}
