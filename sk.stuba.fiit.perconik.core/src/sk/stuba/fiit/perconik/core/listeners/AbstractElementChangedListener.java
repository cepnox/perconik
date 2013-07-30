package sk.stuba.fiit.perconik.core.listeners;

import java.util.EnumSet;
import java.util.Set;
import sk.stuba.fiit.perconik.jdt.core.ElementChangedEventType;

public abstract class AbstractElementChangedListener extends AbstractFilteringListener<ElementChangedEventType> implements ElementChangedListener
{
	protected AbstractElementChangedListener()
	{
		super(EnumSet.allOf(ElementChangedEventType.class));
	}
	
	protected AbstractElementChangedListener(final Set<ElementChangedEventType> types)
	{
		super(types);
	}
}