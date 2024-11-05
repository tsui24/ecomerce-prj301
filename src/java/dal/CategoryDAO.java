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
import model.Category;
/**
 *
 * @author admin
 */
public class CategoryDAO extends DBContext{
    public List<Category>  getAllCategory(){
        List<Category> result = new ArrayList<>();
        String sql = "select * from Category";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setNameCategory(rs.getString("nameCategory"));
                c.setSlug(rs.getString("slug"));
                result.add(c);
            }
        } catch (SQLException e) {
        }
        return result;
    }
}
