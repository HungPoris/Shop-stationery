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
import model.*;
import utils.DBContext;

/**
 *
 * @author TRUNG NHAN
 */
public class orderItemDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext context;

    public orderItemDAO() {
        context = new DBContext();
    }

    public List<CartItem> getItemsOfThisOrder(OrderInfo order) throws SQLException {
        String sql = "SELECT Book.*, OrderItems.*\n"
                + "FROM     Book INNER JOIN\n"
                + "                  OrderItems ON Book.bookID = OrderItems.bookID\n"
                + "WHERE  (OrderItems.orderID = ?)";
        Object[] params = {order.getOrderID()};
        rs = context.exeQueryAlt(sql, params);

        List<CartItem> itemList = new ArrayList<>();
        HomeBookDAO dao = new HomeBookDAO();
        while (rs.next()) {
            itemList.add(new CartItem(rs.getString("orderID"), dao.getbookbyid(rs.getInt("bookID")), rs.getInt("quantity"), rs.getInt("finalPrice")));
        }
        return itemList;

    }

}
