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
    <title>show_order</title>
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
        <a href="/expert/expertHomePage" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Expert Home Page</a>
        <a href="/userLogout" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Logout</a>
    </div>
    <div style="width: 100%;margin-right: 20px;margin-left: 20px;">
        <form action="/expert/showOrdersToClickEndOfWork" method="get" id="serviceForm">
            <table class="table table-striped">
                <tr>
                    <td>Date Of Work</td>
                    <td>Situation</td>
                    <td colspan="2">Sub Service</td>
                    <td colspan="2">Customer Info</td>
                    <td colspan="2">Address</td>
                    <td colspan="2">Start Work</td>
                    <td colspan="2">End Of Work</td>
                    <td colspan="2">Confirm Payment</td>
                </tr>
                <c:forEach items="${ordersList}" var="list">
                    <tr>
                        <td rowspan="4">${list.dateOfWork}</td>
                        <td rowspan="4">${list.situation}</td>
                        <td rowspan="2">Name</td>
                        <td rowspan="2">${list.subService.name}</td>
                        <td rowspan="2">Name</td>
                        <td rowspan="2">${list.customer.name}</td>
                        <td>Province</td>
                        <td>${list.address.province}</td>
                        <td rowspan="4">
                            <c:if test="${list.situation eq 'Waiting_for_expert_to_come'}">
                                <a onclick="startWork(${list.id});" href="#" id="link">click</a>
                            </c:if>
                        </td>
                        <td rowspan="4">
                            <c:if test="${list.situation eq 'STARTED'}">
                                <a onclick="endOfWork(${list.id});" href="#" id="endOfWorkLink">click</a>
                            </c:if>
                        </td>
                        <td rowspan="4">
                            <c:if test="${list.situation eq 'paid'}">
                                <a onclick="confirmPay(${list.id});" href="#" id="confirmPay">click</a>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>City</td>
                        <td>${list.address.city}</td>
                    </tr>
                    <tr>
                        <td rowspan="2">Base Price</td>
                        <td rowspan="2">${list.subService.basePrice}</td>
                        <td rowspan="2">Last Name</td>
                        <td rowspan="2">${list.customer.lastName}</td>
                        <td>Address</td>
                        <td>${list.address.neighbourhood}</td>
                    </tr>
                    <tr>
                        <td>Plaque</td>
                        <td>${list.address.plaque}</td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </div>
</div>
<script>
    function startWork(id) {
        window.location.href = "http://localhost:8080/order/startWork/" + id;
    }

    function endOfWork(id) {
        window.location.href = "http://localhost:8080/order/endOfWork/" + id;
    }

    function confirmPay(id) {
        window.location.href = "http://localhost:8080/order/confirmPay/" + id;
    }
</script>
</body>
</html>


