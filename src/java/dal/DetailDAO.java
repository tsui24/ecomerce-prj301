/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import com.oracle.wls.shaded.org.apache.bcel.generic.DDIV;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Product;
import model.ProductOption;
import model.ProductOptionValue;
import model.ProductVariant;

/**
 *
 * @author admin
 */
public class DetailDAO extends DBContext {
    
    public Product getProductDetai(int id) {
        String sql = "select *\n"
                + "from Product\n"
                + "where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Product p = new Product();
                p.setCategoryId(rs.getInt("category_id"));
                p.setId(rs.getInt("id"));
                p.setDescription(rs.getString("description"));
                p.setName(rs.getString("name"));
                p.setSlug(rs.getString("slug"));
                p.setStorage(rs.getString("storage"));
                p.setPriceDefault(rs.getDouble("default_price"));
                p.setCreateAt(rs.getDate("create_at"));
                p.setBrand(rs.getString("brand"));
                return p;

            }
        } catch (SQLException e) {
        }
        return null;
    }
   
    public ProductVariant getVariant(int id){
        String sql = "select * from ProductVariant where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
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
    
    public String getImgForProductDetail(int id, String optionValue) {
        String resul = "";

        String sql = "select *\n"
                + "from ProductImage\n"
                + "where product_id = ? and type=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.setString(2, optionValue);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                resul = rs.getString("img");
            }
        } catch (SQLException e) {
        }
        return resul;
    }

    public List<ProductOption> getListOption(int id) {
        List<ProductOption> l = new ArrayList<>();
        String sql = "select *\n"
                + "from ProductOption\n"
                + "where product_id = ? and storage = 'yes'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ProductOption po = new ProductOption();
                po.setId(rs.getInt("id"));
                po.setOptionName(rs.getString("optionName"));
                po.setSku(rs.getString("sku"));
                po.setStorage(rs.getString("storage"));
                po.setOptionValue(rs.getString("option_value"));
                l.add(po);
            }
        } catch (Exception e) {
        }
        return l;
    }

    public List<ProductOptionValue> getProductOptionValues(int optionId) {
        String sql = "select *\n"
                + "from ProductOptionValue\n"
                + "where productOptionId = ? and storage = 'yes'";
        List<ProductOptionValue> l = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, optionId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ProductOptionValue p = new ProductOptionValue();
                p.setId(rs.getInt("id"));
                p.setSku(rs.getString("sku"));
                p.setStorage(rs.getString("storage"));
                p.setProductOptionId(optionId);
                l.add(p);
            }
        } catch (SQLException e) {
        }
        return l;
    }
    
    public ProductVariant getProductVariant(int productId, String sku) {
        String sql = "select *\n"
                + "from ProductVariant\n"
                + "where product_id = ? and sku =? and storage = 'yes'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, productId);
            st.setString(2, sku);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                ProductVariant p = new ProductVariant();
                p.setId(rs.getInt("id"));
                p.setProductId(productId);
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
    
    public List<Product> getListProductRelate(int id, String brand) {
        String sql = "select top 4 *\n"
                + "from Product\n"
                + "where brand = ? and id != ?";
        List<Product> l = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, brand);
            st.setInt(2, id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Product p = new Product();
                p.setCategoryId(rs.getInt("category_id"));
            p.setId(rs.getInt("id"));
            p.setDescription(rs.getString("description"));
            p.setName(rs.getString("name"));
            p.setSlug(rs.getString("slug"));
            p.setStorage(rs.getString("storage"));
            p.setPriceDefault(rs.getDouble("default_price"));
            p.setCreateAt(rs.getDate("create_at"));
            p.setBrand(rs.getString("brand"));
            l.add(p);
            }
        } catch (Exception e) {
        }
        return l;
    }
    public List<ProductVariant> getListProductVariants(int productId){
        List<ProductVariant> l = new ArrayList<>();
        String sql ="select * from ProductVariant where product_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, productId);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                ProductVariant p = new ProductVariant();
                p.setId(rs.getInt("id"));
                p.setProductId(rs.getInt("product_id"));
                p.setSku(rs.getString("sku"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setSoldQuantity(rs.getInt("sold_quantity"));
                p.setStorage(rs.getString("storage"));
                l.add(p);
            }
        } catch (SQLException e) {
        }
        return l;
    }
     public static void main(String[] args) {
        DetailDAO d = new DetailDAO();
        System.out.println(d.getListProductVariants(2).get(0).getSku());
    }
    
}
