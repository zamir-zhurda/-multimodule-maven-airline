package com.airline.service;

import java.util.ArrayList;
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

import com.airline.models.Flight;
import com.airline.models.Passenger;

/**
 * Session Bean implementation class PassengerService
 */
@Stateless
@LocalBean
public class PassengerService {

    /**
     * Default constructor. 
     */
    public PassengerService() {
        // TODO Auto-generated constructor stub
    }

    @PersistenceContext(unitName="airline") //we can see this name at the persistence.xml file!!
    private EntityManager entityManager;
    
    public Passenger addPassenger(Passenger newPassenger){
    	entityManager.persist(newPassenger);
    	System.out.println("newPassenger: "+ newPassenger.toString());
    	return newPassenger;
    }
        
    public List<Passenger> getPassengers() {
    	List<Passenger> oListPassengers = new ArrayList<Passenger>();
    	
    	TypedQuery<Passenger> queryListPassengers = entityManager.createQuery("SELECT  p from Passenger p", Passenger.class);
    	
    	oListPassengers = queryListPassengers.getResultList();
    	
    	return oListPassengers;
    }
    
    public Passenger getPassengerById(Integer passengerId) {    	
    	
//    	TypedQuery<Passenger> queryListPassenger = entityManager.createNamedQuery("Passenger.findById", Passenger.class);
//    	
//    	queryListPassenger.setParameter("id", passengerId);
//    	
//    	Passenger fetchedPassenger = queryListPassenger.getSingleResult();
//    	
//    	return fetchedPassenger;
    	
    	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    	CriteriaQuery<Passenger> criteriaQueryPassenger = builder.createQuery(Passenger.class);
		Root<Passenger> passengerRoot = criteriaQueryPassenger.from(Passenger.class);
		criteriaQueryPassenger.select(passengerRoot)
		.where(builder.equal(passengerRoot.get("id").as(Integer.class), passengerId));
		Passenger fetchedPassenger = null;
		try {
			 fetchedPassenger = entityManager.createQuery(criteriaQueryPassenger).getSingleResult();
		} catch (NoResultException ex){
			System.out.println("Exception ex: "+ ex.toString());
			throw new NotFoundException("The record with id: "+passengerId+" was not found");
		}
		
		return fetchedPassenger;
    }
    
    public void addFlightTicketToPassenger(String flightId, String passengerId ) {
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
		
		//Associate the flight with the passenger
		List<Flight> oListFlights = fetchedPassenger.getFlights();
		oListFlights.add(fetchedFlight);
		
		fetchedPassenger.setFlights(oListFlights);
		fetchedFlight.getPassengers().add(fetchedPassenger);
    }
    
	public Passenger updatePassenger(Integer passengerId, Passenger updatedPassenger){
		
		Passenger fetchedPassenger = entityManager.find(Passenger.class, passengerId);
		if(fetchedPassenger == null){
			return null;
		}
		
		if(updatedPassenger.getFirstName() != null)
		{
			fetchedPassenger.setFirstName(updatedPassenger.getFirstName());
		}
		
		if(updatedPassenger.getLastName() != null)
		{
			fetchedPassenger.setLastName(updatedPassenger.getLastName());
		}
		
		if(updatedPassenger.getBirthDate() != null)
		{
			fetchedPassenger.setBirthDate(updatedPassenger.getBirthDate());
		}
		
		if(updatedPassenger.getGender() != null)
		{
			fetchedPassenger.setGender(updatedPassenger.getGender());
		}
		entityManager.persist(fetchedPassenger);
		return fetchedPassenger;
	}
	
	public Passenger updatePassengerVersion2(Integer passengerId, Passenger updatedPassenger){
		
		updatedPassenger.setId(passengerId);
		
		Passenger doesPassengerExist = entityManager.find(Passenger.class, passengerId);
		
		if(doesPassengerExist == null){
			return null;
		}
		
		entityManager.merge(updatedPassenger);
		
		return updatedPassenger;
	}
}
