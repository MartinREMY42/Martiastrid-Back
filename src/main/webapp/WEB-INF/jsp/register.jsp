<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script>
        $(document).ready(function(){
            $("#passwordConfirm").keyup(function(){
                if ($("#password").val() != $("#passwordConfirm").val()) {
                    $("#msg").html("Password do not match").css("color","red");
                    $("#registerButton").prop('disabled', true);
                }else{
                    $("#msg").html("Password matched").css("color","green");
                    console.log($("#registerButton").attr("disabled"));
                    $("#registerButton").prop('disabled', false);
                    console.log( $("#registerButton").attr("disabled"));
                }
            });
        });
    </script>
</head>
<body>

<div align="center">
    <img class="mb-4" src="<spring:url value='/resources/images/martiastrid-s-pizzeria.svg'/>" widht="200" ,
         height="200"/>
    <br>
    <c:if test="${errorMessage != null}">
        <div style="color: red">
                ${errorMessage}
        </div>
    </c:if>
    <br>

    <form:form  method="POST"
                action="/martiastrid/register"
                modelAttribute="userToRegister"
                class="form-login">
        <form:input path="username" placeholder="Username" autofocus="true" class="form-control"/>
        <br>

        <form:input path="birthdate" placeholder="birthdate" class="form-control"/>
        <br/>

        <form:password id="password" path="password" placeholder="Password" class="form-control"/>
        <br/>

        <form:password id="passwordConfirm" path="passwordConfirm" placeholder="Confirm password" class="form-control"/>
        <br/>

        <div id="msg"></div>
        <form:errors path="*"/><br/>

        <form:button id="registerButton" disabled="true" class="btn btn-lg btn-primary btn-block">Submit</form:button>
    </form:form>
</div>



</div>

</body>
</html>