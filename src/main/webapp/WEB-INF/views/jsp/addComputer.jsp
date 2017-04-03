<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/resources/css/bootstrap.min.css"/>"
	rel="stylesheet" media="screen" />
<link href="<c:url value="/resources/css/font-awesome.css"/>"
	rel="stylesheet" media="screen" />
<link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet"
	media="screen" />
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="cdb.add.title"/></h1>
                    <form:form commandName="computer">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message code="cdb.computer.name"/></label> <input
									name="name" type="text" class="form-control" id="computerName"
									>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="cdb.computer.introduced"/></label> <input
									name="introduced" type="date" class="form-control"
									id="introduced">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="cdb.computer.discontinued"/></label> <input
									name="discontinued" type="date" class="form-control"
									id="discontinued">
							</div>

							<div class="form-group">
								<form:select path="company.id">
									<form:option value="0" label="--Please Select"/>
									<form:options items="${companies}" itemValue="id" itemLabel="name" />
								</form:select>

							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="cdb.form.edit"/>" class="btn btn-primary">
							or <a href="database" class="btn btn-default"><spring:message code="cdb.form.cancel"/></a>
						</div>
					</form:form>
                </div>
            </div>
        </div>
    </section>
    <script src="./js/jquery.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/addComputer.js"></script>
	
</body>
</html>