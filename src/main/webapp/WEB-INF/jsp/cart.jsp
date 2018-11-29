<%@ include file="include/importTags.jsp" %>
<head>
    <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/cart.css"/>">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <title><spring:message code="cart"/></title>
</head>
<body>
<p>
    <spring:message code="cart"/>
    <c:if test="${errorMessage != null}">
        <div style="color: red">
        ${errorMessage}
        </div>
</c:if>
</p>

<div class="wrapper">

</div>

<hr>

<div class="container">
    <table id="cart" class="table table-hover table-condensed">
        <thead>
        <tr>
            <th style="width:50%">Pizza</th>
            <th style="width:10%"><spring:message code="price"/></th>
            <th style="width:8%"><spring:message code="quantity"/></th>
            <th style="width:22%" class="text-center"><spring:message code="subtotal"/></th>
            <th style="width:50%">Product</th>
      </tr>
        </thead>
        <tbody>
        <c:set var="total" value="${0}"/>
        <c:forEach var="pizzaEntry" items="${cart}">
            <c:set var="total" value="${total + (pizzaEntry.key.price * pizzaEntry.value)}"/>
            <tr>
                <td data-th="Product">
                    <div class="row">
                        <div class="col-sm-2 hidden-xs">
                            <img src="<spring:url value='/resources/images/${pizzaEntry.key.genericName}.png' />"
                                 alt="generic" class="img-responsive"/>

                        </div>
                        <div class="col-sm-10">
                            <h4 class="nomargin">
                                        <spring:message code="${pizzaEntry.key.genericName}"/>
                            </h4>
                            <p>
                                <c:forEach var="ingredient" items="${pizzaEntry.key.ingredients}">
                                    <spring:message code="${ingredient.genericName}"/>
                                </c:forEach>
                            </p>
                        </div>
                    </div>
                </td>
                <td data-th="Price">${pizzaEntry.key.price}</td>
                <td data-th="Quantity">
                        ${pizzaEntry.value}
                </td>
                <td data-th="Subtotal" class="text-center">
                        ${pizzaEntry.key.price * pizzaEntry.value}
                </td>
                <td class="actions" data-th="">
                    <form:form method="POST"
                               action="/martiastrid/cart/add"
                               modelAttribute="pizzaToEdit">
                        <form:hidden path="id" value="${pizzaEntry.key.id}"/>
                        <c:forEach items="${pizzaEntry.key.ingredients}" var="ingredient" varStatus="s">
                            <form:hidden path="ingredients[${s.index}].id" value="${ingredient.id}"
                                         class="form-control"/>
                        </c:forEach>
                        <form:hidden path="custom" value="${pizzaEntry.key.custom}"/>
                        <form:button class="btn btn-info btn-sm my-plus-button"><i
                                class="glyphicon glyphicon-plus"></i></form:button>
                    </form:form>

                    <form:form method="POST"
                               action="/martiastrid/cart/remove"
                               modelAttribute="pizzaToEdit">
                        <form:hidden path="id" value="${pizzaEntry.key.id}"/>
                        <c:forEach items="${pizzaEntry.key.ingredients}" var="ingredient" varStatus="s">
                            <form:hidden path="ingredients[${s.index}].id" value="${ingredient.id}"
                                         class="form-control"/>
                        </c:forEach>
                        <form:hidden path="custom" value="${pizzaEntry.key.custom}"/>
                        <form:button class="btn btn-danger btn-sm"><i
                                class="glyphicon glyphicon-minus"></i></form:button>
                    </form:form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr class="visible-xs">
            <td class="text-center"><strong>Total 1.99</strong></td>
        </tr>
        <tr>
            <td></td>
            <td colspan="2" class="hidden-xs"></td>
            <td class="hidden-xs text-center"><strong>Total ${total}</strong></td>
            <td><a href="<spring:url value="/checkout" />" class="btn btn-success btn-block"><spring:message code="pay"/><i
                    class="fa fa-angle-right"></i></a></td>
        </tr>
        </tfoot>
    </table>
</div>
</body>