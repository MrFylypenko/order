<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ page session="false"%> --%>
<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>

<%--<!doctype html>
<html lang="ru" ng-app="app">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>Document</title>
    &lt;%&ndash;<script src="resources/vendors/angular.js"></script>&ndash;%&gt;
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.3.13/angular.js"></script>
    <script src="resources/vendors/angular-route.js"></script>
    <script src="resources/vendors/ankular.min.js"></script>
    <link rel="stylesheet" href="resources/vendors/ankular.min.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

    <script src="resources/scripts/app.js"></script>
    <link rel="stylesheet" href="resources/styles/default.css">

    <script src="resources/scripts/controllers/adminController.js"></script>
    <script src="resources/scripts/controllers/managerController.js"></script>
    <script src="resources/scripts/controllers/loginController.js"></script>
    <script src="resources/scripts/controllers/storekeeperController.js"></script>
    <script src="resources/scripts/controllers/assistantController.js"></script>
    <script src="resources/scripts/controllers/densityController.js"></script>
    <script src="resources/scripts/controllers/recipeController.js"></script>
    <script src="resources/scripts/services/recipeService.js"></script>
    <script src="resources/scripts/services/storageService.js"></script>
    <script src="resources/scripts/services/loginService.js"></script>
</head>
<body>--%>

<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Вход в приложение</title>
    <link rel="stylesheet" href="resources/old/bower_components/bootstrap/dist/css/bootstrap.css">

    <link rel="stylesheet" href="resources/old/css/app.css">
    <script src="resources/old/bower_components/jquery/dist/jquery.js"></script>
    <script src="resources/old/bower_components/bootstrap/dist/js/bootstrap.js"></script>


    </head>
    <body>

<div id="login-box">
<br>
    <br>
    <br>
    <br>
    <br>
    <br><br>


</div>

    <div style="width: 500px; margin: 0 auto;">

        <h3 style="text-indent: 90px;">

            Вход в приложение:
        </h3>
        <c:if test="${not empty error}">
            <div style="text-indent: 90px; color: #E41C01;">
                Неправильный логин или пароль!
            </div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="msg">${msg}</div>
        </c:if>
        <br>


        <form class="form-horizontal" action="<c:url value='/j_spring_security_check' />" method='POST' >
            <div class="form-group">
                <label for="inputEmail3" class="col-sm-2 control-label">Логин</label>
                <div class="col-sm-10">
                    <input name='j_username' type="text" class="form-control" id="inputEmail3" placeholder="Логин">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword3" class="col-sm-2 control-label">Пароль</label>
                <div class="col-sm-10">
                    <input name='j_password' type="password" class="form-control" id="inputPassword3" placeholder="Пароль">
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Войти</button>
                </div>
            </div>
        </form>
    </div>


</body>
</html>


<%--<form style="
  margin: auto;
  position: absolute;
  top: 0; left: 0; bottom: 0; right: 0;
  width: 200px;
  height: 200px;
" novalidate="novalidate" name="loginForm">
    <fieldset class="group vertical">
        <input type="text" placeholder="Логин" ng-model="username" required="">
        <input type="password" placeholder="Пароль" ng-model="password" required="">
        <input type="button" class="primary" value="Войти" ng-click="login()" ng-disabled="loginForm.$invalid">
    </fieldset>
</form>
</body>
</html>--%>
