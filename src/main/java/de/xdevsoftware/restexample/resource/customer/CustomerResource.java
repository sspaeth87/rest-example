
package de.xdevsoftware.restexample.resource.customer;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rapidclipse.framework.server.Rap;

import de.xdevsoftware.restexample.dal.CustomerDAO;
import de.xdevsoftware.restexample.domain.Customer;
import de.xdevsoftware.restexample.dto.CustomerDTO;
import de.xdevsoftware.restexample.util.ItemMapper;


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
		
		return Response.ok(ItemMapper.fromList(query.get(), CustomerDTO.class)).build();
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
			return Response.ok(ItemMapper.fromItem(customer, CustomerDTO.class)).build();
		}
		
		return Response.status(Status.NOT_FOUND).entity("No customer was found with the id: " + id).build();
	}

	@POST
	public Response create(final CustomerDTO customerDTO) throws InterruptedException, ExecutionException
	{
		final Customer customer = ItemMapper.fromItem(customerDTO, Customer.class);
		// workaround to generate a id
		final String uuid = UUID.randomUUID().toString();
		customer.setCustomerid(uuid.substring(0, 5));
		
		final Validator                             validator =
			Validation.buildDefaultValidatorFactory().getValidator();
		final Set<ConstraintViolation<CustomerDTO>> validate  = validator.validate(customerDTO);
		
		if(validate.isEmpty())
		{
			final Future<Customer> submit = Rap.getExecutorService().submit(() -> {
				return new CustomerDAO().save(customer);
			});

			return Response.status(Status.CREATED).entity(submit.get()).build();
		}

		validate.forEach(error -> System.out.println(error.getMessage()));

		return Response.status(Status.BAD_REQUEST).build();
	}
}
