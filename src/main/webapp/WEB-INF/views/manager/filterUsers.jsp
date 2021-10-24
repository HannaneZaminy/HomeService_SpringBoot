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
<div  style="width: 100%;align-items: center;padding-bottom: 20px">
    <div style="width: 50%">
        <a href="/" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Home</a>
        <a href="/managerPage/managerHomePage" class="btn btn-primary btn-lg active" role="button" aria-pressed="true"> Manager Home Page</a>
        <a href="/managerLogout" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Logout</a>
    </div>
    <div style="margin-right: auto;margin-left: auto;width: 30% ;padding-bottom: 50px">
        <form:form modelAttribute="users" action="/user/searchUser" method="post">
            <h1 class="h3 mb-3 fw-normal">Filter Users</h1>

            <div class="form-floating">
                <form:input type="text" class="form-control" id="floatingInput" path="name" name="name"/>
                <form:label for="floatingInput" path="name">Name</form:label>
                <p class="text-danger">${name}</p>
            </div>
            <div class="form-floating">
                <form:input type="text" class="form-control" id="floatingPassword" path="lastName" name="lastName"/>
                <form:label for="floatingPassword" path="lastName">Last Name</form:label>
                <p class="text-danger">${lastName}</p>
            </div>
            <div class="form-floating">
                <form:input type="text" class="form-control" id="floatingPassword" path="email" name="email"/>
                <form:label for="floatingPassword" path="email">Email</form:label>
                <p class="text-danger">${email}</p>
            </div>
            <div class="form-floating">
                <form:input type="text" class="form-control" id="floatingPassword" path="rate" name="rate"/>
                <form:label for="floatingPassword" path="rate">Rate</form:label>
                <p class="text-danger">${rate}</p>
            </div>

            <div class="form-floating">
                Expert <form:checkbox path="role" value="EXPERT"></form:checkbox>
                Customer <form:checkbox path="role" value="CUSTOMER"></form:checkbox>
                <p class="text-danger">${role}</p>
            </div>
            <div>
                <c:if test="${errorAlert ne null}">
                    <blockquote class="blockquote">
                        <p style="  margin: auto;width: 100%;border: 3px solid #be081d;padding: 10px;"
                           class="mb-0">${errorAlert}</p>
                    </blockquote>
                </c:if>
            </div>
            <form:button class="w-100 btn btn-lg btn-primary" type="submit">Search</form:button>
        </form:form>
    </div>
    <div style="width: 70%;margin-right: 100px;margin-left: 200px;">
        <table class="table table-striped">
            <tr>
                <td>Name</td>
                <td>Last Name</td>
                <td>Email</td>
                <td>UserRole</td>
                <td>Registry Data</td>
                <td>Confirm Users</td>
                <td>Search a user orders</td>
            </tr>
            <c:forEach items="${usersList}" var="list">
                <tr>
                    <td>${list.name}</td>
                    <td>${list.lastName}</td>
                    <td>${list.email}</td>
                    <td>${list.userRole}</td>
                    <td>${list.date}</td>
                    <td>
                        <c:if test="${list.userRole eq 'EXPERT' && list.userSituation eq 'Pending_approval'}">
                            <a onclick="confirmUser(${list.id});" href="#" id="link">Confirm User</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${list.userRole eq 'CUSTOMER'}">
                            <a onclick="searchUser(${list.id});" href="#" id="searchUserOrders">Search orders for
                                user</a>
                        </c:if>

                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<script>
    function confirmUser(id) {
        window.location.href = "http://localhost:8080/user/confirmUser/" + id;
    }

    function searchUser(id) {
        window.location.href = "http://localhost:8080/user/searchUser/" + id;
    }

</script>

</body>
</html>


