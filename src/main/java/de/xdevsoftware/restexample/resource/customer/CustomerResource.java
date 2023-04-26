
package de.xdevsoftware.restexample.resource.customer;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rapidclipse.framework.server.Rap;

import de.xdevsoftware.restexample.dal.CustomerDAO;
import de.xdevsoftware.restexample.domain.Customer;


// http://localhost:8080/RestExample/api/customer
@Path("customer")
public class CustomerResource
{
	@GET
	public Response all() throws InterruptedException, ExecutionException
	{
		final Future<List<Customer>> query = Rap.getExecutorService().submit(() -> {
			return new CustomerDAO().findAll();
		});

		return Response.ok(query.get()).build();
	}

	@GET()
	@Path("{id}")
	public Response find(@PathParam("id") final String id) throws InterruptedException, ExecutionException
	{
		final Future<Customer> query = Rap.getExecutorService().submit(() -> {
			return new CustomerDAO().find(id);
		});
		
		final Customer customer = query.get();
		if(customer != null)
		{
			return Response.ok(customer).build();
		}

		return Response.status(Status.NOT_FOUND).entity("No customer was found with the id: " + id).build();
	}
	
}
