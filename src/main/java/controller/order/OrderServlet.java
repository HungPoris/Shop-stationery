/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.order;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.*;
import jakarta.servlet.ServletConfig;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import utils.CustomException;
import utils.Utility;

/**
 *
 * @author Tran Viet Minh - CE182526
 */
public class OrderServlet extends HttpServlet {

    BookDAO bookDAO;
    OrderDAO orderDAO;
    CartDAO cartDAO;
    AddressDAO addressDAO;
    Utility tool;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            bookDAO = new BookDAO();
            orderDAO = new OrderDAO();
            cartDAO = new CartDAO();
            addressDAO = new AddressDAO();
            tool = new Utility();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");
        Cart newCart = (Cart) request.getSession().getAttribute("newCart");
        try {
            // Kiểm tra quyền truy cập
            if (account == null || !account.getRoleId().equals("ROL003")) {
                response.sendRedirect("home");
                return;
            }
            Customer customerInfo = addressDAO.getJoinedAddressDetails(account.getAccountId());
            request.getSession().setAttribute("newCart", newCart);
            request.getSession().setAttribute("customerInfo", customerInfo);
            request.getRequestDispatcher("order.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Account account = (Account) request.getSession().getAttribute("LOGIN_USER");
            Cart newCart = (Cart) request.getSession().getAttribute("newCart");
            Cart cart = (Cart) request.getSession().getAttribute("cart");
            String adressID = request.getParameter("address");

            List<CartItem> updatedCartItems = new ArrayList<>();
            for (CartItem cartItem : cart.getItemList()) {
                boolean isPurchased = false;
                for (CartItem purchasedItem : newCart.getItemList()) {
                    if (cartItem.getBook().getBookID() == purchasedItem.getBook().getBookID()) {
                        isPurchased = true;
                        break;
                    }
                }
                if (isPurchased) {
                    cartDAO.deleteAnItemOfThisCart(cart, cartItem.getBook());
                } else {
                    updatedCartItems.add(cartItem);
                }
            }

            // Cập nhật lại giỏ hàng
            cart.setItemList(updatedCartItems);
            cart.setTotalQuantity();
            cart.setTotalAmount(tool.getTotalAmount(cart.getItemList()));

            // Lưu giỏ hàng vào session
            request.getSession().setAttribute("cart", cart);
            request.getSession().removeAttribute("cart");

            OrderInfo order = new OrderInfo(orderDAO.getOrderInfonextID(), account.getAccountId(), adressID, 2, "PAY002", 1, newCart.getTotalAmount());
            //Lưu order vào database
            orderDAO.insert(order, newCart.getItemList());
            for (CartItem item : newCart.getItemList()) {
                orderDAO.insertOrderItem(order.getOrderID(), item);
            }
            request.getSession().removeAttribute("newCart");
        } catch (SQLException | CustomException ex) {
            Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "Lỗi khi đặt hàng! Vui lòng thử lại.");
        }

        // Sau khi đặt hàng, chuyển hướng đến trang thành công
        response.sendRedirect("home");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
