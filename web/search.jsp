<%-- 
    Document   : search
    Created on : Oct 21, 2024, 4:18:15 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product Grid</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/search.css">
        <style>
            .product-card img{
                width: 200px;
                height: 150px;
            }
            .discount{
                margin: 10px auto;
                width: 150px;
            }
            .quantity{
                text-align: center;
            }
        </style>
    </head>
    <body>

        <%@include file="header.jsp" %>
        <h4 class="quantity">Tìm thấy ${requestScope.quantity} sản phẩm cho từ khóa "${requestScope.key}"</h4>
        <div class="product-grid container">
            <c:forEach items="${requestScope.list}" var="c" varStatus="status">
                <div class="product-card">
                    <c:choose>
                        <c:when test="${empty limg[status.index]}">
                            <a href="detail?id=${c.id}&sku=${c.slug}-default"><img src="https://via.placeholder.com/150" alt="iPhone 16 Pro"></a>
                        </c:when>
                        <c:otherwise>
                        <a href="detail?id=${c.id}&sku=${c.slug}-default"><img src="./asset/image/${limg[status.index]}" alt="iphone"></a>
                            
                        </c:otherwise>
                    </c:choose>
                    <div class="product-info">
                        <h3>${c.name}</h3>
                        <p class="price">${c.priceDefault} $</p>
                        <p class="discount">Giảm 2 triệu qua thẻ tín dụng, ví điện tử</p>
                        <button class="installment-btn">Trả góp 0%</button>
                    </div>
                </div>
            </c:forEach>




        </div>
    </body>
</html>

