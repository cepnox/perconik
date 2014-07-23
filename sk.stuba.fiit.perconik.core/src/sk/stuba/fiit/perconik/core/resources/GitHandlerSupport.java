package sk.stuba.fiit.perconik.core.resources;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.events.RepositoryListener;
import org.eclipse.jgit.lib.Repository;

final class GitHandlerSupport<L extends RepositoryListener>
{
	private final Class<L> type;
	
	private final Map<L, ListenerHandle> map;
	
	GitHandlerSupport(final Class<L> type)
	{
		this.type = Preconditions.checkNotNull(type);
		this.map  = Maps.newHashMap();
	}
	
	public final void register(final L listener)
	{
		if (!this.map.containsKey(listener))
		{
			ListenerHandle handle = Repository.getGlobalListenerList().addListener(this.type, listener);
			
			this.map.put(listener, handle);
		}
	}

	public final void unregister(final L listener)
	{
		ListenerHandle handle = this.map.remove(listener);
		
		if (handle != null)
		{
			handle.remove();
		}
	}
}
