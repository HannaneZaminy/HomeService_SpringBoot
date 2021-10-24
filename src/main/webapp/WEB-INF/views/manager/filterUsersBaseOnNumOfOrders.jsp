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
    <div style="margin-right: auto;margin-left: auto;width: 30%;padding-bottom: 50px">
        <form name="myForm">
            <h1 class="h3 mb-3 fw-normal">Filter User By Orders</h1>

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
                <input type="number" class="form-control" id="maxNumOffer" name="maxNumOffer"/>
                <label for="maxNumOffer">Max Number Of Offer</label>
                <%--            <p class="text-danger">${endDate}</p>--%>
            </div>
            <div class="form-floating">
                <input type="number" class="form-control" id="minNumOffer" name="maxNumOffer"/>
                <label for="maxNumOffer">Min Number Of Offer</label>
                <%--            <p class="text-danger">${endDate}</p>--%>
            </div>
            <div class="form-floating">
                <input type="number" class="form-control" id="maxNumOrder" name="maxNumOrder"/>
                <label for="maxNumOrder">Max Number Of Order</label>
                <%--            <p class="text-danger">${endDate}</p>--%>
            </div>
            <div class="form-floating">
                <input type="number" class="form-control" id="minNumOrder" name="minNumOrder"/>
                <label for="minNumOrder">Min Number Of Order</label>
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
    <div style="width: 70%;margin-right: 100px;margin-left: 130px;">
        <table class="table table-striped" id="showOrders" style="width: 100%">
            <thead>
            <tr>
                <td>Name</td>
                <td>Last Name</td>
                <td>Status</td>
                <td>Email</td>
                <td>Registry Data</td>
            </tr>
            </thead>
        </table>
    </div>
<%--    <div style="width: 100%;height: auto">--%>
<%--        <footer class="footer mt-auto py-3 bg-light">--%>
<%--            <div class="container" style="width: 5%">--%>
<%--                <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">--%>
<%--                    <li><a href="/" class="nav-link px-2 link-secondary">Home</a></li>--%>
<%--                    <li><a href="/managerPage/managerHomePage" class="nav-link px-2 link-secondary">ManagerHomePage Home--%>
<%--                        Page</a></li>--%>
<%--                    <li><a href="/managerLogout" class="nav-link px-2 link-secondary">log out</a></li>--%>
<%--                </ul>--%>
<%--            </div>--%>
<%--        </footer>--%>
<%--    </div>--%>
</div>
<script>
    function searchOrders() {
        $("#showOrders tbody tr").remove();
        console.log("successssss")
        var startDate = document.getElementById("startDate").value;
        var endDate = document.getElementById("endDate").value;
        var maxOffer = document.getElementById("maxNumOffer").value;
        var minOffer = document.getElementById("minNumOffer").value;
        var maxOrder = document.getElementById("maxNumOrder").value;
        var minOrder = document.getElementById("minNumOrder").value;
        var json = '{"maxNumberOfOrders":"' + maxOrder + '", "minNumberOfOrders":"' + minOrder +
            '", "maxNumberOfOffers":"' + maxOffer + '", "startDate": "' + startDate + '", "endDate": "' + endDate + '", "minNumberOfOffers": "' + minOffer + '"}';
        console.log(json)
        $.ajax({
            method: "POST",
            contentType: "application/json",
            data: json,
            dataType: 'json',
            url: "/managerRestController/filterUsers",
            success: function (result) {
                console.log(result);
                var row = "";
                for (const entry of result) {
                    console.log(entry)
                    row += "<tr><td >" + entry.name + "</td>" +
                        "<td >" + entry.lastName + "</td>" +
                        "<td >" + entry.userSituation + "</td>" +
                        "<td >" + entry.email + "</td>" +
                        "<td >" + entry.date + "</td></tr>";

                }
                $("#showOrders").append("<tbody>" + row + "</tbody>");
            }
        });
    }
</script>

</body>
</html>


