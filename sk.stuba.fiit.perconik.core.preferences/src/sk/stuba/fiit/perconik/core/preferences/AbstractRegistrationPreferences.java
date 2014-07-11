package sk.stuba.fiit.perconik.core.preferences;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.Set;
import org.osgi.service.prefs.BackingStoreException;
import sk.stuba.fiit.perconik.core.persistence.Registration;
import sk.stuba.fiit.perconik.core.preferences.plugin.Activator;
import sk.stuba.fiit.perconik.eclipse.core.runtime.PluginConsoles;
import sk.stuba.fiit.perconik.osgi.framework.Bundles;
import sk.stuba.fiit.perconik.preferences.AbstractObjectPreferences;

abstract class AbstractRegistrationPreferences<R extends Registration> extends AbstractObjectPreferences
{
	AbstractRegistrationPreferences(final Scope scope, final String qualifier)
	{
		super(scope, qualifier, Bundles.newClassResolver(Activator.getDefault().getBundle()));
	}

	final void setRegistrations(final String key, final Set<R> registrations)
	{
		try
		{
			this.putObject(key, registrations);
		}
		catch (RuntimeException e)
		{
			PluginConsoles.create(Activator.getDefault()).error(e, "Unable to write registrations under key %s", key);
		}
	}

	final Set<R> getRegistrations(final String key)
	{
		try
		{
			return (Set<R>) checkNotNull(this.getObject(key));
		}
		catch (RuntimeException e)
		{
			PluginConsoles.create(Activator.getDefault()).error(e, "Unable to read registrations under key %s", key);

			if (this.scope() != Scope.DEFAULT)
			{
				Set<R> registrations = this.getDefaultRegistrations();

				this.setRegistrations(key, registrations);

				try
				{
					this.synchronize();
				}
				catch (BackingStoreException x)
				{
					PluginConsoles.create(Activator.getDefault()).error(e, "Unable to synchronize registrations under key %s", key);
				}

				return registrations;
			}

			throw e;
		}
	}

	abstract Set<R> getDefaultRegistrations();
}
