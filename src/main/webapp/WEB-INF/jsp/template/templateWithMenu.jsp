<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="type=text/html; charset=UTF-16">
    <link type="text/css" href="<spring:url value='/resources/css/bootstrap.min.css' />" rel="Stylesheet">
    <link type="text/css" href="<spring:url value='/resources/css/templateWithMenu.css' />" rel="Stylesheet">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <title>martiastrid Pizzeria</title>
</head>
<body>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">

<%@include file="header.jsp" %>
<div id="left" class="nav-side-menu">
    <div class="brand"> Menu </div>
    <i class="fa fa-bars fa-2x toggle-btn" data-toggle="collapse" data-target="#menu-content"></i>

    <div class="menu-list">




            <li data-toggle="collapse" data-target="#products" class="collapsed">
                <a href="#"><i class="fa fa-circle fa-lg"></i> Pizzas <span class="arrow"></span></a>
            </li>
            <ul class="sub-menu collapse" id="products">
                <li> <i style="margin-left: 2em" class="fa fa-chevron-right" aria-hidden="true"></i>
                    <a href=<spring:url value="/standardPizzas"/>><spring:message code="all"/></a>
                </li>
                <li>
                    <i style="margin-left: 2em" class="fa fa-chevron-right" aria-hidden="true"></i>
                    <a href=<spring:url value="/standardPizzas?category=vegan"/>> <spring:message code="vegan"/> </a>
                </li>
                <li>
                    <i style="margin-left: 2em" class="fa fa-chevron-right" aria-hidden="true"></i>
                    <a href=<spring:url value="/standardPizzas?category=spicy"/>> <spring:message code="spicy"/> </a>
                </li>
                <li>
                    <i style="margin-left: 2em" class="fa fa-chevron-right" aria-hidden="true"></i>
                    <a href=<spring:url value="/standardPizzas?category=cannibale"/>> <spring:message code="canibal"/></a>
                </li>
                <li>
                    <i style="margin-left: 2em" class="fa fa-chevron-right" aria-hidden="true"></i>
                    <a href=<spring:url value="/standardPizzas?category=special"/>> <spring:message code="special"/> </a>
                </li>
            </ul>

            <li class="nav-item">
                <a class="nav-link fa fa-pie-chart fa-lg" href=<spring:url value="/customPizza"/>> <spring:message code="customPizza"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link fa fa-shopping-cart fa-lg" href=<spring:url value="/cart/"/>> <spring:message code="cart"/></a>
            </li>
        </ul>
    </div>
</div>

<div id="right">
    <tiles:insertAttribute name="main-content" />
</div>
</body>
</html>