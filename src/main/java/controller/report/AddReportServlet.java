package controller.report;

import dal.ReportDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@MultipartConfig
@WebServlet("/AddReportServlet")
public class AddReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("report.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String suggestion = request.getParameter("suggestion");
        String img = "images/default.png"; // Ảnh mặc định nếu không có file tải lên

        try {
            Part part = request.getPart("txtCover");
            if (part != null && part.getSize() > 0) {
                String picFolder = "report";

                // Xác định đường dẫn lưu ảnh trong thư mục webapp
                String[] context = request.getServletContext().getRealPath("").split("target");
                String realPath = context[0] + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + picFolder;

                // Lấy tên file gốc và lưu ảnh
                String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                part.write(realPath + File.separator + fileName);
                img = picFolder + File.separator + fileName;

            }

            // Gọi DAO để thêm báo cáo vào database
            ReportDAO dao = new ReportDAO();
            if (dao.addReport(img, suggestion)) {
                response.sendRedirect("home"); // Thành công thì chuyển hướng
            } else {
                request.setAttribute("ERROR_TYPE", "red");
                request.setAttribute("ERROR_MESSAGE", "Thêm báo cáo thất bại!");
                request.getRequestDispatcher("report.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_TYPE", "red");
            request.setAttribute("ERROR_MESSAGE", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("home").forward(request, response);
        }
    }
}
