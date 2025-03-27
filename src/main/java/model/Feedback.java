/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

public class Feedback {

    private int bookID;
    private String content;
    private Date createdAt;
    private Customer customer;

    public Feedback() {
    }

    public Feedback(String accountID, int bookID, String content, Date createdAt) {
        this.bookID = bookID;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Feedback(Customer customer, int bookID, String content, Date createdAt) {
        this.bookID = bookID;
        this.content = content;
        this.createdAt = createdAt;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerName() {
        return customer.getFirstName() + customer.getLastName();
    }
}
