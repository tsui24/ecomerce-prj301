<%-- 
    Document   : ChatService
    Created on : Oct 11, 2024, 6:03:17 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>WebSocket Chat with Role</title>
    <a href="${requestScope.redirect}"><h4>Quay lại</h4></a>
        <script type="text/javascript">
            var ws;
            var role = '<%= request.getParameter("role") %>';  // Lấy role 
            var id = '<%= request.getParameter("id") %>';  // Lấy ID 
            
            function connectWebSocket() {
                ws = new WebSocket("ws://localhost:9999/ProjectEnd/chat/" + role + "/" + id);
                ws.onopen = function () {
                    console.log("WebSocket connection established as " + role + " with ID: " + id);
                    document.getElementById("status").innerHTML = "Connected as " + role;
                };

                ws.onmessage = function (event) {
                    var chatBox = document.getElementById("chatBox");
                    chatBox.value += event.data + "\n";  
                };

                ws.onclose = function (event) {
                    console.log("WebSocket connection closed. Reconnecting in 3 seconds...");
                    document.getElementById("status").innerHTML = "Disconnected. Reconnecting...";
                    setTimeout(connectWebSocket, 3000); 
                };

                ws.onerror = function (error) {
                    console.error("WebSocket error: ", error);
                };
            }

            function sendMessage() {
                var message = document.getElementById("message").value;
                var chatBox = document.getElementById("chatBox");

                if (ws.readyState === WebSocket.OPEN) {
                    ws.send(message);  // Gửi tin nhắn tới server

                    if (role === "admin") {

                        chatBox.value += "You (Admin): " + message.split(':')[1] + "\n";
                    } else {
                        chatBox.value += "You (User): " + message + "\n";
                    }

                    document.getElementById("message").value = "";  
                } else {
                    console.error("WebSocket is not open. Current state is: " + ws.readyState);
                }
            }

            window.onload = function () {
                connectWebSocket();  
            };
        </script>
    </head>
    <body>
        <h2>WebSocket Chat with Role: <%= request.getParameter("role") %> and ID: <%= request.getParameter("id") %></h2>
        <p>Status: <span id="status">Connecting...</span></p>
        <textarea id="chatBox" rows="10" cols="50" readonly></textarea><br>
        <input type="text" id="message" placeholder="Enter your message here"/>
        <button onclick="sendMessage()">Send</button>
    </body>
</html>


