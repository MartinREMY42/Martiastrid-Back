<header>
    <spring:url var="localeFr" value="">
        <spring:param name="locale" value="fr" />
    </spring:url>

    <spring:url var="localeEn" value="">
        <spring:param name="locale" value="en" />
    </spring:url>
    <nav class="fixed-top d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">

        <h5 class="my-0 mr-md-auto font-weight-normal"><a href="<spring:url value="/"/>"><spring:message code="homeHeader"/></a></h5>
        <sec:authorize access="!isAuthenticated()">
            <nav class="my-2 my-md-0 mr-md-3">
                <a class="p-2 text-dark" href="${localeFr}">FR</a>
                <a class="p-2 text-dark" href="${localeEn}">EN</a>
                <a class="p-2 text-dark"
                   href="<spring:url value='/register' />">
                    <spring:message code="register"/>
                </a>
            </nav>
            <a class="btn btn-outline-primary"
               href="<spring:url value='/showMyLoginPage' />">
                <spring:message code="login"/>
            </a>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <nav class="my-2 my-md-0 mr-md-3">
                <a class="p-2 text-dark" href="${localeFr}">FR</a>
                <a class="p-2 text-dark" href="${localeEn}">EN</a>
                <a class="p-2 text-dark" href="#">
                    <sec:authentication property="principal.username" />
                </a>
                <a class="p-2 text-dark"
                   href="<spring:url value='/cart'/>">
                    <spring:message code="cart"/>
                </a>
            </nav>
            <a class="btn btn-outline-primary"
               href="<spring:url value='/logout' />">
                <spring:message code="logout"/>
            </a>
        </sec:authorize>
    </nav>

</header>