<header>
    <spring:url var="localeFr" value="">
        <spring:param name="locale" value="fr" />
    </spring:url>

    <spring:url var="localeEn" value="">
        <spring:param name="locale" value="en" />
    </spring:url>

    <nav  class="fixed-top d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
        <h5 class="my-0 mr-md-auto font-weight-normal"><a href="<spring:url value="/"/>"></a></h5>


        <a href="<spring:url value="/"/>" class="btn btn-info btn-lg">
            <span class="glyphicon glyphicon-home"></span> Home
        </a>

        <nav class="my-2 my-md-0 mr-md-3"><a class="p-2 text-dark" href="${localeFr}">FR</a>
        <a class="p-2 text-dark" href="${localeEn}">EN</a></nav>
    </nav>


</header>