<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Enter details to log in</title>

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

<div class="row" style="margin-top: 150px" align="center">

    <form style="margin-top: 150px" method="POST" action="/login">
        <div class="form-group col-md-4">
            <label class="font-weight-bold" align="center">Enter Login Details</label>
            <div class="input-group col-md-4">
                <input type="text" class="form-control" style="margin-left: 40px" name="username" placeholder="username">
            </div>

            <div class="input-group col-md-4">
                <input type="password" class="form-control" style="margin-left: 40px; margin-top: 10px" name="password" placeholder="Password">
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-dark col-md-3" style="margin-left: 60px; margin-top: 20px">Sign In</button>
    </form></div>

<script>alert("Username or password is incorrect. Please try again")</script>
</body>
</html>