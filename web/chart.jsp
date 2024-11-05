<%-- 
    Document   : chart
    Created on : Oct 20, 2024, 12:39:30 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Doanh thu và Số lượng bán ra</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.2/html2pdf.bundle.min.js"></script>

        <style>
            body {
                display: flex;
                height: 100vh;
                margin: 0;
            }
            .sidebar {
                width: 230px;
                height: 100%;
                background-color: #343a40;
                position: fixed;
                top: 0;
                left: 0;
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
                color: white;
                font-size: 24px;
                margin-bottom: 30px;
            }
            .content {
                margin-left: 250px;
                padding: 20px;
                width: calc(100% - 250px);
                overflow-y: auto;
            }
            form {
                display: flex;
                justify-content: center;
                align-items: center;
                gap: 10px;
                margin-bottom: 20px;
            }
            form input[type="number"] {
                width: 150px;
                padding: 10px;
                border: 1px solid #ced4da;
                border-radius: 5px;
                font-size: 16px;
                outline: none;
            }
            form button {
                padding: 10px 20px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .button-type {
                text-align: center;
                margin-bottom: 20px;
            }
            .button-type button {
                margin: 0 10px;
                padding: 10px 20px;
                font-size: 16px;
                cursor: pointer;
            }
            .con {
                display: flex;
                justify-content: center;
            }
            

            @media print {
                body {
                    margin: 0;
                    padding: 0;
                }
                .content {
                    page-break-before: auto;
                    page-break-after: auto;
                    page-break-inside: avoid; /* Tránh chia nhỏ nội dung */
                }
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
            <div class="button-type">
                <a href="chart?type=sales">
                    <button>Doanh số sản lượng</button>
                </a>
                <a href="chart?type=quantity">
                    <button>Thống kê số lượng</button>
                </a>
                <h3>${requestScope.error}</h3>
            </div>

            <c:if test="${requestScope.type == 'sales'}">
                <form action="chart" method="get">
                    <input type="hidden" name="type" value="${requestScope.type}" />
                    <input type="number" placeholder="Year" name="year" min="2000" max="2100" required />
                    <button type="submit">Enter</button>
                </form>

                <h2 style="text-align: center">
                    Biểu đồ Doanh thu và Số lượng bán ra theo tháng - ${requestScope.year}
                </h2>

                <canvas id="mixedChart" width="400" height="200"></canvas>
                </c:if>

            <c:if test="${requestScope.type == 'quantity'}">
                <form action="chart" method="get">
                    <input type="hidden" name="type" value="${requestScope.type}" />
                    <input type="number" placeholder="Year" name="year" min="2000" max="2100" required />
                    <input type="number" placeholder="Month" name="month" min="1" max="12" required />
                    <button type="submit">Enter</button>
                </form>
                <div class="con">
                    <canvas id="myChart" class="chartquantity" style="width:100%;max-width:400px; max-height: 550px"></canvas>

                </div>
            </c:if>
            <button id="exportPdf" style="margin-top: 20px;">Xuất PDF</button>

        </div>

        <script>
            <c:if test="${requestScope.type == 'quantity'}">
            const productNames = ${requestScope.productNames};
            const quantities = ${requestScope.quantities};

            function getRandomColor() {
                const letters = '0123456789ABCDEF';
                let color = '#';
                for (let i = 0; i < 6; i++) {
                    color += letters[Math.floor(Math.random() * 16)];
                }
                return color;
            }

            const barColors = productNames.map(() => getRandomColor());
            new Chart("myChart", {
                type: "doughnut",
                data: {
                    labels: productNames,
                    datasets: [{
                            backgroundColor: barColors,
                            data: quantities
                        }]
                },
                options: {
                    title: {
                        display: true,
                        text: "Thống kê số lượng sản phẩm"
                    }
                }
            });

            </c:if>

            <c:if test="${requestScope.type == 'sales'}">
            const revenues = JSON.parse('${requestScope.revenues}');
            const quantities = JSON.parse('${requestScope.quantities}');

            const ctx = document.getElementById('mixedChart').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                    datasets: [
                        {
                            type: 'line',
                            label: 'Doanh thu',
                            data: revenues,
                            borderColor: 'rgba(255, 99, 132, 1)',
                            backgroundColor: 'rgba(255, 99, 132, 0.5)',
                            yAxisID: 'y1',
                        },
                        {
                            type: 'bar',
                            label: 'Số lượng',
                            data: quantities,
                            backgroundColor: 'rgba(54, 162, 235, 0.5)',
                            yAxisID: 'y2',
                        }
                    ]
                },
                options: {
                    responsive: true,
                    scales: {
                        y1: {beginAtZero: true, position: 'left', title: {text: 'Doanh thu (VNĐ)', display: true}},
                        y2: {beginAtZero: true, position: 'right', grid: {drawOnChartArea: false}, title: {text: 'Số lượng (Sản phẩm)', display: true}}
                    }
                }
            });
            </c:if>

            document.addEventListener("DOMContentLoaded", function () {
                var role = "${sessionScope.user.role}";
                var userId = "${sessionScope.user.id}";
                var currentUrl = window.location.href; // Lấy URL hiện tại

                // Tạo URL mới cho liên kết
                var messagingUrl = "messaging?role=" + encodeURIComponent(role) + "&id=" + encodeURIComponent(userId) + "&redirect=" + encodeURIComponent(currentUrl);

                // Cập nhật href của liên kết
                document.getElementById("messagingLink").href = messagingUrl;
            });
            document.getElementById("exportPdf").addEventListener("click", function () {
                const content = document.querySelector('.content'); // Chọn phần cần xuất
                const opt = {
                    margin: 0.5,
                    filename: 'thong_ke.pdf',
                    image: {type: 'jpeg', quality: 0.98},
                    html2canvas: {scale: 2},
                    jsPDF: {unit: 'in', format: 'a2', orientation: 'portrait'}
                };
                html2pdf().set(opt).from(content).save();
            });
        </script>
    </script>
</body>
</html>
