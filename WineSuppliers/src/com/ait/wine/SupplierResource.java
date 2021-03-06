package com.ait.wine;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_JSON})
	public Response create(Supplier resource) {
		
		Supplier resourceObj = dao.create(resource);
		
		return Response.status(201).entity(resourceObj).build();
	}
	
	@DELETE @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON})
	public Response remove(@PathParam("id") int id) {
		dao.remove(id);
		return Response.status(204).build();
	}
	
	@GET @Path("{id}/wines")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findWinesBySupplier(@PathParam("id") int id) {
		List<Wine> wines = dao.findWinesBySupplierId(id);
		return Response.status(200).entity(wines).build();
	}

}
