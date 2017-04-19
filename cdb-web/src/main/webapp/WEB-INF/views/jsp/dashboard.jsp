<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <!-- Bootstrap -->
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen"/>
    <link href="<c:url value="/resources/css/flag.min.css"/>" rel="stylesheet" media="screen"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.3.0/css/mdb.min.css" rel="stylesheet">

</head>
<body>

<nav class="navbar navbar-toggleable-md navbar-dark fixed-top scrolling-navbar bg-primary " style="text-align:center">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
            <strong>Computer Database</strong>
        </a>

        <form id="searchForm" action="" class="form-inline waves-effect waves-light col-lg-6" style="display:inline-block;padding:5px;">
            <input type="hidden" name="size" value="${size}"/>
            <input class="form-control"
                   value="${filter }"
                   name="filter"
                   type="search" id="searchbox"
                   placeholder="<spring:message code="cdb.dashboard.search"/>"/>
            <button type="submit" id="searchsubmit" class="btn btn-primary btn-sm">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                <strong>Filter by name</strong>
            </button>
        </form>

        <div class="navbar-toggler navbar-toggler-right col-md-3">
            <ul class="navbar-nav mr-auto">
                    <a class="navbar-link" href="/login">
                        <button class="btn btn-primary btn-sm" >
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                            <strong>Sign in </strong>
                        </button>
                    </a>

                <div class="btn-group" style="display:inline-block;padding:10px;">
                    <button class="btn btn-primary btn-sm dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
                        <strong>Langue</strong>
                    </button>

                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="?locale=en_US">
                            <i class="us flag"> </i>
                            <strong>English (US)</strong>
                        </a>
                        <br/>
                        <a class="dropdown-item" href=" ?locale=fr_FR" style="margin-top:9px;">
                            <i class="fr flag"> </i>
                            <strong>French(FR)</strong>
                        </a>
                    </div>
                </div>
            </ul>

        </div>
    </div>
</nav>

<div class="container">
    <c:if test="${not empty msg}">
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>

    <div class="row fadeIn" data-wow-delay="0.2s">
        <h1 id="homeTitle" class="col-md-6">${totalComputers}
            <spring:message code="cdb.dashboard.total"/>
        </h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-right">
                <a class="btn btn btn-primary" id="addComputer" href="/computers/add">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    <strong><spring:message code="cdb.dashboard.add"/></strong>
                </a>

                <button type="button"
                        id="editComputer"
                        class="btn btn-primary "
                        href="#"
                        onclick="$.fn.toggleEditMode();">
                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                    <strong><spring:message code="cdb.dashboard.edit"/></strong>
                </button>
            </div>
        </div>
    </div>
</div>

<form:form id="deleteForm" method="POST" action="/computers/delete" modelAttribute="deleteForm">
    <form:input path="computers" type="hidden"/>
</form:form>

<div class="container" style="margin-top: 10px;">
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th class="editMode" style="width: 60px; height: 22px;">
                <input type="checkbox" id="selectall"/>
                <span style="vertical-align: top;">
                    <b>-</b>
                    <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    </a>
                </span>
            </th>
            <th>
                <small>
                    <a href="?size=${size}&filter=${filter}&page=${currentIndexPage}&orderby=name&sens=ASC">
                        <span class="glyphicon glyphicon-chevron-up" aria-hidden="true"/>
                    </a>
                </small>
                <spring:message code="cdb.computer.name"/>
                <small>
                    <a href="?size=${size}&filter=${filter}&page=${currentIndexPage}&orderby=name&sens=DESC">
                        <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"/>
                    </a>
                </small>
            </th>
            <th>
                <small>
                    <a href="?size=${size}&filter=${filter}&page=${currentIndexPage}&orderby=introduced&sens=ASC">
                        <span class="glyphicon glyphicon-chevron-up" aria-hidden="true"/>
                    </a>
                </small>
                <spring:message code="cdb.computer.introduced"/>
                <small>
                    <a href="?size=${size}&filter=${filter}&page=${currentIndexPage}&orderby=introduced&sens=DESC">
                        <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"/>
                    </a>
                </small>
            </th>
            <th>
                <small>
                    <a href="?size=${size}&filter=${filter}&page=${currentIndexPage}&orderby=discontinued&sens=ASC">
                        <span class="glyphicon glyphicon-chevron-up" aria-hidden="true"/>
                    </a>
                </small>
                <spring:message code="cdb.computer.discontinued"/>
                <small>
                    <a href="?size=${size}&filter=${filter}&page=${currentIndexPage}&orderby=discontinued&sens=DESC">
                        <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"/>
                    </a>
                </small>
            </th>
            <th>
                <small>
                    <a href="?size=${size}&filter=${filter}&page=${currentIndexPage}&orderby=company&sens=ASC">
                        <span class="glyphicon glyphicon-chevron-up" aria-hidden="true"/>
                    </a>
                </small>
                <spring:message code="cdb.computer.company"/>
                <small>
                    <a href="?size=${size}&filter=${filter}&page=${currentIndexPage}&orderby=company&sens=DESC">
                        <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"/>
                    </a>
                </small>
            </th>
        </tr>
        </thead>
        <!-- Browse attribute computers -->
        <tbody id="results">
        <c:forEach items="${computers}" var="computer">
            <tr>
                <td class="editMode">
                    <input type="checkbox" name="cb" class="cb" value="${computer.id}">
                </td>
                <td>
                    <a href="/computers/${computer.id}/edit" onclick="">${computer.name}</a>
                </td>
                <td>
                        ${computer.introduced}
                </td>
                <td>
                        ${computer.discontinued}
                </td>
                <td>
                        ${computer.company.name}
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>

<div class="container text-center">
    <div class="row">
        <nav>
            <ul id="pagination-demo" class="pagination center-block"></ul>
            <div class="btn-group btn-group-sm  pull-right" role="group">
                <a class="btn btn-default " href="?size=10&filter=${filter}">10</a>
                <a class="btn btn-default " href="?size=50&filter=${filter}">50</a>
                <a class="btn btn-default " href="?size=100&filter=${filter}">100</a>
            </div>
        </nav>
    </div>
</div>

<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/dashboard.js"/>"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.3.0/js/mdb.min.js"></script>

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
            window.location.href = "?page=" + page + "&size=${size}&filter=${filter}";
        }
    });
</script>
</body>
</html>
