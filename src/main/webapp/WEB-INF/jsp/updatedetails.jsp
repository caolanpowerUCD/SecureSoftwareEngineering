<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Update Personal Details</title>

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

<div class="container" style="margin-top: 100px;">
    <form:form method="POST" modelAttribute="updateUserForm" class="form-signin">
        <h2 class="form-signin-heading">Update your account</h2>

        <spring:bind path="first_name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="first_name" class = "form-control" placeholder="Name" />
                <form:errors path="first_name" ></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="last_name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="last_name" class = "form-control" placeholder="Surname" />
                <form:errors path="last_name" ></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="email">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="email" class = "form-control" placeholder="Email" />
                <form:errors path="email" ></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="address">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="address" class="form-control" placeholder="123 Main Street" id="address" onkeypress="clear()"></form:input>
                <br>
            </div>
        </spring:bind>

        <spring:bind path="phone_number">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="phone_number" class = "form-control" placeholder="E.g. 0871132476" />
                <form:errors path="phone_number" ></form:errors>
            </div>
        </spring:bind>
        <br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-dark col-md-3" type="submit" style="margin-left: 60px; margin-top: 25px">Update Details</button>
    </form:form>
</div>
</body>
</html>