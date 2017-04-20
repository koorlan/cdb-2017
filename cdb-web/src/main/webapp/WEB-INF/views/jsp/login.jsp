<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page session="true" %>
<html>
<head>
    <title>Login Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <!-- Bootstrap -->
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen"/>
    <link href="<c:url value="/resources/css/mdb.min.css"/>" rel="stylesheet" media="screen"/>
    <link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet" media="screen"/>
</head>

<body onload='document.loginForm.username.focus();'>

<nav class="navbar navbar-toggleable-md navbar-dark fixed-top scrolling-navbar bg-primary ">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
            <strong style="padding-left: 5px">Computer Database</strong>
        </a>
    </div>
</nav>

<section id="main">
    <div class="container">
        <h1 class="text-center">CDB Authentification</h1>
            <h3 class="text-center"><spring:message code="cdb.login.title"/></h3>
            <c:if test="${not empty error}">
                <div class="alert alert-danger col-md-4 col-md-offset-4" role="alert">

                    <div class="error"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true" style="padding-right: 8px"></span><spring:message code="cdb.login.invalid"/> </div>
                </div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="alert alert-warning" role="alert">
                    <div class="msg"><spring:message code="cdb.login.logout"/></div>
                </div>
            </c:if>
                <div class="card col-md-4 col-md-offset-4">
                    <form name='loginForm' action="<c:url value='/login' />" method='POST'>

                        <div class="md-form mt-2">
                            <i class="fa fa-user prefix"></i>
                            <input type="text" name="username" id="username" class="form-control">
                            <label for="username"><spring:message code="cdb.login.user"/></label>
                        </div>

                        <div class="md-form mt-2">
                            <i class="fa fa-lock prefix"></i>
                            <input type="password" name="password" id="password" class="form-control">
                            <label for="password"><spring:message code="cdb.login.password"/></label>
                        </div>


                        <div class="text-center mt-2">
                            <button class="btn btn-primary btn-lg btn-block" type="submit" value="submit">
                                <strong><spring:message code="cdb.login.submit"/></strong>
                            </button>
                        </div>

                        <input type="hidden" id="_csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </div>
            </div>

</section>

<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/mdb.min.js"/>"></script>

</body>
</html>