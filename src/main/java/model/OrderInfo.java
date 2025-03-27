/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author anhkc
 */
public class OrderInfo {

    private String orderID;
    private String customerID;
    private Date orderDate;
    private String orderAddressID;
    private int deliveryStatusID;
    private String paymentMethodID;
    private int paymentStatusID;
    private int orderTotalAmount;
    List<CartItem> ItemList;

    public OrderInfo() {
    }

    public OrderInfo(String orderID, String customerID, List<CartItem> itemList) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.ItemList = itemList;
    }

    public OrderInfo(String orderID, String customerID, String orderAddressID, int deliveryStatusID, String paymentMethodID, int paymentStatusID, int orderTotalAmount) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderAddressID = orderAddressID;
        this.deliveryStatusID = deliveryStatusID;
        this.paymentMethodID = paymentMethodID;
        this.paymentStatusID = paymentStatusID;
        this.orderTotalAmount = orderTotalAmount;
    }

    public OrderInfo(String orderID, String customerID, String orderAddressID, int deliveryStatusID, String paymentMethodID, int paymentStatusID, int orderTotalAmount, List<CartItem> itemList) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderAddressID = orderAddressID;
        this.deliveryStatusID = deliveryStatusID;
        this.paymentMethodID = paymentMethodID;
        this.paymentStatusID = paymentStatusID;
        this.orderTotalAmount = orderTotalAmount;
        this.ItemList = itemList;
    }

    public OrderInfo(String orderID, String customerID, Date orderDate, String orderAddressID, int deliveryStatusID, String paymentMethodID, int paymentStatusID, int orderTotalAmount) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.orderAddressID = orderAddressID;
        this.deliveryStatusID = deliveryStatusID;
        this.paymentMethodID = paymentMethodID;
        this.paymentStatusID = paymentStatusID;
        this.orderTotalAmount = orderTotalAmount;
    }

    public OrderInfo(String orderID, String customerID, Date orderDate, String orderAddressID, int deliveryStatusID, String paymentMethodID, int paymentStatusID, int orderTotalAmount, List<CartItem> itemList) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.orderAddressID = orderAddressID;
        this.deliveryStatusID = deliveryStatusID;
        this.paymentMethodID = paymentMethodID;
        this.paymentStatusID = paymentStatusID;
        this.orderTotalAmount = orderTotalAmount;
        this.ItemList = itemList;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderAddressID() {
        return orderAddressID;
    }

    public void setOrderAddressID(String orderAddressID) {
        this.orderAddressID = orderAddressID;
    }

    public int getDeliveryStatusID() {
        return deliveryStatusID;
    }

    public void setDeliveryStatusID(int deliveryStatusID) {
        this.deliveryStatusID = deliveryStatusID;
    }

    public String getPaymentMethodID() {
        return paymentMethodID;
    }

    public void setPaymentMethodID(String paymentMethodID) {
        this.paymentMethodID = paymentMethodID;
    }

    public int getPaymentStatusID() {
        return paymentStatusID;
    }

    public void setPaymentStatusID(int paymentStatusID) {
        this.paymentStatusID = paymentStatusID;
    }

    public int getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(int orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public List<CartItem> getItemList() {
        return ItemList;
    }

    public void setItemList(List<CartItem> ItemList) {
        this.ItemList = ItemList;
    }

}
