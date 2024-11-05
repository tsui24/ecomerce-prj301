/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.ProductImage;
import model.ProductOption;
import model.ProductOptionValue;
import model.ProductVariant;

/**
 *
 * @author admin
 */
public class ProductDAO extends DBContext {

    public static void main(String[] args) {
        ProductDAO pd = new ProductDAO();
        ProductOption p = new ProductOption();
        p.setProductId(26);
        p.setOptionName("COLOR");
        p.setStorage("yes");
        System.out.println(pd.getAllProduct("ip").size());
    }

    public void insertProductVariant(ProductVariant pv) {
        String sql = "insert into ProductVariant(product_id, sku, price, quantity, sold_quantity, storage)\n"
                + "values(?,?,?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, pv.getProductId());
            st.setString(2, pv.getSku());
            st.setDouble(3, pv.getPrice());
            st.setInt(4, pv.getQuantity());
            st.setInt(5, pv.getSoldQuantity());
            st.setString(6, pv.getStorage());
            st.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void insertOptionValue(ProductOptionValue pov) {
        String sql = "insert into ProductOptionValue(sku, storage, productOptionId)\n"
                + "values(?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, pov.getSku());
            st.setString(2, pov.getStorage());
            st.setInt(3, pov.getProductOptionId());
            st.executeUpdate();
        } catch (Exception e) {
        }
    }

