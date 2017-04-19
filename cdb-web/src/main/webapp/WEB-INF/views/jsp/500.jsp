<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen"/>
    <link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet" media="screen"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.3.0/css/mdb.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-toggleable-md navbar-dark fixed-top scrolling-navbar bg-primary ">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
            <strong>Retour</strong></a>
    </div>
</nav>

<section id="main">
    <div class="container">
        <div class="alert alert-danger">
            Error 500: An error has occured!
            <br/>
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