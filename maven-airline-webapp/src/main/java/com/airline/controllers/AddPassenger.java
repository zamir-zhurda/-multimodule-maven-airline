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

import com.airline.models.FlightClass;
import com.airline.models.Gender;
import com.airline.models.Passenger;
import com.airline.service.PassengerService;

/**
 * Servlet implementation class AddPassenger
 */
@WebServlet("/AddPassenger")
public class AddPassenger extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	PassengerService passengerService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPassenger() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Passenger passenger = new Passenger();
		
		passenger.setFirstName("Zamir");
		passenger.setLastName("Zhurda");
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1985);
		calendar.set(Calendar.MONTH, 2);
		calendar.set(Calendar.DAY_OF_MONTH, 21);
		Date dob = calendar.getTime();
		passenger.setBirthDate(dob);
		
		passenger.setGender(Gender.Male);
		passenger.setFlightClass(FlightClass.First);
		
		System.out.println("passenger to be persisted: " + passenger);
		
		passengerService.addPassenger(passenger);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String dob_raw = request.getParameter("dob");
		String gender = request.getParameter("gender");
		
		String regex = "\\/";
		String[] dob_array = dob_raw.split(regex);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(dob_array[2]));
		calendar.set(Calendar.MONTH, Integer.parseInt(dob_array[0]) - 1);
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dob_array[1]));
		
		Date dob = calendar.getTime();
		
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(firstName);
		passenger.setLastName(lastName);
		passenger.setBirthDate(dob);
		passenger.setGender(Gender.valueOf(gender));
		
		passengerService.addPassenger(passenger);
		response.sendRedirect("Passengers");
	}

}
