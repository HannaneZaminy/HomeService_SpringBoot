<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <title>Home</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800,900" rel="stylesheet">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<%--    <link rel="stylesheet" href="<c:url value="/static/mainMenuStyleSheet.css/>">--%>
    <link rel="stylesheet" href="<c:url value="/static/mainMenuStyleSheet.css"/>" />
</head>
<body>

<div class="container d-flex align-items-stretch">
    <nav id="sidebar" class="img">
        <div class="p-4">
            <h1><a href="index.html" class="logo">Home Services System</a></h1>
            <ul class="list-unstyled components mb-5">
                <li class="active">
                    <a href="${pageContext.request.contextPath}/"><span class="fa fa-home mr-3"></span> Home</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/managerPage"><span class="fa fa-user mr-3"></span> Manager Page</a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/customer/register"><span class="fa fa-user mr-3"></span> Customer register</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/expert/register"><span class="fa fa-user mr-3"></span> Expert register</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/userLogin"><span class="fa fa-user mr-3"></span> Login users</a>
                </li>
            </ul>

        </div>
    </nav>

    <!-- Page Content  -->
    <div id="content" class="p-4 p-md-5 pt-5">
        <h2 class="mb-4">Home Service</h2>
        <h5>Welcome to home services system....</h5><br><br><br><br><br><br><br><br><br><br><br><br><br>
        <p>Hannane Zamini</p>
        <p>zaminyh@yahoo.com</p>
        <p>spring 1400</p>





    </div>
</div>
</body>
</html>