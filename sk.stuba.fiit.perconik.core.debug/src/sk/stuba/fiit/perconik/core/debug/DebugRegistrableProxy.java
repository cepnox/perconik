package sk.stuba.fiit.perconik.core.debug;

import sk.stuba.fiit.perconik.core.Registrable;
import sk.stuba.fiit.perconik.core.debug.runtime.DebugConsole;

public abstract class DebugRegistrableProxy extends DebugObjectProxy implements DebugRegistrable
{
	protected DebugRegistrableProxy(final DebugConsole console)
	{
		super(console);
	}
	
	@Override
	public abstract Registrable delegate();
	
	public final void preRegister()
	{
		this.put("Pre register %s ... ", this);
		
		this.delegate().preRegister();
		
		this.print("done");
	}

	public final void postRegister()
	{
		this.put("Post register %s ... ", this);
		
		this.delegate().postRegister();
		
		this.print("done");
	}

	public final void preUnregister()
	{
		this.put("Pre unregister %s ... ", this);
		
		this.delegate().preUnregister();
		
		this.print("done");
	}

	public final void postUnregister()
	{
		this.put("Post unregister %s ... ", this);
		
		this.delegate().postUnregister();
		
		this.print("done");
	}
}
