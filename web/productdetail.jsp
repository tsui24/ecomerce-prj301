<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết sản phẩm - iPhone 15</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/detail.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

        <style>
            .option-btn {
                background-color: #f4f4f4;
                border: 2px solid #ddd;
                border-radius: 5px;
                padding: 10px 15px;
                cursor: pointer;
                margin-right: 10px;
                margin-bottom: 10px;
                transition: all 0.3s ease;
                position: relative;
            }

            .option-btn a {
                text-decoration: none;
                color: black;
                font-weight: bold;
                display: block;
            }

            .option-btn.active {
                background-color: #3498db;
                border-color: #2980b9;
            }

            .option-btn.active a {
                color: white;
            }
            .main-image{
                width: 300px;
                height: 200px;
            }

            .relate-name {
                font-size: 18px;
                font-weight: bold;
                margin: 10px 0;
                height: 60px;
            }

            .relate-price {
                color: red;
                font-size: 22px;
                font-weight: bold;
            }
            .relate-container {
                display: flex;
                gap: 20px;
                justify-content: center;
                flex-wrap: wrap;
                margin-bottom: 30px;
            }
            .relate-card {
                width: 250px;
                background-color: white;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                padding: 20px;
                position: relative;
                transition: transform 0.2s ease;
            }
            .relate-card:hover {
                transform: translateY(-5px);
            }

            .relate-card img {

                border-radius: 10px;
            }
            .discount-info {
                font-size: 12px;
                color: #555;
                margin: 5px 0;
            }

            .shipping-info {
                font-size: 12px;
                color: #555;
                background-color: #f2f2f2;
                padding: 5px;
                border-radius: 5px;
            }

            .wishlist {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-top: 10px;
            }

            .wishlist-icon {
                font-size: 18px;
                color: red;
                cursor: pointer;
            }

            .stars {
                color: #ffcc00;
                font-size: 16px;
            }
            .add-cart{

            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp" %>

        <div class="container">
            <!-- Header sản phẩm -->
            <div class="product-header">
                <h1>${requestScope.product.name} ${requestScope.variant.id}</h1>
                <div class="rating">⭐ ${requestScope.variant.soldQuantity} Đã bán</div>
            </div>

            <!-- Nội dung sản phẩm -->
            <div class="product-content">
                <!-- Hình ảnh sản phẩm -->
                <div class="image-gallery">
                    <img src="./asset/image/${requestScope.image}" 
                         alt="${requestScope.product.name}" 
                         class="main-image">
                </div>

                <!-- Thông tin sản phẩm -->
                <div class="product-info">
                    <!-- Phần giá -->
                    <div class="price-section">
                        <span class="current-price">${variant.price}</span>
                        <span class="old-price">${variant.price + 20}</span>
                    </div>

                    <!-- Lựa chọn tùy chọn sản phẩm -->
                    <c:forEach items="${requestScope.options}" var="option">
                        <div class="product-option">
                            <h4>Chọn ${option.optionName}:</h4>
                            <c:forEach items="${requestScope.optionValues}" var="value">
                                <button class="option-btn" data-sku="${option.optionName}-${value.sku}">
                                    <a href="detail?id=${requestScope.product.id}&sku=${requestScope.product.slug}-${option.optionName}-${value.sku}" 
                                       class="option-link">${value.sku}</a>
                                </button>
                            </c:forEach>
                            <button class="option-btn" data-sku="${option.optionName}-${value.sku}">
                                <a href="detail?id=${requestScope.product.id}&sku=${requestScope.product.slug}-default" 
                                   class="option-link">DEFAULT</a>
                            </button>
                        </div>
                    </c:forEach>

                    <div class="promotions">
                        <p>🎁 Giảm ngay 500.000₫ khi thanh toán qua thẻ JCB MB</p>
                    </div>

                    <!-- Nút mua ngay -->
                    <div class="add-cart">
                        <!--                        <button class="buy-now" id="buyNowBtn">Mua ngay</button>-->
                        <form action="cart" method="post">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="userId" value="${requestScope.userId}">
                            <input type="hidden" name="variantId" value="${requestScope.variant.id}"> 
                            <button class="buy-now" type="submit" id="buyNowBtn">Add to Cart </button>
                        </form>
                    </div>

                </div>
            </div>

            <!-- Thông tin cửa hàng -->
            <div class="store-info">
                <h3>Thông tin cửa hàng:</h3>
                <ul>
                    <li>📍 218-220 Trần Quang Khải, P. Tân Định</li>
                    <li>📍 55B Trần Khắc Chân, Quận 1</li>
                    <li>📍 134 Nguyễn Thái Học, Quận 1</li>
                </ul>
            </div>
        </div>
        <div class="product-relate container">
            <h4 style="font-size: 24px; margin: 20px">San pham lien quan</h4>
            <div class="relate-container">
                <c:forEach items="${requestScope.relate}" var="r" >
                    <div class="relate-card">
                        <img src="./asset/image/" alt="alt"/>
                        <div class="relate-name">${r.name}</div>
                        <div class="relate-price">${r.priceDefault}</div>
                        <div class="discount-info">S-Student giảm thêm đến 200.000đ</div>
                        <div class="shipping-info">
                            Không phí chuyển đổi khi trả góp 0% qua thẻ tín dụng kỳ hạn 3-6 tháng
                        </div>
                        <div class="wishlist">
                            <div class="stars">★★★★★</div>
                            <div class="wishlist-icon">❤ Yêu thích</div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <script>
            document.querySelectorAll('.option-link').forEach(link => {
                link.addEventListener('click', function () {
                    const button = this.parentElement;
                    const selectedSku = button.getAttribute('data-sku');  // Lấy SKU từ button
                    localStorage.setItem('selectedSku', selectedSku);     // Lưu vào localStorage
                });
            });


            window.addEventListener('DOMContentLoaded', () => {
                const selectedSku = localStorage.getItem('selectedSku');

                if (selectedSku) {
                    const buttonToActivate = document.querySelector(`button[data-sku="${selectedSku}"]`);
                    if (buttonToActivate) {
                        buttonToActivate.classList.add('active');
                    }
                }
            });
            document.getElementById('buyNowBtn').addEventListener('click', function () {
                $.ajax({
                    url: '${pageContext.request.contextPath}/checksession',
                    method: 'GET',
                    success: function (response) {
                        if (response === 'logged_in') {
                            // Đã đăng nhập, chuyển đến trang thanh toán hoặc giỏ hàng
                            //window.location.href = 'checkout.jsp';
                            document.getElementById('buyNowBtn').submit();
                        } else {
                            // Chưa đăng nhập, chuyển tới trang đăng nhập và lưu URL hiện tại
                            const currentUrl = window.location.href;
                            window.location.href = 'login.jsp?redirect=' + encodeURIComponent(currentUrl);
                        }
                    },
                    error: function () {
                        alert('Có lỗi xảy ra, vui lòng thử lại.');
                    }
                });
            });
            

            document.getElementById('addToCartBtn').addEventListener('click', function (event) {
                event.preventDefault(); // Ngăn form submit ngay lập tức
                $.ajax({
                    url: '${pageContext.request.contextPath}/checksession',
                    method: 'GET',
                    success: function (response) {
                        if (response === 'logged_in') {
                            // Đã đăng nhập, tiến hành submit form
                            document.getElementById('addToCartForm').submit();
                        } else {
                            // Chưa đăng nhập, chuyển hướng đến trang đăng nhập với URL hiện tại
                            const currentUrl = window.location.href;
                            window.location.href = 'login.jsp?redirect=' + encodeURIComponent(currentUrl);
                        }
                    },
                    error: function () {
                        alert('Có lỗi xảy ra, vui lòng thử lại.');
                    }
                });
            });

        </script>
    </body>
</html>
