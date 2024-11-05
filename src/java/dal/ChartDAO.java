/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public class ChartDAO extends DBContext {

    public List<Object[]> getMonthlyStatistics() {
        List<Object[]> statistics = new ArrayList<>();

        String sql = "WITH MonthList AS (\n"
                + "    SELECT MONTH(DateAdd(MONTH, Number, '2024-01-01')) AS thang\n"
                + "    FROM master.dbo.spt_values\n"
                + "    WHERE type = 'P' AND Number < 12\n"
                + ")\n"
                + "SELECT \n"
                + "    ml.thang, \n"
                + "    COALESCE(SUM(bd.quantity), 0) AS total_quantity,\n"
                + "    COALESCE(SUM(bd.quantity * pv.price), 0) AS total_revenue\n"
                + "FROM MonthList ml\n"
                + "LEFT JOIN Bill b ON MONTH(b.createdDate) = ml.thang\n"
                + "LEFT JOIN BillDetail bd ON b.id = bd.billId\n"
                + "LEFT JOIN ProductVariant pv ON pv.id = bd.productVariantId\n"
                + "GROUP BY ml.thang\n"
                + "ORDER BY ml.thang;";

        try (
                PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int month = resultSet.getInt("thang");
                int totalQuantity = resultSet.getInt("total_quantity");
                double totalRevenue = resultSet.getDouble("total_revenue");
                statistics.add(new Object[]{month, totalQuantity, totalRevenue});
            }
        } catch (SQLException e) {

        }
        return statistics;
    }

    public List<Double> getTotalRevenue(int year) {
        List<Double> l = new ArrayList();
        String sql = "WITH MonthList AS (\n"
                + "    SELECT MONTH(DateAdd(MONTH, Number, '2024-01-01')) AS thang\n"
                + "    FROM master.dbo.spt_values\n"
                + "    WHERE type = 'P' AND Number < 12\n"
                + ")\n"
                + "SELECT \n"
                + "    COALESCE(SUM(bd.quantity * pv.price), 0) AS total_revenue\n"
                + "FROM \n"
                + "    MonthList ml\n"
                + "LEFT JOIN \n"
                + "    Bill b ON MONTH(b.createdDate) = ml.thang AND YEAR(b.createdDate) = ?\n"
                + "LEFT JOIN \n"
                + "    BillDetail bd ON b.id = bd.billId\n"
                + "LEFT JOIN \n"
                + "    ProductVariant pv ON pv.id = bd.productVariantId	\n"
                + "GROUP BY \n"
                + "    ml.thang\n"
                + "ORDER BY \n"
                + "    ml.thang;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                l.add(rs.getDouble("total_revenue"));
            }
        } catch (SQLException e) {
        }
        return l;
    }

    public List<Integer> getTotalQuantity(int year) {
        List<Integer> l = new ArrayList<>();
        String sql = "WITH MonthList AS (\n"
                + "    SELECT MONTH(DateAdd(MONTH, Number, '2024-01-01')) AS thang\n"
                + "    FROM master.dbo.spt_values\n"
                + "    WHERE type = 'P' AND Number < 12\n"
                + ")\n"
                + "SELECT \n"
                + "    COALESCE(SUM(bd.quantity), 0) AS quantity\n"
                + "FROM \n"
                + "    MonthList ml\n"
                + "LEFT JOIN \n"
                + "    Bill b ON MONTH(b.createdDate) = ml.thang AND YEAR(b.createdDate) = ?\n"
                + "LEFT JOIN \n"
                + "    BillDetail bd ON b.id = bd.billId\n"
                + "LEFT JOIN \n"
                + "    ProductVariant pv ON pv.id = bd.productVariantId	\n"
                + "GROUP BY \n"
                + "    ml.thang\n"
                + "ORDER BY \n"
                + "    ml.thang;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                l.add(rs.getInt("quantity"));
            }
        } catch (SQLException e) {
        }
        return l;
    }

    public Map<String, Integer> getQuantityProductByMonth(int month, int year) {
        String sql = "select p.name as nameproduct, SUM(bd.quantity) as sum\n"
                + "from Bill b\n"
                + "join BillDetail bd on b.id = bd.billId\n"
                + "join ProductVariant pv on pv.id = bd.productVariantId\n"
                + "join Product p on p.id = pv.product_id\n"
                + "where month(b.createdDate) = ? and year(b.createdDate) = ?\n"
                + "group by p.name";
        Map<String, Integer> h = new HashMap<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, month);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                h.put(rs.getString("nameproduct"), rs.getInt("sum"));
            }
        } catch (Exception e) {
        }
        return h;
    }

    public static void main(String[] args) {
        ChartDAO c = new ChartDAO();
        
        
        System.out.println(c.getQuantityProductByMonth(10, 2024));
    }
}
