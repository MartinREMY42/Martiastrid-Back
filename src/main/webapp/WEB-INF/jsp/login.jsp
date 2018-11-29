<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

</head>
<div align="center">
    <img class="mb-4" src="<spring:url value='/resources/images/martiastrid-s-pizzeria.svg'/>" widht="200" ,
         height="200"/>

    <form:form
            id="auth"
            action="${pageContext.request.contextPath}/authenticateTheUser"
            method="post"
            class="form-signin">

        <h1 class="h3 mb-3 font-weight-normal">
            <a href="<spring:url value="/register"/>"><spring:message code="noAccoutRegister"/></a>
        </h1>

        <c:if test="${param.logout != null}">
            <div style="color: #4CAF50"><spring:message code="youJustLoggedOut"/></div>
        </c:if>

        <c:if test="${param.error != null}">
            <div style="color: red">
                <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
            </div>
        </c:if>
        <br>

        <label for="inputUsername" class="sr-only"><spring:message code="username"/></label>
        <input type="text" id="inputUsername" class="form-control" placeholder="Username" required autofocus
               name="username">

        <br>

        <label for="inputPassword" class="sr-only"><spring:message code="password"/></label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required
               name="password">
        <label><input type="checkbox" name="remember-me"><spring:message code="rememberMe"/></label>

        <button class="btn btn-lg btn-primary btn-block" type="submit">
            <spring:message code="login"/>
        </button>
        <p class="mt-5 mb-3 text-muted">&copy; 2017-2018</p>
    </form:form></div>