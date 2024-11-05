/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.BillDetail;

/**
 *
 * @author admin
 */
public class BillDetailDAO extends DBContext {

    public void insertBillDetai(int billId, int variantId, int quantity) {
        String sql = "insert into BillDetail(billId, productVariantId, quantity)\n"
                + "values(?,?,?)";
        try {
            PreparedStatement st = connection.prepareCall(sql);
            st.setInt(1, billId);
            st.setInt(2, variantId);
            st.setInt(3, quantity);
            st.executeUpdate();
        } catch (Exception e) {
        }
    }

    public List<BillDetail> getBillDetailByBillId(int billId) {
        String sql = "select *\n"
                + "from BillDetail\n"
                + "where billId = ?";
        List<BillDetail> l = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, billId);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                BillDetail bd = new BillDetail();
                bd.setId(rs.getInt("id"));
                bd.setBillId(rs.getInt("billId"));
                bd.setProductVariantId(rs.getInt("productVariantId"));
                bd.setQuantity(rs.getInt("quantity"));
                l.add(bd);
            }
        } catch (SQLException e) {
        }
        return l;
    }
    public static void main(String[] args) {
        BillDetailDAO bd = new BillDetailDAO();
        System.out.println(bd.getBillDetailByBillId(3).get(0).getProductVariantId());
    }
}
