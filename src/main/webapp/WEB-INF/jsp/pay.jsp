<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
    <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/pay.css"/>">
</head>

<div align="center">
    <img class="crop" src="<spring:url value="/resources/images/payNow.jpg"/>" alt="pay" style="height: 250px">
</div>

<br>

<div>

<div class="recap">
    <c:if test="${promoName != null}">
        <spring:message code="oldSum"/> : ${oldSum} <br/>
        <spring:message code="promotionName"/> : ${promoName} <br/>
    </c:if>

    <p><spring:message code="total"/> : ${sum} &euro;</p>
</div>

<br>

<form:form method="post" action="${pageContext.request.contextPath}/checkout/pay">
    <input type="hidden" value="${sum}" name="sum">
    <input type="image" src="https://www.paypalobjects.com/webstatic/en_US/i/buttons/checkout-logo-large.png">
</form:form>