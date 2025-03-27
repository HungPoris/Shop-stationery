/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Address;
import model.Customer;
import model.Role;
import utils.DBContext;

/**
 *
 * @author Acer
 */
public class AddressDAO {

    DBContext dbContext = new DBContext();

// Phương thức để lấy dữ liệu với các bảng được join
    public Customer getJoinedAddressDetails(String customerID) {
        String sql = "SELECT \n"
                + "    Address.addressID, \n"
                + "    Address.addressDetail, \n"
                + "    Customer.customerID,\n"
                + "    Customer.firstName, \n"
                + "    Customer.lastName, \n"
                + "    Customer.email, \n"
                + "    Customer.phoneNumber,\n"
                + "    Customer.birthDate, \n"
                + "    Account_Address.defaultAddress, \n"
                + "    Role.roleID, \n"
                + "    Role.roleName\n"
                + "FROM Account\n"
                + "LEFT JOIN Customer ON Account.accountID = Customer.customerID\n"
                + "LEFT JOIN Account_Address ON Account.accountID = Account_Address.accountID\n"
                + "LEFT JOIN Address ON Account_Address.addressID = Address.addressID\n"
                + "LEFT JOIN Role ON Account.roleID = Role.roleID\n"
                + "WHERE Customer.customerID = ?;";

        Customer customer = null;
        try {
            Object[] params = {customerID};
            ResultSet rs = dbContext.exeQueryAlt(sql, params);

            List<Address> addressList = new ArrayList<>();
            Role role = null;

            while (rs.next()) {
                if (customer == null) {
                    customer = new Customer();
                    customer.setCustomerID(rs.getString("customerID"));
                    customer.setFirstName(rs.getString("firstName"));
                    customer.setLastName(rs.getString("lastName"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhoneNumber(rs.getString("phoneNumber"));
                    customer.setBirthDate(rs.getDate("birthDate"));
                    role = new Role(rs.getString("roleID"), rs.getString("roleName"));
                }
                Address address = new Address();
                address.setAddressID(rs.getString("addressID"));
                address.setAddressDetail(rs.getString("addressDetail"));
                address.setDefaultAddress(rs.getBoolean("defaultAddress"));
                addressList.add(address);
            }
            if (customer != null) {
                customer.setAddressList(addressList);
                customer.setRole(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public void insertAddress(Object[] params) throws SQLException {
        String sql = "insert into address values(?,?)";
        dbContext.exeNonQuery(sql, params);
    }

    public void insertAccountAddress(Object[] params) throws SQLException {
        String sql = "insert into Account_Address values(?,?,?)";
        dbContext.exeNonQuery(sql, params);
    }

    public List<Address> selectAllAddress() throws SQLException {
        String sql = "select * from address";
        List<Address> categoryList = new ArrayList<>();

        ResultSet rs = dbContext.exeQuery(sql);
        while (rs.next()) {
            categoryList.add(new Address(rs.getString(1), rs.getString(2)
            ));
        }
        return categoryList;
    }

    public void addAddress(String accountID, String addressDetail) throws SQLException {
        // Sinh addressID tự động theo định dạng ADDxxx
        String newAddressID = generateNewAddressID();

        String sql = "INSERT INTO Address (addressID, addressDetail) VALUES (?, ?);";
        Object[] params = new Object[]{
            newAddressID,
            addressDetail
        };
        dbContext.exeNonQuery(sql, params);

        sql = "INSERT INTO [dbo].[Account_Address]\n"
                + "           ([accountID]\n"
                + "           ,[addressID]\n"
                + "           ,[defaultAddress])\n"
                + "     VALUES (?, ?, ?)";
        params = new Object[]{
            accountID,
            newAddressID,
            0
        };
        dbContext.exeNonQuery(sql, params);
    }

// Hàm tự động sinh addressID dạng ADDxxx
    private String generateNewAddressID() throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(addressID, 4, LEN(addressID) - 3) AS INT)) AS maxID FROM Address";

        DBContext dbContext = new DBContext();
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int maxID = rs.getInt("maxID");
                return "ADD" + String.format("%03d", maxID + 1);
            }
        }
        return "ADD001"; // Nếu bảng Address rỗng
    }

    public void addAccountAddress(String accountID, String addressID, boolean isDefault) throws SQLException {
        String sql = "INSERT INTO Account_Address (accountID, addressID, defaultAddress) VALUES (?, ?, ?)";

        DBContext dbContext = new DBContext();
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountID);
            stmt.setString(2, addressID);
            stmt.setInt(3, isDefault ? 1 : 0);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi thêm vào bảng Account_Address: " + e.getMessage(), e);
        }
    }

    public Customer getJoinedAddressDetailsByID(String customerID, String addressID) {

        String sql = "SELECT Address.addressID, Address.addressDetail, Customer.customerID, "
                + "Customer.firstName, Customer.lastName, Customer.email, Customer.phoneNumber, "
                + "Customer.birthDate, Account_Address.defaultAddress "
                + "FROM Account "
                + "INNER JOIN Account_Address ON Account.accountID = Account_Address.accountID "
                + "INNER JOIN Address ON Account_Address.addressID = Address.addressID "
                + "INNER JOIN Customer ON Account.accountID = Customer.customerID "
                + "WHERE Customer.customerID = ? AND Address.addressID = ?";

        Customer customer = new Customer();
        try {
            Object[] params = {customerID, addressID};
            ResultSet rs = dbContext.exeQueryAlt(sql, params);

            List<Address> addressList = new ArrayList<>();
            while (rs.next()) {

                Address address = new Address();
                address.setAddressID(rs.getString("addressID"));
                address.setAddressDetail(rs.getString("addressDetail"));
                address.setDefaultAddress(rs.getBoolean("defaultAddress"));
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                Date birthDate = rs.getDate("birthDate");

                addressList.add(address);
                customer.setCustomerID(rs.getString("customerID"));

                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setEmail(email);
                customer.setPhoneNumber(phoneNumber);
                customer.setBirthDate(birthDate);

            }
            customer.setAddressList(addressList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public boolean addProfileAndAddress(Connection conn, String accountID, String firstName, String lastName,
            String phoneNumber, String birthDate, double purchaseAmount,
            String addressDetail, boolean isDefaultAddress) throws SQLException {

        String insertCustomerSQL = "INSERT INTO Customer (customerID, firstName, lastName, email, phoneNumber, birthDate, purchaseAmount) "
                + "SELECT ?, ?, ?, email, ?, ?, ? FROM Account WHERE accountID = ?";

        String insertAddressSQL = "INSERT INTO Address (addressDetail) VALUES (?)";

        String getLastAddressIDSQL = "SELECT TOP 1 addressID FROM Address ORDER BY addressID DESC";

        String insertAccountAddressSQL = "INSERT INTO Account_Address (accountID, addressID, defaultAddress) VALUES (?, ?, ?)";

        try ( PreparedStatement insertCustomerStmt = conn.prepareStatement(insertCustomerSQL);  PreparedStatement insertAddressStmt = conn.prepareStatement(insertAddressSQL);  PreparedStatement getLastAddressIDStmt = conn.prepareStatement(getLastAddressIDSQL);  PreparedStatement insertAccountAddressStmt = conn.prepareStatement(insertAccountAddressSQL)) {

            conn.setAutoCommit(false); // Bắt đầu transaction

            // Thêm profile vào bảng Customer
            insertCustomerStmt.setString(1, accountID);
            insertCustomerStmt.setString(2, firstName);
            insertCustomerStmt.setString(3, lastName);
            insertCustomerStmt.setString(4, phoneNumber);
            insertCustomerStmt.setString(5, birthDate);
            insertCustomerStmt.setDouble(6, purchaseAmount);
            insertCustomerStmt.setString(7, accountID);
            insertCustomerStmt.executeUpdate();

            // Thêm địa chỉ vào bảng Address
            insertAddressStmt.setString(1, addressDetail);
            insertAddressStmt.executeUpdate();

            // Lấy addressID mới thêm vào
            try ( ResultSet rs = getLastAddressIDStmt.executeQuery()) {
                if (rs.next()) {
                    String newAddressID = rs.getString("addressID");

                    // Thêm vào bảng Account_Address
                    insertAccountAddressStmt.setString(1, accountID);
                    insertAccountAddressStmt.setString(2, newAddressID);
                    insertAccountAddressStmt.setInt(3, isDefaultAddress ? 1 : 0);
                    insertAccountAddressStmt.executeUpdate();
                }
            }

            conn.commit(); // Hoàn tất transaction
            return true;
        } catch (SQLException e) {
            conn.rollback(); // Nếu lỗi, rollback
            throw e;
        } finally {
            conn.setAutoCommit(true); // Bật lại auto commit
        }
    }

    public Address getAddressByID(String id) throws SQLException {
        Object[] params = {id};
        String sql = "SELECT Address.*, Account_Address.defaultAddress\n"
                + "FROM     Account_Address INNER JOIN\n"
                + "                  Address ON Account_Address.addressID = Address.addressID\n"
                + "WHERE  (Address.addressID = ?)";
        ResultSet rs = dbContext.exeQueryAlt(sql, params);
        if (rs.next()) {
            return new Address(rs.getString(1), rs.getString(2), rs.getBoolean(3));
        }
        return null;
    }

    public int countAddressID() throws SQLException {
        String sql = "SELECT COUNT(addressID) FROM [dbo].[Address]";
        ResultSet rs = dbContext.exeQuery(sql);

        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public void updateAddress(Address address) throws SQLException {
        String sql = "UPDATE [dbo].[Address]\n"
                + "   SET [addressDetail] = ?\n"
                + " WHERE [addressID] = ?";
        Object[] params = {
            address.getAddressDetail(),
            address.getAddressID()
        };
        dbContext.exeNonQuery(sql, params);
    }

    public void deleteAddress(String addressID) throws SQLException {
        String sql = "DELETE FROM [dbo].[Account_Address]\n"
                + "      WHERE [addressID] = ?";
        Object[] params = new Object[]{
            addressID
        };
        dbContext.exeNonQuery(sql, params);

        sql = "DELETE FROM [dbo].[Address]\n"
                + "      WHERE [addressID] = ?";
        dbContext.exeNonQuery(sql, params);
    }

    public void updateDefaultAddress(String accountID, String defaultAddressID) throws SQLException {
        String sql = "UPDATE [dbo].[Account_Address]\n"
                + "   SET [defaultAddress] = 0\n"
                + " WHERE [accountID] = ?";
        Object[] params = new Object[]{
            accountID
        };
        dbContext.exeNonQuery(sql, params);

        sql = "UPDATE [dbo].[Account_Address]\n"
                + "   SET [defaultAddress] = 1\n"
                + " WHERE [addressID] = ?";
        params = new Object[]{
            defaultAddressID
        };
        dbContext.exeNonQuery(sql, params);
    }

    public List<Address> getAddressByAccount(String id) throws SQLException {
        Object[] params = {id};
        String sql = "SELECT Address.*, Account_Address.defaultAddress\n"
                + "                FROM     Account_Address INNER JOIN\n"
                + "                                  Address ON Account_Address.addressID = Address.addressID\n"
                + "                WHERE  (Account_Address.accountID LIKE ?)";
        ResultSet rs = dbContext.exeQueryAlt(sql, params);

        List<Address> addressList = new ArrayList<>();
        while (rs.next()) {
            addressList.add(new Address(rs.getString(1), rs.getString(2), rs.getBoolean(3)));
        }
        return addressList;
    }
}
