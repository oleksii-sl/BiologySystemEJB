<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error page</title>
</head>
<body>
<h2>An error occurred!</h2>
<h3>Request that failed: ${pageContext.errorData.requestURI}</h3>
<h3>Status code: ${pageContext.errorData.statusCode}</h3>
<h3>Throwable: ${pageContext.errorData.throwable}</h3>
<h3>Throwable cause: ${pageContext.errorData.throwable.cause}</h3>
<!--<h3> Stack Trace: </h3>-->
<p style="font-family: 'Consolas'">
    <c:forEach items="${pageContext.exception.stackTrace}" var="element">
        <!--<c:out value='${element}' />-->
    </c:forEach>
</p>
<h2><a href="main" >Main</a></h2>
</body>
</html>