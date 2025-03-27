/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.profile;

import dal.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Address;
import model.Customer;

/**
 *
 * @author conkg
 */
@WebServlet(name = "ShowProfileServlet", urlPatterns = {"/ShowProfileServlet"})
public class ShowProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin tài khoản đang đăng nhập từ session
        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");

        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String customerID = account.getAccountId(); // Lấy customerID từ tài khoản đang đăng nhập
        AddressDAO addressDAO = new AddressDAO();
        Customer customer = addressDAO.getJoinedAddressDetails(customerID);

        if (customer == null) {
            response.sendRedirect("AddProfileController");
        } else {
            request.setAttribute("customer", customer);
            request.setAttribute("roleName", (customer.getRole() != null) ? customer.getRole().getRoleName() : "");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
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
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String birthDate = request.getParameter("birthDate");
        String[] addressID = request.getParameterValues("addressID");
        String[] addressDetail = request.getParameterValues("addressDetail");
        String defaultAddressID = request.getParameter("defaultAddress");
        String newAddress = request.getParameter("newAddress");
        String deleteAddress = request.getParameter("deleteAddress");

        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");

        try {
            // Chuyển đổi ngày sinh từ String -> java.sql.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(birthDate);
            java.sql.Date sqlBirthDate = new java.sql.Date(utilDate.getTime());

            // Cập nhật thông tin khách hàng
            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = new Customer(account.getAccountId(), firstName, lastName, email, phoneNumber, sqlBirthDate);
            customerDAO.updateCustomerInfo(customer);

            // Cập nhật địa chỉ khách hàng
            AddressDAO addressDAO = new AddressDAO();
            if (addressID != null && addressDetail != null) {
                for (int i = 0; i < addressID.length; i++) {
                    Address address = new Address(addressID[i], addressDetail[i]);
                    addressDAO.updateAddress(address);
                }
            }

            // Cập nhật địa chỉ mặc định
            if (defaultAddressID != null) {
                addressDAO.updateDefaultAddress(account.getAccountId(), defaultAddressID);
            }

            // Thêm địa chỉ mới (nếu có)
            if (newAddress != null && !newAddress.trim().isEmpty()) {
                addressDAO.addAddress(account.getAccountId(), newAddress);
            }
            // Xóa địa chỉ
            if (deleteAddress != null) {
                addressDAO.deleteAddress(deleteAddress);
            }
            response.sendRedirect("ShowProfileServlet");
        } catch (ParseException ex) {
            Logger.getLogger(ShowProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ShowProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
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
