/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.beans.Statement;
import model.Bill;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.ProductVariant;

/**
 *
 * @author admin
 */
public class BillDAO extends DBContext {
    public static void main(String[] args) {
        BillDAO b = new BillDAO();

        System.out.println(b.getBillById(3));
    }
    public Bill getBillById(int id){
        String sql = "select * from Bill where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                Bill b = new Bill();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("userId"));
                b.setCreatedDate(rs.getDate("createdDate"));
                b.setSdt(rs.getString("sdt"));
                b.setLocation(rs.getString("location"));
                b.setStatus(rs.getString("status"));
                return b;
            }
        } catch (SQLException e) {
        }
        return null;
    }
    public Integer insertBill(Bill bill) {

        String sql = "INSERT INTO Bill (createdDate, userId, sdt, location, status) "
                + "OUTPUT INSERTED.id VALUES (?, ?, ?, ?, ?)";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {

            // Thiết lập giá trị cho các tham số
            ps.setDate(1, new java.sql.Date(bill.getCreatedDate().getTime()));
            ps.setInt(2, bill.getUserId());
            ps.setString(3, bill.getSdt());
            ps.setString(4, bill.getLocation());
            ps.setString(5, bill.getStatus());
            // Thực thi câu lệnh và lấy ID từ OUTPUT
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);  // Trả về ID vừa được tạo
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Trả về null nếu có lỗi
    }

    public void updateQuantity(int quantity, int sold, int variantId) {
        String sql = "update ProductVariant\n"
                + "set quantity = ? ,sold_quantity = ?\n"
                + "where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, quantity);
            st.setInt(2, sold);
            st.setInt(3, variantId);
            st.executeUpdate();
        } catch (SQLException e) {
        }
    }

    

    public List<Bill> getAllBill() {
        String sql = "select * from Bill";
        List<Bill> l = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Bill b = new Bill();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("userId"));
                b.setCreatedDate(rs.getDate("createdDate"));
                b.setSdt(rs.getString("sdt"));
                b.setLocation(rs.getString("location"));
                b.setStatus(rs.getString("status"));
                l.add(b);
            }
        } catch (SQLException e) {
        }
        return l;
    }
//    public List<Bill> getBillMostTotal(){
//        
//    }

    public List<Bill> getAllBillSearch(String search) {
        String sql = "select * from Bill where 1=1 ";
        if (search.equals("done")) {
            sql += "and status = 'done'";
        } else if (search.equals("processing")) {
            sql += "and status = 'processing'";
        } else if (search.equals("cancel")) {
            sql += "and status = 'cancel'";
        } else if (search.equals("null")) {
            sql += "and status is null";
        }
        List<Bill> l = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Bill b = new Bill();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("userId"));

                b.setCreatedDate(rs.getDate("createdDate"));
                b.setSdt(rs.getString("sdt"));
                b.setLocation(rs.getString("location"));
                b.setStatus(rs.getString("status"));
                l.add(b);
            }
        } catch (SQLException e) {
        }
        return l;
    }

    public ProductVariant getProductVariantById(int id) {
        String sql = "select * from ProductVariant where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                ProductVariant p = new ProductVariant();
                p.setId(rs.getInt("id"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setSku(rs.getString("sku"));
                p.setSoldQuantity(rs.getInt("sold_quantity"));
                p.setStorage("storage");
                return p;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public void updateStatusBill(int id, String status) {
        String sql = "update Bill\n"
                + "set status= ?\n"
                + "where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, status);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (Exception e) {
        }
    }

}
