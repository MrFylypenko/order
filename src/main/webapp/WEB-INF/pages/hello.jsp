<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html lang="en" <%--ng-app="orderSite"--%>>
<head>
    <link rel="stylesheet" href="resources/bower_components/bootstrap/dist/css/bootstrap.css">
    <link rel="stylesheet" href="resources/css/app.css">

    <script src="resources/bower_components/angular/angular.js"></script>
    <script src="resources/bower_components/angular-animate/angular-animate.js"></script>
    <script src="resources/bower_components/angular-route/angular-route.js"></script>
    <script src="resources/bower_components/angular-resource/angular-resource.js"></script>

    <script src="resources/js/app.js"></script>

    <script src="resources/js/animations.js"></script>
    <script src="resources/js/controllers.js"></script>

    <script src="resources/js/services.js"></script>
    <script src="resources/js/filter.js"></script>

    <script src="resources/bower_components/jquery/dist/jquery.js"></script>
    <script src="resources/bower_components/bootstrap/dist/js/bootstrap.js"></script>
    <script src="resources/js/calendar.js"></script>

    <script src="resources/bower_components/angular-bootstrap/ui-bootstrap.min.js"></script>
    <script src="resources/bower_components/angular-bootstrap/ui-bootstrap-tpls.js"></script>
</head>


<body >

	<h1>${message}</h1>







<div ng-controller="AdminController">


Тут будут данные

    <a href="admin">admin</a>

    ${pageContext.request.userPrincipal.name}


    <sec:authorize access="hasRole('ROLE_DELEING_ORDER')">
        <h3>роль ROLE_DELEING_ORDER теперь имеется</h3>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_ADDING_ORDER')">
        <h3>роль ROLE_ADDING_ORDER теперь имеется</h3>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_ADDING_RECIPE')">
        <h3>роль ROLE_ADDING_RECIPE теперь имеется</h3>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_DELEING_RECIPE')">
        <h3>роль ROLE_DELEING_RECIPE теперь имеется</h3>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_PUT_ITEM')">
        <h3>роль ROLE_PUT_ITEM теперь имеется</h3>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_GET_ITEM')">
        <h3>роль ROLE_GET_ITEM теперь имеется</h3>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_VIEW_SKLAD')">
        <h3>роль ROLE_VIEW_SKLAD теперь имеется</h3>
    </sec:authorize>





</div>





</body>
</html>