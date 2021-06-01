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
        <a class="nav-item nav-link active" href="/login">Sign In</a>
        <a class="nav-item nav-link active" href="/register">Register</a>
        <a class="nav-item nav-link active" href="/">Flights</a>
        <a class="nav-item nav-link active" href="/findGuestBookings">Bookings</a>
    </div>
</nav>
<div style="margin-top: 150px" class="container">
    <div align="center" class="col-md-10">
        <h1>Booking Confirmation</h1>
    </div>
    <div align="center" class="col-md-8">
        <c:out value="Your flight ${flightId} has been booked successfully. A confirmation email has been sent to ${email}."/>
    </div>
    <div style="margin-top: 40px" align="center" class="col-md-8">
        <a href="/book/${flightId}">Click this to book another ticket on this flight?</a>
    </div>
</div>
</body>
</html>