<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>

<p>
<spring:message code="customPizza"/>
<c:if test="${errorMessage != null}">
    <div style="color: red">
            ${errorMessage}
    </div>
</c:if>
</p>

<hr>

<div>
    <form:form action="${pageContext.request.contextPath}/customPizza/add" modelAttribute="chosenIngredients">
        <div class="container">
            <table id="cart" class="table table-hover table-condensed">
                <thead>
                <tr>
                    <th style="width:35%">Ingredients</th>
                    <th style="width:15%"><spring:message code="price"/></th>
                    <th style="width:8%" class="text-center">Selection</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${allIngredients}" var="ingredient">
                    <tr>
                        <td data-th="Product">
                            <div class="row">
                                <div class="col-sm-2 hidden-xs"><img
                                        src="<spring:url value="/resources/images/${ingredient.genericName}.png"/>"
                                        alt="..."
                                        class="img-responsive"/></div>
                                <div class="col-sm-10">
                                    <h4 class="nomargin">
                                            <spring:message code="${ingredient.genericName}"/>
                                    </h4>
                                </div>
                            </div>
                        </td>
                        <td>
                                ${ingredient.priceForComposition}
                        </td>
                        <td>
                            <form:checkbox path="ingredients" value="${ingredient.id}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td align="right"><form:label path="quantity"> <spring:message code="quantity"/></form:label></td>
                    <td align="center"><form:input path="quantity" size="4" type="number" value="1" min="1" max="30" cssStyle="width: 4em"/>
                        <c:if test="${errorMessageCustomAmount != null}">
                            <div style="color: red">
                                    ${errorMessageCustomAmount}
                            </div>
                        </c:if>
                    </td>
                    <td><form:button class="btn btn-primary"> <spring:message code="addToCart"/> </form:button></td>
                </tr>
                </tfoot>
            </table>
        </div>


    </form:form>
</div>

