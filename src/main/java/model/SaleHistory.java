/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Tran Viet Minh - CE182526
 */
public class SaleHistory {

    private String saleID;
    private int bookID;
    private int soldQuantity;
    private Date saleDate;
    private int totalPrice;

    // Constructor
    public SaleHistory() {
    }

    public SaleHistory(String saleID, int bookID, int soldQuantity, Date saleDate, int totalPrice) {
        this.saleID = saleID;
        this.bookID = bookID;
        this.soldQuantity = soldQuantity;
        this.saleDate = saleDate;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public String getSaleID() {
        return saleID;
    }

    public void setSaleID(String saleID) {
        this.saleID = saleID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
