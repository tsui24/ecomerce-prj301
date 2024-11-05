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
            }
            .sidebar {
                width: 230px;
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
            .content {
                margin-left: 220px; /* Offset for sidebar */
                flex-grow: 1; /* Take the remaining space */
                padding: 20px;
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
            <button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#addUserModal">Add User</button>
            <table class="table table-bordered">
                <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Banned</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.luser}" var="u">
                        <tr>
                            <td>${u.id}</td>
                            <td>${u.firstName}</td>
                            <td>${u.lastName}</td>
                            <td>${u.email}</td>
                            <td>${u.role}</td>
                            <td>${u.isBanned}</td>
                            <td>
                                <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editUserModal-${u.id}">Edit</button>
                                <button class="btn btn-danger" onclick="deleteUser(${u.id})">Delete</button>
                            </td>
                        </tr>
                        <!-- Edit User Modal -->
                    <div class="modal fade" id="editUserModal-${u.id}" tabindex="-1" aria-labelledby="editUserLabel-${u.id}" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editUserLabel-${u.id}">Edit User</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="usercontrol?action=edit&id=${u.id}" method="post">
                                        <div class="mb-3">
                                            <label for="firstName-${u.id}" class="form-label">First Name</label>
                                            <input type="text" class="form-control" id="firstName-${u.id}" name="firstName" value="${u.firstName}" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="lastName-${u.id}" class="form-label">Last Name</label>
                                            <input type="text" class="form-control" id="lastName-${u.id}" name="lastName" value="${u.lastName}" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="email-${u.id}" class="form-label">Email</label>
                                            <input type="email" class="form-control" id="email-${u.id}" name="email" value="${u.email}" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="role-${u.id}" class="form-label">Role</label>
                                            <input type="text" class="form-control" id="role-${u.id}" name="role" value="${u.role}" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="isBanned-${u.id}" class="form-label">Banned</label>
                                            <select class="form-select" id="isBanned-${u.id}" name="isBanned">
                                                <option value="false">No</option>
                                                <option value="true">Yes</option>
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

        <!-- Add User Modal -->
        <div class="modal fade" id="addUserModal" tabindex="-1" aria-labelledby="addUserLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addUserLabel">Add User</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="usercontrol?action=add" method="post">

                            <div class="mb-3">
                                <label for="firstName" class="form-label">UserName</label>
                                <input type="text" class="form-control" id="userName" name="userName" required>
                            </div>
                            <div class="mb-3">
                                <label for="firstName" class="form-label">PassWord</label>
                                <input type="text" class="form-control" id="password" name="password" required>
                            </div>
                            <div class="mb-3">
                                <label for="firstName" class="form-label">First Name</label>
                                <input type="text" class="form-control" id="firstName" name="firstName" required>
                            </div>
                            <div class="mb-3">
                                <label for="lastName" class="form-label">Last Name</label>
                                <input type="text" class="form-control" id="lastName" name="lastName" required>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                            <div class="mb-3">
                                <label for="role" class="form-label">Role</label>
                                <input type="text" class="form-control" id="role" name="role" required>
                            </div>

                            <button type="submit" class="btn btn-primary">Add User</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
        <script>
                                    function deleteUser(userId) {
                                        if (confirm("Are you sure you want to delete this user?")) {
                                            window.location.href = `usercontrol?action=delete&id=${userId}`;
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
