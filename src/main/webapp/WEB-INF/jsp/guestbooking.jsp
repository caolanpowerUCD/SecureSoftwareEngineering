<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Your Bookings</title>

    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">

</head>
<body>

<nav style="margin-bottom: 40px" class="navbar navbar-expand-sm navbar-light bg-light fixed-top">
    <a href="/">
        <img class="nav-logo navbar-brand" src="../images/BA_logo.jpg" alt="Image of Logo" height="100" width="150">
        <a/>
        <div class="navbar-nav">
            <a class="nav-item nav-link active" href="/login">Sign In</a>
            <a class="nav-item nav-link active" href="/register">Register</a>
            <a class="nav-item nav-link active" href="/">Flights</a>
            <a class="nav-item nav-link active" href="/findGuestBookings">Bookings</a>
        </div>
</nav>
<div class="contentContainer">
    <table style="margin-top: 150px" align="center" border="0.2" cellpadding="5">
        <caption align="center" style="caption-side: top"><h2 align="center">Flight Details</h2></caption>
        <tr>
            <th>Flight Id</th>
            <th>Origin</th>
            <th>Destination</th>
            <th>Departure Date</th>
        </tr>
        <c:forEach var="flight" items="${listFlights}">
            <tr>
                <td><c:out value="${flight.flightId}" /></td>
                <td><c:out value="${flight.flightOrigin}" /></td>
                <td><c:out value="${flight.flightDestination}" /></td>
                <td><c:out value="${flight.departureDate}" /></td>

                <td>&nbsp;&nbsp;&nbsp;
                    <a href="/guestcancel/${flight.flightId}">Cancel</a>
                </td>
            </tr>
        </c:forEach>
    </table>


</div>
</body>
</html>
