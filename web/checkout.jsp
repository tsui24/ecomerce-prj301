<%-- 
    Document   : checkout
    Created on : Oct 18, 2024, 11:56:54 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/payment.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style>
            .box {
                background-color: white;
                padding: 20px;
            }
            .title {
                margin: 20px 0;
            }
        </style>

        <title>Checkout</title>
    </head>
    <body>
        <h1 style="text-align: center;">Thông tin đơn hàng</h1>

        <div class="container">
            <form action="endpay" method="POST">
                <div class="product-list box">
                    <c:forEach items="${requestScope.variantItems}" var="variant" varStatus="status">
                        <div class="product">
                            <img src="./asset/image/${limg[status.index]}" alt="Product Image">
                            <div class="product-info">
                                <h3>${variant.sku}</h3>
                                <p class="price">${variant.price}$</p>
                                <p>Số lượng: ${quantity[status.index]}</p>
                                <input type="hidden" name="quantities" value="${quantity[status.index]}">
                                <input type="hidden" name="variantIds" value="${variant.id}">
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <h4 class="title">THÔNG TIN KHÁCH HÀNG</h4>
                <div class="customer-info box">
                    <p><b>${requestScope.user.firstName} ${requestScope.user.lastName}</b></p>
                    Email: <input type="text" name="email" value="${requestScope.user.email}" readonly>
                </div>

                <h4 class="title">THÔNG TIN NHẬN HÀNG</h4>
                <div class="delivery-info box">
                    <div class="form-group">
                        <label for="name">Tên người nhận</label>
                        <input type="text" id="name" name="customerName" 
                               value="${requestScope.user.firstName} ${requestScope.user.lastName}">
                    </div>

                    <div class="form-group">
                        <label for="phone">SĐT người nhận</label>
                        <input type="text" id="phone" name=customerPhone>
                    </div>

                    <div class="form-group">
                        <label for="address">Địa chỉ</label>
                        <input type="text" id="address" name="customerAddress" placeholder="Nhập địa chỉ">
                    </div>

                    <div class="form-group">
                        <label for="note">Ghi chú khác (nếu có)</label>
                        <input type="text" id="note" name="note" placeholder="Ghi chú">
                    </div>
                </div>

                <div class="summary">
                    <p>Tổng tiền tạm tính: <span class="total">${requestScope.total}$</span></p>
                    <input type="hidden" name="total" value="${requestScope.total}">
                </div>

                <button type="submit" class="continue-btn">Tiếp tục</button>
            </form>
        </div>
    </body>
</html>

