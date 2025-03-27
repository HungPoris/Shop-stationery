package dal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static utils.DBConnect.getConnection;
import utils.DBContext;
import utils.DBUtils;

public class AccountDAO {

    public Account checkLogin(String usernameOrEmail, String password) throws SQLException, ClassNotFoundException {
        Account user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            // Tạo kết nối cơ sở dữ liệu
            conn = new DBContext().getConnection();
            String sql = "SELECT * FROM [Account] WHERE (username = ? OR email = ?) AND password = ?";

            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setString(1, usernameOrEmail);  // Sử dụng tên người dùng hoặc email
                ptm.setString(2, usernameOrEmail);  // Sử dụng tên người dùng hoặc email
                ptm.setString(3, password);         // Mật khẩu đăng nhập

                rs = ptm.executeQuery();

                if (rs.next()) {
                    // Lấy thông tin người dùng từ kết quả truy vấn
                    String accountId = rs.getString("accountID");
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String role = rs.getString("roleID");
                    String registrationDate = rs.getString("registrationDate");
                    int status = rs.getInt("status"); // Lấy trạng thái tài khoản (0: bị khóa, 1: hoạt động)

                    // Tạo đối tượng Account với thông tin từ cơ sở dữ liệu
                    user = new Account(accountId, username, email, password, role, registrationDate, status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Ném lại exception để phía caller có thể xử lý
        } finally {
            // Đảm bảo đóng tài nguyên sau khi sử dụng
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return user;  // Trả về đối tượng Account (null nếu không tìm thấy tài khoản)
    }

    public boolean register(String username, String email, String password, String accountID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean isRegistered = false;
        try {
            conn = new DBContext().getConnection();
            String sql = "INSERT INTO [Account] (username, email, password, accountID, registrationDate, roleId, status) VALUES (?, ?, ?, ?, ?, 'ROL003', 1)";
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setString(1, username);
                ptm.setString(2, email);
                ptm.setString(3, password);
                ptm.setString(4, accountID);
                ptm.setString(5, LocalDate.now().toString());
                isRegistered = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return isRegistered;
    }

    // ✅ Kiểm tra tài khoản có tồn tại không
    public boolean checkExist(String accountId) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT accountId FROM [Account] WHERE accountId = ?";
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setString(1, accountId);
                rs = ptm.executeQuery();
                exists = rs.next(); // Nếu có dữ liệu thì tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return exists;
    }

    public boolean editUser(int accountId, String username, int roleId, String password) throws SQLException {
        String sql = "UPDATE Account SET username = ?, roleID = ?, password = ? WHERE accountID = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, roleId);
            ps.setString(3, password);
            ps.setInt(4, accountId);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM [Account]";
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String accountId = rs.getString("accountId");
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String role = rs.getString("roleId");
                    String registrationDate = rs.getString("registrationDate");
                    int status = rs.getInt("status");
                    accounts.add(new Account(accountId, username, email, password, role, registrationDate, status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return accounts;
    }

    // ✅ Lấy tài khoản theo ID
    public Account getById(String accountId) throws SQLException, ClassNotFoundException {
        Account account = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        conn = DBUtils.getConnection();
        // Cập nhật SQL để lấy thêm email
        String sql = "SELECT accountId, username, email, password, roleId, registrationDate, status FROM [Account] WHERE accountId = ?";
        ptm = conn.prepareStatement(sql);
        ptm.setString(1, accountId);
        rs = ptm.executeQuery();
        if (rs.next()) {
            // Khởi tạo Account với đầy đủ các trường bao gồm email, roleId, registrationDate, status
            account = new Account(
                    rs.getString("accountId"),
                    rs.getString("username"),
                    rs.getString("email"), // Thêm email vào constructor
                    rs.getString("password"),
                    rs.getString("roleId"),
                    rs.getString("registrationDate"),
                    rs.getInt("status") // Cập nhật status
            );
        }
        if (rs != null) {
            rs.close();
        }
        if (ptm != null) {
            ptm.close();
        }
        if (conn != null) {
            conn.close();
        }
        return account;
    }

    public Account getByIdh(int accountId) throws SQLException, ClassNotFoundException {
        Account account = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        conn = DBUtils.getConnection();
        String sql = "SELECT accountId, username, email, password, roleId, registrationDate, status FROM [Account] WHERE accountId = ?";
        ptm = conn.prepareStatement(sql);
        ptm.setInt(1, accountId); // Ép kiểu int
        rs = ptm.executeQuery();
        if (rs.next()) {
            account = new Account(
                    rs.getString("accountId"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("roleId"),
                    rs.getString("registrationDate"),
                    rs.getInt("status")
            );
        }
        if (rs != null) {
            rs.close();
        }
        if (ptm != null) {
            ptm.close();
        }
        if (conn != null) {
            conn.close();
        }
        return account;
    }

    public Account getByUsername(String username) throws SQLException {
        Account account = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM [Account] WHERE username = ?";
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setString(1, username);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String accountId = rs.getString("accountId");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String role = rs.getString("roleId");
                    String registrationDate = rs.getString("registrationDate");
                    int status = rs.getInt("status");
                    account = new Account(accountId, username, email, password, role, registrationDate, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return account;
    }

    public boolean resetPassword(String accountId, String newPassword) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean isUpdated = false;

        try {
            conn = DBUtils.getConnection();
            String sql = "UPDATE [Account] SET password = ? WHERE accountId = ?";
            ptm = conn.prepareStatement(sql);
            ptm.setString(1, newPassword);
            ptm.setString(2, accountId);

            int rowsAffected = ptm.executeUpdate();
            if (rowsAffected > 0) {
                isUpdated = true;
            }
        } catch (SQLException ex) {
            throw new SQLException("Error while updating password", ex);
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return isUpdated;
    }

    public boolean updateAccountStatus(String accountId, boolean isActive) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean result = false;

        try {
            conn = DBUtils.getConnection();
            String sql = "UPDATE [Account] SET status = ? WHERE accountID = ?";
            ptm = conn.prepareStatement(sql);
            ptm.setInt(1, isActive ? 1 : 0); // 1: kích hoạt, 0: vô hiệu hóa
            ptm.setString(2, accountId);
            result = ptm.executeUpdate() > 0; // Trả về true nếu có bản ghi được cập nhật
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public Account getAccountById(String accountId) throws SQLException, ClassNotFoundException {
        Account account = null;
        String sql = "SELECT accountId, username, email, password, roleId FROM Account WHERE accountId = ?";

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account = new Account(
                        rs.getString("accountId"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("roleId")
                );
            }
        }
        return account;
    }

    public boolean updateAccount(Account account) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Account SET username = ?, email = ?"
                + (account.getPassword() != null && !account.getPassword().isEmpty() ? ", password = ?" : "")
                + ", roleId = ? WHERE accountId = ?";

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getEmail());

            int paramIndex = 3;
            if (account.getPassword() != null && !account.getPassword().isEmpty()) {
                ps.setString(paramIndex++, account.getPassword());
            }

            // Đảm bảo roleId luôn có giá trị hợp lệ
            System.out.println("Final roleID before update: " + account.getRoleId());
            ps.setString(paramIndex++, account.getRoleId());

            ps.setString(paramIndex, account.getAccountId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateAccountRole(String accountID, String RoleID) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Account SET roleId = ? "
                + "WHERE accountId = ?";
        Object[] params = {
            RoleID, accountID
        };
        DBContext dBContext = new DBContext();
        return dBContext.exeNonQuery(sql, params) > 0;
    }

    public boolean lockAccount(String accountId) throws SQLException, ClassNotFoundException {
        return updateAccountStatus(accountId, false); // Vô hiệu hóa (status = 0)
    }

    public boolean unlockAccount(String accountId) throws SQLException, ClassNotFoundException {
        return updateAccountStatus(accountId, true); // Kích hoạt (status = 1)
    }

    public String convertToMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] byteData = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : byteData) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
