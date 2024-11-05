package Socket;

import dal.MessageDAO;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import model.Message;

@ServerEndpoint("/chat/{role}/{userId}")
public class WebSocketServer {

    private static final Map<Integer, Session> onlineUsers = new ConcurrentHashMap<>();
    private static boolean isAdminOnline = false;
    private static Session adminSession = null;
    private final MessageDAO messageDAO = new MessageDAO();

    @OnOpen
    public void onOpen(Session session, @PathParam("role") String role, @PathParam("userId") int userId) {
        onlineUsers.put(userId, session);
        System.out.println("Kết nối: " + session.getId() + " với vai trò " + role + " (UserID: " + userId + ")");

        if (role.equalsIgnoreCase("admin")) {
            isAdminOnline = true;
            adminSession = session;
            sendPendingMessagesForAdmin(); 
        } else {
            sendPendingMessagesForUser(userId, session); 
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        Integer userId = getUserIdFromSession(session);

        if (userId == null) {
            System.err.println("Không xác định được userId cho session: " + session.getId());
            return;
        }

        if (session == adminSession) {
            handleAdminMessage(message);
        } else {
            handleUserMessage(userId, message);
        }
    }

    @OnClose
    public void onClose(Session session) {
        Integer userId = getUserIdFromSession(session);

        onlineUsers.remove(userId);

        if (session == adminSession) {
            isAdminOnline = false;
            adminSession = null;
            System.out.println("Admin đã ngắt kết nối.");
        }

        System.out.println("Ngắt kết nối: " + session.getId() + " (UserID: " + userId + ")");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Lỗi xảy ra ở session: " + session.getId());
        throwable.printStackTrace();
    }

    private void handleAdminMessage(String message) throws IOException {
        String[] parts = message.split(":", 2);
        if (parts.length < 2) {
            System.err.println("Sai định dạng tin nhắn từ admin.");
            return;
        }

        int targetUserId = Integer.parseInt(parts[0].trim());
        String adminMessage = "Admin: " + parts[1].trim();

        Session userSession = onlineUsers.get(targetUserId);
        if (userSession != null && userSession.isOpen()) {
            userSession.getBasicRemote().sendText(adminMessage);
        } else {
            saveMessageToDatabase(1, targetUserId, adminMessage); // Lưu tin nhắn nếu user offline
        }
    }

    // Xử lý tin nhắn từ user, gửi tới admin hoặc lưu nếu admin offline
    private void handleUserMessage(int userId, String message) throws IOException {
        String formattedMessage = "[" + userId + "]: " + message;

        if (isAdminOnline && adminSession.isOpen()) {
            adminSession.getBasicRemote().sendText(formattedMessage);
        } else {
            saveMessageToDatabase(userId, 1, message); 
        }
    }

    // Gửi tin nhắn chờ cho admin khi online
    private void sendPendingMessagesForAdmin() {
        List<Message> pendingMessages = messageDAO.getPendingMessages(1); 

        try {
            for (Message message : pendingMessages) {
                String msgToSend = "Chờ từ " + message.getSenderId() + ": " + message.getMessage();
                adminSession.getBasicRemote().sendText(msgToSend);
                messageDAO.markMessageAsRead(message.getId());
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi gửi tin nhắn chờ cho admin.");
            e.printStackTrace();
        }
    }

    // Gửi tin nhắn chờ cho user khi online
    private void sendPendingMessagesForUser(int userId, Session session) {
        List<Message> pendingMessages = messageDAO.getPendingMessages(userId); // Lấy tin nhắn gửi tới user

        try {
            for (Message message : pendingMessages) {
                String msgToSend = "Chờ từ Admin: " + message.getMessage();
                session.getBasicRemote().sendText(msgToSend);
                messageDAO.markMessageAsRead(message.getId());
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi gửi tin nhắn chờ cho user.");
            e.printStackTrace();
        }
    }

    // Lưu tin nhắn vào cơ sở dữ liệu
    private void saveMessageToDatabase(int senderId, int receiverId, String message) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Message newMessage = new Message(senderId, receiverId, message, currentTimestamp, false);
        messageDAO.saveMessage(newMessage);
    }

    // Lấy userId từ session
    private Integer getUserIdFromSession(Session session) {
        for (Map.Entry<Integer, Session> entry : onlineUsers.entrySet()) {
            if (entry.getValue().equals(session)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
