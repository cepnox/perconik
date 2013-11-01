package sk.stuba.fiit.perconik.core.resources;

import java.util.Map;
import sk.stuba.fiit.perconik.core.Listener;
import sk.stuba.fiit.perconik.core.Resource;
import com.google.common.collect.Maps;

abstract class AbstractHookSupport<H extends Hook<T, L>, T, L extends Listener> implements HookFactory<T, L>
{
	private final Map<L, H> hooks;
	
	AbstractHookSupport()
	{
		this.hooks = Maps.newHashMap();
	}
	
	final void hook(final Resource<? super H> resource, final L listener)
	{
		H hook = this.hooks.get(listener);
		
		if (hook == null)
		{
			hook = (H) this.create(listener);
			
			this.hooks.put(listener, hook);
		}
		
		// TODO rm
		//resource.register(hook);
		
		DefaultResources.registerAndHookListener(resource, hook);
	}
	
	final void unhook(final Resource<? super H> resource, final L listener)
	{
		H hook = this.hooks.get(listener);
		
		if (hook != null)
		{
			// TODO rm
			//resource.unregister(hook);
			
			DefaultResources.unregisterAndHookListener(resource, hook);
		}
	}
}
