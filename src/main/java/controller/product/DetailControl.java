/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.product;

import dal.AuthorDAO;
import dal.BookDAO;
import dal.CategroryDAO;
import dal.FeedbackDAO;
import dal.PublisherDAO;
import model.Author;
import model.Book;
import model.Category;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import model.Account;
import model.Feedback;
import model.Publisher;

/**
 *
 * @author conkg
 */
public class DetailControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ID = request.getParameter("pid");
        try {
            BookDAO bookDAO = new BookDAO();
            AuthorDAO authorDAO = new AuthorDAO();
            PublisherDAO publisherDAO = new PublisherDAO();
            CategroryDAO categoryDAO = new CategroryDAO();

            int bookID = Integer.parseInt(ID);

            // Lấy thông tin sách, tác giả, nhà xuất bản, thể loại
            Book book = bookDAO.getbookbyid(bookID);
            List<Author> authors = authorDAO.getbookbyAuthor(ID);
            Publisher publisher = publisherDAO.getPublisherByBookID(bookID);
            List<Category> categories = categoryDAO.getCategoriesByBookID(ID);

            // Lấy 5 sách ngẫu nhiên cùng thể loại
            if (!categories.isEmpty()) {
                String categoryId = categories.get(0).getCategoryID(); // Lấy thể loại đầu tiên
                List<Book> randomBooks = bookDAO.getRandomBooksByCategory(categoryId);
                request.setAttribute("randomBooks", randomBooks);
            }
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            List<Feedback> feedbacks = feedbackDAO.getAllFeedbacks(bookID);
            // Truyền dữ liệu đến JSP
            request.setAttribute("feedbackList", feedbacks);
            request.setAttribute("Chitiet", book);
            request.setAttribute("Chitiet1", authors);
            request.setAttribute("Chitiet2", publisher);
            request.setAttribute("Chitiet3", categories);

            request.getRequestDispatcher("Chitiet.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Chuyển hướng nếu lỗi
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookID = request.getParameter("bookID");
        String content = request.getParameter("content");
        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");

        // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập ngay lập tức
        if (account == null) {
            response.sendRedirect("login");
            return;
        }

        // Nếu nội dung phản hồi không hợp lệ, không thực hiện thêm phản hồi
        if (content == null || content.isBlank()) {
            response.sendRedirect("detail?pid=" + bookID);
            return;
        }

        try {
            // Gọi DAO để thêm phản hồi
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            List<Feedback> feedbacks = feedbackDAO.getAllFeedbacks(Integer.parseInt(bookID));
            for (Feedback feedback : feedbacks) {
                if (account.getAccountId().equals(feedback.getCustomer().getCustomerID())) {
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().println("<script>alert('Mỗi sản phẩm chỉ được nhận xét 1 lần!'); history.back();</script>");
                    return;
                }
            }
            feedbackDAO.addFeedback(account.getAccountId(), bookID, content);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra, vui lòng thử lại sau.");
            return;
        }
        response.sendRedirect("detail?pid=" + bookID);
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
