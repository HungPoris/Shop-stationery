package controller.login;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LockUnlockUserController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            String accountId = request.getParameter("accountId");
            String action = request.getParameter("action"); // lock hoặc unlock
            AccountDAO dao = new AccountDAO();

            boolean success = false;
            if ("lock".equalsIgnoreCase(action)) {
                success = dao.lockAccount(accountId);
            } else if ("unlock".equalsIgnoreCase(action)) {
                success = dao.unlockAccount(accountId);
            }

            if (success) {
                response.sendRedirect("manageUser?success=" + action);
            } else {
                response.sendRedirect("manageUser?error=actionFailed");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LockUnlockUserController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("manageUser?error=exception");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LockUnlockUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LockUnlockUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet để vô hiệu hóa hoặc kích hoạt người dùng";
    }
}