    public boolean isExistOptionValue(int productOptionId, String nameValue) {
        String sql = "select *\n"
                + "from ProductOptionValue\n"
                + "where sku = ? and productOptionId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, nameValue);
            st.setInt(2, productOptionId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public int getProductOptionId(int productId, String optionName) {
        String sql = "select * \n"
                + "from ProductOption\n"
                + "where product_id = ? and optionName = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, productId);
            st.setString(2, optionName);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
        }
        return -1;
    }

    public int insertOption(ProductOption po) {
        String sql = "INSERT INTO ProductOption(product_id, optionName, storage) VALUES (?, ?, ?)";
        int generatedId = -1;

        try ( // Ensure you have a valid connection method
                PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, po.getProductId());
            pstmt.setString(2, po.getOptionName());
            pstmt.setString(3, po.getStorage());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return generatedId; // Return the generated ID (or -1 if failed)
    }

    public boolean productOptionExistByProductIdAndOptionName(int productId, String optionName) {
        String sql = "select * \n"
                + "from ProductOption\n"
                + "where product_id = ? and optionName = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, productId);
            st.setString(2, optionName);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public List<Product> getAllProduct(String search) {
        List<Product> result = new ArrayList<>();
        String sql = "select * from Product where storage = 'yes' ";
        if(search != null){
            sql += "and name like ?";
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%"+search+"%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
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
                result.add(p);
            }
        } catch (SQLException e) {
        }
        return result;
    }

    public List<Product> getTopProductWithCategory(int categoryId) {
        List<Product> result = new ArrayList<>();
        String sql = "select top 4 *\n"
                + "from Product\n"
                + "where category_id = ? and storage = 'yes'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, categoryId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setCategoryId(rs.getInt("category_id"));
                p.setId(rs.getInt("id"));
                p.setDescription(rs.getString("description"));
                p.setName(rs.getString("name"));
                p.setSlug(rs.getString("slug"));
                p.setStorage("storage");
                p.setPriceDefault(rs.getDouble("default_price"));
                result.add(p);
            }
        } catch (SQLException e) {
        }
        return result;
    }

    public List<ProductImage> getAllProductImage() {
        List<ProductImage> result = new ArrayList<>();
        String sql = "select *\n"
                + "from ProductImage\n"
                + "where  type='DEFAULT'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ProductImage p = new ProductImage();
                p.setId(rs.getInt("id"));
                p.setProductId(rs.getInt("product_id"));
                p.setImg(rs.getString("img"));
                p.setAlt(rs.getString("alt"));
                p.setType(rs.getString("type"));

                result.add(p);
            }
        } catch (SQLException e) {
        }
        return result;
    }

    public String getImageDefault(int id) {
        String resul = "";
        String sql = "select *\n"
                + "from ProductImage\n"
                + "where product_id = ? and type = 'DEFAULT'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                resul = rs.getString("img");
            }
        } catch (SQLException e) {
            System.out.println("Loi o day");
        }
        return resul;
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO Product (name, slug, description, category_id, storage, default_price, create_at, brand) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getSlug());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getCategoryId());
            statement.setString(5, product.getStorage());
            statement.setDouble(6, product.getPriceDefault());
            java.util.Date utilDate = product.getCreateAt();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());  // Chuyển đổi java.util.Date sang java.sql.Date
            statement.setDate(7, sqlDate);
            statement.setString(8, product.getBrand());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Loi o day");
        }
    }

    // Phương thức xóa sản phẩm theo ID
    public void deleteProduct(int id) {
        String deleteVariantSql = "DELETE FROM ProductVariant WHERE product_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteVariantSql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa từ ProductVariant");
            e.printStackTrace(); // In ra thông tin chi tiết lỗi
        }

        // Sau đó, xóa sản phẩm từ bảng Product
        String deleteProductSql = "DELETE FROM Product WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteProductSql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa từ Product");
            e.printStackTrace(); // In ra thông tin chi tiết lỗi
        }
    }

    public boolean updateProduct(Product product) {
        String sql = "update Product \n"
                + "set name = ?, slug = ?, description=?, category_id = ?, storage = ?, default_price = ?, brand = ?\n"
                + "where id = ?";
        boolean rowUpdated = false;

        try (
                PreparedStatement statement = connection.prepareStatement(sql)) {

            // Thiết lập giá trị cho từng trường
            statement.setString(1, product.getName());
            statement.setString(2, product.getSlug());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getCategoryId());
            statement.setString(5, product.getStorage());
            statement.setDouble(6, product.getPriceDefault());
            statement.setString(7, product.getBrand());
            statement.setInt(8, product.getId());  // Thiết lập ID của sản phẩm

            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Loi o day");
        }

        return rowUpdated;
    }

    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> l = new ArrayList<>();
        String sql = "select * from Product where category_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, categoryId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
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
        } catch (SQLException e) {
        }
        return l;
    }

    public List<Product> findProductsForPage(int pageNumber, int pageSize, int category_id, double minPrice, double maxPrice, String sortBy) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM Product WHERE category_id = ? AND default_price BETWEEN ? AND ? ";

        // Thêm điều kiện sắp xếp
        if (sortBy != null) {
            switch (sortBy) {
                case "priceHigh":
                    query += " ORDER BY default_price DESC";
                    break;
                case "priceLow":
                    query += " ORDER BY default_price ASC";
                    break;
                case "releaseDate":
                    query += " ORDER BY create_at DESC";
                    break;
                case "mostViewed":
                    query += " ORDER BY id DESC";
                    break;
                default:
                    query += " ORDER BY id ";
                    break;
            }
        } else {
            query += " ORDER BY id ";
        }
        query += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        List<Product> products = new ArrayList<>();
        try (
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, category_id);
            ps.setDouble(2, minPrice);
            ps.setDouble(3, maxPrice);
            ps.setInt(4, (pageNumber - 1) * pageSize);
            ps.setInt(5, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setCategoryId(rs.getInt("category_id"));
                p.setId(rs.getInt("id"));
                p.setDescription(rs.getString("description"));
                p.setName(rs.getString("name"));
                p.setSlug(rs.getString("slug"));
                p.setStorage(rs.getString("storage"));
                p.setPriceDefault(rs.getDouble("default_price"));
                p.setCreateAt(rs.getDate("create_at"));
                products.add(p);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public int getTotalProductsByCategory(int categoryId, double minPrice, double maxPrice) {
        String query = "SELECT COUNT(*) FROM Product WHERE category_id = ? and default_price between ? and ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, categoryId);
            ps.setDouble(2, minPrice);
            ps.setDouble(3, maxPrice);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);  // Return total number of products
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Product> getListProductByKeyword(String key) {
        String sql = "select top 10 *\n"
                + "from Product\n"
                + "where name like ?";
        List<Product> result = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + key + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
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
                result.add(p);
            }
        } catch (Exception e) {
        }
        return result;
    }

}
