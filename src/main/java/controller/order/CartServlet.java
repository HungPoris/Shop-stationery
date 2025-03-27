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
import java.util.Iterator;
import model.*;
import utils.*;

/**
 *
 * @author Tran Viet Minh - CE182526
 */
public class CartServlet extends HttpServlet {

    HomeBookDAO homeDAO;
    OrderDAO orderDAO;
    Utility tool;
    CartDAO cartDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        homeDAO = new HomeBookDAO();
        orderDAO = new OrderDAO();
        cartDAO = new CartDAO();
        tool = new Utility();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");
        try {

            // Kiểm tra quyền truy cập
            if (account == null) {
                response.sendRedirect("login");
            } else if (!account.getRoleId().equals("ROL003")) {
                response.sendRedirect("home");
            } else if (cart == null) {
                cart = cartDAO.getLatestCartOfThisAccount(account);
                if (cart != null) {
                    List<CartItem> itemList = cartDAO.getItemsOfThisCart(cart);
                    cart.setItemList(itemList);
                    cart.setTotalQuantity();
                    cart.setTotalAmount(tool.getTotalAmount(itemList));
                    request.getSession().setAttribute("cart", cart);
                }
            }

            request.getRequestDispatcher("cart.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "add":
                    addToCart(request, response);
                    break;
                case "delete":
                    deleteSelectedItems(request, response);
                    break;
                case "update":
                    updateQuantity(request, response);
                    break;
                case "pay":
                    pay(request, response);
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void addToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String bookID = request.getParameter("id");
        String quantity = request.getParameter("quantity");

        Book orderedBook = homeDAO.getbookbyid(Integer.parseInt(bookID));
        if (orderedBook == null) {
            throw new Exception("Book not found!");
        }

        if (orderedBook.getBookQuantity() == 0) {
            throw new Exception("Book is out of stock!");
        }

        Account account = (Account) request.getSession().getAttribute("LOGIN_USER");
        Cart sessionCart = (Cart) request.getSession().getAttribute("cart");
        List<CartItem> itemList = new ArrayList<>();
        Cart dtbCart;

        if (account == null) {
            response.sendRedirect("login");
        } else {
            if (sessionCart == null) {
                dtbCart = cartDAO.getLatestCartOfThisAccount(account);
                if (dtbCart != null) {
                    itemList = cartDAO.getItemsOfThisCart(dtbCart);
                    updateItemList(itemList, dtbCart, orderedBook, Integer.parseInt(quantity));
                    dtbCart.setItemList(itemList);
                } else {
                    List<Cart> cartList = cartDAO.getALLCart();
                    String cartID = tool.generateCartID(cartList);
                    CartItem newItem = new CartItem(cartID, orderedBook, Integer.parseInt(quantity), tool.getPrice(orderedBook));
                    itemList.add(newItem);
                    dtbCart = new Cart(cartID, account.getAccountId(), itemList, false);
                    cartDAO.insertCart(dtbCart, "False");
                }
                dtbCart.setTotalQuantity();
                dtbCart.setTotalAmount(tool.getTotalAmount(itemList));
                request.getSession().setAttribute("cart", dtbCart);
            } else {
                itemList = sessionCart.getItemList();
                updateItemList(itemList, sessionCart, orderedBook, Integer.parseInt(quantity));
                sessionCart.setTotalQuantity();
                sessionCart.setTotalAmount(tool.getTotalAmount(itemList));
                request.getSession().setAttribute("cart", sessionCart);
            }
        }
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    public void updateItemList(List<CartItem> itemList, Cart cart, Book orderedBook, int quantity) throws SQLException {
        boolean isFound = tool.findItemAndUpdate(itemList, orderedBook, quantity);
        if (!isFound) {
            itemList.add(new CartItem(cart.getCartID(), orderedBook, quantity, tool.getPrice(orderedBook)));
            Object[] params = {cart.getCartID(), orderedBook.getBookID(), quantity, tool.getPrice(orderedBook)};
            cartDAO.insertCartItem(params);
        } else {
            cartDAO.updateItemQuantityOnInsert(cart, orderedBook, quantity);
        }
    }

    public void deleteSelectedItems(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, CustomException, SQLException {
        String id = request.getParameter("id");
        if (id != null) {
            Cart cart = (Cart) request.getSession().getAttribute("cart");
            if (cart == null) {
                throw new CustomException("Cart not found!");
            }

            List<CartItem> itemList = cart.getItemList();
            if (itemList == null || itemList.isEmpty()) {
                throw new CustomException("Cart has no item!");
            }

            Book bookToDelete = homeDAO.getbookbyid(Integer.parseInt(id));
            if (bookToDelete == null) {
                throw new CustomException("Book not found!");
            }

            Iterator<CartItem> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.getBook().getBookID() == bookToDelete.getBookID()) {
                    iterator.remove();
                    break;
                }
            }
            cartDAO.deleteAnItemOfThisCart(cart, bookToDelete);
            cart.setTotalQuantity();
            cart.setTotalAmount(tool.getTotalAmount(itemList));
            request.getSession().setAttribute("cart", cart);
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }

    public void updateQuantity(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, CustomException, SQLException {
        String[] id = request.getParameterValues("id");
        String[] quantities = request.getParameterValues("quantity");
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        if (cart == null) {
            throw new CustomException("Cart not found!");
        }

        List<CartItem> itemList = cart.getItemList();
        if (itemList == null || itemList.isEmpty()) {
            throw new CustomException("Cart has no item!");
        }

        // Biến để lưu lỗi
        String errorMessage = null;

        for (int i = 0; i < itemList.size(); i++) {
            String quantityStr = quantities[i];

            // Kiểm tra không được để trống
            if (quantityStr == null || quantityStr.trim().isEmpty()) {
                errorMessage = "Số lượng không được để trống!";
                break;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    errorMessage = "Số lượng phải là số nguyên dương!";
                    break;
                }
            } catch (NumberFormatException e) {
                errorMessage = "Số lượng phải là một số nguyên hợp lệ!";
                break;
            }

            // Nếu không có lỗi, cập nhật số lượng
            itemList.get(i).setQuantity(quantity);
            cartDAO.updateItemQuantity(cart, id[i], quantityStr);
        }

        // Nếu có lỗi, chuyển về trang giỏ hàng và hiển thị thông báo
        if (errorMessage != null) {
            request.setAttribute("error", errorMessage);
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        // Cập nhật tổng số lượng và tổng tiền
        cart.setTotalQuantity();
        cart.setTotalAmount(tool.getTotalAmount(itemList));
        request.getSession().setAttribute("cart", cart);

        // Quay về trang giỏ hàng sau khi cập nhật thành công
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    public void pay(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, CustomException, SQLException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        String[] ids = request.getParameterValues("id");

        List<CartItem> cartItemList = new ArrayList<>(cart.getItemList());
        List<CartItem> selectedItems = new ArrayList<>();

        // Lọc các item cần chuyển sang giỏ hàng mới
        Iterator<CartItem> iterator = cartItemList.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            for (String id : ids) {
                if (item.getBook().getBookID() == Integer.parseInt(id)) {
                    selectedItems.add(item); // Thêm vào giỏ hàng mới
                    iterator.remove(); // Xóa khỏi giỏ hàng hiện tại
                    break;
                }
            }
        }
        // Tạo giỏ hàng mới cho các item được chọn
        String newCartID = tool.generateCartID(cartDAO.getALLCart());
        Cart newCart = new Cart(newCartID, cart.getAccountID(), selectedItems, false);
        newCart.setTotalAmount(tool.getTotalAmount(selectedItems));
        // Cập nhật lại session
        request.getSession().setAttribute("newCart", newCart);

        response.sendRedirect("order");
    }
}
