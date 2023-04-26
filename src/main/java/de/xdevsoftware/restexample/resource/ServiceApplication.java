
package de.xdevsoftware.restexample.resource;

import org.glassfish.jersey.server.ResourceConfig;

import de.xdevsoftware.restexample.config.GsonJerseyProvider;
import de.xdevsoftware.restexample.config.filter.AuthenticationFilter;


public class ServiceApplication extends ResourceConfig
{
	public ServiceApplication()
	{
		this.packages(this.getClass().getPackage().getName());
		this.register(GsonJerseyProvider.class);
		this.register(AuthenticationFilter.class);
	}
}
