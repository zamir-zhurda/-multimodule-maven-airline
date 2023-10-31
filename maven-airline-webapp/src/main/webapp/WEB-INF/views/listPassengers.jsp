<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.airline.models.Passenger"%>
<%@page import="java.util.List"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="resources/css/jpaStyles.css" />
<title>List of Passengers </title>
</head>
<body>
	<h1> List of Passengers </h1>
	<table>
	 <tr>
	   <th>Id</th>
	   <th>First Name</th>
	   <th>Last Name</th>
	   <th>Date of birth</th>
	   <th>Gender</th>
	 </tr>
	 <%
	    List<Passenger> oListPassengers = (List<Passenger>) request.getAttribute("passenger_list");

		for (Integer i = 0; i < oListPassengers.size(); i++) {
	 %>
	 <tr>
	   <td> <%= oListPassengers.get(i).getId() %> </td>
	   <td> <%= oListPassengers.get(i).getFirstName() %> </td>
	   <td> <%= oListPassengers.get(i).getLastName() %> </td>
	   <td> <%= oListPassengers.get(i).getBirthDate() %> </td>
	   <td> <%= oListPassengers.get(i).getGender() %> </td>
	 </tr>
	  <tr>
	   <td colspan="5">
	   	<% if(oListPassengers.get(i).getFlights() != null && oListPassengers.get(i).getFlights().size() > 0) {
	   	   
	   		for (Integer j = 0; j < oListPassengers.get(i).getFlights().size(); j++) {
	   		
	   	%>
	   	
	     <%= 
	     j+1 + ") From: " + oListPassengers.get(i).getFlights().get(j).getFlightOrigin() + " to: " +
	      oListPassengers.get(i).getFlights().get(j).getFlightDestination() + " at: " +
	      oListPassengers.get(i).getFlights().get(j).getFlightTime()+ "<br/>"
	     %>
	    
	    <% } } else { %>
	       No Flight tickets yet.
	     <% } %>
	   </td>
	  </tr>
	  <%
		 } //closing for loop		
	  %>
	</table>
</body>
</html>