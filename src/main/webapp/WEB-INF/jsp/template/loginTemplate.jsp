<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="type=text/html; charset=UTF-16">
    <link type="text/css" href="<spring:url value='/resources/css/bootstrap.min.css' />" rel="Stylesheet">
    <link type="text/css" href="<spring:url value='/resources/css/signin.css' />" rel="Stylesheet">
    <title>martiastrid Pizzeria</title>
</head>
<body class="text-center">
<%@include file="simpleHomeHeader.jsp" %>
<div><tiles:insertAttribute name="main-content" /></div>
</body>
</html>