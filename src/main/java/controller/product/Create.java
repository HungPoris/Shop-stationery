/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.product;

import dal.BookDAO;
import dal.CategroryDAO;
import dal.PublisherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import model.Book;
import model.Category;
import model.Publisher;

/**
 *
 * @author conkg
 */
@MultipartConfig
public class Create extends HttpServlet {

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
            out.println("<title>Servlet Create</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Create at " + request.getContextPath() + "</h1>");
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
        PublisherDAO publisherDao = new PublisherDAO();
        CategroryDAO categoryDao = new CategroryDAO();
        List<Publisher> pushlishers = publisherDao.getAllPublishers();
        List<Category> categories = categoryDao.getAllCategories();
        request.setAttribute("pushlishers", pushlishers);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("Create.jsp").forward(request, response);
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
        boolean isBook = request.getParameter("isBook") != null;
        String bookIDStr = request.getParameter("txtID");
        String bookTitle = request.getParameter("txtTitle");
        String bookVersion = request.getParameter("txtVersion");
        String publisherID = isBook ? request.getParameter("txtPublisherID") : "PUB015";
        String[] categoryIDs = request.getParameterValues("txtCategoryID");
        String bookPublishDateStr = request.getParameter("txtPublishDate");
        String bookImportDateStr = request.getParameter("txtImportDate");
        String bookIntro = request.getParameter("txtIntro");
        String bookQuantityStr = request.getParameter("txtQuantity");
        String bookPriceStr = request.getParameter("txtPrice");
        String bookDiscountStr = request.getParameter("txtDiscount");
        String bookFlashStr = request.getParameter("txtFlashsale");

        String img = "";
        try {
            Part part = request.getPart("txtCover");
            if (part == null) {
                throw new Exception("No file part found.");
            }
            String picFolder = "images";
            String[] context = request.getServletContext().getRealPath("").split("target");
            String realPath = context[0] + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + picFolder;

            // Lấy tên file gốc và lưu ảnh
            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            part.write(realPath + File.separator + fileName);
            img = picFolder + File.separator + fileName;

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the file: " + e.getMessage());
            return;
        }

        try {
            BookDAO bookDAO = new BookDAO();
            // Parse input values
            int bookID = Integer.parseInt(bookIDStr);
            int bookQuantity = Integer.parseInt(bookQuantityStr);
            int bookPrice = Integer.parseInt(bookPriceStr);
            int bookDiscount = Integer.parseInt(bookDiscountStr);
            int bookFlashSale = Integer.parseInt(bookFlashStr);
            Date bookPublishDate = Date.valueOf(bookPublishDateStr);
            Date bookImportDate = Date.valueOf(bookImportDateStr);

            // Create Book object
            Book book = new Book(bookID, bookTitle, img, bookVersion, publisherID,
                    bookPublishDate, bookImportDate, bookIntro, bookQuantity, bookPrice, bookDiscount, bookFlashSale);
            // Update the book information in the database
            bookDAO.createBookWithCategories(book, categoryIDs);
            response.sendRedirect("ListServlet"); // Redirect after successful edit
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console
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
