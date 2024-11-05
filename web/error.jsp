<%-- 
    Document   : error
    Created on : Oct 10, 2024, 12:50:38 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Error: Access Denied</h1>
    <p>${errorMessage}</p>
    <a href="${pageContext.request.contextPath}/log">Login</a>
    </body>
</html>
