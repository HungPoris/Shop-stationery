/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author TRUNG NHAN
 */
public class deliveryStatus {

    private int deliveryStatusID;
    private String statusDescription;

    public deliveryStatus(int deliveryStatusID, String statusDescription) {
        this.deliveryStatusID = deliveryStatusID;
        this.statusDescription = statusDescription;
    }

    public int getDeliveryStatusID() {
        return deliveryStatusID;
    }

    public void setDeliveryStatusID(int deliveryStatusID) {
        this.deliveryStatusID = deliveryStatusID;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

}
