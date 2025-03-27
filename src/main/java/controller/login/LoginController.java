package controller.login;

import dal.AccountDAO;
import dal.AddressDAO;
import model.Account;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "login";
        }
        // Forwarding to the login page
        if ("login".equals(action)) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy thông tin từ form đăng nhập
        String usernameOrEmail = request.getParameter("usernameOrEmail");
        String password = request.getParameter("password");

        // Tạo đối tượng AccountDAO
        AccountDAO accountDAO = new AccountDAO();
        AddressDAO addressDAO = new AddressDAO(); // Để lấy thông tin Customer
        String hashedPassword = accountDAO.convertToMD5(password);
        try {
            // Kiểm tra đăng nhập
            Account loginUser = accountDAO.checkLogin(usernameOrEmail, hashedPassword);
            if (loginUser != null) {
                if (loginUser.getStatus() == 0) { // Kiểm tra tài khoản bị khóa
                    // Gửi thông báo tài khoản bị khóa
                    request.setAttribute("ERROR_TYPE", "account_locked");
                    request.setAttribute("ERROR_MESSAGE", "Tài khoản của bạn đã bị khóa! Vui lòng liên hệ bộ phận hỗ trợ để được mở lại.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    // Tạo session cho người dùng khi đăng nhập thành công
                    HttpSession session = request.getSession();
                    session.setAttribute("LOGIN_USER", loginUser);
                    Customer customer = addressDAO.getJoinedAddressDetails(loginUser.getAccountId());
                    if (customer != null) {
                        session.setAttribute("customer", customer); // Lưu customer vào session
                    }
                    // Chuyển hướng sang trang chủ
                    response.sendRedirect("home");
                }
            } else {
                // Nếu đăng nhập không thành công, hiển thị thông báo sai tài khoản hoặc mật khẩu
                request.setAttribute("ERROR_TYPE", "incorrect_credentials");
                request.setAttribute("ERROR_MESSAGE", "Tên tài khoản hoặc mật khẩu không chính xác.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("ERROR_TYPE", "system_error");
            request.setAttribute("ERROR_MESSAGE", "Đã xảy ra lỗi khi kiểm tra tài khoản.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            request.setAttribute("ERROR_TYPE", "system_error");
            request.setAttribute("ERROR_MESSAGE", "Lỗi hệ thống. Vui lòng thử lại sau.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
