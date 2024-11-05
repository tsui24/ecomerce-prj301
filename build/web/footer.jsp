<%-- 
    Document   : footer
    Created on : Oct 7, 2024, 4:04:28 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            /* Footer Style */
.footer {
    background-color: #333;
    color: #fff;
    padding: 40px 0;
    font-family: Arial, sans-serif;
    width: 100%
}

.footer .container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
}

.footer-columns {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
}

.footer-column {
    flex: 1;
    margin: 20px;
    min-width: 200px;
}

.footer-column h4 {
    margin-bottom: 15px;
    font-size: 18px;
    color: #ffcc00;
}

.footer-column p, .footer-column ul {
    font-size: 14px;
    line-height: 1.6;
    color: #ccc;
}

.footer-column ul {
    list-style-type: none;
    padding: 0;
}

.footer-column ul li {
    margin-bottom: 10px;
}

.footer-column ul li a {
    color: #fff;
    text-decoration: none;
}

.footer-column ul li a:hover {
    text-decoration: underline;
}

.footer-column .social-links a {
    margin-right: 10px;
}

.footer-column .social-links a img {
    width: 24px;
    height: 24px;
}

.footer-bottom {
    text-align: center;
    margin-top: 20px;
    font-size: 14px;
    color: #bbb;
}

.footer-bottom p {
    margin: 0;
}

        </style>
    </head>
    <body>
        <!-- Footer -->
<footer class="footer">
    <div class="container">
        <div class="footer-columns">
            <!-- Cột 1: Về chúng tôi -->
            <div class="footer-column">
                <h4>Về chúng tôi</h4>
                <p>Công ty chúng tôi cung cấp các sản phẩm chất lượng cao với giá cả hợp lý.</p>
            </div>
            <!-- Cột 2: Hỗ trợ khách hàng -->
            <div class="footer-column">
                <h4>Hỗ trợ khách hàng</h4>
                <ul>
                    <li><a href="#">Chính sách bảo hành</a></li>
                    <li><a href="#">Hướng dẫn mua hàng</a></li>
                    <li><a href="#">Liên hệ</a></li>
                </ul>
            </div>
            <!-- Cột 3: Liên hệ -->
            <div class="footer-column">
                <h4>Liên hệ</h4>
                <p>Email: info@company.com</p>
                <p>Điện thoại: 0123 456 789</p>
                <p>Địa chỉ: 123 Đường ABC, Quận X, TP.HCM</p>
            </div>
            <!-- Cột 4: Mạng xã hội -->
            <div class="footer-column">
                <h4>Theo dõi chúng tôi</h4>
                <div class="social-links">
                    <a href="#"><img src="icon-facebook.png" alt="Facebook"></a>
                    <a href="#"><img src="icon-twitter.png" alt="Twitter"></a>
                    <a href="#"><img src="icon-instagram.png" alt="Instagram"></a>
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <p>&copy; 2024 Công ty ABC. Tất cả các quyền được bảo lưu.</p>
        </div>
    </div>
</footer>

    </body>
</html>
