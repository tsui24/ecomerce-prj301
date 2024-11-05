<%-- 
    Document   : admin-variant
    Created on : Oct 23, 2024, 11:50:56 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                padding: 20px;
            }
            .btn-edit {
                background-color: #ffc107;
                color: white;
            }
            .btn-delete {
                background-color: #dc3545;
                color: white;
            }
            .btn-add {
                background-color: #007bff;
                color: white;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1 class="mt-4">Product Management</h1>
            <a href="control"><h4>Quay Lại</h4></a>
            <!-- Add Product Button -->
            <button class="btn btn-add my-3" data-bs-toggle="modal" data-bs-target="#addProductModal">Add Product</button>

            <!-- Product Table -->
            <table class="table table-bordered">
                <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>ProductId</th>
                        <th>Sku</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Sold Quantity</th>
                        <th>Storage</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.lvariant}" var="c">
                        <tr>
                            <td>${c.id}</td>
                            <td>${c.productId}</td>
                            <td>${c.sku}</td>
                            <td>${c.price}</td>
                            <td>${c.quantity}</td>
                            <td>${c.soldQuantity}</td>
                            <td>${c.storage}</td>
                            <td>
                                <!-- Edit Button -->
                                <button class="btn btn-edit" data-bs-toggle="modal" 
                                        data-bs-target="#editProductModal-${c.id}">Edit</button>
                            </td>
                        </tr>

                        <!-- Edit Product Modal -->
                    <div class="modal fade" id="editProductModal-${c.id}" tabindex="-1" 
                         aria-labelledby="editProductLabel-${c.id}" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editProductLabel-${c.id}">Edit Product</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="updateProduct?id=${c.id}" method="post">
                                        <div class="mb-3">
                                            <label class="form-label">SKU</label>
                                            <input type="text" class="form-control" name="sku" value="${c.sku}" required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Price</label>
                                            <input type="number" class="form-control" name="price" value="${c.price}" required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Quantity</label>
                                            <input type="number" class="form-control" name="quantity" value="${c.quantity}" required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Sold Quantity</label>
                                            <input type="number" class="form-control" name="soldQuantity" value="${c.soldQuantity}" required>
                                        </div>
                                        <button type="submit" class="btn btn-primary">Save Changes</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Add Product Modal -->
        <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addProductLabel">Add New Product</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body">
                        <form action="managevariant" method="post" onsubmit="return validateForm(event)">
                            <div class="mb-3">
                                <input type="hidden" class="form-control" name="productId" value="${requestScope.productId}" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Option Name</label>
                                <input type="text" class="form-control" name="option" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Storage</label>
                                <input type="text" class="form-control" name="storage" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Option Value</label>
                                <input type="text" class="form-control" name="optionvalue" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Price</label>
                                <input type="number" step="0.01" class="form-control" name="price" id="price" required>
                                <div class="invalid-feedback">Price must be 0 or greater.</div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Quantity</label>
                                <input type="number" class="form-control" name="quantity" id="quantity" required>
                                <div class="invalid-feedback">Quantity must be 0 or greater.</div>
                            </div>

                            <button type="submit" class="btn btn-primary">Add Product</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function validateForm(event) {
                let isValid = true;

                const priceInput = document.getElementById('price');
                const quantityInput = document.getElementById('quantity');

                priceInput.classList.remove('is-invalid');
                quantityInput.classList.remove('is-invalid');

                if (priceInput.value < 0) {
                    priceInput.classList.add('is-invalid');
                    isValid = false;
                }
                if (quantityInput.value < 0) {
                    quantityInput.classList.add('is-invalid');
                    isValid = false;
                }

                if (!isValid) {
                    event.preventDefault();
                }

                return isValid;
            }
        </script>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
    </body>
</html>

