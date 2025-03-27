/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.*;
import utils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anhkc
 */
public class OrderDAO extends DBContext {

    PreparedStatement ps = null;
    DBContext context;

    public OrderDAO() {
        context = new DBContext();
    }

    public List<OrderInfo> selectAllOrderInfo() throws SQLException {
        String sql = "SELECT [orderID]\n"
                + "      ,[customerID]\n"
                + "      ,[orderDate]\n"
                + "      ,[orderAdressID]\n"
                + "      ,[deliveryStatusID]\n"
                + "      ,[paymentMethodID]\n"
                + "      ,[paymentStatusID]\n"
                + "      ,[orderTotalAmount]\n"
                + "  FROM [dbo].[OrderInfo]\n"
                + "  ORDER BY [orderDate] desc";
        List<OrderInfo> orderList = new ArrayList<>();
        ResultSet rs = context.exeQuery(sql);
        while (rs.next()) {
            orderList.add(new OrderInfo(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDate(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getString(6),
                    rs.getInt(7),
                    rs.getInt(8)));
        }
        return orderList;
    }

    public List<deliveryStatus> getAllDelStatus() throws SQLException {
        String sql = "SELECT *\n"
                + "FROM [dbo].[DeliveryStatus]";
        List<deliveryStatus> deliveryStatus = new ArrayList<>();
        ResultSet rs = context.exeQuery(sql);
        while (rs.next()) {
            deliveryStatus.add(new deliveryStatus(rs.getInt(1), rs.getString(2))
            );
        }
        return deliveryStatus;
    }

    public List<OrderInfo> getOrderByID(String id) throws SQLException {
        String sql = "SELECT [orderID]\n"
                + "                      ,[customerID]\n"
                + "                      ,[orderDate]\n"
                + "                      ,[orderAdressID]\n"
                + "                      ,[deliveryStatusID]\n"
                + "                      ,[paymentMethodID]\n"
                + "                      ,[paymentStatusID]\n"
                + "                      ,[orderTotalAmount]\n"
                + "                  FROM [dbo].[OrderInfo]\n"
                + "                  WHERE [dbo].[OrderInfo].[customerID] LIKE ?\n"
                + "                  ORDER BY [orderDate] desc";
        Object[] params = {id};
        List<OrderInfo> orderList = new ArrayList<>();
        ResultSet rs = context.exeQueryAlt(sql, params);
        while (rs.next()) {
            orderList.add(new OrderInfo(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDate(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getString(6),
                    rs.getInt(7),
                    rs.getInt(8)));
        }
        return orderList;
    }

    public List<OrderInfo> getOrderByFillter(String id, String fillter) throws SQLException {
        String sql = "SELECT [orderID]\n"
                + "                    ,[customerID]\n"
                + "                    ,[orderDate]\n"
                + "                    ,[orderAdressID]\n"
                + "                    ,[deliveryStatusID]\n"
                + "                    ,[paymentMethodID]\n"
                + "                    ,[paymentStatusID]\n"
                + "                    ,[orderTotalAmount]\n"
                + "                FROM [dbo].[OrderInfo]\n"
                + "                WHERE [dbo].[OrderInfo].[customerID] LIKE ?\n"
                + "				AND " + fillter
                + "                ORDER BY [orderDate] desc";
        Object[] params = {id};
        List<OrderInfo> orderList = new ArrayList<>();
        ResultSet rs = context.exeQueryAlt(sql, params);
        while (rs.next()) {
            orderList.add(new OrderInfo(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDate(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getString(6),
                    rs.getInt(7),
                    rs.getInt(8)));
        }
        return orderList;
    }

    public List<OrderInfo> getShipperOrderByFillter(String id, String fillter) throws SQLException {
        String sql = "SELECT [orderID]\n"
                + "                                    ,[customerID]\n"
                + "                                    ,[orderDate]\n"
                + "                                    ,[orderAdressID]\n"
                + "                                    ,[deliveryStatusID]\n"
                + "                                    ,[paymentMethodID]\n"
                + "                                    ,[paymentStatusID]\n"
                + "                                    ,[orderTotalAmount]\n"
                + "                                    ,[shipperID]\n"
                + "                                FROM [dbo].[OrderInfo]\n"
                + "                                WHERE [dbo].[OrderInfo].[shipperID] LIKE ?\n"
                + "                				AND " + fillter
                + "                                ORDER BY [orderDate] desc";
        Object[] params = {id};
        List<OrderInfo> orderList = new ArrayList<>();
        ResultSet rs = context.exeQueryAlt(sql, params);
        while (rs.next()) {
            orderList.add(new OrderInfo(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDate(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getString(6),
                    rs.getInt(7),
                    rs.getInt(8)));
        }
        return orderList;
    }

    public List<PaymentMethod> getAllPaymentMethod() {
        List<PaymentMethod> listPaymentMethod = new ArrayList<>();
        String sql = "select * from paymentMethod";
        try {
            ResultSet rs = context.exeQuery(sql);
            while (rs.next()) {
                PaymentMethod paymentMethod = new PaymentMethod(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3));
                listPaymentMethod.add(paymentMethod);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return listPaymentMethod;
    }

    public void insert(OrderInfo orderInfo, List<CartItem> itemList) throws SQLException {
        String sql = "INSERT INTO [dbo].[OrderInfo]\n"
                + "           ([orderID]\n"
                + "           ,[customerID]\n"
                + "           ,[orderAdressID]\n"
                + "           ,[deliveryStatusID]\n"
                + "           ,[paymentMethodID]\n"
                + "           ,[paymentStatusID]\n"
                + "           ,[orderTotalAmount])\n"
                + "             VALUES(?,?,?,?,?,?,?)";
        Object[] params = {
            orderInfo.getOrderID(),
            orderInfo.getCustomerID(),
            orderInfo.getOrderAddressID(),
            orderInfo.getDeliveryStatusID(),
            orderInfo.getPaymentMethodID(),
            orderInfo.getPaymentStatusID(),
            orderInfo.getOrderTotalAmount()};
        int rowsAffected = context.exeNonQuery(sql, params);
        System.out.println(rowsAffected + " rows affected");
        for (CartItem item : itemList) {
            insertOrderItem(orderInfo.getOrderID(), item);
        }
    }

    public void insertOrderItem(String orderID, CartItem item) throws SQLException {
        String sql = "insert into OrderItems values(?,?,?,?)";
        Object[] params = {orderID, item.getBook().getBookID(), item.getQuantity(), item.getFinalPrice()};
        int rows = context.exeNonQuery(sql, params);
        System.out.println(rows);
    }

    public String getOrderInfonextID() throws SQLException {
        String sql = "SELECT COUNT(orderID) FROM [dbo].[OrderInfo]";
        try ( ResultSet rows = context.exeQuery(sql)) {
            if (rows.next()) {
                int count = rows.getInt(1);
                return String.format("ORD%03d", count + 1);
            }
        }
        return "ORD000";
    }

    public void updateInfo(Object[] thamso) throws SQLException {
        String sqlInfo = "UPDATE [dbo].[OrderInfo]\n"
                + "   SET \n"
                + "     [orderDate] = ?\n"
                + "      ,[deliveryAddress] = ?\n"
                + "      ,[paymentMethodID] = ?\n"
                + "      ,[deliveryOptionID] = ?\n"
                + "      ,[orderTotalAmount] = ?\n"
                + "      ,[customerID] = ?\n"
                + "      ,[employeeID] = ?\n"
                + " WHERE [orderID] = ?";
        context.exeNonQuery(sqlInfo, thamso);
    }

    public void updateItem(Object[] thamso) throws SQLException {
        String sqlItems = "UPDATE [dbo].[OrderItems]\n"
                + "   SET \n"
                + "      [bookID] = ?\n"
                + "      ,[quantity] = ?\n"
                + "      ,[finalPrice] = ?\n"
                + " WHERE [orderID] = ?";

        context.exeNonQuery(sqlItems, thamso);
    }

    public List<paymentStatus> getAllPaymentStatus() {
        List<paymentStatus> list = new ArrayList<>();
        String sql = "select * from paymentStatus";
        try {
            ResultSet rs = context.exeQuery(sql);
            while (rs.next()) {
                paymentStatus odl = new paymentStatus(rs.getInt(1),
                        rs.getString(2));
                list.add(odl);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return list;
    }

    public void deleteInfo(String id) {
        String sql = "DELETE FROM [dbo].[OrderInfo]\n"
                + "      WHERE orderID = ?";
        Object[] params = {id};
        try {
            context.exeNonQuery(sql, params);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteItem(String id) {
        String sql = "DELETE FROM [dbo].[OrderItems]\n"
                + "      WHERE orderID =?";
        Object[] params = {id};
        try {
            context.exeNonQuery(sql, params);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateInfo(OrderInfo o) {
        String sql = "UPDATE	[dbo].[OrderInfo]\n"
                + "SET		[customerID] =  ?\n"
                + "		,[orderDate] = ?\n"
                + "		,[orderAdressID] = ?\n"
                + "		,[deliveryStatusID] = ?\n"
                + "		,[paymentMethodID] = ?\n"
                + "		,[paymentStatusID] = ?\n"
                + "		,[orderTotalAmount] = ?\n"
                + "WHERE	[orderID] = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, o.getCustomerID());
            ps.setDate(2, o.getOrderDate());
            ps.setString(3, o.getOrderAddressID());
            ps.setInt(4, o.getDeliveryStatusID());
            ps.setString(5, o.getPaymentMethodID());
            ps.setInt(6, o.getPaymentStatusID());
            ps.setDouble(7, o.getOrderTotalAmount());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updatePaymentStatus(String OrderID, String PaymentStatusID) throws SQLException {
        String sql = "UPDATE	[dbo].[OrderInfo]\n"
                + "SET		[paymentStatusID] = ?\n"
                + "WHERE	[orderID] = ?";
        context.exeQuery(sql);
    }

    public void updateDeliveryStatus(String deliveryStatusID, String shipperID, String OrderID) throws SQLException {
        String sql = "UPDATE	[dbo].[OrderInfo]\n"
                + "SET		[deliveryStatusID] = ?\n"
                + "             ,[shipperID] = ?\n"
                + "WHERE	[orderID] = ?";
        Object[] params = {deliveryStatusID, shipperID, OrderID};
        context.exeQueryAlt(sql, params);
    }
}
