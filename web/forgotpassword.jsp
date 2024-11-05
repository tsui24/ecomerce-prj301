<%-- 
    Document   : forgotpassword
    Created on : Oct 16, 2024, 5:01:30 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên Mật Khẩu</title>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/pass.css">

</head>
<body>
    <h2>Forgot Password</h2>
    <form action="sendEmail" method="post">
        <label for="email">Enter your email:</label>
        <input type="email" id="email" name="email" required>
        <button type="submit">Send OTP</button>
    </form>
    
</body>
</html>

