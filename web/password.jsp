<%-- 
    Document   : password
    Created on : Oct 10, 2024, 8:02:10 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        
        <title>Change Password</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f2f5;
                
                
                height: 100vh;
                margin: 0;
            }

            .change-password-form {
                background-color: #fff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
                width: 450px;
            }

            h2 {
                text-align: center;
                color: #333;
                margin-bottom: 20px;
            }

            label {
                font-weight: bold;
                margin-bottom: 5px;
                display: block;
            }

            .password-input {
                position: relative;
            }

            input[type="password"], input[type="text"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 20px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
                box-sizing: border-box;
            }

            .toggle-password {
                position: absolute;
                top: 30%;
                right: 10px;
                transform: translateY(-50%);
                cursor: pointer;
            }

            .submit-button {
                width: 100%;
                background-color: #28a745;
                color: white;
                padding: 10px;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
            }

            .submit-button:hover {
                background-color: #218838;
            }

            .error-message {
                color: red;
                text-align: center;
                margin-bottom: 15px;
            }
            .sidebar{
                display: block;
            }
            .list-link li{
                list-style: none;
                margin-bottom: 20px;
            }
            .card-img{
                text-align: center;
            }
            .list-link{
                margin-top: 30px;
            }
            .link-it{
                font-size: 18px;
                text-decoration: none;
            }
            .content{
                display: flex;
                margin-top: 20px;
                
            }
        </style>
    </head>
    <body>
        <div class="content">
            <div class="sidebar col-sm-4">
                <div class="card-img">
                    <h3>${sessionScope.user.username}</h3>
                    <<img src="./asset/img/${sessionScope.user.avt}" alt="alt"/>
                </div>
                <ul class="list-link">
                    <li><a class="link-it" href="profile.jsp">Profile</a></li>
                    <li><a class="link-it" href="password.jsp">Change Password</a></li>
                </ul>
                
            </div>
            <div class="change-password-form col-sm-8">
                <h2>Change Password</h2>

                <!-- Error Message if passwords don't match -->
                <div class="error-message" id="error-message"></div>

                <form action="changepassword" method="post" onsubmit="return validatePasswords();">
                    <input type="hidden" name="id" value="${sessionScope.user.id}">
                    <label for="current-password">Current Password:</label>
                    <div class="password-input">
                        <input type="password" id="current-password" name="currentPassword" required>
                        <span class="toggle-password" onclick="togglePassword('current-password')">👁️</span>
                    </div>
                    <h3>${requestScope.errorMessage}</h3>

                    <!-- New password input with show/hide functionality -->
                    <label for="new-password">New Password:</label>
                    <div class="password-input">
                        <input type="password" id="new-password" name="newPassword" required>
                        <span class="toggle-password" onclick="togglePassword('new-password')">👁️</span>
                    </div>

                    <!-- Confirm new password input with show/hide functionality -->
                    <label for="confirm-password">Confirm New Password:</label>
                    <div class="password-input">
                        <input type="password" id="confirm-password" name="confirmPassword" required>
                        <span class="toggle-password" onclick="togglePassword('confirm-password')">👁️</span>
                    </div>

                    <!-- Submit button -->
                    <button type="submit" class="submit-button">Change Password</button>
                </form>
            </div>
        </div>


        <script>
            // JavaScript to toggle between showing and hiding passwords
            function togglePassword(fieldId) {
                const passwordField = document.getElementById(fieldId);
                const type = passwordField.getAttribute('type') === 'password' ? 'text' : 'password';
                passwordField.setAttribute('type', type);
            }


        </script>

    </body>
</html>


