package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Message;

public class MessageDAO extends DBContext {

    // Lưu tin nhắn vào cơ sở dữ liệu
    public void saveMessage(Message message) {
        String sql = "INSERT INTO Messages (sender_id, receiver_id, message, sent_at, is_read) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, message.getSenderId());
            statement.setInt(2, message.getReceiverId());
            statement.setString(3, message.getMessage());
            statement.setTimestamp(4, message.getSentAt());
            statement.setBoolean(5, message.isRead());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //tự động xóa tin nhắn sau 1 ngày
    public void autoDeleteMessage() {
        String sql = "DELETE FROM Messages\n"
                + "WHERE sent_at < DATEADD(DAY, -1, GETDATE());";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public int getQuantitiesMessages(int userId) {
    String sql = "SELECT COUNT(*) AS num " +
                 "FROM Messages " +
                 "WHERE receiver_id = ? AND is_read = 0";

    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setInt(1, userId); 
        try (ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("num");
            }
        }
    } catch (Exception e) {
        e.printStackTrace(); 
    }
    return 0;
}
    public static void main(String[] args) {
        MessageDAO m = new MessageDAO();
        System.out.println(m.getQuantitiesMessages(2));
    }

    // Lấy danh sách tin nhắn chưa đọc cho một người nhận từ một người gửi
    public List<Message> getUnreadMessages(int senderId, int receiverId) {
        String sql = "SELECT * FROM Messages WHERE sender_id = ? AND receiver_id = ? AND is_read = 0";
        List<Message> messages = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, senderId);
            statement.setInt(2, receiverId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Message message = new Message(
                            resultSet.getInt("id"),
                            resultSet.getInt("sender_id"),
                            resultSet.getInt("receiver_id"),
                            resultSet.getString("message"),
                            resultSet.getTimestamp("sent_at"),
                            resultSet.getBoolean("is_read")
                    );
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    // Đánh dấu một tin nhắn là đã đọc
    public void markMessageAsRead(int messageId) {
        String sql = "UPDATE Messages SET is_read = 1 WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, messageId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Đánh dấu tất cả tin nhắn chưa đọc giữa một người gửi và người nhận là đã đọc
    public void markMessagesAsRead(int senderId, int receiverId) {
        String sql = "UPDATE Messages SET is_read = 1 WHERE sender_id = ? AND receiver_id = ? AND is_read = 0";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, senderId);
            statement.setInt(2, receiverId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy tin nhắn chưa đọc cho một người nhận
    public List<Message> getPendingMessages(int receiverId) {
        String sql = "SELECT * FROM Messages WHERE receiver_id = ? AND is_read = 0";
        List<Message> messages = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, receiverId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Message message = new Message(
                            resultSet.getInt("id"),
                            resultSet.getInt("sender_id"),
                            resultSet.getInt("receiver_id"),
                            resultSet.getString("message"),
                            resultSet.getTimestamp("sent_at"),
                            resultSet.getBoolean("is_read")
                    );
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
