package sk.stuba.fiit.perconik.core.debug.resources;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import sk.stuba.fiit.perconik.core.Listener;
import sk.stuba.fiit.perconik.core.debug.DebugListener;
import sk.stuba.fiit.perconik.core.services.resources.ResourceNamesSupplier;

public final class DebugListenerPoolNameSupplier implements ResourceNamesSupplier
{
	public final SetMultimap<Class<? extends Listener>, String> get()
	{
		SetMultimap<Class<? extends Listener>, String> map = HashMultimap.create();
		
		map.put(DebugListener.class, DebugListenerPool.getInstance().getName());
		
		return map;
	}
}
