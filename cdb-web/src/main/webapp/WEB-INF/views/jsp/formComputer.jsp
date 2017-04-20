<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen"/>
    <link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet" media="screen"/>
    <link href="<c:url value="/resources/css/mdb.min.css"/>" rel="stylesheet" media="screen"/>
    <link href="<c:url value="/resources/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css"/>"
          rel="stylesheet"/>

</head>
<body>

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
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <c:choose>
                    <c:when test="${computerForm['new']}">
                        <h1 class="text-center">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> <spring:message
                                code="cdb.add.title"/>
                        </h1>
                    </c:when>
                    <c:otherwise>
                        <h1 class="text-center">
                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span> <spring:message
                                code="cdb.edit.title"/>
                        </h1>
                    </c:otherwise>
                </c:choose>
                <br/>
                <div class="card col-md-10 col-md-offset-1">
                    <spring:url value="/computers" var="computerActionUrl"/>
                    <form:form class="form-horizontal" method="post" modelAttribute="computerForm" action="/computers">
                        <form:hidden path="id"/>
                        <spring:bind path="name">
                            <div class="md-form mt-2 ${status.error ? 'has-error' : ''}">
                                <i class="fa fa-tags prefix"></i>
                                <label for="name"><spring:message code="cdb.form.name"/></label>
                                <form:input path="name" type="text" class="form-control mt-1" id="name"/>
                            </div>
                        </spring:bind>

                        <spring:bind path="introduced">
                            <div class="md-form mt-2 input-group date ${status.error ? 'has-error' : ''}"
                                 id='date_introduced'>
                                <i class="fa fa-sign-in prefix"></i>
                                <label for="introduced"><spring:message code="cdb.form.introduced"/></label>
                                <form:input path="introduced" type="text" class="form-control mt-1" id="introduced"
                                            style="margin-left:50px;"/>
                                <span class="input-group-addon">
                                             <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                            </div>
                        </spring:bind>

                        <spring:bind path="discontinued">
                            <div class="md-form mt-2 input-group date ${status.error ? 'has-error' : ''}"
                                 id='date_discontinued'>
                                <i class="fa fa-sign-out prefix"></i>
                                <label for="discontinued"><spring:message code="cdb.form.discontinued"/></label>
                                <form:input path="discontinued" type="text" class="form-control mt-1" id="discontinued"
                                            style="margin-left:50px;"/>
                                <span class="input-group-addon">
                                             <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                            </div>
                        </spring:bind>

                        <spring:bind path="company">
                            <div class="md-form ${status.error ? 'has-error' : ''}">
                                <form:select path="company.id" class="form-control" id="scompany">
                                    <form:option value="0" label=""/>
                                    <form:options items="${companies}" itemValue="id" itemLabel="name"/>
                                    <c:forEach items="${companies}" var="item" varStatus="count">
                                        <option value="${count.index}" style="margin-left: 20px;">${item.name}</option>
                                    </c:forEach>
                                </form:select>
                                <form:errors path="company" class="control-label"/>
                            </div>
                        </spring:bind>

                        <spring:hasBindErrors name="computerForm">
                            <div class="alert alert-danger alert-dismissible">
                                <c:forEach var="error" items="${errors.allErrors}">
                                    <div id="errors" class="errors">
                                        <p class="col-md-offset-1">* <spring:message message="${error}"/></p>
                                    </div>
                                </c:forEach>
                            </div>
                        </spring:hasBindErrors>

                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <a href="/">
                                    <button type="button" class="btn btn-blue-grey pull-right">
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                        <strong><spring:message code="cdb.form.cancel"/></strong></button>
                                </a>
                                <c:choose>
                                    <c:when test="${computerForm['new']}">
                                        <button type="submit" class="btn btn-primary pull-right">
                                            <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                                            <strong> <spring:message code="cdb.form.add"/></strong>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" class="btn btn-primary pull-right">
                                            <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                                            <strong><spring:message code="cdb.form.update"/></strong>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                    </form:form>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/addComputer.js"/>"></script>
<script src="<c:url value="/resources/js/mdb.min.js"/>"></script>

<script type="text/javascript" src="<c:url value="/resources/bower_components/moment/min/moment.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/bower_components/moment/locale/fr.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="/resources/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"/>"></script>
<script type="text/javascript">
    $(function () {
        $('#date_introduced').datetimepicker({
            locale: '${locale}',
            format: '${date_format}'
        });
    });
    $(function () {
        $('#date_discontinued').datetimepicker({
            locale: '${locale}',
            format: '${date_format}'
        });
    });
</script>

</body>
</html>