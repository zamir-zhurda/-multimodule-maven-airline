package com.airline.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.airline.models.Airplane;
import com.airline.models.Flight;
import com.airline.models.FlightDestinations;
import com.airline.service.FlightService;

/**
 * Servlet implementation class AddFlight
 */
@WebServlet("/AddFlight")
public class AddFlight extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	FlightService flightService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFlight() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Flight flight = new Flight();
		//#region OLD ENCODED VALUES
		//		flight.setFlightOrigin(FlightDestinations.Los_Angeles);
		//		flight.setFlightDestination(FlightDestinations.London);
		//		flight.setPrice(400);
		//		
		//		Calendar calendar = Calendar.getInstance();
		//		calendar.set(Calendar.YEAR, 2014);
		//		calendar.set(Calendar.MONTH, 10);
		//		calendar.set(Calendar.DAY_OF_MONTH, 4);
		//		calendar.set(Calendar.HOUR_OF_DAY, 19);
		//		calendar.set(Calendar.MINUTE, 0);
		//		
		//		Date flightTime = calendar.getTime();
		//		
		//		System.out.println("\n\t flightTime: " + flightTime);
		//		
		//		flight.setFlightTime(flightTime);
		//		
		//		Airplane airplane = new Airplane();
		//		airplane.setModelName("Boeing 787");
		//		airplane.setPlaneMake("Boeing");
		//		airplane.setSeatingCapacity(250);
		//		
		//		flight.setAirplaneDetails(airplane);
		//		
		//		System.out.println("\n\t flight: " + flight);
		//		System.out.println("\n\t airplane: " + airplane);
		//#endregion
		
		String fromDestination = request.getParameter("from_destination");
		flight.setFlightOrigin(FlightDestinations.valueOf(fromDestination));
		
		String toDestination = request.getParameter("to_destination");
		flight.setFlightDestination(FlightDestinations.valueOf(toDestination));
		
		String price = request.getParameter("price");
		flight.setPrice(Integer.parseInt(price));
		
		Integer year = Integer.parseInt(request.getParameter("year"));
		Integer month = Integer.parseInt(request.getParameter("month"));
		Integer day = Integer.parseInt(request.getParameter("day"));
		Integer hour = Integer.parseInt(request.getParameter("hour"));
		Integer minute = Integer.parseInt(request.getParameter("minute"));
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		
		Date flightTime = calendar.getTime();
		flight.setFlightTime(flightTime);
		
		Airplane airplane = new Airplane();
		String planeMake = request.getParameter("airplane_make");
		String planeModelName = request.getParameter("airplane_model");
		Integer seatingCapacity = Integer.parseInt(request.getParameter("airplane_seating"));
		airplane.setModelName(planeModelName);
		airplane.setPlaneMake(planeMake);
		airplane.setSeatingCapacity(seatingCapacity);
		
		flight.setAirplaneDetails(airplane);
		System.out.println("======================================================");
		System.out.println("flight: "+ flight.toString());
		System.out.println("airplane: "+airplane.toString());
		System.out.println("======================================================");
		flightService.addFlight(flight, airplane);
		response.sendRedirect("Flights");
	}

}
