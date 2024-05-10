package com.example.mymall;

public class productSpecificationModel {
    private String featurename;
    private String featurevalue;

    public productSpecificationModel(String featurename, String featurevalue) {
        this.featurename = featurename;
        this.featurevalue = featurevalue;
    }

    public String getFeaturename() {
        return featurename;
    }

    public void setFeaturename(String featurename) {
        this.featurename = featurename;
    }

    public String getFeaturevalue() {
        return featurevalue;
    }

    public void setFeaturevalue(String featurevalue) {
        this.featurevalue = featurevalue;
    }
}
