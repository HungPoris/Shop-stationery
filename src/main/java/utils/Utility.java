/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ArrayList;
import model.Address;
import model.Book;
import model.Cart;
import model.CartItem;
import model.OrderInfo;

/**
 *
 * @author anhkc
 */
public class Utility {

    public int generateID(List<Integer> idList) {
//        for (int i = 0; i < idList.size(); i++) {
//            if (idList.get(i) != i + 1) {
//                return i + 1;
//            }
//        }
        return idList.size() + 1;
    }

    public String generateCartID(List<Cart> cartList) {
        List<Integer> idSuffixes = new ArrayList<>();
        for (Cart cart1 : cartList) {
            int idSuffix = Integer.parseInt(cart1.getCartID().substring(3));
            idSuffixes.add(idSuffix);
        }
        int newIDSuffix = generateID(idSuffixes);
        String formattedIDSuffix = String.format("%03d", newIDSuffix);
        return "CAR" + formattedIDSuffix;
    }

    public String generateOrderID(List<OrderInfo> orderList) {
        List<Integer> idSuffixes = new ArrayList<>();
        for (OrderInfo orderInfo : orderList) {
            int idSuffix = Integer.parseInt(orderInfo.getOrderID().substring(3));
            idSuffixes.add(idSuffix);
        }

        int newIDSuffix = generateID(idSuffixes);
        String formattedIDSuffix = String.format("%03d", newIDSuffix);
        return "ORD" + formattedIDSuffix;
    }

    public String generateAddressID(List<Address> orderList) {
        List<Integer> idSuffixes = new ArrayList<>();
        for (Address orderInfo : orderList) {
            int idSuffix = Integer.parseInt(orderInfo.getAddressID().substring(3));
            idSuffixes.add(idSuffix);
        }

        int newIDSuffix = generateID(idSuffixes);
        String formattedIDSuffix = String.format("%03d", newIDSuffix);
        return "ADD" + formattedIDSuffix;
    }

    public Date getLocalDate() {

        // Get the current local date
        LocalDate today = LocalDate.now();

        // Format the local date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedToday = today.format(formatter);
        System.out.println("Current local date: " + formattedToday);

        // Convert LocalDate to java.sql.Date
        Date sqlDate = Date.valueOf(today);
        return sqlDate;
    }

    public int getTotalAmount(List<CartItem> itemList) {
        int total = 0;
        for (CartItem item : itemList) {
            total += item.getFinalPrice() * item.getQuantity();
        }
        return total;
    }

    public int getPrice(Book book) {
        if (book.getBookFlashSale() == 0) {
            return book.getBookPrice() * (100 - book.getBookDiscount()) / 100;
        }
        return book.getBookPrice() * (100 - book.getBookFlashSale()) / 100;
    }

    public boolean findItemAndUpdate(List<CartItem> itemList, Book itemToAdd, int quantity) {
        boolean isFound = false;
        if (itemList.isEmpty()) {
            return false;
        }

        for (CartItem cartItem : itemList) {
            if (itemToAdd.getBookID() == cartItem.getBook().getBookID()) {
                isFound = true;
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                break;
            }
        }

        return isFound;
    }

}
