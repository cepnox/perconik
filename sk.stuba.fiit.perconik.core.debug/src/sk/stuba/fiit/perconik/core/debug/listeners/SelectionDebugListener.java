package sk.stuba.fiit.perconik.core.debug.listeners;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import sk.stuba.fiit.perconik.core.debug.AbstractDebugListener;
import sk.stuba.fiit.perconik.core.debug.Debug;
import sk.stuba.fiit.perconik.core.debug.runtime.DebugConsole;
import sk.stuba.fiit.perconik.core.listeners.SelectionListener;

public final class SelectionDebugListener extends AbstractDebugListener implements SelectionListener
{
	public SelectionDebugListener()
	{
	}
	
	public SelectionDebugListener(final DebugConsole console)
	{
		super(console);
	}
	
	public final void selectionChanged(final IWorkbenchPart part, final ISelection selection)
	{
		this.printHeader("Selection changed");
		this.printSelection(selection);
	}

	private final void printSelection(final ISelection selection)
	{
		this.put(Debug.dumpSelection(selection));
	}
}
