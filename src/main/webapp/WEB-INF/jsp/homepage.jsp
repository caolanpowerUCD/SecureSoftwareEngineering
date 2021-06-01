<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>British Airways Homepage</title>

    <!-- CSS only -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/common.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-md navbar-light bg-light fixed-top">
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

<div class="row" style="margin-top: 60px" align="center">

    <form style="margin-top: 60px" method="POST" action="/search">
        <div class="form-group col-md-4">
            <h2 class="font-weight-bold" align="center">Search all flights</h2>
            <div class="input-group col-md-4">
                <label for="origin">Origin</label>
                <input id="origin" pattern="[A-Za-z]{3}" type="text" class="form-control" style="margin-left: 40px" name="origin" placeholder="E.g. AUS,IRL,FRN">
            </div>

            <div class="input-group col-md-4">
                <label for="destination">Destination</label>
                <input id="destination" pattern="[A-Za-z]{3}" type="text" class="form-control" style="margin-left: 40px; margin-top: 10px" name="destination" placeholder="E.g. RUS, SPN, FRN">
            </div>

            <div class="input-group col-md-4">
                <label for="startDate">Start Date</label>
                <div> <input id="endDate" type="date" class="col-md-3 form-control" style="margin-left: 40px; margin-top: 15px" name="startDate" placeholder="Start Date of departure"> </div>
            </div>

            <div class="input-group col-md-4">
                <label for="endDate">End Date</label>
                <div><input id="endDate" type="date" class="col-md-3 form-control" style="margin-left: 40px; margin-top: 15px" name="endDate" placeholder="End Date of departure"></div>
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
                   <a href="/book/${flight.flightId}">Book This!</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>