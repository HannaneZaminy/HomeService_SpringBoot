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
    <title>filter</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
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
<body class="text-center" style="height: auto">
<div style="width: 100%;align-items: center;padding-bottom: 20px">
    <div style="width: 50%">
        <a href="/" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Home</a>
        <a href="/managerPage/managerHomePage" class="btn btn-primary btn-lg active" role="button" aria-pressed="true"> Manager Home Page</a>
        <a href="/managerLogout" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Logout</a>
    </div>
    <div style="margin-right: auto;margin-left: auto;width: 30% ;padding-bottom: 50px">
        <form name="myForm">
            <h1 class="h3 mb-3 fw-normal">Filter Orders</h1>
            <select id="situations">
                <option value="NONE" label="select order status"/>
                <c:forEach items="${situationList}" var="list">
                    <option value="${list}" name="situations">${list}</option>
                </c:forEach>
            </select>
            <div class="form-floating">
                <input type="date" class="form-control" id="startDate" name="startDate"/>
                <label for="startDate">Start Date</label>
                <%--            <p class="text-danger">${startDate}</p>--%>
            </div>
            <div class="form-floating">
                <input type="date" class="form-control" id="endDate" name="endDate"/>
                <label for="endDate">End Date</label>
                <%--            <p class="text-danger">${endDate}</p>--%>
            </div>
            <div class="form-floating">
                <input type="number" class="form-control" id="maxPrice" name="maxPrice"/>
                <label for="maxPrice">Max Price</label>
                <%--            <p class="text-danger">${endDate}</p>--%>
            </div>
            <div class="form-floating">
                <input type="number" class="form-control" id="minPrice" name="minPrice"/>
                <label for="minPrice">Min Price</label>
                <%--            <p class="text-danger">${endDate}</p>--%>
            </div>

            <div class="form-floating">

            </div>
            <div>
                <c:if test="${errorAlert ne null}">
                    <blockquote class="blockquote">
                        <p style="  margin: auto;width: 100%;border: 3px solid #be081d;padding: 10px;"
                           class="mb-0">${errorAlert}</p>
                    </blockquote>
                </c:if>
            </div>
        </form>
        <button class="w-100 btn btn-lg btn-primary" type="submit" onclick="searchOrders()">Search</button>
    </div>
    <div  style="width: 70%;margin-right: 100px;margin-left: 100px;">
        <table class="table table-striped" id="showOrders">
            <thead>
            <tr>
                <td>Job description</td>
                <td>Proposed price</td>
                <td>Situation</td>
                <td>Date Of Work</td>
                <td>Date Of Create Order</td>
                <td colspan="2">Address</td>
                <td colspan="2">Expert Info</td>
                <td colspan="2">Customer Info</td>
            </tr>
            </thead>
        </table>
    </div>
</div>
<script>
    function searchOrders() {
        // $("#showOrders tbody tr").remove();
        console.log("successssss")
        var situation = document.getElementById("situations").value;
        var startDate = document.getElementById("startDate").value;
        var endDate = document.getElementById("endDate").value;
        var maxPrice = document.getElementById("maxPrice").value;
        var minPrice = document.getElementById("minPrice").value;
        var json = '{"situation":"' + situation + '", "maxOfferPrice":"' + maxPrice +
            '", "minOfferPrice":"' + minPrice + '", "startDate": "' + startDate + '", "endDate": "' + endDate + '"}';
        console.log(json)
        $.ajax({
            method: "POST",
            contentType: "application/json",
            data: json,
            dataType: 'json',
            url: "/managerRestController/filterUserOrders",
            success: function (result) {
                console.log(result);
                var row = "";
                for (const entry of result) {
                    console.log(entry)
                    row += "<tr><td rowspan='4'>" + entry.jobDescription + "</td>" +
                        "<td rowspan='4'>" + entry.proposedPrice + "</td>" +
                        "<td rowspan='4'>" + entry.situation + "</td>" +
                        "<td rowspan='4'>" + entry.dateOfWork + "</td>" +
                        "<td rowspan='4'>" + entry.dateOfOrderRegistration + "</td>" +
                        "<td> Provine </td>" +
                        "<td>" + entry.address.province + "</td>" +
                        "<td> Name </td>" +
                        "<td>" + (!entry.expert ? "" : entry.expert.name) + "</td>" +
                        "<td>Name </td>" +
                        "<td>" + entry.customer.name + "</td></tr>" +
                        "<tr><td>City</td>" +
                        "<td>" + entry.address.city + "</td>" +
                        "<td>Last Name </td>" +
                        "<td>" + (!entry.expert ? "" : entry.expert.lastName) + "</td>" +
                        "<td> Last Name</td>" +
                        "<td>" + entry.customer.lastName + "</td></tr>" +
                        "<tr><td>Address</td>" +
                        "<td>" + entry.address.neighbourhood + "</td>" +
                        "<td>Email </td>" +
                        "<td>" + (!entry.expert ? "" : entry.expert.email) + "</td>" +
                        "<td> Email </td>" +
                        "<td>" + entry.customer.email + "</td></tr>" +
                        "<tr><td>Plaque</td>" +
                        "<td>" + entry.address.plaque + "</td>" +
                        "<td>Situation</td>" +
                        "<td>" + (!entry.expert ? "" : entry.expert.userSituation) + "</td>" +
                        "<td>Situation</td>" +
                        "<td>" + entry.customer.userSituation + "</td></tr>";

                }
                $("#showOrders").append("<tbody>" + row + "</tbody>");
            }
        });
    }
</script>

</body>
</html>


