/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author admin
 */
public class UserDAO extends DBContext {
    public String getFullnameById(int id){
        String sql = "select * from Users where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                return rs.getString("first_name") + " "+ rs.getString("last_name");
            }
        } catch (Exception e) {
        }
        return null;
    }
    public void addUserByAdmin(User u) {
        String sql = "insert into Users(first_name, last_name, username, password, role, is_banned, email)\n"
                + "values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, u.getFirstName());
            st.setString(2, u.getLastName());
            st.setString(3, u.getUsername());
            st.setString(4, u.getPassword());
            st.setString(5, u.getRole());
            st.setBoolean(6, u.isIsBanned());
            st.setString(7, u.getEmail());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý lỗi ở đây
        }
    }

   public boolean existUserByEmail(String email) {
    String sql = "SELECT * FROM users WHERE email = ?";
    
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        
        return rs.next(); 
    } catch (SQLException e) {
        e.printStackTrace(); 
        return false; 
    }
}
public void changePassword(String plainPassword, int id) {
        String sql = "update Users set password = ? where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, hashPassword(plainPassword));
            st.setInt(2, id);
            st.executeUpdate();
        } catch (Exception e) {
        }
    }   
    public int getIdUserByEmail(String email){
        String sql = "select * from Users where email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                return rs.getInt("id");
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public static void main(String[] args) {
        UserDAO ud = new UserDAO();
        System.out.println(ud.hashPassword("1234"));
    }

    public List<User> getAllUser() {
        List<User> l = new ArrayList<>();
        String sql = "select * from Users where role = 'user'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setCreateAt(rs.getString("create_at"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                u.setAvt(rs.getString("avt"));
                u.setIsBanned(rs.getBoolean("is_banned"));
                u.setEmail(rs.getString("email"));
                u.setUsername(rs.getString("username"));
                l.add(u);
            }
        } catch (SQLException e) {
        }
        return l;
    }

    public boolean existsByUsername(String username) {
        String query = "SELECT 1 FROM [Users] WHERE username = ?";
        try (
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra xem email đã tồn tại chưa
    public boolean existsByEmail(String email) {
        String query = "SELECT 1 FROM [Users] WHERE email = ?";
        try (
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //add
    public void saveUser(User user) {
        String query = "INSERT INTO [Users] (first_name, last_name, username, email, password, role, is_banned) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getRole());
            stmt.setBoolean(7, user.isIsBanned());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User findUserbyUsername(String username) {
        String sql = "select *\n"
                + "from Users\n"
                + "where username = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setCreateAt(rs.getString("create_at"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                u.setAvt(rs.getString("avt"));
                u.setIsBanned(rs.getBoolean("is_banned"));
                u.setEmail(rs.getString("email"));
                u.setUsername(username);
                return u;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public User findUserbyId(int id) {
        String sql = "select *\n"
                + "from Users\n"
                + "where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(id);
                u.setCreateAt(rs.getString("create_at"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                u.setAvt(rs.getString("avt"));
                u.setIsBanned(rs.getBoolean("is_banned"));
                u.setEmail(rs.getString("email"));
                u.setUsername(rs.getString("username"));
                return u;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public void updateUserByAdmin(User user) {
        String sql = "update Users\n"
                + "set first_name = ?, last_name= ?, email = ?, role = ?,is_banned = ? where id = ?";
        try {
            PreparedStatement st = connection.prepareCall(sql);
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getEmail());
            st.setString(4, user.getRole());
            st.setBoolean(5, user.isIsBanned());
            st.setInt(6, user.getId());
            st.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void updateUser(User user) {
        String sql = "update Users\n"
                + "set first_name = ?, last_name = ?, avt = ?\n"
                + "where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getAvt());
            st.setInt(4, user.getId());
            st.executeUpdate();
        } catch (SQLException e) {
        }
    }

    

    // Tìm người dùng theo username
//    public User findByUsername(String username) {
//        String query = "SELECT * FROM [user] WHERE username = ?";
//        try (
//             PreparedStatement stmt = connection.prepareStatement(query)) {            
//            stmt.setString(1, username);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return mapUser(rs);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public User findByEmail(String email) {
//        String query = "SELECT * FROM [user] WHERE email = ?";
//        try (
//             PreparedStatement stmt = connection.prepareStatement(query)) {
//             
//            stmt.setString(1, email);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return mapUser(rs);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    // Tạo token để khôi phục mật khẩu (Giả định có một bảng token trong DB)
//    public String createPasswordResetToken(User user) {
//        String token = generateRandomToken();
//        String query = "INSERT INTO password_reset_token (user_id, token) VALUES (?, ?)";
//        try (Connection conn = Database.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//             
//            stmt.setInt(1, user.getId());
//            stmt.setString(2, token);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return token;
//    }
    // Helper để ánh xạ đối tượng User từ ResultSet
//    private User mapUser(ResultSet rs) throws SQLException {
//        User user = new User();
//        user.setId(rs.getInt("id"));
//        user.setFirstName(rs.getString("first_name"));
//        user.setLastName(rs.getString("last_name"));
//        user.setUsername(rs.getString("username"));
//        user.setEmail(rs.getString("email"));
//        user.setPassword(rs.getString("password"));
//        user.setRole(rs.getString("role"));
//        user.setIsBanned(rs.getBoolean("is_banned"));
//        return user;
//    }
    // Tạo token ngẫu nhiên
//    private String generateRandomToken() {
//        // Có thể dùng thư viện Apache Commons Codec hoặc UUID để tạo token
//        return java.util.UUID.randomUUID().toString();
//    }
}
