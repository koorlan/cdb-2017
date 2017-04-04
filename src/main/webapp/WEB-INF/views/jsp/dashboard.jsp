<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
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
		<a class="navbar-brand" href="<c:url value="/"/>"> Application -
			Computer Database </a>
	</div>
	</header>
	<section id="main"> Language : <a href="?locale=en">English</a>|<a
		href="?locale=fr_FR">Français</a>
	<div class="container">

		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>

		<h1 id="homeTitle">${totalComputers}
			<spring:message code="cdb.dashboard.total" />
		</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="" method="GET" class="form-inline">
					<input type="search" id="searchbox" name="filter"
						class="form-control"
						placeholder="<spring:message code="cdb.dashboard.search"/>"
						value="${filter }" /> <input type="submit" id="searchsubmit"
						value="Filter by name" class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer" href="/computers/add"><spring:message
						code="cdb.dashboard.add" /></a> <a class="btn btn-default"
					id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message
						code="cdb.dashboard.edit" /></a>
			</div>
		</div>
	</div>

	<form id="deleteForm" action="/computers/delete" method="POST">
		<input type="hidden" name="action" value="delete"> <input
			type="hidden" name="selection" value="">
	</form>

	<div class="container" style="margin-top: 10px;">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->

					<th class="editMode" style="width: 60px; height: 22px;"><input
						type="checkbox" id="selectall" /> <span
						style="vertical-align: top;"> - <a href="#"
							id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
								class="fa fa-trash-o fa-lg"></i>
						</a>
					</span></th>
					<th><spring:message code="cdb.computer.name" /></th>
					<th><spring:message code="cdb.computer.introduced" /></th>
					<!-- Table header for Discontinued Date -->
					<th><spring:message code="cdb.computer.discontinued" /></th>
					<!-- Table header for Company -->
					<th><spring:message code="cdb.computer.company" /></th>

				</tr>
			</thead>
			<!-- Browse attribute computers -->
			<tbody id="results">
				<c:forEach items="${computers}" var="computer">
					<tr>
						<td class="editMode"><input type="checkbox" name="cb"
							class="cb" value="${computer.id}"></td>
						<td><a href="/computers/${computer.id}/edit" onclick="">${computer.name}</a></td>
						<td>${computer.introduced}</td>
						<td>${computer.discontinued}</td>
						<td>${computer.company.name}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
	</section>

	<footer class="navbar-fixed-bottom">
	<div class="container text-center">
		<%-- <ul class="pagination">
            <li><a href="database?action=prev" aria-label="Previous"> <span
                    aria-hidden="true">&laquo;</span>
                    <c:forEach var="i" begin="1" end="3" varStatus="loop">
                         <c:choose>
                            <c:when test="${currentIndexPage-loop.end+i > 0}">
                                <li><a href="database?action=goto&page=${currentIndexPage-loop.end+i}">${currentIndexPage-loop.end+i}</a></li>
                            </c:when>
                        </c:choose>

                    </c:forEach>
                     <c:forEach var="i" begin="1" end="3">
                         <c:choose>
                            <c:when test="${currentIndexPage + i < maxIndexPage}">
                                <li><a href="database?action=goto&page=${currentIndexPage+i}">${currentIndexPage+i}</a></li>
                            </c:when>
                        </c:choose>
                    </c:forEach>

                    <li><a href="database?action=next" aria-label="Next"> <span
                            aria-hidden="true">&raquo;</span></a></li>
        </ul>
         --%>
		<ul id="pagination-demo" class="pagination"></ul>

		<div class="btn-group btn-group-sm pull-right" role="group">
			<a class="btn btn-default " href="?size=10">10</a> <a
				class="btn btn-default " href="?size=50">50</a> <a
				class="btn btn-default " href="?size=100">100</a>
		</div>
	</footer>

	<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/resources/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/resources/js/dashboard.js"/>"></script>
	<script
		src="<c:url value="/resources/js/jquery.twbsPagination.min.js"/>"></script>
	<script>
$('#pagination-demo').twbsPagination({
    first: "<spring:message code="cdb.dashboard.frist"/>",
    prev: "<spring:message code="cdb.dashboard.prev"/>",
    next: "<spring:message code="cdb.dashboard.next"/>",
    last: "<spring:message code="cdb.dashboard.last"/>",	
	initiateStartPageClick: false,
    startPage: ${currentIndexPage},
    totalPages: ${maxIndexPage},
    visiblePages: 7,
    onPageClick: function (event, page) {
        window.location.href = "?page=" + page ;
    }
});
</script>
</body>
</html>
