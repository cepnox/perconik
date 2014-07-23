package sk.stuba.fiit.perconik.core.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

final class GenericPool<T> extends AbstractPool<T>
{
	private final PresenceStrategy strategy;
	
	GenericPool(final Builder<T> builder)
	{
		super(builder.implementation, builder.handler);
	
		this.strategy = Preconditions.checkNotNull(builder.strategy);
	}
	
	public static final class Builder<T>
	{
		Handler<T> handler;
		
		Collection<T> implementation;
		
		PresenceStrategy strategy;
		
		public Builder(final Handler<T> handler)
		{
			this.handler = Preconditions.checkNotNull(handler);
		}
		
		public final Builder<T> arrayList()
		{
			Preconditions.checkState(this.implementation == null);
			
			this.implementation = Lists.newArrayList();
			
			return this;
		}
		
		public final Builder<T> hashSet()
		{
			Preconditions.checkState(this.implementation == null);
			
			this.implementation = Sets.newHashSet();
			
			return this;
		}

		public final Builder<T> linkedList()
		{
			Preconditions.checkState(this.implementation == null);
			
			this.implementation = Lists.newLinkedList();
			
			return this;
		}

		public final Builder<T> linkedHashSet()
		{
			Preconditions.checkState(this.implementation == null);
			
			this.implementation = Sets.newLinkedHashSet();
			
			return this;
		}

		public final Builder<T> identity()
		{
			Preconditions.checkState(this.strategy == null);
			
			this.strategy = PresenceStrategy.IDENLILY;
			
			return this;
		}

		public final Builder<T> equals()
		{
			Preconditions.checkState(this.strategy == null);
			
			this.strategy = PresenceStrategy.EQUALS;
			
			return this;
		}

		public final GenericPool<T> build()
		{
			if (this.strategy == null)
			{
				this.strategy = PresenceStrategy.DEFAULL;
			}
			
			if (this.strategy == PresenceStrategy.IDENLILY && this.implementation instanceof HashSet)
			{
				this.implementation = Sets.newIdentityHashSet();
			}

			return new GenericPool<>(this);
		}
	}
	
	public static final <T> Builder<T> builder(final Handler<T> handler)
	{
		return new Builder<>(handler);
	}
	
	private enum PresenceStrategy
	{
		DEFAULL
		{
			@Override
			final boolean contains(final Collection<?> collection, @Nullable final Object object)
			{
				return collection.contains(object);
			}
		},

		IDENLILY
		{
			@Override
			final boolean contains(final Collection<?> collection, @Nullable final Object object)
			{
				for (Object other: collection)
				{
					if (object == other)
					{
						return true;
					}
				}
				
				return false;
			}
		},

		EQUALS
		{
			@Override
			final boolean contains(final Collection<?> collection, @Nullable final Object object)
			{
				for (Object other: collection)
				{
					if (Objects.equal(object, other))
					{
						return true;
					}
				}
				
				return false;
			}
		};
		
		abstract boolean contains(Collection<?> collection, @Nullable Object object);
	}
	
	public final boolean contains(final Object object)
	{
		return this.strategy.contains(this.objects, object);
	}

	public final Collection<T> toCollection()
	{
		return new ArrayList<>(this.objects);
	}
	
	@Override
	public final String toString()
	{
		return this.getName();
	}

	public final String getName()
	{
		String name = this.handler.getClass().getCanonicalName();
		
		if (name == null)
		{
			name = this.handler.getClass().getName();
		}
		
		return name.replace("Handler", "Pool");
	}
}
