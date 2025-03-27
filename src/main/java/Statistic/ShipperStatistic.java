/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Statistic;

import com.google.gson.Gson;
import dal.ShipperDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import model.Account;
import model.Shipper;

/**
 *
 * @author Tran Viet Minh - CE182526
 */
@WebServlet(name = "ShipperStatistic", urlPatterns = {"/ShipperStatistic"})
public class ShipperStatistic extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");

        // Kiểm tra quyền truy cập
        if (account == null || !account.getRoleId().equals("ROL004")) {
            response.sendRedirect("home");
            return;
        }
        ShipperDAO shipperDAO = new ShipperDAO();
        ArrayList<Shipper> shipper = shipperDAO.getShipperSalaryByID(account.getAccountId());
        Map<String, Double> statisticByMonth = calculateStatisticByMonth(shipper);
        Map<String, Double> statisticByYear = calculateStatisticByYear(shipper);

        Gson gson = new Gson();
        String jsonStatisticByMonth = gson.toJson(statisticByMonth);
        String jsonStatisticByYear = gson.toJson(statisticByYear);
        String[] firstCeil = {"Month", "Year"};
        String[] secondCeil = {"Salary", "Salary"};
        request.getSession().setAttribute("firstCeil", firstCeil);
        request.getSession().setAttribute("secondCeil", secondCeil);
        request.getSession().setAttribute("statisticByMonth", statisticByMonth);
        request.getSession().setAttribute("statisticByYear", statisticByYear);
        request.setAttribute("jsonStatisticByMonth", jsonStatisticByMonth);
        request.setAttribute("jsonStatisticByYear", jsonStatisticByYear);

        // Sử dụng RequestDispatcher để giữ dữ liệu khi chuyển trang
        request.getRequestDispatcher("statistic.jsp").forward(request, response);
    }

    private Map<String, Double> calculateStatisticByMonth(ArrayList<Shipper> shippers) {
        TreeMap<String, Double> statisticByMonth = new TreeMap<>();

        for (Shipper shipper : shippers) {
            LocalDate invoiceDate = shipper.getMonthShipped().toLocalDate();
            String monthYear = invoiceDate.format(DateTimeFormatter.ofPattern("MM-yyyy"));

            // Tính tổng doanh thu theo tháng
            double total = statisticByMonth.getOrDefault(monthYear, 0.0) + shipper.getSalaryOfMonth();
            total = Math.ceil(total * 100) / 100;
            statisticByMonth.put(monthYear, total);
        }
        return statisticByMonth;
    }

    private Map<String, Double> calculateStatisticByYear(ArrayList<Shipper> shippers) {
        TreeMap<String, Double> byYear = new TreeMap<>();

        for (Shipper shipper : shippers) {
            LocalDate invoiceDate = shipper.getMonthShipped().toLocalDate();
            String year = invoiceDate.format(DateTimeFormatter.ofPattern("yyyy"));

            // Tính tổng doanh thu theo năm
            double total = byYear.getOrDefault(year, 0.0) + shipper.getSalaryOfMonth();
            total = Math.round(total * 100.0) / 100.0;
            byYear.put(year, total);
        }

        return byYear;
    }

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
