<%-- 
    Document   : sidebar
    Created on : Oct 10, 2024, 9:58:48 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            body {
                font-family: "Arial", sans-serif;
                margin: 0;
                padding: 0;
            }

            /* Sidebar styles */
            .sidebar {
                height: 100%;
                width: 250px;
                position: fixed;
                top: 0;
                left: 0;
                background-color: white;
                padding-top: 20px;
                overflow-x: hidden;
            }

            .sidebar a {
                padding: 10px 15px;
                text-decoration: none;
                font-size: 18px;
                color: black;
                display: block;
                transition: 0.3s;
            }

            .sidebar a:hover {
                background-color: #575757;
                color: white;
            }
            .card-img{
                padding: 10px 15px;
                font-size: 18px;
                text-align: center;
            }
            .card-img img{
                border: 50%;
            }
        </style>
    </head>
    <body>
        <div class="sidebar">
            <div class="card-img">
                <h3>${sessionScope.user.username}</h3>
                <<img src="./asset/img/${sessionScope.user.avt}" alt="alt"/>
            </div>
            
            
            <a href="profile.jsp">Profile</a>
            <a href="password.jsp">Change Password</a>
        </div>
    </body>
</html>
