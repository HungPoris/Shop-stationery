/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.order;

import dal.AddressDAO;
import dal.CustomerDAO;
import dal.OrderDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Address;
import model.Customer;
import model.OrderInfo;
import model.deliveryStatus;

/**
 *
 * @author Tran Viet Minh - CE182526
 */
@WebServlet(name = "ShipperServlet", urlPatterns = {"/ShipperOrder"})
public class ShipperServlet extends HttpServlet {

    OrderDAO orderDAO;
    AddressDAO addressDAO;
    CustomerDAO CustomerDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderDAO = new OrderDAO();
        addressDAO = new AddressDAO();
        CustomerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");
        try {
            // Kiểm tra quyền truy cập
            if (account == null || !account.getRoleId().equals("ROL004")) {
                response.sendRedirect("home");
                return;
            }
            Object fillter = request.getSession().getAttribute("Fillter");
            List<Address> addressList = addressDAO.getAddressByAccount("%");
            List<deliveryStatus> deliveryStatusList = orderDAO.getAllDelStatus();
            List<OrderInfo> orderList;
            List<Customer> customerList = CustomerDAO.getAllCustomerInfo();
            if (fillter != null) {
                orderList = orderDAO.getShipperOrderByFillter(account.getAccountId(), fillter.toString());
            } else {
                orderList = orderDAO.getOrderByFillter("%%", "deliveryStatusID = 2");
            }
            request.getSession().removeAttribute("Fillter");
            request.setAttribute("orderList", orderList);
            request.setAttribute("deliveryStatusList", deliveryStatusList);
            request.setAttribute("addressList", addressList);
            request.setAttribute("customerList", customerList);
            request.getRequestDispatcher("ShipperOrder.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");
        String deliveryStatus = request.getParameter("DeliveryStatus");
        String deliveryUpdate = request.getParameter("DeliveryUpdate");

        String filter = "";
        if (deliveryStatus != null) {
            filter = "deliveryStatusID = " + deliveryStatus;
        } else if (deliveryUpdate != null) {
            String orderID = request.getParameter("orderID");
            try {
                orderDAO.updateDeliveryStatus(deliveryUpdate, account.getAccountId(), orderID);
            } catch (SQLException ex) {
                Logger.getLogger(ShipperServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.getSession().setAttribute("Fillter", filter.isEmpty() ? null : filter);
        response.sendRedirect("ShipperOrder");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
