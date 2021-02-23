package com.ait.wine;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/suppliers")
public class SupplierResource {
	
	SupplierDAO dao = new SupplierDAO();
	
	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public Response findAll() {
		List<Supplier> suppliers = dao.findAll();
		return Response.status(200).entity(suppliers).build();

	}
	
	@GET @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON})
	public Response findById(@PathParam("id") int id) {
		Supplier supplier = dao.findById(id);
		return Response.status(200).entity(supplier).build();
	}
	
	@GET @Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findByName(
			@PathParam("query") String name){
		List<Supplier> suppliers= dao.findByName(name);
		return Response.status(200).entity(suppliers).build();
	}

}
