<%-- 
    Document   : tableproduct
    Created on : Oct 7, 2024, 3:43:10 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/product.css">
        <style>
            .img-cat{
                width: 100%;
                height: 200px;
            }
        </style>
    </head>
    <body>
        <div class="product-smart phone">
            <h2 style="padding:0 100px; margin-bottom: 40px">Điện thoại nổi bật</h2>
            <div class="product-container">

                <!-- Product Card 1 -->
                <c:forEach items="${requestScope.listp}" var="b" varStatus="status">
                    <div class="product-card">
                        <div class="badge">Giảm 2%</div>
                        <div class="badge-tragop">Trả góp 0%</div>
                        <c:choose>
                            <c:when test="${empty listimg[status.index]}">
                                <a href="detail?id=${b.id}&sku=${b.slug}-default"><img src="https://via.placeholder.com/150" alt="iPhone 16 Pro" class="img-cat"></a>
                                </c:when>
                                <c:otherwise>
                                <a href="detail?id=${b.id}&sku=${b.slug}-default"><img src="./asset/image/${listimg[status.index]}" alt="iphone" class="img-cat"></a>

                            </c:otherwise>
                        </c:choose>
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
        <div class="product-smart laptop">
            <h2 style="padding:0 100px; margin-bottom: 40px">Máy tính nổi bật</h2>
            <div class="product-container">

                <!-- Product Card 1 -->
                <c:forEach items="${requestScope.list2}" var="c" varStatus="status">
                    <div class="product-card">
                        <div class="badge">Giảm 2%</div>
                        <div class="badge-tragop">Trả góp 0%</div>
                        
                        <c:choose>
                            <c:when test="${empty listimg2[status.index]}">
                                <a href="detail?id=${c.id}&sku=${c.slug}-default"><img src="https://via.placeholder.com/150" alt="iPhone 16 Pro" class="img-cat"></a>
                                </c:when>
                                <c:otherwise>
                                <a href="detail?id=${c.id}&sku=${c.slug}-default"><img src="./asset/image/${listimg2[status.index]}" alt="iphone" class="img-cat"></a>

                            </c:otherwise>
                        </c:choose>
                        <div class="product-name">${c.name}</div>
                        <div class="product-price">${c.priceDefault}$</div>
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
    </body>
</html>
