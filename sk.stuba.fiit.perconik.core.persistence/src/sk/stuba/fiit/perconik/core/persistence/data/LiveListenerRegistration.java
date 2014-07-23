package sk.stuba.fiit.perconik.core.persistence.data;

import java.util.Set;

import com.google.common.collect.Sets;

import sk.stuba.fiit.perconik.core.Listener;
import sk.stuba.fiit.perconik.core.Listeners;

/**
 * Standard listener registration with lively updated registration status.
 * 
 * @author Pavol Zbell
 * @since 1.0
 */
public class LiveListenerRegistration extends AbstractListenerRegistration
{
	private final Class<? extends Listener> type;
	
	private final Listener listener;

	private LiveListenerRegistration(final Class<? extends Listener> type, final Listener listener)
	{
		this.type     = type;
		this.listener = listener;
	}
	
	static final LiveListenerRegistration construct(final Class<? extends Listener> type, final Listener listener)
	{
		Utilities.checkListenerClass(type);
		Utilities.checkListenerImplementation(type, listener);
		
		return copy(type, listener);
	}
	
	static final LiveListenerRegistration copy(final Class<? extends Listener> type, final Listener listener)
	{
		return new LiveListenerRegistration(type, listener);
	}

	public static final LiveListenerRegistration of(final Listener listener)
	{
		return construct(listener.getClass(), listener);
	}
	
	public static final Set<LiveListenerRegistration> snapshot()
	{
		Set<LiveListenerRegistration> data = Sets.newHashSet();
		
		for (Listener listener: Listeners.registrations().values())
		{
			data.add(of(listener));
		}
		
		return data;
	}
	
	public final ListenerPersistenceData toPersistenceData()
	{
		return ListenerPersistenceData.copy(this.isRegistered(), this.type, Utilities.serializableOrNull(this.listener));
	}
	
	public final Listener getListener()
	{
		return this.listener;
	}

	public final Class<? extends Listener> getListenerClass()
	{
		return this.type;
	}
}
