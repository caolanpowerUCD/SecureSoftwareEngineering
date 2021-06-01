<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>British Airways Homepage</title>

    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">

</head>
<body>

<nav class="navbar navbar-expand-md navbar-light bg-light fixed-top">
    <a href="/">
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
        <a class="nav-item nav-link active" href="/admin">ADMIN</a>
        <a class="nav-item nav-link active" href="/editFlights">Edit Flights</a>
        <a class="nav-item nav-link active" href="/addFlight">Add a Flight</a>
        <a class="nav-item nav-link active" href="/editBookings">Edit Bookings</a>
    </div>
</nav>

<div class="row" style="margin-top: 150px" align="center">

    <form style="margin-top: 150px" method="POST" action="/adminSearchFlights">
        <div class="form-group col-md-4">
            <label class="font-weight-bold" align="center">Search all flights</label>
            <div class="input-group col-md-4">
                <input type="text" class="form-control" style="margin-left: 40px" name="origin" placeholder="Origin">
            </div>

            <div class="input-group col-md-4">
                <input type="text" class="form-control" style="margin-left: 40px; margin-top: 10px" name="destination" placeholder="Destination">
            </div>

            <div class="input-group col-md-4">
                <div><p class="col-md-2 font-weight-bold" style="margin-left: 40%">Start date</p></div>
                <div> <input type="date" class="col-md-3 form-control" style="margin-left: 40px; margin-top: 15px" name="startDate" placeholder="Start Date of departure"> </div>
            </div>

            <div class="input-group col-md-4">

                <div><p class="col-md-2 font-weight-bold" style="margin-left: 40%">End date</p></div>
                <div><input type="date" class="col-md-3 form-control" style="margin-left: 40px; margin-top: 15px" name="endDate" placeholder="End Date of departure"></div>
            </div>
        </div>
        <button type="submit" class="btn btn-dark col-md-3" style="margin-left: 60px; margin-top: 20px">Search</button>
    </form>
</div>
<div class="contentContainer">
    <table align="center" border="1" cellpadding="5">
        <caption align="center" style="caption-side: top"><h2>Flight List</h2></caption>
        <tr>
            <th>Flight Id</th>
            <th>Origin</th>
            <th>Destination</th>
            <th>Departure Date</th>
            <th>Total Seats</th>
        </tr>
        <c:forEach var="flight" items="${listFlights}">
            <tr>
                <td><c:out value="${flight.flightId}" /></td>
                <td><c:out value="${flight.flightOrigin}" /></td>
                <td><c:out value="${flight.flightDestination}" /></td>
                <td><c:out value="${flight.departureDate}" /></td>
                <td><c:out value="${flight.totalSeats}" /></td>

                <td>
                   <a href="/edit/${flight.flightId}">Edit This!</a>
                   <a href="/deleteFlight/${flight.flightId}">Delete This!</a>

                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>