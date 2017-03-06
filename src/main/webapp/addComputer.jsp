<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
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
                    <h1>Add Computer</h1>
                    <form action="database" method="POST" onsubmit="return validateForm()">
                    <input name="action" type="hidden" value="add" />
                        <fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									name="name" type="text" class="form-control" id="computerName"
									placeholder="MackBook Pro">
							</div>
							
							<div id="alert-name-danger"  style="display:none;" class="alert alert-danger" role="alert">The name is invalid, it can't be empty and can contains only alphanumeric, space, dot, dash or parenthesis</div>
							
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									name="introduced" type="date" class="form-control"
									id="introduced" placeholder="yyyy-mm-dd">
							</div>
							
							<div id="alert-introduced-danger"  style="display:none;" class="alert alert-danger" role="alert">Date is invalid format year-month-day (YYYY-MM-DD)</div>
							
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									name="discontinued" type="date" class="form-control"
									id="discontinued" placeholder="yyyy-mm-dd">
							</div>
							
							<div id="alert-discontinued-danger"  style="display:none;" class="alert alert-danger" role="alert">Date is invalid format year-month-day (YYYY-MM-DD)</div>
							<div id="alert-date-danger"  style="display:none;" class="alert alert-danger" role="alert">Discontinued date must be after introduced</div>
							
							<div class="form-group">
								<label for="companyId">Company</label> <select name="company"
									class="form-control" id="companyId">
									
									<option value=""></option>
									<c:forEach items="${companies}" var="company">										
										<option value="${company.id}:${company.name}">${company.name}</option>
									</c:forEach>

								</select>
							</div>
						</fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="database" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="./js/jquery.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/addComputer.js"></script>
	
</body>
</html>