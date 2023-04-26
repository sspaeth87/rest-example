
package de.xdevsoftware.restexample.config.filter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Provider
public class AuthenticationFilter implements ContainerRequestFilter
{
	@Override
	public void filter(final ContainerRequestContext requestContext)
	{
		final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
			.entity("You cannot access this resource")
			.build();
		
		// Fetch authorization header
		final List<String> authorization = requestContext.getHeaders().get("Authorization");
		
		// If no authorization information present; block access
		if(authorization == null || authorization.isEmpty())
		{
			requestContext.abortWith(ACCESS_DENIED);
			return;
		}
		
		// Split username and password tokens
		final StringTokenizer tokenizer =
			new StringTokenizer(new String(
				Base64.getDecoder()
					.decode(authorization.get(0).replaceFirst("Basic" + " ", "").getBytes(StandardCharsets.UTF_8))),
				":");
		final String          username  = tokenizer.nextToken();
		final String          password  = tokenizer.nextToken();
		
		if(!username.contentEquals("admin") || !password.contentEquals("geheim123"))
		{
			requestContext.abortWith(ACCESS_DENIED);
		}
	}

}
