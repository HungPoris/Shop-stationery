/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Acer
 */
public class Address {

    private String addressID;
    private String addressDetail;
    private boolean defaultAddress;

    public Address() {
    }

    public Address(String addressID, String addressDetail) {
        this.addressID = addressID;
        this.addressDetail = addressDetail;
    }

    public Address(String addressID, String addressDetail, boolean defaultAddress) {
        this.addressID = addressID;
        this.addressDetail = addressDetail;
        this.defaultAddress = defaultAddress;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

}
