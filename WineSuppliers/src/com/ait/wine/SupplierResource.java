package com.ait.wine;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
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

}
