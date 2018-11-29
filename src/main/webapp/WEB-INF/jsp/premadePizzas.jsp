<%@ include file="include/importTags.jsp" %>

<head>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>

<p>Pizzas
<c:if test="${errorMessage != null}">
    <div style="color: red">
            ${errorMessage}
    </div>
</c:if>
</p>
<hr>

<c:if test="${pizzaCounter != null && allPizzas != null}">
    <div>
        <form:form action="${pageContext.request.contextPath}/standardPizzas/add" method="post"
                   modelAttribute="pizzaCounter">
            <div class="container">
                <table id="cart" class="table table-hover table-condensed">
                    <thead>
                    <tr>
                        <th style="width:40%">Pizza</th>
                        <th style="width:10%">Price</th>
                        <th style="width:2%" class="text-center">Selection</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${allPizzas}" var="pizza">
                        <tr>
                            <td data-th="Pizza">
                                <div class="row">
                                    <div class="col-sm-2 hidden-xs"><img
                                            src="<spring:url value='/resources/images/${pizza.genericName}.png' />"
                                            alt="..."
                                            class="img-responsive"/></div>
                                    <div class="col-sm-10">
                                        <h4 class="nomargin">
                                            <strong class="d-block text-gray-dark">
                                                <spring:message code="${pizza.genericName}"/>
                                            </strong>
                                        </h4>
                                        <h5>
                                            <c:forEach items="${pizza.ingredients}" var="recipe">
                                                <spring:message code="${recipe.genericName}"/>
                                            </c:forEach>
                                        </h5>
                                    </div>
                                </div>
                            </td>
                            <td>
                                    ${pizza.price}
                            </td>
                            <td>
                                <div><form:input path="orderedPizzas[${pizza.id}]" type="number" value="0" min="0" max="30" cssStyle="width: 4em"/></div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td align="right"></td>
                        <td align="center"></td>
                        <td><form:button class="btn btn-primary"> <spring:message code="addToCart"/> </form:button></td>
                    </tr>
                    </tfoot>
                </table>
            </div>


        </form:form>
    </div>
</c:if>