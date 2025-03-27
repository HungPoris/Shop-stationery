package controller.login;

import dal.AccountDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.DBConnect;

public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO accountDAO = new AccountDAO();
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String roleID = "ROL003";

        // Kiểm tra dữ liệu đầu vào
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            response.sendRedirect("login.jsp?message=empty");
            return;
        } else if (!isValidPassword(password)) {
            request.setAttribute("ERROR_TYPE", "account_locked");
            request.setAttribute("ERROR_MESSAGE", "Mật khẩu phải có ít nhất 8 ký tự, 1 chữ hoa, 1 số và 1 ký tự đặc biệt!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try ( Connection conn = DBConnect.getConnection()) {
            if (isEmailExist(conn, email)) {
                response.sendRedirect("login.jsp?message=exist");
                return;
            }

            String accountID = generateAccountID(conn);

            String sql = "INSERT INTO Account (accountID, username, password, roleID, registrationDate, status, email) "
                    + "VALUES (?, ?, ?, ?, GETDATE(), 1, ?)";
            try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, accountID);
                stmt.setString(2, name);
                stmt.setString(3, accountDAO.convertToMD5(password)); // Cần mã hóa mật khẩu trước khi lưu
                stmt.setString(4, roleID);
                stmt.setString(5, email);
                stmt.executeUpdate();
            }

            // Chỉ chuyển hướng đến login.jsp với thông tin để gửi email khi tài khoản được tạo thành công
            response.sendRedirect("login.jsp?message=success&email=" + email + "&name=" + name);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?message=error");
        }
    }

    private boolean isValidPassword(String password) {
        return (password != null && password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$"));
    }

    private boolean isEmailExist(Connection conn, String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Account WHERE email = ?";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    private String generateAccountID(Connection conn) throws SQLException {
        String sql = "SELECT TOP 1 accountID FROM Account WHERE accountID LIKE 'ACC%' ORDER BY accountID DESC";
        try ( PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String lastID = rs.getString("accountID");
                int number = Integer.parseInt(lastID.substring(3)) + 1;
                return String.format("ACC%03d", number);
            }
        }
        return "ACC001";
    }
}
