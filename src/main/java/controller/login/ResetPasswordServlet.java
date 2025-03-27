/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.login;

import dal.AccountDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.DBConnect;

public class ResetPasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO accountDAO = new AccountDAO();
        // Lấy mật khẩu mới từ form
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Lấy email từ session (được lưu khi gửi OTP)
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("resetEmail");

        // Kiểm tra email có hợp lệ không
        if (email == null) {
            response.sendRedirect("forget_password.jsp?error=expired");
            return;
        }

        // Kiểm tra mật khẩu có khớp không
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu không khớp!");
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu có đủ mạnh không
        if (!isValidPassword(newPassword)) {
            request.setAttribute("error", "Mật khẩu phải có ít nhất 8 ký tự, 1 chữ hoa, 1 số và 1 ký tự đặc biệt!");
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            return;
        }

        try ( Connection conn = DBConnect.getConnection()) {
            String sql = "UPDATE Account SET [password] = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountDAO.convertToMD5(newPassword));
            stmt.setString(2, email);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                session.removeAttribute("resetEmail");
                response.sendRedirect("login.jsp?success=reset");
            } else {
                request.setAttribute("error", "Không thể cập nhật mật khẩu!");
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống, vui lòng thử lại!");
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
        }
    }

    private boolean isValidPassword(String password) {
        return (password != null && password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$"));
    }
}
