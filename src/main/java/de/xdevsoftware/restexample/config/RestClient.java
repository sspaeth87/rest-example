
package de.xdevsoftware.restexample.config;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;


public class RestClient
{
	public static WebTarget getWebTarget()
	{
		final Client client = ClientBuilder
			.newBuilder()
			.register(HttpAuthenticationFeature.basic("admin", "geheim123"))
			.build();

		return client.target(RestClient.getBaseURI());
	}

	private static URI getBaseURI()
	{
		return UriBuilder.fromUri("http://localhost:8080/RestExample/api").build();
	}
}
