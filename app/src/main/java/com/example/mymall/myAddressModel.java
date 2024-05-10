package com.example.mymall;

public class myAddressModel {
    private String fullname;
    private String mobileNumber;
    private String alternativeMobileNumber;
    private String address;
    private String pincode;
    private Boolean selected;

    public myAddressModel(String fullname,String mobileNumber,String alternativeMobileNumber ,String address, String pincode,Boolean selected) {
        this.fullname = fullname;
        this.mobileNumber=mobileNumber;
        this.alternativeMobileNumber=alternativeMobileNumber;
        this.address = address;
        this.pincode = pincode;
        this.selected=selected;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAlternativeMobileNumber() {
        return alternativeMobileNumber;
    }

    public void setAlternativeMobileNumber(String alternativeMobileNumber) {
        this.alternativeMobileNumber = alternativeMobileNumber;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
