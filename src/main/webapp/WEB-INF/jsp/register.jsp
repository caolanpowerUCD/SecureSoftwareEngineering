<%@page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Create an account</title>

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
            <a class="nav-item nav-link active" href="/login">Sign In</a>
            <a class="nav-item nav-link active" href="/register">Register</a>
            <a class="nav-item nav-link active" href="/">Flights</a>
            <a class="nav-item nav-link active" href="/findGuestBookings">Bookings</a>
        </div>
    </nav>

<div class="container" style="margin-top: 100px;">

    <script>
        function myFunction() {
            var x = document.getElementById("txtPassword");
            if (x.type === "password") {
                x.type = "text";
            } else {
                x.type = "password";
            }
            clear()
        }
    </script>
    <script>
        function clear() {
            document.getElementById("perrors").innerHTML = '';

        }
    </script>

    <form:form method="POST" modelAttribute="userForm" class="form-signin">
        <h2 class="form-signin-heading">Create your account</h2>

        <spring:bind path="first_name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="first_name" class = "form-control" placeholder="First Name" />
                <form:errors path="first_name" ></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="last_name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="last_name" class = "form-control" placeholder="Last Name" />
                <form:errors path="last_name" ></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="email">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="email" class = "form-control" placeholder="Email" />
                <form:errors path="email" ></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">

                <form:input type="text" path="username" class="form-control" placeholder="Username"  onkeypress="clear()"></form:input>
                <form:errors path="username" ></form:errors>
                <br>

            </div>
        </spring:bind>

        <spring:bind path="address">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="address" class="form-control" placeholder="Address" id="address" onkeypress="clear()"></form:input>
                <br>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">

                <form:input type="password" path="password" class="form-control" placeholder="Password" id="txtPassword" onkeyup="CheckPasswordStrength(this.value)"></form:input>

                <input type="checkbox" onclick="myFunction()">Show Password
                <br>

            </div>
        </spring:bind>

        <spring:bind path="passwordConfirm">
            <div class="form-group ${status.error ? 'has-error' : ''}">

                <form:input type="password" path="passwordConfirm" class="form-control" placeholder="Confirm your password" onkeydown="clear()"></form:input>
                <br>

                <form:errors path="passwordConfirm" id="perrors"></form:errors>

            </div>
        </spring:bind>
        
        <br>
        <span  id="password_strength"></span>
        <br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit" style="margin-left:1.5cm">Submit</button>
    </form:form>

</div>


</body>
</html>