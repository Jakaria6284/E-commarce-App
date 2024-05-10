package com.example.mymall;

import java.util.Date;

public class rewardModel {
    private String type;
    private String body;
    private String lower_limit;
    private String upper_limit;
    private String percentage;
    private Date validity;
    private boolean alreadyusedCoupen;
    private String coupenId;


    public rewardModel(String coupenID,String type, String body, String lower_limit, String upper_limit, String percentage, Date validity,boolean alreadyusedCoupen) {
        this.type = type;
        this.body = body;
        this.lower_limit = lower_limit;
        this.upper_limit = upper_limit;
        this.percentage = percentage;
        this.validity = validity;
        this.alreadyusedCoupen=alreadyusedCoupen;
        this.coupenId=coupenID;
    }

    public String getCoupenId() {
        return coupenId;
    }

    public void setCoupenId(String coupenId) {
        this.coupenId = coupenId;
    }

    public boolean isAlreadyusedCoupen() {
        return alreadyusedCoupen;
    }

    public void setAlreadyusedCoupen(boolean alreadyusedCoupen) {
        this.alreadyusedCoupen = alreadyusedCoupen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLower_limit() {
        return lower_limit;
    }

    public void setLower_limit(String lower_limit) {
        this.lower_limit = lower_limit;
    }

    public String getUpper_limit() {
        return upper_limit;
    }

    public void setUpper_limit(String upper_limit) {
        this.upper_limit = upper_limit;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }
}
