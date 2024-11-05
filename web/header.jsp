<%-- 
    Document   : header
    Created on : Oct 6, 2024, 12:07:14 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            *{
                box-sizing: border-box;
                margin: 0;
                padding: 0;
                font-family: Arial, sans-serif;
            }
            header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px 50px;
                background-color: #ff4d4f;
                color: white;

            }

            .logo img {
                height: 50px;
            }

            .search-bar input {
                padding: 10px;
                width: 300px;
                border-radius: 5px;
                border: none;
            }

            .search-bar button {
                padding: 10px;
                background-color: #ff4d4f;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            .cart a {
                color: white;
                text-decoration: none;
            }

            nav {
                background-color: #333;
                padding: 8px 20px;
                height: 80px;
                margin-bottom: 40px;
            }

            nav ul {
                list-style: none;
                display: flex;
                justify-content: space-around;
                align-items: center;
            }

            nav ul li a {
                color: white;
                text-decoration: none;
                padding: 20px 20px;
                display: block;
            }

            nav ul li a:hover {
                background-color: #555;
            }
            .profile {
                position: relative;
                display: inline-block;
            }

            /* Kiểu mặc định cho liên kết trong profile */
            .profile a {
                text-decoration: none;
                color: white;
                cursor: pointer;
            }

            /* Khi hover vào profile */
            .profile .dropdown-content {
                display: none;
                position: absolute;
                background-color: white;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                z-index: 1;
                text-align: left;
            }

            .profile .dropdown-content a {
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
            }

            .profile .dropdown-content a:hover {
                background-color: #ddd;
            }

            /* Hiển thị dropdown khi hover */
            .profile:hover .dropdown-content {
                display: block;
            }
            .customer-support {
                position: relative;
                display: inline-block;
                padding-right: 10px;
            }

            .badge1 {
                position: absolute;
                right:  0;
                top: -10px;
                background-color: none;
                color: white;
                border-radius: 50%;
                padding: 3px 7px;
                font-size: 12px;
                font-weight: bold;
                width: 2px;
            }

        </style>
    </head>
    <body>
        <div></div>
        <header>
            <div class="logo">
                <img src="logo.png" alt="Cellphones Fake">
            </div>
            <div class="search-bar">
                <form action="searchkey" method="GET">
                    <input type="text" name="key" placeholder="Tìm kiếm sản phẩm..." required>
                    <button type="submit">Tìm kiếm</button>
                </form>
            </div>
            <div class="cart">
                <a href="#" onclick="checkLogin(${sessionScope.user.id})">Giỏ hàng</a>
            </div>
            <div class="profile">
                <% if (user != null) { %>

                <a class="customer-support">Welcome, ${sessionScope.user.lastName}<span class="badge1">${requestScope.isread}</span></a>

                <div class="dropdown-content">
                    <a href="profile.jsp">Profile</a>
                    <a href="logout.jsp">Đăng xuất</a>
                    <a href="#" id="messagingLink">Hỗ trợ khách hàng 

                    </a>
                </div>
                <% } else { %>
                <!-- Hiển thị link Đăng nhập nếu người dùng chưa đăng nhập -->
                <a href="log">Đăng nhập</a>
                <a href="register">Đăng ký</a>

                <% } %>
            </div>
        </header>

        <!-- Navbar -->
        <nav>
            <ul>

                <li><a href="producthome">Trang chủ</a></li>
                <li><a href="productcategory?id=2">Điện thoại</a></li>
                <li><a href="productcategory?id=3">Laptop</a></li>
                <li><a href="#">Phụ kiện</a></li>
                <li><a href="#">Liên hệ</a></li>
            </ul>
        </nav>
        <script>
            function checkLogin(id) {

                if (id) {
                    window.location.href = "cart?id=" + id;
                } else {
                    alert("Vui lòng đăng nhập để xem giỏ hàng.");
                    window.location.href = "login.jsp";
                }
            }

            document.addEventListener("DOMContentLoaded", function () {
                var role = "${sessionScope.user.role}";
                var userId = "${sessionScope.user.id}";
                var currentUrl = window.location.href; // Lấy URL hiện tại

                // Tạo URL mới cho liên kết
                var messagingUrl = "messaging?role=" + encodeURIComponent(role) + "&id=" + encodeURIComponent(userId) + "&redirect=" + encodeURIComponent(currentUrl);

                // Cập nhật href của liên kết
                document.getElementById("messagingLink").href = messagingUrl;
            });

        </script>
    </body>
</html>
