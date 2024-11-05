/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.ProductImage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author admin
 */
public class ProductImageDAO extends DBContext{
    public void addImgProduct(ProductImage p){
        String sql = "insert into ProductImage(product_id, img, alt, [type])\n" +
"values (?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, p.getProductId());
            st.setString(2, p.getImg());
            st.setString(3, p.getAlt());
            st.setString(4, p.getType());
            st.executeUpdate();
        } catch (SQLException e) {
        }
    }
}
