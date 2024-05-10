package com.example.mymall;

import java.util.ArrayList;
import java.util.List;

public class CartitemModel {
    public static final int CART_ITEM=0;
    public static final int TOTAL_AMOUNT=1;


    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //cart item
    private String product_id;
    private String productImage;
    private String productTitle;
    private Long freeCoupens;
    private String productPrice;
    private String cuttedPrice;
   private Long ProductQuantity;
   private Long maxQty;
    private Long offerApplied;
    private Long coupensApplied;
    private boolean in_stock;
    private List<String> qtyids;
    private Long stock_qty;
    private boolean qty_error;
    private String selectedCoupenID;
    private String discountedpricerewardmodel;


    public CartitemModel(int type,String product_id, String productImage, String productTitle, long freeCoupens, String productPrice, String cuttedPrice, long productQuantity,long maxQty ,long offerApplied, long coupensApplied,boolean in_stock,long stock_qty) {
        this.type = type;
        this.product_id=product_id;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupens = freeCoupens;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        ProductQuantity = productQuantity;
        this.offerApplied = offerApplied;
        this.coupensApplied = coupensApplied;
        this.in_stock=in_stock;
        this.maxQty=maxQty;
        qtyids=new ArrayList<>();
        this.stock_qty=stock_qty;
        qty_error=false;
    }

    public String getSelectedCoupenID() {
        return selectedCoupenID;
    }

    public void setSelectedCoupenID(String selectedCoupenID) {
        this.selectedCoupenID = selectedCoupenID;
    }

    public String getDiscountedpricerewardmodel() {
        return discountedpricerewardmodel;
    }

    public void setDiscountedpricerewardmodel(String discountedpricerewardmodel) {
        this.discountedpricerewardmodel = discountedpricerewardmodel;
    }

    public boolean isQty_error() {
        return qty_error;
    }

    public void setQty_error(boolean qty_error) {
        this.qty_error = qty_error;
    }

    public Long getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(Long stock_qty) {
        this.stock_qty = stock_qty;
    }

    public List<String> getQtyids() {
        return qtyids;
    }

    public void setQtyids(List<String> qtyids) {
        this.qtyids = qtyids;
    }

    public Long getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(Long maxQty) {
        this.maxQty = maxQty;
    }

    public boolean isIn_stock() {
        return in_stock;
    }

    public void setIn_stock(boolean in_stock) {
        this.in_stock = in_stock;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public long getFreeCoupens() {
        return freeCoupens;
    }

    public void setFreeCoupens(long freeCoupens) {
        this.freeCoupens = freeCoupens;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public long getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(long productQuantity) {
        ProductQuantity = productQuantity;
    }

    public long getOfferApplied() {
        return offerApplied;
    }

    public void setOfferApplied(long offerApplied) {
        this.offerApplied = offerApplied;
    }

    public long getCoupensApplied() {
        return coupensApplied;
    }

    public void setCoupensApplied(long coupensApplied) {
        this.coupensApplied = coupensApplied;
    }

    //cartitem

    //cart total
   private int total_item;
    private int total_item_price;
    private int total_amount;
    private int saved_amount;
    private String delivery_price;





    public CartitemModel(int type) {
        this.type = type;
    }

    public int getTotal_item() {
        return total_item;
    }

    public void setTotal_item(int total_item) {
        this.total_item = total_item;
    }

    public int getTotal_item_price() {
        return total_item_price;
    }

    public void setTotal_item_price(int total_item_price) {
        this.total_item_price = total_item_price;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public int getSaved_amount() {
        return saved_amount;
    }

    public void setSaved_amount(int saved_amount) {
        this.saved_amount = saved_amount;
    }

    public String getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(String delivery_price) {
        this.delivery_price = delivery_price;
    }
}
