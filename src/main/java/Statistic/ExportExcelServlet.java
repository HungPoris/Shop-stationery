/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Statistic;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import model.Account;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Tran Viet Minh - CE182526
 */
@WebServlet(name = "ExportExcelServlet", urlPatterns = {"/ExportExcel"})
public class ExportExcelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");
        if (account == null) {
            response.sendRedirect("login");
            return;
        }

        try ( XSSFWorkbook workbook = new XSSFWorkbook()) {
            //Lấy dữ liệu từ Session
            Map<String, Double> month = (Map<String, Double>) request.getSession().getAttribute("statisticByMonth");
            Map<String, Double> year = (Map<String, Double>) request.getSession().getAttribute("statisticByYear");
            String[] firstCeil = (String[]) request.getSession().getAttribute("firstCeil");
            String[] secondCeil = (String[]) request.getSession().getAttribute("secondCeil");

            // Tạo Sheet doanh thu theo tháng
            XSSFSheet sheetMonth = workbook.createSheet("Monthly Revenue");
            XSSFRow headerRowMonth = sheetMonth.createRow(0);
            headerRowMonth.createCell(0).setCellValue(firstCeil[0]);
            headerRowMonth.createCell(1).setCellValue("Lương");

            int rowNum = 1;
            for (Map.Entry<String, Double> entry : month.entrySet()) {
                XSSFRow dataRow = sheetMonth.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(entry.getKey());
                dataRow.createCell(1).setCellValue(entry.getValue());
            }

            // Tạo Sheet doanh thu theo năm
            XSSFSheet sheetYear = workbook.createSheet("Yearly Revenue");
            XSSFRow headerRowYear = sheetYear.createRow(0);
            headerRowYear.createCell(0).setCellValue(firstCeil[1]);
            headerRowYear.createCell(1).setCellValue(secondCeil[1]);

            rowNum = 1;
            for (Map.Entry<String, Double> entry : year.entrySet()) {
                XSSFRow dataRow = sheetYear.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(entry.getKey());
                dataRow.createCell(1).setCellValue(entry.getValue());
            }

            // Thiết lập header cho file Excel
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + secondCeil[0] + "_Report.xlsx\"");

            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
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
