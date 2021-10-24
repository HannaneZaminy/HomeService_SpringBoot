<!doctype html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.83.1">
    <title>show_offers</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/sign-in/">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">


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


    <link href="/static/loginPageStyleSheet.css" rel="stylesheet">
</head>
<body class="text-center" style="height: auto">
<div style="width: 100%;align-items: center;padding-bottom: 20px">
    <div style="width: 80%;padding-bottom: 50px">
        <a href="/" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Home</a>
        <a href="/customer/customerHomePage" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">
            Cutstomer Home Page</a>
        <a href="/userLogout" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Logout</a>
    </div>
    <div style="width: 100%;margin-right: 20px;margin-left: 20px;">
        <form:form method="get" action="/customer/showOffers">
            <table class="table table-striped">
                <tr>
                    <td>Duration Of Work</td>
                    <td>Offer Price</td>
                    <td>Start Time</td>
                    <td colspan="2">Expert Info</td>
                    <td>Select Offer</td>
                </tr>
                <c:forEach items="${offersList}" var="list">
                    <tr>
                        <td rowspan="3" >${list.durationOfWork}</td>
                        <td rowspan="3" >${list.offerPrice}</td>
                        <td rowspan="3" >${list.startTime}</td>
                        <td>Name</td>
                        <td >${list.expert.name}</td>
                        <td>
                            <a onclick="selectOffer(${list.id});" href="#" id="link">select offer</a>
                        </td>

                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td>${list.expert.lastName}</td>
                    </tr>
                    <tr>
                        <td>Email</td>
                        <td>${list.expert.email}</td>
                    </tr>
                </c:forEach>

            </table>
        </form:form>
    </div>
</div>
<script>
    function selectOffer(id) {
        console.log("hello" + id)
        window.location.href = "http://localhost:8080/offer/selectOffer/" + id;
    }
</script>
</body>
</html>


