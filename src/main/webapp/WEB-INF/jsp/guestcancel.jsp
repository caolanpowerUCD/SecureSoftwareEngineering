<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Cancel a booking</title>

    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">

</head>
<body>

<nav class="navbar navbar-expand-md navbar-light bg-light fixed-top">
    <img class="nav-logo navbar-brand" src="../images/BA_logo.jpg" alt="Image of Logo" height="100" width="150">
    <div class="navbar-nav">
        <a class="nav-item nav-link active" href="/login">Sign In</a>
        <a class="nav-item nav-link active" href="/register">Register</a>
        <a class="nav-item nav-link active" href="/">Flights</a>
        <a class="nav-item nav-link active" href="/findGuestBookings">Bookings</a>
    </div>
</nav>

<div class="row" style="margin-top: 150px" align="center">

    <form style="margin-top: 150px" id="ccform" method="POST" action="/guestcancel/${FlightId}">
        <div class="form-group col-md-4">
            <label class="font-weight-bold" align="center">Enter card details to cancel booking</label>
            <div class="input-group col-md-4">
                <input type="text" class="form-control" style="margin-left: 40px" name="ccnum" placeholder="1111-2222-3333-4444">
            </div>

            <div class="input-group col-md-4">
                <input type="text" class="form-control" style="margin-left: 40px; margin-top: 10px" name="expiry_month" placeholder="January">
            </div>
            <div class="input-group col-md-4">
                <input type="text" class="form-control" style="margin-left: 40px; margin-top: 10px" name="expiry_year" placeholder="2021">
            </div>

            <div class="input-group col-md-4">
                <input type="" class="form-control" style="margin-left: 40px; margin-top: 15px" name="cvv" placeholder="123">
            </div>
            <div class="input-group col-md-4">
                <input type="" class="form-control" style="margin-left: 40px; margin-top: 15px" name="flightId" placeholder="${FlightId}">
            </div>
            <div class="input-group col-md-4">
                <input type="email" class="form-control" style="margin-left: 40px; margin-top: 10px" name="email" placeholder="Email">
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit" form="ccform" class="btn btn-dark col-md-3" style="margin-left: 60px; margin-top: 20px">Cancel</button>
    </form></div>

</body>
</html>