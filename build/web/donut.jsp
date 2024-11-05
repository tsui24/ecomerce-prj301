<%-- 
    Document   : donut
    Created on : Oct 28, 2024, 4:42:24 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>JSP Page</title>
    </head>
    <body>
        <canvas id="myChart" style="width:100%;max-width:600px"></canvas>
        <script>
            const xValues = ["Italy", "France", "Spain", "USA", "Argentina"];
            const yValues = [55, 49, 44, 24, 15];
            const barColors = ["#b91d47", "#00aba9", "#2b5797", "#e8c3b9", "#1e7145"];

            new Chart("myChart", {
                type: "doughnut",
                data: {
                    labels: xValues,
                    datasets: [{
                            backgroundColor: barColors,
                            data: yValues
                        }]
                },
                options: {
                    title: {
                        display: true,
                        text: "World Wide Wine Production 2018"
                    }
                }
            });
        </script>
    </body>


</html>
