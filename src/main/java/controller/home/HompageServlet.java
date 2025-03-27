/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dal.BookDAO;
import dal.CartDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalTime;
import model.Account;
import model.Book;
import model.Cart;
import model.CartItem;
import model.Category;
import utils.CustomException;
import utils.Utility;

/**
 *
 * @author anhkc
 */
public class HompageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet bookServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet bookServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws ServletException, IOException {
        initializeAccount(request, response);
        showFeaturedBooks(request, response);

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
        processRequest(request, response);
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

    public void showCategories(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws ServletException, IOException, CustomException {
        try {

            BookDAO bookDAO = new BookDAO();

            List<Category> listC = bookDAO.getAllcategory();
            if (listC == null || listC.isEmpty()) {
                throw new CustomException("Categories not found!");
            }
            request.getSession().setAttribute("listC", listC);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void initializeAccount(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws ServletException, IOException {
        Utility tool = new Utility();
        CartDAO cartDAO = new CartDAO();
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");
        try {
            if (cart == null) {
                cart = cartDAO.getLatestCartOfThisAccount(account);
                if (cart != null) {
                    List<CartItem> itemList = cartDAO.getItemsOfThisCart(cart);
                    cart.setItemList(itemList);
                    cart.setTotalQuantity();
                    cart.setTotalAmount(tool.getTotalAmount(itemList));
                    request.getSession().setAttribute("cart", cart);
                }
            }

            if (account != null) {
                request.getSession().setAttribute("LOGIN_USER", account);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showFeaturedBooks(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws ServletException, IOException {
        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();
        try {
            showCategories(request, response);
            dal.HomeBookDAO bookDAO = new dal.HomeBookDAO();
//            List<Book> bookListNew = bookDAO.selectAllBook();

            List<Book> bookListNew = bookDAO.selectTop10OrderBy("bookImportDate", "desc");
            request.setAttribute("newBooks", bookListNew);

            List<Book> bookListFlash;

            if (currentHour < 9 || currentHour % 3 != 0) {
                bookDAO.callResetFlashSale();
            } else {
                bookDAO.callUpdateFlashSale();
                bookListFlash = bookDAO.selectTop10OrderBy("bookFlashSale", "desc");
                request.setAttribute("flashBooks", bookListFlash);
            }
//                bookDAO.callUpdateFlashSale();
//                bookListFlash = bookDAO.selectTop10OrderBy("bookFlashSale", "desc");
//                request.setAttribute("flashBooks", bookListFlash);

            List<Book> bookListBest = bookDAO.selectTop10BestSeller();
            request.setAttribute("bestBooks", bookListBest);

            List<Book> bookListDiscount = bookDAO.selectTop10OrderBy("bookDiscount", "desc");
            request.setAttribute("discountBooks", bookListDiscount);

            request.getRequestDispatcher("homepage.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
