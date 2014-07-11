package com.gratex.perconik.activity.ide.listeners;

import sk.stuba.fiit.perconik.core.annotations.Experimental;
import sk.stuba.fiit.perconik.core.annotations.Unsupported;
import com.gratex.perconik.activity.ide.UacaProxy;
import com.gratex.perconik.services.uaca.ide.IdeCodeElementEventRequest;

/**
 * A listener of IDE code element events. This listener handles desired
 * events and eventually builds corresponding data transfer objects
 * of type {@link IdeCodeElementEventRequest} and passes them to the
 * {@link UacaProxy} to be transferred into the <i>User Activity Central
 * Application</i> for further processing.
 * 
 * <p>Experimental and unsupported implementation.
 * 
 * @author Pavol Zbell
 * @since 1.0
 */
@Experimental
@Unsupported
public final class IdeElementListener extends IdeListener
{
}
