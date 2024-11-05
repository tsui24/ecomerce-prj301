<%-- 
    Document   : cart
    Created on : Oct 17, 2024, 11:21:30 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Shopping Cart</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/cart.css">
        <style>
            .item-details img{
                width: 200px;
                height: 150px;
            }
        </style>
    </head>
    <body>
        <div class="cart-container">
            <a href="producthome">Quay về</a>
            <header>

                <h1>Giỏ hàng của bạn</h1>
            </header>
            <div class="cart-item">
                <input type="checkbox" id="select-all" onclick="toggleSelectAll()">
                <label for="select-all">Chọn tất cả</label>
            </div>
            <c:forEach items="${requestScope.list}" var="c" varStatus="status">
                <div class="cart-item" data-product-id="${c.id}" data-quantity="${c.quantity}">
                    <input type="checkbox" class="product-checkbox" onchange="updateSelectAll()">
                    <div class="item-details">
                        <img src="./asset/image/${limg[status.index]}" alt="${product[status.index].name}">
                        <div class="item-info">
                            <h2>${product[status.index].name} | ${ltype[status.index]}</h2>
                            <p class="price">
                                <span class="current-price">${c.price}$</span>
                            </p>
                            <div class="promotion">Giảm ngay 500.000đ thanh toán qua thẻ JCB MB</div>
                            <div class="warranty">Bảo vệ toàn diện với Bảo hành mở rộng</div>
                        </div>
                        <div class="quantity-controls">
                            <button class="quantity-btn" onclick="changeQuantity(-1, this)">-</button>
                            <span class="quantity">1</span>
                            <button class="quantity-btn" onclick="changeQuantity(1, this)">+</button>
                            <form action="cart" method="POST" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="userId" value="${requestScope.userId}">
                                <input type="hidden" name="variantId" value="${c.id}">
                                <button type="submit" class="remove-btn">xóa</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>




            <div class="subtotal">
                <p>Tạm tính: <span>0đ</span></p>
                <p>Chưa gồm chiết khấu SMember</p>
            </div>
            <button class="checkout-btn" onclick="submitCart()">Mua ngay</button>
        </div>

        <script>
            function submitCart() {
                const productCheckboxes = document.querySelectorAll('.product-checkbox');
                const quantities = document.querySelectorAll('.quantity');
                const selectedProducts = [];

                productCheckboxes.forEach((checkbox, index) => {
                    if (checkbox.checked) {
                        const variantId = checkbox.closest('.cart-item').dataset.productId;
                        const quantity = parseInt(quantities[index].textContent);
                        selectedProducts.push({variantId: variantId, quantity: quantity});
                    }
                });

                if (selectedProducts.length > 0) {
                    const form = document.createElement('form');
                    form.method = 'POST';
                    form.action = 'checkout'; // Chuyển sang trang checkout

                    selectedProducts.forEach(product => {
                        const inputVariant = document.createElement('input');
                        inputVariant.type = 'hidden';
                        inputVariant.name = 'variantId';
                        inputVariant.value = product.variantId;
                        form.appendChild(inputVariant);

                        const inputQuantity = document.createElement('input');
                        inputQuantity.type = 'hidden';
                        inputQuantity.name = 'quantity';
                        inputQuantity.value = product.quantity;
                        form.appendChild(inputQuantity);
                    });

                    document.body.appendChild(form);
                    form.submit();
                } else {
                    alert('Vui lòng chọn ít nhất một sản phẩm!');
                }
            }

            function removeItem(userId, variantId) {
                if (confirm('Are u sure delete ' + id)) {
                    window.location = 'cart?id=' + userId + 'action=delete&variantid=' + variantId;
                }
            }


            function changeQuantity(amount, button) {
                const quantityElement = button.parentElement.querySelector('.quantity');
                const cartItem = button.closest('.cart-item');
                const stock = parseInt(cartItem.dataset.quantity); 

                let currentQuantity = parseInt(quantityElement.textContent);

                currentQuantity += amount;

                if (currentQuantity > stock) {
                    alert('Số lượng bạn chọn vượt quá số lượng tồn kho!');
                    currentQuantity = stock;
                } else if (currentQuantity < 1) {
                    currentQuantity = 1;
                }

                quantityElement.textContent = currentQuantity;
                updateSubtotal(); 
            }

            function toggleSelectAll() {
                const selectAllCheckbox = document.getElementById('select-all');
                const productCheckboxes = document.querySelectorAll('.product-checkbox');

                productCheckboxes.forEach(checkbox => {
                    checkbox.checked = selectAllCheckbox.checked;
                });

                updateSubtotal();
            }

            function updateSelectAll() {
                const productCheckboxes = document.querySelectorAll('.product-checkbox');
                const selectAllCheckbox = document.getElementById('select-all');

                selectAllCheckbox.checked = Array.from(productCheckboxes).every(checkbox => checkbox.checked);
                updateSubtotal();
            }

            function updateSubtotal() {
                const quantities = document.querySelectorAll('.quantity');
                const productCheckboxes = document.querySelectorAll('.product-checkbox');
                let total = 0;

                quantities.forEach((quantityElement, index) => {
                    const quantity = parseInt(quantityElement.textContent);
                    const priceElement = document.querySelectorAll('.current-price')[index];
                    const price = parseFloat(priceElement.textContent.replace(/[,đ]/g, '').trim()); // Dùng parseFloat
                    const checkbox = productCheckboxes[index];

                    if (checkbox.checked) {
                        total += quantity * price;
                    }
                });

                // Giữ nguyên số thập phân và thêm đơn vị tiền tệ
                document.querySelector('.subtotal span').textContent = total.toFixed(2).replace('.', ',') + 'đ';
            }
        </script>
    </body>
</html>

