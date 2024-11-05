/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Cart;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ProductVariant;

/**
 *
 * @author admin
 */
public class CartDAO extends DBContext {

    public void addProductInCart(int userId, int productVariant) {
        String sql = "insert into Cart(userId, productVariantId) values(?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, productVariant);
            st.executeUpdate();
        } catch (Exception e) {
        }
    }

    public List<ProductVariant> getListProductInCart(int userId) {
        String sql = "select *\n"
                + "from ProductVariant\n"
                + "where id in (select productVariantId\n"
                + "from Cart\n"
                + "where userId = ?)";
        List<ProductVariant> l = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                ProductVariant p = new ProductVariant();
                p.setId(rs.getInt("id"));
                p.setPrice(rs.getDouble("price"));
                p.setProductId(rs.getInt("product_id"));
                p.setSku(rs.getString("sku"));
                p.setQuantity(rs.getInt("quantity"));
                p.setSoldQuantity(rs.getInt("sold_quantity"));
                p.setStorage(rs.getString("storage"));
                l.add(p);
            }
        } catch (SQLException e) {
        }
        return l;
    }
    public List<Cart> getListCart(int userId){
        String sql = "select *from Cart where userId = ?";
        List<Cart> l = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Cart c = new Cart(rs.getInt("id"), rs.getInt("userId"), rs.getInt("productVariantId"));
                l.add(c);
            }
        } catch (SQLException e) {
        }
        return l;
    }
    public ProductVariant getProductVariantById(int id){
        String sql = "select * from ProductVariant where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs= st.executeQuery();
            if(rs.next()){
                ProductVariant p = new ProductVariant();
                p.setId(rs.getInt("id"));
                p.setProductId(rs.getInt("product_id"));
                p.setSku(rs.getString("sku"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setSoldQuantity(rs.getInt("sold_quantity"));
                p.setStorage(rs.getString("storage"));
                return p;
            }
        } catch (SQLException e) {
        }
        return null;
    }
    
    public void deleteProductVariantsInCart(int userId, int productVariantId){
        String sql ="delete from Cart where productVariantId = ? and userId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, productVariantId);
            st.setInt(2, userId);
            st.executeUpdate();
        } catch (SQLException e) {
        }        
    }
    public boolean checkProductInCart(int userId, int variantId) {
    String sql = "SELECT COUNT(*) FROM Cart WHERE userId = ? AND productVariantId = ?";
    try ( PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ps.setInt(2, variantId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; 
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; 
}
    public static void main(String[] args) {
        CartDAO cd = new CartDAO();
        System.out.println(cd.getProductVariantById(2).getSku());
    }
}
