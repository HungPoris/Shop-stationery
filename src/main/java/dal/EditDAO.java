/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Address;
import model.Customer;
import utils.DBContext;

/**
 *
 * @author Acer
 */
public class EditDAO {

    private DBContext dbContext;

    public EditDAO() {
        dbContext = new DBContext();
    }

    public boolean updateCustomer(Customer customer, Address address) {
        String query = "update Customer\n"
                + "	set firstName =?,\n"
                + "	lastName =?,\n"
                + "	phoneNumber =?,\n"
                + "	email =?,\n"
                + "	birthDate =?\n"
                + "	where customerID =?";
        try ( Connection connection = dbContext.getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());

            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setDate(5, customer.getBirthDate());
            preparedStatement.setString(6, customer.getCustomerID());

            int rowsUpdated = preparedStatement.executeUpdate();
            updateAddress(customer, address);
            updateAddressStatus(customer, address);
            return rowsUpdated > 0; // Trả về true nếu có ít nhất một hàng được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public boolean updateAddress(Customer customer, Address address) {
        String query = "update Address\n"
                + "	set addressDetail = ?\n"
                + "	where addressID = ?";
        try ( Connection connection = dbContext.getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, address.getAddressDetail());
            preparedStatement.setString(2, address.getAddressID());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có ít nhất một hàng được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public boolean updateAddressStatus(Customer customer, Address address) {
        String query = "update Account_Address\n"
                + "	set defaultAddress = ?\n"
                + "	where accountID = ?\n"
                + "	and addressID = ?";
        try ( Connection connection = dbContext.getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBoolean(1, address.isDefaultAddress());
            preparedStatement.setString(2, customer.getCustomerID());
            preparedStatement.setString(3, address.getAddressID());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có ít nhất một hàng được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public boolean deleteAddressOfThisCustomer(String addressID, String accountID) {
        String query = "DELETE FROM Account_Address WHERE addressID = ? AND accountID = ?";
        try ( Connection connection = dbContext.getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, addressID);
            preparedStatement.setString(2, accountID);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
