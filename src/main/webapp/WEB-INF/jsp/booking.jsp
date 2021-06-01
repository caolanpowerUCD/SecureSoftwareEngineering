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
        <a class="nav-item nav-link active" href="/editBookings">Edit Bookings</a>
        <a class="nav-item nav-link active" href="/addBooking">Add a Bookings</a>
    </div>
</nav>

<div class="row" style="margin-top: 150px" align="center">

    <form style="margin-top: 150px" method="POST" action="/findBookings">
        <div class="form-group col-md-4">
            <label class="font-weight-bold" align="center">Search bookings by email</label>
            <div class="input-group col-md-4">
                <input type="text" class="form-control" style="margin-left: 40px" name="email" placeholder="Email">
            </div>

        </div>
        <button type="submit" class="btn btn-dark col-md-3" style="margin-left: 60px; margin-top: 20px">Search</button>
    </form>
</div>
<div align="center" style="margin-top: 150px">
    <table cellpadding="5">
        <caption style="caption-side: top" align="center">All Bookings</caption>
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
                    <a href="/edit/${flight.flightId}"/>Edit</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <table cellpadding="5">
        <caption style="caption-side: top" align="center">All Bookings</caption>
        <tr>
            <th>Booking Id</th>
            <th>flight id</th>
            <th>customer email</th>
        </tr>
        <c:forEach var="booking" items="${bookings}">
            <tr>
                <td><c:out value="${booking.bookingID}" /></td>
                <td><c:out value="${booking.flightId}" /></td>
                <td><c:out value="${booking.customerEmail}" /></td>
                <td>&nbsp;&nbsp;&nbsp;
                    <a href="/edit/booking/${booking.bookingID}"/>Edit</a>
                    <a href="/deleteBooking/${booking.bookingID}"/>Delete</a>

                </td>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>