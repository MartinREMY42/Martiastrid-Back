<%@ include file="include/importTags.jsp" %>
<head>
    <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/index.css"/>">
</head>
<body>
<div align="center">
</div>
<br>
<br>
<br>
<br>
<br>

<div class="wrapper"><img class="crop" src="<spring:url value="/resources/images/pizza.jpg"/>" alt="pizza"></div>

<hr>

<div class="wrapper"><a class="button myButtons" href=<spring:url value="/standardPizzas/"/>>Pizzas</a>
    <a class="button myButtons" href=<spring:url value="/customPizza"/>>Custom Pizzas</a>
</div>
</body>