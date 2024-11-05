<%-- 
    Document   : admin-user
    Created on : Oct 19, 2024, 12:25:20 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                display: flex;
                min-height: 100vh;
                font-family: 'Arial', sans-serif;
                background-color: #f8f9fa;
            }
            .sidebar {
                width: 250px;
                background-color: #343a40;
                color: white;
                position: fixed;
                top: 0;
                bottom: 0;
                padding: 20px;
                overflow-y: auto;
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
                font-size: 24px;
                margin-bottom: 30px;
                text-align: center;
            }
            .content {
                margin-left: 250px;
                padding: 20px;
                flex-grow: 1;
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
            <h1>User Management</h1>
            <form action="order" method="get" class="mb-4">
                <div class="in-put" style="display:flex; align-items: center">
                    <div class="input-group mb-2">
                        <input type="number" class="form-control" name="billId" placeholder="Search by Bill ID" />
                    </div>
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>

            </form>
            <form action="order" method="get" class="mb-4">                 
                <div class="input-group">
                    <!-- Filter by Status -->
                    <select class="form-select" name="status">
                        <option value="">All</option>
                        <option value="done">Done</option>
                        <option value="processing">Processing</option>
                        <option value="cancel">Cancel</option>
                        <option value="most">Most Total</option>>
                    </select>

                    <!-- Search Button -->
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>
            </form>
            <table class="table table-bordered">
                <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Created At</th>
                        <th>User</th>
                        <th>Location</th>
                        <th>Detail Bill</th>
                        <th>Status</th>
                        <th>Total</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.lbill}" var="u" varStatus="status">
                        <tr>
                            <td>${u.id}</td>
                            <td>${u.createdDate}</td>
                            <td>${lfullname[status.index]}</td>
                            <td>${u.location}</td>
                            <td>${ldetail[status.index]}</td>
                            <td>${u.status}</td>
                            <td>${ltotal[status.index]}</td>
                            <td>
                                <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editUserModal-${u.id}">Edit</button>

                            </td>
                        </tr>

                        <!-- Edit Entry Modal -->
                    <div class="modal fade" id="editUserModal-${u.id}" tabindex="-1" aria-labelledby="editUserLabel-${u.id}" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editUserLabel-${u.id}">Edit Status</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="order?id=${u.id}" method="post">
                                        <div class="mb-3">
                                            <label class="form-label">Status</label>
                                            <select class="form-control" name="status" required>
                                                <option value="done">Done</option>
                                                <option value="processing">Processing</option>
                                                <option value="cancel">Cancel</option>
                                            </select>
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




        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
        <script>
            
            document.addEventListener("DOMContentLoaded", function() { 
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
