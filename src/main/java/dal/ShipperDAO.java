/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Shipper;
import utils.DBContext;

/**
 *
 * @author Tran Viet Minh - CE182526
 */
public class ShipperDAO {

    DBContext dBContext = new DBContext();

    public ArrayList<Shipper> getShipperSalaryByID(String shipperID) {
        ArrayList<Shipper> shippers = new ArrayList<>();
        String sql = "SELECT MonthShipped, Amount, SalaryOfMonth\n"
                + "FROM Shipper\n"
                + "WHERE ShipperID = ?";
        Object[] params = {
            shipperID
        };
        try {
            ResultSet rs = dBContext.exeQueryAlt(sql, params);
            while (rs.next()) {
                Date monthShipped = rs.getDate("MonthShipped");
                int amount = rs.getInt("Amount");
                int salaryOfMonth = rs.getInt("SalaryOfMonth");

                Shipper shipper = new Shipper(shipperID, monthShipped, amount, salaryOfMonth);
                shippers.add(shipper);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippers;
    }
}
