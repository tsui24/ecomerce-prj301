<%-- 
    Document   : upload
    Created on : Nov 5, 2024, 12:22:36 AM
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
        <h2>Upload Excel File to Generate SQL</h2>
        <form action="uploadExcel" method="post" enctype="multipart/form-data">
            <label for="tableName">Table Name:</label>
            <input type="text" id="tableName" name="tableName" required><br><br>

            <label for="file">Choose Excel File:</label>
            <input type="file" id="file" name="file" accept=".xlsx" required><br><br>

            <button type="submit">Upload and Generate SQL</button>
        </form>
    </body>
</html>
