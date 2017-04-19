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
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<c:choose>
						<c:when test="${computerForm['new']}">
							<h1>
								<spring:message code="cdb.add.title" />
							</h1>
						</c:when>
						<c:otherwise>
							<h1>
								<spring:message code="cdb.edit.title" />
							</h1>
						</c:otherwise>
					</c:choose>
					<br />

					<spring:url value="/computers" var="computerActionUrl" />
					<form:form class="form-horizontal" method="post"
						modelAttribute="computerForm" action="/computers">

						<form:hidden path="id" />

						<spring:bind path="name">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label"><spring:message code="cdb.form.name" /></label>
								<div class="col-sm-10">
									<form:input path="name" type="text" class="form-control "
										id="name" placeholder="Name" />
									<form:errors path="name" class="control-label" />
								</div>
							</div>
						</spring:bind>

						<spring:bind path="introduced">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label"><spring:message code="cdb.form.introduced" /></label>
								<div class="col-sm-10">
									<form:input path="introduced" class="form-control"
										id="introduced" placeholder="${dateFormat}" />
									<form:errors path="introduced" class="control-label" />
								</div>
							</div>
						</spring:bind>

						<spring:bind path="discontinued">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label"><spring:message code="cdb.form.discontinued" /></label>
								<div class="col-sm-10">
									<form:input path="discontinued" class="form-control"
										id="discontinued" placeholder="${dateFormat}" />
									<form:errors path="discontinued" class="control-label" />
								</div>
							</div>
						</spring:bind>

						<spring:bind path="company">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label"><spring:message code="cdb.form.company" /></label>
								<div class="col-sm-5">
									<form:select path="company.id" class="form-control">
										<form:option value="0" label="--Please Select" />
										<form:options items="${companies}" itemValue="id"
											itemLabel="name" />
									</form:select>
									<form:errors path="company" class="control-label" />
								</div>
								<div class="col-sm-5"></div>
							</div>
						</spring:bind>

						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<c:choose>
									<c:when test="${computerForm['new']}">
										<button type="submit" class="btn-lg btn-primary pull-right"><spring:message code="cdb.form.add" /></button>
									</c:when>
									<c:otherwise>
										<button type="submit" class="btn-lg btn-primary pull-right"><spring:message code="cdb.form.update" /></button>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/resources/js/addComputer.js"/>"></script>

</body>
</html>