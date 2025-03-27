/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tran Viet Minh - CE182526
 */
import java.sql.Date;

public class Shipper {

    private String shipperID;
    private Date monthShipped;
    private int amount;
    private int salaryOfMonth;

    // Constructor không tham số
    public Shipper() {
    }

    // Constructor đầy đủ tham số
    public Shipper(String shipperID, Date monthShipped, int amount, int salaryOfMonth) {
        this.shipperID = shipperID;
        this.monthShipped = monthShipped;
        this.amount = amount;
        this.salaryOfMonth = salaryOfMonth;
    }

    // Getter và Setter
    public String getShipperID() {
        return shipperID;
    }

    public void setShipperID(String shipperID) {
        this.shipperID = shipperID;
    }

    public Date getMonthShipped() {
        return monthShipped;
    }

    public void setMonthShipped(Date monthShipped) {
        this.monthShipped = monthShipped;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSalaryOfMonth() {
        return salaryOfMonth;
    }

    public void setSalaryOfMonth(int salaryOfMonth) {
        this.salaryOfMonth = salaryOfMonth;
    }

}
