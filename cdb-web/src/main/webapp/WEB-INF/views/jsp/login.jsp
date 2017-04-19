<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<html>
<head>
    <title>Login Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <!-- Bootstrap -->
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.3.0/css/mdb.min.css" rel="stylesheet">
    <link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet" media="screen"/>
</head>

<body onload='document.loginForm.username.focus();'>

<nav class="navbar navbar-toggleable-md navbar-dark fixed-top scrolling-navbar bg-primary " style="text-align:center">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
            <strong>Retour</strong>
        </a>
    </div>
</nav>

<section id="main">
    <div class="container text-center">

        <h1>CDB Authentification</h1>

        <div id="login-box">
            <h3>Login with Username and Password</h3>

            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    <div class="error">${error}</div>
                </div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="alert alert-warning" role="alert">
                    <div class="msg">${msg}</div>
                </div>
            </c:if>
            <div class="card col-md-6 col-md-offset-3">
                <div class="card-block center-block">
                    <form name='loginForm' action="<c:url value='/login' />" method='POST'>

                        <div class="md-form mt-1">
                            <input id="username" type='text' name='username' class="form-control">
                            <label for="username" class="">
                                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                User
                            </label>
                        </div>

                        <div class="md-form">
                            <input id="password" type='password' name='password' class="form-control">
                            <label for="password" class="">
                                <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
                                Password
                            </label>
                        </div>

                        <div class="text-center">
                            <button class="btn btn-primary btn-lg btn-block" type="submit" value="submit">
                                <strong>Login</strong>
                            </button>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/bootstrap.min.js"/>"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.3.0/js/mdb.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>