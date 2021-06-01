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
<div class="contentContainer">
    <table style="margin-top: 150px" align="center" border="0.2" cellpadding="5">
        <caption align="center" style="caption-side: top"><h2 align="center">Flight Details</h2></caption>
        <tr>
            <th>Flight Id</th>
            <th>Origin</th>
            <th>Destination</th>
            <th>Departure Date</th>
            <th>Available Seats</th>
        </tr>
        <tr>
            <td><c:out value="${flightData.flightId}" /></td>
            <td><c:out value="${flightData.flightOrigin}" /></td>
            <td><c:out value="${flightData.flightDestination}" /></td>
            <td><c:out value="${flightData.departureDate}" /></td>
            <td><c:out value="${availableSeats}" /></td>
        </tr>
    </table>

    <div class="row" style="margin-top:0px" align="center">
        <form style="margin-top: 10px" method="POST" action="/bookFlight">
            <div class="form-group col-md-4">
                <label align="center" class="font-weight-bold" style="margin-left: 40%">Please Enter Your Details:</label>
                <div class="input-group col-md-4">
                    <input type="hidden" style="margin-left: 40px" name="first_name" value="${userData.first_name}">
                </div>
                <div class="input-group col-md-4">
                    <input type="hidden" style="margin-left: 40px; margin-top: 10px" name="last_name" value="${userData.last_name}">
                </div>
                <div class="input-group col-md-4">
                    <input type="hidden" style="margin-left: 40px; margin-top: 15px" name="email" value="${userData.email}">
                </div>
                <div class="input-group col-md-4">
                    <input type="hidden" style="margin-left: 40px; margin-top: 15px" name="address" value="${userData.address}">
                </div>
                <div>
                    <input type="hidden" style="margin-left: 40px; margin-top: 15px" name="number" value="${userData.phone_number}">
                </div>
                <div>
                    <input type="text" class="form-control" style="margin-left: 40px; margin-top: 10px" name="flightId" value="${flightData.flightId}">
                </div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-dark col-md-3" style="margin-left: 60px; margin-top: 25px">Confirm Booking with the following information:</button>
        </form>
    </div>
    <table style="margin-top: 50px" align="center" border="0.2" cellpadding="5">
        <caption align="center" style="caption-side: top"><h2 align="center">Your Details:</h2></caption>
        <tr>
            <th>Name</th>
            <th>Email Address</th>
            <th>Tel. Number</th>
            <th>Address</th>
        </tr>
        <tr>
            <td><c:out value="${userData.first_name} ${userData.last_name}" /></td>
            <td><c:out value="${userData.email}" /></td>
            <td><c:out value="${userData.phone_number}" /></td>
            <td><c:out value="${userData.address}" /></td>
            <td><a href="/book/${flightData.flightId}">Book a guest ticket?</a></td>
        </tr>
    </table>
</div>
</body>
</html>
