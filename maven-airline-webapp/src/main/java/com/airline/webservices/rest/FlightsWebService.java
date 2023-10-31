package com.airline.webservices.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;







import com.airline.models.Flight;
import com.airline.service.FlightService;

@Path("/flights")
public class FlightsWebService {

	public FlightsWebService() {
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext(unitName = "airline")
	EntityManager entityManager;
	
	@EJB
	FlightService flightService;
	
	@Context
	UriInfo flightUriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Flight> getFlights(){
		return flightService.getFlights();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{flightId}")
	public Flight getFlightById(@PathParam("flightId") Integer flightId){		
		Flight fetched = flightService.getFlightById(flightId);
		if(fetched == null){
			
		}
		return fetched;
	}
	
	@DELETE
	@Path("{flightId}")
	public Response deleteFlight(@PathParam("flightId")Integer flightId){
		
		Boolean isDeleted = flightService.deleteFlightById(flightId);
		if(!isDeleted){
			throw new NotFoundException("The record with the id: "+ flightId + " was not found!");

		}
		return Response.noContent().build();
	}
}
