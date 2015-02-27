<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 13.02.2015
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ru" ng-app="app">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>Document</title>
    <%--<script src="resources/vendors/angular.js"></script>--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.3.13/angular.js"></script>
    <script src="resources/vendors/angular-route.js"></script>
    <script src="resources/vendors/ankular.min.js"></script>
    <link rel="stylesheet" href="resources/vendors/ankular.min.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

    <script src="resources/scripts/app.js"></script>
    <link rel="stylesheet" href="resources/styles/default.css">

    <script src="resources/scripts/controllers/adminController.js"></script>
    <script src="resources/scripts/controllers/maganerController.js"></script>
    <script src="resources/scripts/controllers/storekeeperController.js"></script>
    <script src="resources/scripts/controllers/assistantController.js"></script>
    <script src="resources/scripts/controllers/densityController.js"></script>
    <script src="resources/scripts/services/storageService.js"></script>
</head>
<body>

<section class="container fixed">
    <ng-view></ng-view>
</section>
</body>
</html>