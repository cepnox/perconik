package sk.stuba.fiit.perconik.core.plugin;

import com.google.common.base.Preconditions;

import sk.stuba.fiit.perconik.core.services.resources.ResourceNamesSupplier;
import sk.stuba.fiit.perconik.core.services.resources.ResourceService;

final class ResolvedResources extends ResolvedService<ResourceService>
{
	final ResourceNamesSupplier supplier;
	
	ResolvedResources(final ResourceService service, final ResourceNamesSupplier supplier)
	{
		super(service);
		
		this.supplier = Preconditions.checkNotNull(supplier);
	}
}
