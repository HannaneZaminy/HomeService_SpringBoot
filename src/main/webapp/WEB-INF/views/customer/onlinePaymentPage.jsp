<!doctype html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.83.1">
    <title>online_payment</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/sign-in/">


    <!-- Bootstrap core CSS -->
    <link href="/static/bootstrap.min.css" rel="stylesheet">

    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>


    <!-- Custom styles for this template -->
    <link href="/static/loginPageStyleSheet.css" rel="stylesheet">
</head>
<body class="text-center">

<main class="form-signin">
    <form:form modelAttribute="onlinePayment" method="post" action="/customer/onlinePayment">
        <h1 class="h3 mb-3 fw-normal">Online Payment</h1>

        <div id="clockdiv"></div>

        <div class="form-floating">
            <form:input type="text" class="form-control" id="floatingInput" path="creditNumber" name="creditNumber"/>
            <form:label for="floatingInput" path="creditNumber">Credit Number</form:label>
            <p class="text-danger">${creditNumber}</p>
        </div>
        <div class="form-floating">
            <form:input type="text" class="form-control" id="floatingPassword" path="password" name="password"/>
            <form:label for="floatingPassword" path="password">Password</form:label>
            <p class="text-danger">${password}</p>
        </div>
        <div class="form-floating">
            <form:input type="text" class="form-control" id="floatingPassword" path="cvv2" name="cvv2"/>
            <form:label for="floatingPassword" path="cvv2">CVV2</form:label>
            <p class="text-danger">${cvv2}</p>
        </div>
        <div class="form-floating">
            <form:input type="text" class="form-control" id="floatingPassword" path="expireDate" name="expireDate"/>
            <form:label for="floatingPassword" path="expireDate">Expire Date</form:label>
            <p class="text-danger">${expireDate}</p>
        </div>
        <div class="form-floating">
            <img src="${pageContext.request.contextPath }/captcha">
            <br>
            <form:input type="text" name="captcha"  style="margin-top: 5px;" path="captcha" />
            <p class="text-danger">${captcha}</p>
        </div>
        <div>
            <c:if test="${errorAlert ne null}">
                <blockquote class="blockquote">
                    <p style="  margin: auto;width: 100%;border: 3px solid #bc091e;padding: 10px;"
                       class="mb-0">${errorAlert}</p>
                </blockquote>
            </c:if>
        </div>
        <form:button class="w-100 btn btn-lg btn-primary" type="submit">Pay</form:button>
    </form:form>
</main>
<script>
    var time_in_minutes = 10;
    var current_time = Date.parse(new Date());
    var deadline = new Date(current_time + time_in_minutes*60*1000);


    function time_remaining(endtime){
        var t = Date.parse(endtime) - Date.parse(new Date());
        var seconds = Math.floor( (t/1000) % 60 );
        var minutes = Math.floor( (t/1000/60) % 60 );
        var hours = Math.floor( (t/(1000*60*60)) % 24 );
        var days = Math.floor( t/(1000*60*60*24) );
        return {'total':t, 'days':days, 'hours':hours, 'minutes':minutes, 'seconds':seconds};
    }
    function run_clock(id,endtime){
        var clock = document.getElementById(id);
        function update_clock(){
            var t = time_remaining(endtime);
            clock.innerHTML = 'minutes: '+t.minutes+'<br>seconds: '+t.seconds;
            if(t.total<=0){
                alert("Time Out...Please Login Again !")
                window.location.href = "http://localhost:8739/customer/login" ;
                clearInterval(timeinterval);
            }
        }
        update_clock(); // run function once at first to avoid delay
        var timeinterval = setInterval(update_clock,1000);
    }
    run_clock('clockdiv',deadline);
</script>
<footer class="footer mt-auto py-3 bg-light">
    <div class="container">
        <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
            <li><a href="/" class="nav-link px-2 link-secondary">Home</a></li>
            <li><a href="/customer/customerHomePage" class="nav-link px-2 link-secondary">Customer Home Page</a></li>
            <li><a href="/userLogout" class="nav-link px-2 link-secondary">Log out</a></li>
        </ul>
    </div>
</footer>
</body>
</html>


