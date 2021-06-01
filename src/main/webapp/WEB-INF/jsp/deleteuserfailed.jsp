<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Delete your account</title>

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
    </div>
</nav>

<div class="row" style="margin-top: 150px" align="center">

    <form id="deleteForm" style="margin-top: 150px" method="POST" action="/delete">
        <div class="form-group col-md-4">
            <label class="font-weight-bold" align="center">Enter Email and Password</label>
            <div class="input-group col-md-4">
                <input type="email" class="form-control" style="margin-left: 40px" name="email" placeholder="john@example.com">
            </div>

            <div class="input-group col-md-4">
                <input type="password" class="form-control" style="margin-left: 40px; margin-top: 10px" name="password" placeholder="Password">
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit" form="deleteForm" class="btn btn-dark col-md-3" style="margin-left: 60px; margin-top: 20px">Delete account</button>
    </form></div>

<script>alert("Username or password is incorrect. Please try again")</script>
</body>
</html>