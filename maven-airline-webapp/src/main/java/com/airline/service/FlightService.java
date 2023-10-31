package com.airline.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.NotFoundException;

import com.airline.models.Airplane;
import com.airline.models.Flight;
import com.airline.models.Passenger;
import com.airline.models.Pilot;

/**
 * Session Bean implementation class FlightService
 */
@Stateless
@LocalBean
public class FlightService {

	/**
	 * Default constructor.
	 */
	public FlightService() {
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext(unitName = "airline")
	EntityManager entityManager;

	public void addFlight(Flight flight, Airplane airplane) {
		entityManager.persist(flight);
//		entityManager.persist(airplane); //we could remove this line since we are persisting in cascade

	}

	public void addPilotToFlight(String pilotId, String flightId) {
		TypedQuery<Flight> flightQuery = entityManager.createNamedQuery(
				"Flight.findById", Flight.class);
		flightQuery.setParameter("id", Integer.parseInt(flightId));
		Flight fetchedFlight = flightQuery.getSingleResult();

		TypedQuery<Pilot> pilotQuery = entityManager.createNamedQuery(
				"Pilot.findById", Pilot.class);
		pilotQuery.setParameter("id", Integer.parseInt(pilotId));
		Pilot fetchedPilot = pilotQuery.getSingleResult();

		List<Pilot> oListPilots = fetchedFlight.getPilots();
		oListPilots.add(fetchedPilot);

		fetchedFlight.setPilots(oListPilots);
		fetchedPilot.setFlightForPilot(fetchedFlight);
	}

	public List<Flight> getFlights() {
		TypedQuery<Flight> flightQuery = entityManager.createQuery(
				"SELECT f FROM Flight f ", Flight.class);
		
		List<Flight> oListFlights =  (List<Flight>) flightQuery.getResultList();
		
		return oListFlights;
	}
	
	public Flight getFlightById(Integer flightId) {
		TypedQuery<Flight> flightQuery = entityManager.createNamedQuery(
				"Flight.findById", Flight.class);
		
		flightQuery.setParameter("id", flightId);
		Flight fetchedFlight = null;
		try {
			fetchedFlight = flightQuery.getSingleResult();
		} catch (NoResultException ex) {
			System.out.println("Exception ex: "+ ex.toString());
			throw new NotFoundException("The record with id: "+ flightId +" was not found");
		}		 
		
		return fetchedFlight;
	}
	
	public void addPassengerToFlight(String passengerId, String flightId){
		//1) First let's fetch the flight by its id
		//TypedQuery<Flight> flightQuery = entityManager.createNamedQuery(
		//"Flight.findById", Flight.class);
		//flightQuery.setParameter("id", Integer.parseInt(flightId));
		//Flight fetchedFlight = flightQuery.getSingleResult();
		
		//1) First let's fetch the Passenger by its id 
		//#region First way
		//TypedQuery<Passenger> passengerQuery = entityManager.createNamedQuery(
		//"Passenger.findById", Passenger.class);
		//passengerQuery.setParameter("id", Integer.parseInt(passengerId));
		//Passenger fetchedPassenger = passengerQuery.getSingleResult();
		//#endregion		
		
		//2) Second way of fetching with  Criteria query
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Flight> criteriaQueryFlight = builder.createQuery(Flight.class);
		Root<Flight> flightRoot = criteriaQueryFlight.from(Flight.class);
		criteriaQueryFlight.select(flightRoot)
		.where(builder.equal(flightRoot.get("id").as(Integer.class), flightId));
		
		Flight fetchedFlight = entityManager.createQuery(criteriaQueryFlight).getSingleResult();
		
		
		builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Passenger> criteriaQueryPassenger = builder.createQuery(Passenger.class);
		Root<Passenger> passengerRoot = criteriaQueryPassenger.from(Passenger.class);
		criteriaQueryPassenger.select(passengerRoot)
		.where(builder.equal(passengerRoot.get("id").as(Integer.class), passengerId));
		
		Passenger fetchedPassenger = entityManager.createQuery(criteriaQueryPassenger).getSingleResult();
		
		//Associate the passenger with the flight
		List<Passenger> oListPassengers = fetchedFlight.getPassengers();
		oListPassengers.add(fetchedPassenger);
		
		fetchedFlight.setPassengers(oListPassengers);
		fetchedPassenger.getFlights().add(fetchedFlight);
		
	}
	
	public Boolean deleteFlightById(Integer flightId){
		Boolean result = false;
		Flight flightToRemove = entityManager.find(Flight.class, flightId);
	
	    if(flightToRemove == null) {
	    	return result;
	    }
	    entityManager.remove(flightToRemove);
	    result = true;
	    return result;
	}

}
