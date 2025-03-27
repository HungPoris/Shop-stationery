package controller.login;

import dal.AccountDAO;
import model.Account;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ManageUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");

            // Lấy thông tin tài khoản đang đăng nhập từ session
            Account account = (Account) request.getSession().getAttribute("LOGIN_USER");

            // Kiểm tra quyền truy cập
            if (account == null || !account.getRoleId().equals("ROL001")) {
                response.sendRedirect("home");
                return;
            }

            // Lấy danh sách tài khoản từ database
            AccountDAO accountDAO = new AccountDAO();
            List<Account> accounts = accountDAO.getAllAccounts();

            // Đặt accounts và loggedInUser vào request để JSP sử dụng
            request.setAttribute("accounts", accounts);
            request.setAttribute("loggedInUser", account);
            request.getRequestDispatcher("managerUser.jsp").forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ManageUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountId = request.getParameter("accountId");
        String roleId = request.getParameter("roleId");  // Đảm bảo tên biến đúng

        // Kiểm tra đầu vào hợp lệ (Không kiểm tra password vì có thể bỏ trống)
        if (accountId == null || accountId.trim().isEmpty()
                || roleId == null || roleId.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Role không hợp lệ");
            doGet(request, response);
            return;
        }

        AccountDAO accountDAO = new AccountDAO();
        boolean isUpdated = false;
        try {
            isUpdated = accountDAO.updateAccountRole(accountId, roleId);
        } catch (SQLException | ClassNotFoundException ex) {
            request.setAttribute("errorMessage", "Không thể cập nhật.");
        }

        if (isUpdated) {
            response.sendRedirect("manageUser?success=UserUpdated");
        } else {
            request.setAttribute("errorMessage", "Không thể cập nhật. Vui lòng thử lại.");
            doGet(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "ManageUserController servlet to handle user management";
    }
}
