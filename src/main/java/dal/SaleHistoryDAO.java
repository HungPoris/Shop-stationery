/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import model.SaleHistory;
import utils.DBContext;

/**
 *
 * @author Tran Viet Minh - CE182526
 */
public class SaleHistoryDAO {

    DBContext dBContext = new DBContext();

    public ArrayList<SaleHistory> getSaleHistories() {
        ArrayList<SaleHistory> saleHistories = new ArrayList<>();
        String sql = "SELECT [saleID]\n"
                + "      ,[bookID]\n"
                + "      ,[soldQuantity]\n"
                + "      ,[saleDate]\n"
                + "      ,[totalPrice]\n"
                + "  FROM [dbo].[SaleHistory]";
        try {
            ResultSet rs = dBContext.exeQuery(sql);
            while (rs.next()) {
                SaleHistory saleHistory = new SaleHistory(
                        rs.getString("saleID"),
                        rs.getInt("bookID"),
                        rs.getInt("soldQuantity"),
                        rs.getDate("saleDate"),
                        rs.getInt("totalPrice")
                );
                saleHistories.add(saleHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saleHistories;
    }

    public Map<String, Integer> getAllMonthlySaleHistories() {
        Map<String, Integer> monthlyRevenue = new LinkedHashMap<>();
        String sql = "SELECT YEAR(saleDate) AS year, MONTH(saleDate) AS month, SUM(totalPrice) AS totalRevenue "
                + "FROM [dbo].[SaleHistory] "
                + "GROUP BY YEAR(saleDate), MONTH(saleDate) "
                + "ORDER BY YEAR(saleDate), MONTH(saleDate)";

        try {
            ResultSet rs = dBContext.exeQuery(sql);
            while (rs.next()) {
                String monthYearKey = String.format("%02d-%d", rs.getInt("month"), rs.getInt("year"));
                monthlyRevenue.put(monthYearKey, rs.getInt("totalRevenue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyRevenue;
    }

}
