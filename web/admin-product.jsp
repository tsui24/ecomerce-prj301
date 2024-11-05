<%-- 
    Document   : admin-product
    Created on : Oct 7, 2024, 9:33:44 PM
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
        <!-- Thêm Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                display: flex;
                height: 100vh; /* Full height for sidebar */
                margin: 0;
            }
            .sidebar {
                width: 250px;
                height: 100%;
                background-color: #343a40;
                position: fixed;
                top: 0;
                left: 0;
                padding: 20px;
                overflow-y: auto; /* Allow scrolling if content overflows */
            }
            .sidebar a {
                display: block;
                color: white;
                text-decoration: none;
                padding: 10px;
                margin: 10px 0;
                border-radius: 4px;
            }
            .sidebar a:hover {
                background-color: #495057;
            }
            .sidebar .logo {
                color: white;
                font-size: 24px;
                margin-bottom: 30px;
            }
            /* Main content */
            .content {
                margin-left: 250px; /* Same as sidebar width */
                padding: 20px;
                width: calc(100% - 250px); /* Full width minus sidebar */
                overflow-y: auto; /* Allow scrolling if content overflows */
            }
        </style>
    </head>
    <body>
        <div class="sidebar">
            <div class="logo">Laptopshop</div>
            <div class="features">
                <a href="control?search=">Dashboard</a>
                <a href="usercontrol">User</a>
                <a href="chart?type=sales">Statistic</a>
                <a href="order">Order</a>
                <a href="#" id="messagingLink">Messaging (${requestScope.isread})</a>
                <a href="logout.jsp">Đăng xuất</a>
            </div>
        </div>
        <div class="content">
            <h2>Product Management</h2>
            <form action="control" method="get">
                <input name="search" class="search-button"/>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
            <!-- Button thêm sản phẩm mới -->
            <button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#addProductModal">Add Product</button>

            <!-- Table danh sách sản phẩm -->
            <table class="table table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Slug</th>
                        <th>Description</th>
                        <th>Category ID</th>
                        <th>Storage</th>
                        <th>Price</th>
                        <th>Created At</th>
                        <th>Brand</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Lặp qua các sản phẩm từ server -->
                    <c:forEach var="product" items="${requestScope.listp}">
                        <tr>
                            <td>${product.id}</td>
                            <td><a href="managevariant?productId=${product.id}">${product.name}</a></td>
                            <td>${product.slug}</td>
                            <td>${product.description}</td>
                            <td>${product.categoryId}</td>
                            <td>${product.storage}</td>
                            <td>${product.priceDefault}</td>
                            <td>${product.createAt}</td>
                            <td>${product.brand}</td>
                            <td>
                                <!-- Nút sửa và xóa -->
                                <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#editProductModal${product.id}">Edit</button>
                                <form id="deleteForm" action="control?action=delete" method="POST" style="display:none;">
                                    <input type="hidden" name="id" id="productId">
                                </form>
                                <button class="btn btn-danger btn-sm" onclick="deleteProduct(${product.id})">Delete</button>
                                <button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#addImageModal${product.id}">Add Product Image</button>
                            </td>
                        </tr>

                        <!-- Modal chỉnh sửa sản phẩm -->
                    <div class="modal fade" id="editProductModal${product.id}" tabindex="-1" aria-labelledby="editProductLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editProductLabel">Edit Product</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="control?action=edit&id=${product.id}" method="post">
                                        <input type="hidden" name="id" value="${product.id}">
                                        <div class="mb-3">
                                            <label for="name" class="form-label">Product Name</label>
                                            <input type="text" class="form-control" id="name" name="name" value="${product.name}">
                                        </div>
                                        <div class="mb-3">
                                            <label for="slug" class="form-label">Slug</label>
                                            <input type="text" class="form-control" id="slug" name="slug" value="${product.slug}">
                                        </div>
                                        <div class="mb-3">
                                            <label for="description" class="form-label">Description</label>
                                            <textarea class="form-control" id="description" name="description">${product.description}</textarea>
                                        </div>
                                        <div class="mb-3">
                                            <label for="categoryId" class="form-label">Category ID</label>
                                            <input type="number" class="form-control" id="categoryId" name="categoryId" value="${product.categoryId}">
                                        </div>
                                        <div class="mb-3">
                                            <label for="storage" class="form-label">Storage</label>
                                            <input type="text" class="form-control" id="storage" name="storage" value="${product.storage}">
                                        </div>
                                        <div class="mb-3">
                                            <label for="priceDefault" class="form-label">Price</label>
                                            <input type="number" step="0.01" class="form-control" id="priceDefault" name="priceDefault" value="${product.priceDefault}">
                                        </div>
                                        <div class="mb-3">
                                            <label for="brand" class="form-label">Brand</label>
                                            <input type="text" class="form-control" id="brand" name="brand" value="${product.brand}">
                                        </div>
                                        <button type="submit" class="btn btn-primary">Save changes</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Modal thêm hình ảnh sản phẩm -->
                    <div class="modal fade" id="addImageModal${product.id}" tabindex="-1" aria-labelledby="addImageLabel${product.id}" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="addImageLabel${product.id}">Add Product Image</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="control?action=addImage" method="post" enctype="multipart/form-data">
                                        <input type="hidden" name="productId" value="${product.id}">  
                                        <div class="mb-3">
                                            <label for="img" class="form-label">Image</label>
                                            <input type="file" class="form-control" id="img" name="img" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="alt" class="form-label">Alt Text</label>
                                            <input type="text" class="form-control" id="alt" name="alt" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="type" class="form-label">Type</label>
                                            <input type="text" class="form-control" id="type" name="type" required>
                                        </div>
                                        <button type="submit" class="btn btn-success">Submit</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Modal thêm sản phẩm mới -->
        <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addProductLabel">Add New Product</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="control?action=add" method="post">
                            <div class="mb-3">
                                <label for="name" class="form-label">Product Name</label>
                                <input type="text" class="form-control" id="name" name="name" required>
                            </div>
                            <div class="mb-3">
                                <label for="slug" class="form-label">Slug</label>
                                <input type="text" class="form-control" id="slug" name="slug" required>
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" id="description" name="description" required></textarea>
                            </div>
                            <div class="mb-3">
                                <label for="categoryId" class="form-label">Category ID</label>
                                <input type="number" class="form-control" id="categoryId" name="categoryId" required>
                            </div>
                            <div class="mb-3">
                                <label for="storage" class="form-label">Storage</label>
                                <input type="text" class="form-control" id="storage" name="storage" required>
                            </div>
                            <div class="mb-3">
                                <label for="priceDefault" class="form-label">Price</label>
                                <input type="number" step="0.01" class="form-control" id="priceDefault" name="priceDefault" required>
                            </div>
                            <div class="mb-3">
                                <label for="brand" class="form-label">Brand</label>
                                <input type="text" class="form-control" id="brand" name="brand" required>
                            </div>
                            <button type="submit" class="btn btn-primary">Add Product</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Thêm Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>

        <script>
                                    function deleteProduct(productId) {
                                        if (confirm("Are you sure you want to delete this product?")) {
                                            document.getElementById("productId").value = productId;
                                            document.getElementById("deleteForm").submit();
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


