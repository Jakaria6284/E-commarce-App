package com.example.mymall;

import java.util.Date;

public class my_order_item_model {

    private String products_images;
    private String products_titles;
    private String order_delivery_date;
    private String order,packed,shipped,delivery,fullName,fullAdress,pincode,userid,productid,orderid;
    private Date orderdate;


    public my_order_item_model(String products_images, String products_titles, String order_delivery_date, String order, String packed, String shipped, String delivery, String fullName, String fullAdress, String pincode, String userid, String productid, String orderid, Date orderdate) {
        this.products_images = products_images;
        this.products_titles = products_titles;
        this.order_delivery_date = order_delivery_date;
        this.order = order;
        this.packed = packed;
        this.shipped = shipped;
        this.delivery = delivery;
        this.fullName = fullName;
        this.fullAdress = fullAdress;
        this.pincode = pincode;
        this.userid = userid;
        this.productid = productid;
        this.orderid = orderid;
        this.orderdate = orderdate;
    }

    public String getProducts_images() {
        return products_images;
    }

    public void setProducts_images(String products_images) {
        this.products_images = products_images;
    }

    public String getProducts_titles() {
        return products_titles;
    }

    public void setProducts_titles(String products_titles) {
        this.products_titles = products_titles;
    }

    public String getOrder_delivery_date() {
        return order_delivery_date;
    }

    public void setOrder_delivery_date(String order_delivery_date) {
        this.order_delivery_date = order_delivery_date;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPacked() {
        return packed;
    }

    public void setPacked(String packed) {
        this.packed = packed;
    }

    public String getShipped() {
        return shipped;
    }

    public void setShipped(String shipped) {
        this.shipped = shipped;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullAdress() {
        return fullAdress;
    }

    public void setFullAdress(String fullAdress) {
        this.fullAdress = fullAdress;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }
}
