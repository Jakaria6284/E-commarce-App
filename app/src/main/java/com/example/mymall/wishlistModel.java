package com.example.mymall;

import java.util.ArrayList;

public class wishlistModel {
    private String wish_product_image;
    private String wish_product_title;

    private long wish_cupon;

    private long totalRating;
    private String rating;
    private String cuttedprice;
    private boolean paymentMethod;
    private String productPrice;
    private String productID;
    private boolean in_stock;
    private boolean COD;
    private ArrayList<String> tags;

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public wishlistModel(String productID, String wish_product_image, String wish_product_title, long wish_cupon, String rating, long totalRating, String productPrice, String cuttedprice, boolean paymentMethod, boolean in_stock ) {
        this.wish_product_image = wish_product_image;
        this.wish_product_title = wish_product_title;
        this.wish_cupon = wish_cupon;
        this.totalRating = totalRating;
        this.rating = rating;
        this.cuttedprice = cuttedprice;
        this.paymentMethod = paymentMethod;
        this.productPrice = productPrice;
        this.productID=productID;
        this.in_stock=in_stock;

    }

    public boolean isCOD() {
        return COD;
    }

    public void setCOD(boolean COD) {
        this.COD = COD;
    }

    public boolean isIn_stock() {
        return in_stock;
    }

    public void setIn_stock(boolean in_stock) {
        this.in_stock = in_stock;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getWish_product_image() {
        return wish_product_image;
    }

    public void setWish_product_image(String wish_product_image) {
        this.wish_product_image = wish_product_image;
    }

    public String getWish_product_title() {
        return wish_product_title;
    }

    public void setWish_product_title(String wish_product_title) {
        this.wish_product_title = wish_product_title;
    }

    public long getWish_cupon() {
        return wish_cupon;
    }

    public void setWish_cupon(long wish_cupon) {
        this.wish_cupon = wish_cupon;
    }

    public long getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(long totalRating) {
        this.totalRating = totalRating;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCuttedprice() {
        return cuttedprice;
    }

    public void setCuttedprice(String cuttedprice) {
        this.cuttedprice = cuttedprice;
    }

    public boolean isPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(boolean paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }


}
