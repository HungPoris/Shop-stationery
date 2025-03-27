/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Book;
import model.Cart;
import model.CartItem;
import utils.DBContext;

/**
 *
 * @author anhkc
 */
public class CartDAO {

    DBContext context;

    public CartDAO() {
        context = new DBContext();
    }

    public List<Cart> getALLCart() throws SQLException {
        String query = "select * from Cart";
        List<Cart> cartList = new ArrayList<>();
        ResultSet rs = context.exeQuery(query);

        while (rs.next()) {
            Cart cart = new Cart();
            cart.setCartID(rs.getString(1));
            cart.setAccountID(rs.getString(3));
            cart.setCheckOutStatus(rs.getBoolean(4));
            cartList.add(cart);

        }

        return cartList;
    }

    public Cart getLatestCartOfThisAccount(Account account) throws SQLException {
        //The subquery in this 'where' clause CAN ONLY RETURN 1 VALUE!!!!!!
        String query = "select top 1 *\n"
                + "from Cart\n"
                + "where accountID = ?\n"
                + "and checkOutStatus = 0\n"
                + "ORDER BY CAST(SUBSTRING(cartID, PATINDEX('%[0-9]%', cartID), LEN(cartID)) AS INT) desc";
        Object[] params = {account.getAccountId()};
        ResultSet rs = context.exeQueryAlt(query, params);

        if (rs.next()) {
            return new Cart(rs.getString(1), rs.getString(3), rs.getBoolean(4));
        }
        return null;
    }

    public void insertCart(Cart cart, String status) throws SQLException {
        String sql = "";
        int rowsAffected = 0;
        sql = "insert into Cart(cartID,accountID,checkOutStatus) values(?,?,?)";
        Object[] params = {cart.getCartID(), cart.getAccountID(), status};
        rowsAffected = context.exeNonQuery(sql, params);
        System.out.println(rowsAffected + " rows affected");
        insertItemNEWCART(cart);

    }

    public void insertItemNEWCART(Cart cart) throws SQLException {
        String sql = "insert into CartItems values(?,?,?,?)";
        for (CartItem item : cart.getItemList()) {
            Object[] params = {cart.getCartID(), item.getBook().getBookID(), item.getQuantity(), item.getFinalPrice()};
            int rows = context.exeNonQuery(sql, params);
        }
    }

    public List<CartItem> getALLCartItem() throws SQLException {
        String query = "select * from CartItems";
        HomeBookDAO dao = new HomeBookDAO();
        List<CartItem> itemList = new ArrayList<>();
        ResultSet rs = context.exeQuery(query);

        while (rs.next()) {
            itemList.add(new CartItem(rs.getString(1), dao.getbookbyid(rs.getInt(2)), rs.getInt(3), rs.getInt(4)));
        }

        return itemList;
    }

    public void insertCartItem(Object[] params) throws SQLException {
        String sql = "insert into CartItems values(?,?,?,?)";
        int rows = context.exeNonQuery(sql, params);
        System.out.println(rows);
    }

    public void callInsertCartItem(Cart cart) throws SQLException {
        for (int i = 0; i < cart.getItemList().size(); i++) {
            Object[] params = {cart.getCartID(), cart.getItemList().get(i).getBook().getBookID(), cart.getItemList().get(i).getQuantity(), cart.getItemList().get(i).getFinalPrice()};
            insertCartItem(params);
        }
    }

    public List<CartItem> getItemsOfThisCart(Cart cart) throws SQLException {
        String sql = "SELECT CartItems.cartID, CartItems.bookID, CartItems.quantity, CartItems.finalPrice\n"
                + "FROM     Cart INNER JOIN\n"
                + "CartItems ON Cart.cartID = CartItems.cartID\n"
                + "where Cart.accountID = ? and CartItems.cartID = ?";
        Object[] params = {cart.getAccountID(), cart.getCartID()};
        ResultSet rs = context.exeQueryAlt(sql, params);

        List<CartItem> itemList = new ArrayList<>();
        HomeBookDAO dao = new HomeBookDAO();
        while (rs.next()) {
            itemList.add(new CartItem(rs.getString(1), dao.getbookbyid(rs.getInt(2)), rs.getInt(3), rs.getInt(4)));
        }
        return itemList;

    }

    public void updateItemQuantityOnInsert(Cart cart, Book book, int quantity) throws SQLException {
        String sql = "update CartItems set quantity = quantity + ? where cartID = ? and bookID = ?";
        Object[] params = {quantity, cart.getCartID(), book.getBookID()};
        int rows = context.exeNonQuery(sql, params);
        System.out.println(rows);
    }

    public void updateItemQuantity(Cart cart, String bookID, String quantity) throws SQLException {
        String sql = "update CartItems set quantity = ? where cartID = ? and bookID = ?";
        Object[] params = {quantity, cart.getCartID(), bookID};
        int rows = context.exeNonQuery(sql, params);
        System.out.println(rows);
    }

    public void deleteAnItemOfThisCart(Cart cart, Book book) throws SQLException {
        String sql = "delete from CartItems where cartID = ? and bookID = ?";
        Object[] params = {cart.getCartID(), book.getBookID()};
        int rows = context.exeNonQuery(sql, params);
        System.out.println(rows);
    }

    public void updateCheckOutStatus(Cart cart) throws SQLException {
        String sql = "update Cart set checkOutStatus = 1 where cartID = ? and accountID = ?";
        Object[] params = {cart.getCartID(), cart.getAccountID()};
        int rows = context.exeNonQuery(sql, params);
        System.out.println(rows);

    }

}
