package com.airline.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.airline.models.Flight;
import com.airline.models.Pilot;

/**
 * Session Bean implementation class PilotService
 */
@Stateless
@LocalBean
public class PilotService {
	
	@PersistenceContext( unitName= "airline" )
	EntityManager entityManager;
    /**
     * Default constructor. 
     */
    public PilotService() {
        // TODO Auto-generated constructor stub
    }
    
    public void addPilot(Pilot pilot){
    	entityManager.persist(pilot);
    }
    
	public void addNewPilotToFlight(Pilot newPilot, String flightId) {
		
		//1) Persist this object to the database
		entityManager.persist(newPilot);
		
		//2 fetch the flight and assign it to the pilot
		TypedQuery<Flight> flightQuery = entityManager.createNamedQuery(
				"Flight.findById", Flight.class);
		flightQuery.setParameter("id", Integer.parseInt(flightId));
		Flight fetchedFlight = flightQuery.getSingleResult();	

		List<Pilot> oListPilots = fetchedFlight.getPilots();
		oListPilots.add(newPilot);

		fetchedFlight.setPilots(oListPilots);
		newPilot.setFlightForPilot(fetchedFlight);
	}
}
