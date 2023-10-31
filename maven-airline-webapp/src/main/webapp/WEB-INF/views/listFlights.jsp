<!DOCTYPE html>
<%@page import="com.airline.models.Passenger"%>
<%@page import="com.airline.models.Pilot"%>
<%@page import="com.airline.models.Flight"%>
<%@page import="java.util.List"%>
<html>
<head>
<link rel="stylesheet" href="resources/css/jpaStyles.css" />
<title>List of Flights</title>
</head>
<body>
	<h1>List of Flights</h1>
	<table>
		<tr>
			<th>Id</th>
			<th>From</th>
			<th>To</th>
			<th>Time</th>
			<th>Price</th>
			<th>Airplane</th>
			<th>Seating</th>
			<th>Number of Pilots</th>
			<th>Pilot Names</th>
		</tr>
		<%
			List<Flight> oListFlights = (List<Flight>) request.getAttribute("flight_list");

			for (Integer i = 0; i < oListFlights.size(); i++) {
		%>
		<tr>
		 <td> <%= oListFlights.get(i).getId() %> </td>
		 <td> <%= oListFlights.get(i).getFlightOrigin() %> </td>
		 <td> <%= oListFlights.get(i).getFlightDestination() %> </td>
		 <td> <%= oListFlights.get(i).getFlightTime() %> </td>
		 <td> <%= oListFlights.get(i).getPrice() %> </td>
		 <td> <%=oListFlights.get(i).getAirplaneDetails().getPlaneMake() + "-" + oListFlights.get(i).getAirplaneDetails().getModelName() %> </td>
		 <td> <%= oListFlights.get(i).getAirplaneDetails().getSeatingCapacity() %> </td>
		 <td> 
		   <% if(oListFlights.get(i).getPilots() != null) { %> 
		   
		   <%= oListFlights.get(i).getPilots().size() %> pilots
		  
		   <% } else { %> 
		     No pilots yet.
		   <% } %>
		 </td>
		 <td> 
		   <% if(oListFlights.get(i).getPilots() != null) { 
		      List<Pilot> oListPilots = oListFlights.get(i).getPilots();
		   		for( Integer j = 0; j < oListPilots.size(); j++ ){
		   %> 
		    <%= 
		     (j+1) + ") " + oListPilots.get(j).getFirstName() + " " +  oListPilots.get(j).getLastName()
		     + " (" + oListPilots.get(j).getPilotRank() + ")"+ "<br/>"
		    %>
		   <% } %>
		  </td>
		</tr>
		<tr>
		 <td colspan="9">
		 	<%
		 		if(oListFlights.get(i).getPassengers() != null && oListFlights.get(i).getPassengers().size() > 0){
		 			List<Passenger> oListPassengers = oListFlights.get(i).getPassengers();
		 			for( Integer k = 0; k < oListPassengers.size(); k++ ){
		 		
		 	%>
		 	<%=
		 	 (k + 1) + ") " +  oListPassengers.get(k).getFirstName() + " " +  oListPassengers.get(k).getLastName() + "<br/>"
		 	%>
		 	<%
		 	  }  //closing for loop 
		 	 }  else {
		 	%>
		 		No passengers in this flight yet.
		 	<%
		 	 } //closing else statement
		 	%>
		 </td>
		</tr>
		<%
		   } //closing for loop
		 } //closing if
		%>

	</table>
</body>
</html>