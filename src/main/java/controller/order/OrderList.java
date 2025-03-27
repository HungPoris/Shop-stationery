/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.order;

import dal.*;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 *
 * @author Tran Viet Minh - CE182526
 */
@WebServlet(name = "OrderList", urlPatterns = {"/listOrder"})
public class OrderList extends HttpServlet {

    OrderDAO orderDAO;
    orderItemDAO orderItemDAO;
    AddressDAO addressDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        orderDAO = new OrderDAO();
        orderItemDAO = new orderItemDAO();
        addressDAO = new AddressDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin tài khoản đang đăng nhập từ session
        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");
        try {
            // Kiểm tra quyền truy cập
            if (account == null || !account.getRoleId().equals("ROL003")) {
                response.sendRedirect("home");
                return;
            }
            Object fillter = request.getSession().getAttribute("Fillter");
            List<Address> addressList = addressDAO.getAddressByAccount(account.getAccountId());
            List<deliveryStatus> deliveryStatusList = orderDAO.getAllDelStatus();
            List<paymentStatus> paymentStatusList = orderDAO.getAllPaymentStatus();
            List<OrderInfo> orderList;
            if (fillter != null) {
                orderList = orderDAO.getOrderByFillter(account.getAccountId(), fillter.toString());
            } else {
                orderList = orderDAO.getOrderByID(account.getAccountId());
            }
            request.getSession().removeAttribute("Fillter");
            for (OrderInfo order : orderList) {
                List<CartItem> list = orderItemDAO.getItemsOfThisOrder(order);
                order.setItemList(list);
            }
            request.setAttribute("orderList", orderList);
            request.setAttribute("deliveryStatusList", deliveryStatusList);
            request.setAttribute("paymentStatusList", paymentStatusList);
            request.setAttribute("addressList", addressList);
            request.getRequestDispatcher("orderList.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String deliveryStatus = request.getParameter("DeliveryStatus");
        String deliveryUpdate = request.getParameter("DeliveryUpdate");

        String filter = "";
        if (deliveryStatus != null) {
            filter = "deliveryStatusID = " + deliveryStatus;
        }

        if (deliveryUpdate != null) {
            String orderID = request.getParameter("orderID");
            try {
                orderDAO.updateDeliveryStatus(deliveryUpdate, null, orderID);
            } catch (SQLException ex) {
                Logger.getLogger(ShipperServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.getSession().setAttribute("Fillter", filter.isEmpty() ? null : filter);
        response.sendRedirect("listOrder");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
