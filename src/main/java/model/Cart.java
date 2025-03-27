/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;
import utils.CustomException;

/**
 *
 * @author anhkc
 */
public class Cart {

    private String cartID;
    private String accountID;
    private List<CartItem> itemList;
    private int totalAmount;
    private int totalQuantity;
    private boolean checkOutStatus;

    public Cart() {
    }

    public Cart(String cartID, String accountID, boolean checkOutStatus) {
        this.cartID = cartID;
        this.accountID = accountID;
        this.checkOutStatus = checkOutStatus;
    }

    public Cart(String cartID, String accountID, List<CartItem> itemList, boolean checkOutStatus) {
        this.cartID = cartID;
        this.accountID = accountID;
        this.itemList = itemList;
        this.checkOutStatus = checkOutStatus;
    }

    public Cart(String cartID, String accountID, List<CartItem> itemList, int totalAmount, int totalQuantity, boolean checkOutStatus) {
        this.cartID = cartID;
        this.accountID = accountID;
        this.itemList = itemList;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.checkOutStatus = checkOutStatus;
    }

    public boolean isCheckOutStatus() {
        return checkOutStatus;
    }

    public void setCheckOutStatus(boolean checkOutStatus) {
        this.checkOutStatus = checkOutStatus;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity() throws CustomException {
        if (this.itemList == null) {
            throw new CustomException("Cart is empty!");
        }
        int totalQuantity = 0;
        for (CartItem item : this.itemList) {
            totalQuantity += item.getQuantity();
        }
        this.totalQuantity = totalQuantity;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public List<CartItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<CartItem> itemList) {
        this.itemList = itemList;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTotalAmount() {
        this.totalAmount = itemList.size();
    }

}
