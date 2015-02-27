<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 09.02.2015
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" ng-app="orderSite">
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




    <title></title>
</head>
<body>
<div ng-controller="AdminController">
    {{admin}}

    <br>
    <br>
    <br>

    <div style="float: left; height: 300px; width: 300px; outline: 2px solid #000; margin: 10px;">
        <ul ng-repeat="user in users">
            <li > <a ng-click="selectUser($index)" href="" >{{user.username}}</a></li>
            <ul ng-repeat="role in user.userRole">
                <li>{{role.role}}</li>
            </ul>
        </ul>
    </div>

    <div style="float: left; height: 300px; width: 300px; outline: 2px solid #000; margin: 10px;">
        <ul ng-repeat="role in roles">
            <li > {{role.role}}  <input ng-click="addRole($index)" type="button" value="+" />                </li>
        </ul>
    </div>

    <div style="float: left; height: 300px; width: 300px; outline: 2px solid #000; margin: 10px;">
        {{currentuser.username}}
        <ul ng-repeat="role in currentuser.userRole">
            <li>{{role.role}}      <input type="button" value="-" ng-click="deleteRole($index)"/>         </li>
        </ul>
        <input type="button" value="Сохранить права юзера" ng-click="saveUser()"/>
    </div>




</div>

</body>
</html>
