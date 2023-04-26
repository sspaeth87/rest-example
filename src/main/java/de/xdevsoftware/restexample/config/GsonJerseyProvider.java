
package de.xdevsoftware.restexample.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;


@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GsonJerseyProvider implements MessageBodyWriter<Object>, MessageBodyReader<Object>, ContainerResponseFilter
{
	private static final String UTF_8 = "UTF-8";
	
	@Override
	public boolean isReadable(
		final Class<?> type,
		final Type genericType,
		final java.lang.annotation.Annotation[] annotations,
		final MediaType mediaType)
	{
		return true;
	}
	
	@Override
	public Object readFrom(
		final Class<Object> type,
		final Type genericType,
		final Annotation[] annotations,
		final MediaType mediaType,
		final MultivaluedMap<String, String> httpHeaders,
		final InputStream entityStream)
		throws IOException
	{
		try(final InputStreamReader streamReader = new InputStreamReader(entityStream, GsonJerseyProvider.UTF_8))
		{
			return GsonUtil.getInstance().fromJson(streamReader, genericType);
		}
		catch(final com.google.gson.JsonSyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean isWriteable(
		final Class<?> type,
		final Type genericType,
		final Annotation[] annotations,
		final MediaType mediaType)
	{
		return true;
	}
	
	@Override
	public long getSize(
		final Object object,
		final Class<?> type,
		final Type genericType,
		final Annotation[] annotations,
		final MediaType mediaType)
	{
		return -1;
	}
	
	@Override
	public void writeTo(
		final Object object,
		final Class<?> type,
		final Type genericType,
		final Annotation[] annotations,
		final MediaType mediaType,
		final MultivaluedMap<String, Object> httpHeaders,
		final OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		try(final OutputStreamWriter writer = new OutputStreamWriter(entityStream, GsonJerseyProvider.UTF_8))
		{
			GsonUtil.getInstance().toJson(object, genericType, writer);
		}
	}

	@Override
	public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext)
		throws IOException
	{
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		responseContext.getHeaders().add("Access-Control-Max-Age", "1209600");
	}
	
}
