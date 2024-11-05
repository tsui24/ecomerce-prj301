<%-- 
    Document   : profile
    Created on : Oct 9, 2024, 10:10:30 PM
    Author     : admin
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    User user = (User) session.getAttribute("user");
%>
<html>
    <head>
        <title>Hồ Sơ Của Tôi</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4; /* Màu nền trang */
                margin: 0;
                padding: 0;
            }

            .profile-container {
                width: 90%;
                max-width: 800px; /* Giới hạn chiều rộng */
                margin: 20px auto; /* Giữa trang */
                background: #ffffff; /* Màu nền của container */
                border-radius: 10px; /* Bo góc */
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* Đổ bóng */
                padding: 20px; /* Khoảng cách bên trong */
            }

            h1 {
                text-align: center; /* Căn giữa tiêu đề */
                color: #333; /* Màu tiêu đề */
            }

            .profile-info {
                margin-top: 20px; /* Khoảng cách trên các thông tin hồ sơ */
            }

            label {
                display: block; /* Hiển thị label dưới dạng block */
                margin: 10px 0 5px; /* Khoảng cách trên, dưới và bên trái */
                color: #555; /* Màu sắc cho label */
            }

            input[type="text"] {
                width: 100%; /* Chiều rộng 100% cho các input */
                padding: 10px; /* Khoảng cách bên trong */
                border: 1px solid #ddd; /* Đường viền */
                border-radius: 5px; /* Bo góc */
                margin-bottom: 15px; /* Khoảng cách dưới mỗi input */
            }

            img.avatar {
                width: 100px; /* Chiều rộng cho avatar */
                height: 100px; /* Chiều cao cho avatar */
                border-radius: 50%; /* Bo tròn avatar */
                margin-top: 10px; /* Khoảng cách trên avatar */
            }

            .change-link {
                display: inline-block; /* Hiển thị link dưới dạng inline block */
                margin-top: 10px; /* Khoảng cách trên */
                text-decoration: none; /* Bỏ gạch chân */
                color: #007BFF; /* Màu sắc cho link */
            }

            .change-link:hover {
                text-decoration: underline; /* Gạch chân khi hover */
            }

            .save-button {
                background-color: #007BFF; /* Màu nền cho nút lưu */
                color: white; /* Màu chữ */
                border: none; /* Không có đường viền */
                padding: 10px 15px; /* Khoảng cách bên trong */
                border-radius: 5px; /* Bo góc */
                cursor: pointer; /* Con trỏ khi hover */
                display: block; /* Hiển thị nút lưu dưới dạng block */
                margin: 20px auto; /* Giữa trang */
            }

            .save-button:hover {
                background-color: #0056b3; /* Màu khi hover */
            }
            save-button a{
                color: white;
                text-decoration: none;
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
                justify-content: center;
            }
        </style>
    </head>
    <body>
        <div class="content">
            <div class="sidebar col-sm-2">
                <div class="card-img">
                    <h3>${sessionScope.user.username}</h3>
                    <<img src="./asset/img/${sessionScope.user.avt}" alt="alt"/>
                </div>
                <ul class="list-link">
                    <li><a class="link-it" href="profile.jsp">Profile</a></li>
                    <li><a class="link-it" href="password.jsp">Change Password</a></li>
                </ul>                
            </div>
            <div class="profile-container col-sm-8">

                <h1>Hồ Sơ Của Tôi</h1>
                <p>Quản lý thông tin hồ sơ để bảo mật tài khoản</p>

                <form class="profile-info" action="updateuser"  method = "post">
                    <input type="hidden" name="id" value="${sessionScope.user.id}">
                    <label>Tên đăng nhập:${sessionScope.user.username}</label>

                    <label>Họ:</label>
                    <input type="text" value="${sessionScope.user.lastName}" name="lastName"/>

                    <label>Tên:</label>
                    <input type="text" value="${sessionScope.user.firstName}" name="firstName"/>
                    <label>Email:</label>
                    <p>${sessionScope.user.email}</p>
                    <label>Ảnh đại diện:</label>
                    <img src="${sessionScope.user.avt}" alt="Avatar" class="avatar" />
                    <input href="#" class="change-link" type="file" name="avt"></a>

                    <label>Ngày tạo:</label>
                    <p>${sessionScope.user.createAt}</p>

                    <label>Vai trò:</label>
                    <p>${sessionScope.user.role}</p>
                    <!-- Nút lưu -->
                    <button class="save-button" type="submit">Lưu</button>
                </form>
                <button class="save-button" type="submit"><a style="color: white; text-decoration: none" href="producthome">Quay về trang chính</button>
            </div>
        </div>

    </body>
</html>