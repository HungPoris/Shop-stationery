/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.product;

import dal.BookDAO;
import dal.FavoriteDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Book;

/**
 *
 * @author Tran Viet Minh - CE182526
 */
@WebServlet(name = "Favorite", urlPatterns = {"/Favorite"})
public class Favorite extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin tài khoản đang đăng nhập từ session
        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");

        // Kiểm tra quyền truy cập
        if (account == null || !account.getRoleId().equals("ROL003")) {
            response.sendRedirect("home");
            return;
        }

        FavoriteDAO favoriteDAO = new FavoriteDAO();
        try {
            List<Book> books = new ArrayList<Book>();
            BookDAO bookDAO = new BookDAO();

            List<Integer> listBookID = favoriteDAO.getFavoriteBooks(account.getAccountId());
            for (int id : listBookID) {
                books.add(bookDAO.getbookbyid(id));
            }

            request.setAttribute("bookList", books);
            request.getRequestDispatcher("favorite.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Favorite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Favorite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookID = request.getParameter("bookID");
        String action = request.getParameter("action");

        // Lấy thông tin tài khoản đang đăng nhập từ session
        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");

        // Kiểm tra quyền truy cập
        if (account == null || !account.getRoleId().equals("ROL003") || action == null) {
            response.sendRedirect("home");
            return;
        }

        FavoriteDAO favoriteDAO = new FavoriteDAO();
        try {
            if (action.equals("favorite")) {
                if (!favoriteDAO.isFavorite(account.getAccountId(), Integer.parseInt(bookID))) {
                    favoriteDAO.addFavorite(account.getAccountId(), Integer.parseInt(bookID));
                }
                response.sendRedirect("detail?pid=" + bookID);
                return;
            } else if (action.equals("delete")) {
                favoriteDAO.removeFavorite(account.getAccountId(), Integer.parseInt(bookID));
                doGet(request, response);
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Favorite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
