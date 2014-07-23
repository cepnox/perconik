package sk.stuba.fiit.perconik.ui.plugin;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchWindow;

import org.osgi.framework.BundleContext;

import sk.stuba.fiit.perconik.eclipse.ui.Workbenches;
import sk.stuba.fiit.perconik.eclipse.ui.plugin.UserInterfacePlugin;
import sk.stuba.fiit.perconik.environment.JavaVerificationException;
import sk.stuba.fiit.perconik.osgi.framework.Versions;

import static sk.stuba.fiit.perconik.osgi.framework.Versions.Component.MAJOR;
import static sk.stuba.fiit.perconik.osgi.framework.Versions.Component.MINOR;

/**
 * The <code>Activator</code> class controls the plug-in life cycle.
 * 
 * <p><b>Warning:</b> Users should not explicitly instantiate this class.
 * 
 * @author Pavol Zbell
 * @since 1.0
 */
public final class Activator extends UserInterfacePlugin
{
	/**
	 * The plug-in identifier.
	 */
	public static final String PLUGIN_ID = "sk.stuba.fiit.perconik.ui";

	/**
	 * The shared instance.
	 */
	private static Activator plugin;

	private static final AtomicBoolean verified = new AtomicBoolean(false);
	
	/**
	 * The constructor.
	 */
	public Activator()
	{
	}

	/**
	 * Gets the shared instance.
	 * @return the shared instance
	 */
	public static final Activator getDefault()
	{
		return plugin;
	}
	
	/**
	 * Plug-in early startup. 
	 * 
	 * <p><b>Warning:</b> Users should not explicitly instantiate this class.
	 * 
	 * @author Pavol Zbell
	 * @since 1.0
	 */
	public static final class Startup implements IStartup
	{
		/**
		 * The constructor.
		 */
		public Startup()
		{
		}

		/**
		 * Processes supplied extensions and starts core services.
		 */
		public final void earlyStartup()
		{
			verifyJava();
		}
	}

	static final void verifyJava()
	{
		if (!verified.compareAndSet(false, true))
		{
			return;
		}
		
		try
		{
			sk.stuba.fiit.perconik.environment.plugin.Activator.getDefault().verifyJava();
		}
		catch (final JavaVerificationException e)
		{
			final Runnable dialog = new Runnable()
			{
				public final void run()
				{
					IWorkbenchWindow window = Workbenches.getActiveWindow();
					
					Shell shell = window != null ? window.getShell() : Display.getDefault().getActiveShell(); 

					String title   = "PerConIK Core for Eclipse Platform";
					String message = "PerConIK Extension requires Java ";
					
					message += Versions.toString(e.getRequiredJavaVersion(), MAJOR, MINOR);
					message += " or greater but Java ";
					message += Versions.toString(e.getDetectedJavaVersion(), MAJOR, MINOR);
					message += " was detected.";
					message += " Extension is not active, please launch Eclipse on Java ";
					message += Versions.toString(e.getRequiredJavaVersion(), MAJOR, MINOR);
					message += " again.";

					MessageDialog.openWarning(shell, title, message);
				}
			};
			
			Display.getDefault().syncExec(dialog);
		}
	}
	
	@Override
	public final void start(final BundleContext context) throws Exception
	{
		super.start(context);

		plugin = this;
		
		verifyJava();
	}

	@Override
	public final void stop(final BundleContext context) throws Exception
	{
		plugin = null;

		super.stop(context);
	}
}
