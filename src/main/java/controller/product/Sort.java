/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.product;

import dal.BookDAO;
import model.Book;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author conkg
 */
public class Sort extends HttpServlet {

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
            out.println("<title>Servlet Sort</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Sort at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String thamsosapxep = request.getParameter("thamsosapxep");  // Lấy tham số sắp xếp từ URL

        List<Book> list = new ArrayList<>();

        try {
            BookDAO dao = new BookDAO();
            switch (thamsosapxep) {
                case "newest":
                    list = dao.selectOrderBy("bookImportDate", "desc");
                    break;
                case "bestselling":
                    list = dao.selectBestSeller();
                    break;
                case "hotdeal":
                    list = dao.selectOrderBy("bookDiscount", "desc");
                    break;
                case "a-z":
                    list = dao.selectOrderBy("bookTitle", "asc");
                    break;
                default:
                    list = dao.selectOrderBy("bookImportDate", "desc");
            }
            request.getSession().setAttribute("thamsosapxep", thamsosapxep);
            request.setAttribute("listP", list);
            request.getRequestDispatcher("newjsp1.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace to identify exact errors
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
