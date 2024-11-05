<%-- 
    Document   : productcate
    Created on : Oct 10, 2024, 11:19:51 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Auto Banner Slider</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/product.css">

        <style>
            body{
                margin: 0;
                padding: 0;
            }
            .bannerk{
                width: 70%;
                margin: 0 auto;
            }
            .contentall{
                text-align: center;
            }
            .img-cat{
                width: 100%;
                height: 200px;
            }
            .phone{
                margin-top: 30px;
            }
            .pagination a {
                margin: 0 5px;
                padding: 8px 16px;
                background-color: #f1f1f1;
                text-decoration: none;
                color: #333;
                border-radius: 4px;
            }

            .pagination a.active {
                background-color: #4CAF50;
                color: white;
            }

            .pagination a:hover {
                background-color: #ddd;
            }
            .pagination{
                display: flex;
                justify-content: center;
                margin-bottom: 30px;
            }
            .filter-container {
                margin-bottom: 20px;
                margin-top: 30px;
            }

            .filter-container form {
                display: flex;
                justify-content: center;
                align-items: center;
                flex-wrap: wrap;
            }

            .filter-container label {
                margin-right: 10px;
            }

            .filter-container input, .filter-container select {
                margin-right: 10px;
                padding: 5px;
                border-radius: 8px;
            }

            .filter-container button {
                padding: 5px 10px;
                background-color: #4CAF50;
                color: white;
                border: none;
                cursor: pointer;
                border-radius: 8px;
            }

            .filter-container button:hover {
                background-color: #45a049;
            }
            .faq-container {
                width: 80%;
                margin: 20px auto;
                border: 1px solid #ddd;
                border-radius: 10px;
                background-color: #f9f9f9;
                padding: 10px;
            }

            .faq-header {
                background-color: #f53d2d;
                color: white;
                font-size: 24px;
                font-weight: bold;
                text-align: center;
                padding: 15px;
                border-radius: 10px 10px 0 0;
            }

            .faq-question {
                font-size: 18px;
                font-weight: bold;
                background-color: #fff;
                padding: 15px;
                border: 1px solid #ddd;
                cursor: pointer;
                margin-bottom: 5px;
                display: flex;
                justify-content: space-between;
            }

            .faq-answer {
                display: none;
                padding: 15px;
                background-color: #fff;
                border: 1px solid #ddd;
                border-top: none;
            }

            .faq-answer ul {
                padding-left: 20px;
            }

            .faq-answer li {
                list-style: disc;
                margin-bottom: 5px;
            }

            .arrow {
                transform: rotate(0deg);
                transition: transform 0.3s;
            }

            .arrow.open {
                transform: rotate(90deg);
            }
            .product-smart{

            }
        </style>
    </head>
    <body>
        <div class="contentall">
            <%@include file="header.jsp" %>


            <div id="bannerCarousel" class="carousel slide bannerk" data-bs-ride="carousel">
                <!-- Indicators (optional) -->
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#bannerCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#bannerCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#bannerCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
                </div>

                <div class="carousel-inner">
                    <!-- First slide -->
                    <div class="carousel-item active">
                        <img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:90/plain/https://dashboard.cellphones.com.vn/storage/dien-thoai-samsung-galaxy-s24-fe-home-27-9-2024.png" class="d-block w-100" alt="Banner 1">
                    </div>
                    <!-- Second slide -->
                    <div class="carousel-item">
                        <img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:90/plain/https://dashboard.cellphones.com.vn/storage/watch-gt-5-series-03-10-home-new-new.jpg" class="d-block w-100" alt="Banner 2">
                    </div>
                    <!-- Third slide -->
                    <div class="carousel-item">
                        <img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:90/plain/https://dashboard.cellphones.com.vn/storage/tecno-spark-go-1-home.jpg" class="d-block w-100" alt="Banner 3">
                    </div>
                </div>

                <!-- Controls for sliding left/right -->
                <button class="carousel-control-prev" type="button" data-bs-target="#bannerCarousel" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#bannerCarousel" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
            <div class="filter-container">
                <form action="productcategory" method="get">
                    <input type="hidden" name="id" value="${param.id}">

                    <!-- Lọc theo giá -->
                    <label for="minPrice">Giá từ:</label>
                    <input type="number" id="minPrice" name="minPrice" min="0" placeholder="Giá thấp nhất" >

                    <label for="maxPrice">đến:</label>
                    <input type="number" id="maxPrice" name="maxPrice" min="0" placeholder="Giá cao nhất" >

                    <!-- Sắp xếp -->
                    <label for="sortBy">Sắp xếp theo:</label>
                    <select id="sortBy" name="sortBy">
                        <option value="mostViewed" ${requestScope.sortBy == 'mostViewed' ? 'selected' : ''}>Xem nhiều</option>
                        <option value="priceLow" ${requestScope.sortBy == 'priceLow' ? 'selected' : ''}>Giá thấp</option>
                        <option value="priceHigh" ${requestScope.sortBy == 'priceHigh' ? 'selected' : ''}>Giá cao</option>
                        <option value="releaseDate" ${requestScope.sortBy == 'releaseDate' ? 'selected' : ''}>Ngày ra mắt</option>
                    </select>

                    <button type="submit">Lọc</button>
                </form>
            </div>
            <c:if test="${not empty message}">
                <div class="no-product-message">
                    ${message}
                </div>
            </c:if>

            <!-- Phần hiển thị danh sách sản phẩm -->
            <c:if test="${not empty data}">
                <div class="product-smart phone ">
                    <div class="product-container">
                        <!-- Product Card 1 -->
                        <c:forEach items="${requestScope.data}" var="b" varStatus="status">
                            <div class="product-card">
                                <div class="badge">Giảm 2%</div>
                                <div class="badge-tragop">Trả góp 0%</div>
                                <a href="detail?id=${b.id}&sku=${b.slug}-default"><img src="./asset/image/${listimg[status.index]}" alt="Product 1" class="img-cat"></a>
                                <div class="product-name">${b.name}</div>
                                <div class="product-price">${b.priceDefault}$</div>
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
            </c:if>
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="productcategory?id=${requestScope.cateid}&page=${currentPage - 1}&minPrice=${requestScope.minPrice}&maxPrice=${requestScope.maxPrice}&sortBy=${requestScope.sortBy}">Trước</a>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="productcategory?id=${requestScope.cateid}&page=${i}&minPrice=${requestScope.minPrice}&maxPrice=${requestScope.maxPrice}&sortBy=${requestScope.sortBy}" class="${i == currentPage ? 'active' : ''}">
                        ${i}
                    </a>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="productcategory?id=${requestScope.cateid}&page=${currentPage + 1}&minPrice=${requestScope.minPrice}&maxPrice=${requestScope.maxPrice}&sortBy=${requestScope.sortBy}">Tiếp</a>
                </c:if>
            </div>
            <div class="faq-container">
                <div class="faq-header">CÂU HỎI THƯỜNG GẶP</div>

                <div class="faq-item">
                    <div class="faq-question">
                        Vì sao tôi nên tham gia thu cũ đổi mới khi mua điện thoại tại CellphoneS?
                        <span class="arrow">›</span>
                    </div>
                    <div class="faq-answer">
                        <p>CellphoneS có chương trình thu cũ đổi mới để giúp khách hàng có cơ hội nâng cấp thiết bị với chi phí hợp lý.</p>
                    </div>
                </div>

                <div class="faq-item">
                    <div class="faq-question">
                        Thủ tục trả góp điện thoại tại CellphoneS như thế nào? Có dễ dàng không?
                        <span class="arrow">›</span>
                    </div>
                    <div class="faq-answer">
                        <p>Thủ tục trả góp tại CellphoneS vô cùng dễ dàng. Bạn chỉ cần mang theo giấy tờ cá nhân đến cửa hàng để được tư vấn và hỗ trợ.</p>
                    </div>
                </div>

                <div class="faq-item">
                    <div class="faq-question">
                        Tôi có thể bảo hành điện thoại ở đâu và chính sách bảo hành như thế nào?
                        <span class="arrow">›</span>
                    </div>
                    <div class="faq-answer">
                        <p>Bạn có thể mang điện thoại đến bất kỳ chi nhánh nào của CellphoneS để bảo hành. Chính sách bảo hành chi tiết có thể khác nhau tùy sản phẩm.</p>
                    </div>
                </div>

                <div class="faq-item">
                    <div class="faq-question">
                        Tôi có thể thanh toán qua những hình thức nào?
                        <span class="arrow">›</span>
                    </div>
                    <div class="faq-answer">
                        <p>CellphoneS cung cấp nhiều phương thức thanh toán:</p>
                        <ul>
                            <li>Tiền mặt</li>
                            <li>Quẹt thẻ</li>
                            <li>Quét mã QR VNPAY</li>
                            <li>Trả góp qua công ty tài chính</li>
                            <li>Trả góp online qua thẻ (Visa, Mastercard, JBC)</li>
                        </ul>
                    </div>
                </div>
            </div>
            <%@include file="footer.jsp" %>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


    </body>
</html>



