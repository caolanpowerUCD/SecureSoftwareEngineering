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
    <div class="row" style="margin-top:200px" align="center">
        <div class="col-md-7">
            <form style="margin-top: 40px" method="POST" action="/bookFlight">
                <div class="form-group col-md-4">
                    <label align="center" class="font-weight-bold" style="margin-left: 40%">Please Enter Your Details:</label>
                    <div align="center" style="color:#FF0000" class="col-md-8">
                        <c:out value="${error}"/>
                    </div>
                    <div class="input-group col-md-6">
                        <label class="font-weight-bold">First Name:</label>
                        <input type="text" pattern="[a-zA-Z]{2-32}" style="margin-left: 40px" name="first_name" >
                    </div>
                    <div class="input-group col-md-6">
                        <label class="font-weight-bold">Surname:</label>
                        <input type="text" pattern="[a-zA-Z]{2-32}" style="margin-left: 40px; margin-top: 10px" name="last_name">
                    </div>
                    <div class="input-group col-md-6">
                        <label class="font-weight-bold">Email:</label>
                        <input type="email" style="margin-left: 40px; margin-top: 15px" name="email">
                    </div>
                    <div class="input-group col-md-6">
                        <label class="font-weight-bold">Address of Residence</label>
                        <input type="address" style="margin-left: 40px; margin-top: 15px" name="address">
                    </div>
                    <div class="input-group col-md-6">
                        <label class="font-weight-bold">Phone Number</label>
                        <input type="tel" pattern="[0-9]{10}" style="margin-left: 40px; margin-top: 15px" name="number"  placeholder="e.g. 0879987656">
                    </div>
                    <div>
                        <input type="hidden" name="flightId" value="${flightData.flightId}" >
                    </div>
                    <div style="margin-top: 50px" class="row">
                        <div class="col-md-6 mb-3">
                            <label class="font-weight-bold">Credit Card Number</label>
                            <input type="text" pattern="^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\d{3})\d{11})$" style="margin-left: 40px; margin-top: 15px" name="ccnum" placeholder="e.g. 4319767567678765">
                        </div>
                        <div class="col-md-3 mb-3">
                            <label class="font-weight-bold">Expiry Month</label>
                            <input type="text" pattern="^(0?[1-9]|1[012])$" style="margin-left: 40px; margin-top: 15px" name="expiry_month" placeholder="E.g. 1,2,...,12">
                        </div>
                        <div class="col-md-3 mb-3">
                            <label class="font-weight-bold">Expiry Year</label>
                            <input type="text" pattern="^20(1[1-9]|[2-9][0-9])$"" style="margin-left: 40px; margin-top: 15px" name="expiry_year" placeholder="E.g. 2021">
                        </div>
                    </div>
                    <div style="margin-top: 5px" class="row">
                        <div class="col-md-2">
                            <label class="font-weight-bold">CVV</label>
                            <input type="text" pattern="[0-9]{3}" style="margin-left: 40px; margin-top: 15px" name="cvv" placeholder="123">
                        </div>
                    </div>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-dark col-md-3" style="margin-left: 60px; margin-top: 25px">Book Flights!</button>
            </form>
        </div>
        <div class="col-md-5">
            <table style="margin-top: 10px" align="center" border="0.2" cellpadding="5">
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
        </div>
    </div>
</div>
</body>
</html>
