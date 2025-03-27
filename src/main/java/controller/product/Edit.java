package controller.product;

import dal.BookDAO;
import dal.CategroryDAO;
import dal.PublisherDAO;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import model.Book;
import model.Category;
import model.Publisher;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class Edit extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookIDStr = request.getParameter("id");

        if (bookIDStr != null) {
            try {
                int bookID = Integer.parseInt(bookIDStr);
                BookDAO bookDAO = new BookDAO();
                PublisherDAO publisherDao = new PublisherDAO();
                CategroryDAO categoryDao = new CategroryDAO();

                // Lấy thông tin sách và danh mục
                Book book = bookDAO.getbookbyid(bookID);
                List<Publisher> publishers = publisherDao.getAllPublishers();
                List<Category> categories = categoryDao.getAllCategories();
                List<String> selectedCategories = bookDAO.getCategoriesByBookID(bookID);

                // Gửi dữ liệu sang JSP
                request.setAttribute("product", book);
                request.setAttribute("publishers", publishers);
                request.setAttribute("categories", categories);
                request.setAttribute("selectedCategories", selectedCategories);
                request.getRequestDispatcher("edit.jsp").forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("ListServlet");
            }
        } else {
            response.sendRedirect("ListServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            BookDAO bookDAO = new BookDAO(); // Đưa vào try-catch để tránh lỗi

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

            // Xử lý ảnh
            String img = null;
            Part part = request.getPart("txtCover");
            if (part != null && part.getSize() > 0 && part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                // Xác định thư mục lưu ảnh
                String picFolder = "images";
                String realPath = request.getServletContext().getRealPath("/") + picFolder;
                File uploadDir = new File(realPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Lưu ảnh vào thư mục
                String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                part.write(realPath + File.separator + fileName);
                img = picFolder + File.separator + fileName;
            } else {
                // Lấy ảnh cũ nếu không có ảnh mới
                img = bookDAO.getBookCoverById(Integer.parseInt(bookIDStr));
                if (img == null) {
                    img = "images/default_cover.jpg"; // Ảnh mặc định nếu sách không có ảnh
                }
            }

            // Chuyển đổi dữ liệu với xử lý lỗi
            int bookID = Integer.parseInt(bookIDStr);
            int bookQuantity = parseIntOrDefault(bookQuantityStr, 0);
            int bookPrice = parseIntOrDefault(bookPriceStr, 0);
            int bookDiscount = parseIntOrDefault(bookDiscountStr, 0);
            int bookFlashSale = parseIntOrDefault(bookFlashStr, 0);
            Date bookPublishDate = parseDateOrNull(bookPublishDateStr);
            Date bookImportDate = parseDateOrNull(bookImportDateStr);

            if (bookPublishDate == null || bookImportDate == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ngày xuất bản hoặc ngày nhập không hợp lệ.");
                return;
            }

            // Tạo đối tượng Book
            Book book = new Book(bookID, bookTitle, img, bookVersion, publisherID,
                    bookPublishDate, bookImportDate, bookIntro, bookQuantity, bookPrice, bookDiscount, bookFlashSale);

            // Kiểm tra categoryIDs trước khi cập nhật
            if (categoryIDs == null || categoryIDs.length == 0) {
                categoryIDs = new String[]{"DEFAULT_CATEGORY"}; // Gán giá trị mặc định nếu cần
            }

            // Cập nhật sách và danh mục trong database
            bookDAO.updateBookWithCategories(book, categoryIDs);
            response.sendRedirect("ListServlet"); // Chuyển hướng sau khi cập nhật thành công

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Lỗi định dạng số: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi cập nhật sách: " + e.getMessage());
        }
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private Date parseDateOrNull(String value) {
        try {
            return Date.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet chỉnh sửa thông tin sách";
    }
}
