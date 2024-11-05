<%-- 
    Document   : payment
    Created on : Oct 18, 2024, 10:23:40 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thanh toán</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/endpay.css">
        <style>
            .tab{
                width: 100%;
            }
        </style>

    </head>
    <body>
        <div class="checkout-container">
            <div class="header">
                <h2>Thanh toán</h2>
            </div>



            <div class="checkout-content">


                <div class="order-summary">
                    <p>Số lượng sản phẩm <span>${requestScope.totalproduct}</span></p>
                    <p>Tiền hàng (tạm tính) <span>${requestScope.total}</span></p>
                    <p>Phí vận chuyển <span>Miễn phí</span></p>
                    <hr>
                    <p class="total">Tổng tiền (đã gồm VAT) <span>${requestScope.total}</span></p>
                </div>

                <div class="customer-info">
                    <h3>THÔNG TIN NHẬN HÀNG</h3>
                    <p>Khách hàng: <span>${requestScope.customerName}</span></p>
                    <p>Số điện thoại: <span></span>${requestScope.customerPhone}</p>
                    <p>Nhận hàng tại: <span>${requestScope.customerAddress}</span></p>
                </div>
                <div class="checkout-footer">
                    <p style="display: flex; justify-content: space-around; align-items: center; margin: 10px">Tổng tiền tạm tính: <span style="color:red; font-size: 24px; font-weight: 700">${requestScope.total}$</span></p>

                    <form action="bill" method="POST">
                        <input type="hidden" name="customerName" value="${requestScope.customerName}">
                        <input type="hidden" name="customerPhone" value="${requestScope.customerPhone}">
                        <input type="hidden" name="customerAddress" value="${requestScope.customerAddress}">
                        <input type="hidden" name="total" value="${requestScope.total}">
                        <input type="hidden" name="totalProduct" value="${requestScope.totalproduct}">
                        <c:forEach var="variantId" items="${requestScope.variantIds}">
                            <input type="hidden" name="variantIds" value="${variantId}">
                        </c:forEach>

                        <c:forEach var="quantity" items="${requestScope.quantities}">
                            <input type="hidden" name="quantities" value="${quantity}">
                        </c:forEach>
                        <button type="submit" class="btn-pay">Thanh toán</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

