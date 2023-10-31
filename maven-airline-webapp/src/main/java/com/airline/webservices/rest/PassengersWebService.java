package com.airline.webservices.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.airline.models.Flight;
import com.airline.models.Passenger;
import com.airline.service.FlightService;
import com.airline.service.PassengerService;

import java.net.*;

@Path("/passengers")
public class PassengersWebService {

	public PassengersWebService() {
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext(unitName = "airline")
	EntityManager entityManager;
	
	@EJB
	PassengerService passengerService;
	
	@Context
	UriInfo passengerUriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Passenger> getPassengers(){
		return passengerService.getPassengers();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{passengerId}")
	public Passenger getPassenger(@PathParam("passengerId") Integer passengerId){
		Passenger fetched = passengerService.getPassengerById(passengerId);
		if(fetched == null){
			
		}
		return fetched; 
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPassenger(Passenger newPassenger){
		Passenger persistedPassenger = passengerService.addPassenger(newPassenger);
		
		UriBuilder passengerUriBuilder = passengerUriInfo.getAbsolutePathBuilder();
		
		URI passengerUri = passengerUriBuilder.path(String.valueOf(persistedPassenger.getId())).build();
		
		return Response.created(passengerUri).build();
	}
	
	@PUT
	@Path("/edit/{passengerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePassenger(@PathParam("passengerId") Integer passengerId, Passenger updatedPassenger){
        Passenger persistedPassenger = passengerService.updatePassenger(passengerId, updatedPassenger);
		
        if(persistedPassenger == null) {
        	throw new NotFoundException("The record with the id: "+passengerId+ " was not found!");
        }
        
		UriBuilder passengerUriBuilder = passengerUriInfo.getAbsolutePathBuilder();
		
		URI passengerUri = passengerUriBuilder.path(String.valueOf(persistedPassenger.getId())).build();
		
		return Response.created(passengerUri).build();
	}
	
	@PUT
	@Path("/v2/edit/{passengerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePassengerVersion2(@PathParam("passengerId") Integer passengerId, Passenger updatedPassenger){
        Passenger persistedPassenger = passengerService.updatePassengerVersion2(passengerId, updatedPassenger);
		
        if(persistedPassenger == null) {
        	throw new NotFoundException("The record with the id: "+passengerId+ " was not found!");
        }
        
		UriBuilder passengerUriBuilder = passengerUriInfo.getAbsolutePathBuilder();
		
		URI passengerUri = passengerUriBuilder.path(String.valueOf(persistedPassenger.getId())).build();
		
		return Response.created(passengerUri).build();
	}

	
}
