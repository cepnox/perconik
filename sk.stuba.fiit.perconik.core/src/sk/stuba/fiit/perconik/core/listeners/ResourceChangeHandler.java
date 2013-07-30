package sk.stuba.fiit.perconik.core.listeners;

import org.eclipse.core.resources.ResourcesPlugin;

enum ResourceChangeHandler implements Handler<ResourceChangeListener>
{
	INSTANCE;
	
	public final void add(final ResourceChangeListener listener)
	{
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener, Handlers.mask(listener));
	}

	public final void remove(final ResourceChangeListener listener)
	{
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(listener);
	}
}
