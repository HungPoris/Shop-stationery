/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import utils.DBContext;

/**
 *
 * @author Tran Viet Minh - CE182526
 */
public class CustomerDAO {

    DBContext dbContext = new DBContext();

    public void addCustomer(String customerID, String firstName, String lastName, String email, String phoneNumber, String birthDate) throws SQLException {
        String sql = "INSERT INTO Customer (customerID, firstName, lastName, email, phoneNumber, birthDate) VALUES (?, ?, ?, ?, ?, ?)";

        DBContext dbContext = new DBContext();
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerID);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, email);
            stmt.setString(5, phoneNumber);
            stmt.setString(6, birthDate);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi thêm khách hàng: " + e.getMessage(), e);
        }
    }

    public void updateCustomerInfo(Customer customer) throws SQLException {
        String sql = "update customer\n"
                + "set firstName =?,\n"
                + "lastName =?,\n"
                + " email =?, \n"
                + "phoneNumber =?,\n"
                + "birthDate =?\n"
                + "where customerID = ?";
        Object[] params = {
            customer.getFirstName(),
            customer.getLastName(),
            customer.getEmail(),
            customer.getPhoneNumber(),
            customer.getBirthDate(),
            customer.getCustomerID()
        };
        dbContext.exeNonQuery(sql, params);
    }

    public List<Customer> getAllCustomerInfo() throws SQLException {
        String sql = "SELECT [customerID]\n"
                + "      ,[firstName]\n"
                + "      ,[lastName]\n"
                + "      ,[email]\n"
                + "      ,[phoneNumber]\n"
                + "  FROM [ICHIBOOKS].[dbo].[Customer]";
        List<Customer> customerList = new ArrayList<>();
        ResultSet rs = dbContext.exeQuery(sql);
        while (rs.next()) {
            Customer customer = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            customerList.add(customer);
        }
        return customerList;
    }

    public Customer getCustomerInfoByID(String accountID) throws SQLException {
        String sql = "SELECT [customerID], [firstName], [lastName], [email], [phoneNumber]\n"
                + "FROM [dbo].[Customer]\n"
                + "WHERE [customerID] = ?";
        Object[] params = {accountID};
        ResultSet rs = dbContext.exeQueryAlt(sql, params);

        // Kiểm tra nếu ResultSet rỗng
        if (rs == null || !rs.next()) {
            System.out.println("❌ ERROR: No customer found for accountID: " + accountID);
            return null;
        }

        return new Customer(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5)
        );
    }
}
