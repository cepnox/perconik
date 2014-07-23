package sk.stuba.fiit.perconik.core.listeners;

import org.eclipse.jface.text.IMarkSelection;
import org.eclipse.ui.IWorkbenchPart;

import sk.stuba.fiit.perconik.core.Listener;

/**
 * A mark selection listener.
 * 
 * @see Listener
 * @see SelectionListener
 * 
 * @author Pavol Zbell
 * @since 1.0
 */
public interface MarkSelectionListener extends Listener
{
	public void selectionChanged(IWorkbenchPart part, IMarkSelection selection);
}
