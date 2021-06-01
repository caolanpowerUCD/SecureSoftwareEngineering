<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Your Past Reservations</title>

    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">

</head>
<body>

<nav class="navbar navbar-expand-md navbar-light bg-light fixed-top">
    <a href="/logout">
        <img class="nav-logo navbar-brand" src="../images/BA_logo.jpg" alt="Image of Logo" height="100" width="150">
        <a/>
    <div class="navbar-nav">
        <a class="nav-item nav-link active" href="/executiveUserHome">Flights</a>
        <a class="nav-item nav-link active" href="/carddetails">Credit card details</a>
        <a class="nav-item nav-link active" href="/update">Personal details</a>
        <a class="nav-item nav-link active" href="/password">Change Password</a>
        <a class="nav-item nav-link active" href="/executiveUserBookings">Booking history</a>
        <a class="nav-item nav-link active" href="/delete">Delete Account</a>
        <a class="nav-item nav-link active" href="/logout">Logout</a>
    </div>
</nav>

<div align="center" style="margin-top: 150px">
    <table cellpadding="5">
        <caption style="caption-side: top" align="center">Your Reservations</caption>
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
                    <a href="/cancel/${flight.flightId}"/>Cancel</a>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>
