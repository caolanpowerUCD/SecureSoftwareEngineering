<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Confirm Reservation</title>

    <!-- CSS only -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/common.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
    <script src="../../js/password_validation.js"></script>
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
    </div>
</nav>

<div class="container" style="margin-top: 100px" align="center">
    <form:form method="POST" modelAttribute="creditCardForm" class="form-signin" action="/confirmReservation">
        <h2 class="form-signin-heading">Confirm your Reservation!</h2>

        <spring:bind path="card_number">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="card_number">Credit Card Number</label>
                <form:input id="card_number" type="text" path="card_number" class = "form-control" placeholder="1111-2222-3333-4444" />
                <form:errors path="card_number" ></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="card_cvv">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="card_cvv">Card CVV</label>
                <form:input id="card_cvv" type="text" path="card_cvv" class = "form-control" placeholder="123" />
                <form:errors path="card_cvv" ></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="card_expiry_month">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="card_expiry_month">Expiration Month</label>
                <form:input id="card_expiry_month" type="text" path="card_expiry_month" class = "form-control" placeholder="E.g. 5" />
                <form:errors path="card_expiry_month" ></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="card_expiry_year">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="card_expiry_year">Expiration Year</label>
                <form:input id="card_expiry_year" type="text" path="card_expiry_year" class="form-control" placeholder="E.g. 2023"/>
                <form:errors path="card_expiry_year" ></form:errors>
                    <br>  
            </div>
        </spring:bind>
        <br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit" style="margin-left:1.5cm">Update</button>
    </form:form>
</div>

</body>
</html>