<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ page session="false"%> --%>
<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>


<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Вход в приложение</title>
    <link rel="stylesheet" href="resources/bower_components/bootstrap/dist/css/bootstrap.css">

    <link rel="stylesheet" href="resources/css/app.css">
    <script src="resources/bower_components/jquery/dist/jquery.js"></script>
    <script src="resources/bower_components/bootstrap/dist/js/bootstrap.js"></script>


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
