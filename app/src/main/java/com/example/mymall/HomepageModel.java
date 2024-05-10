package com.example.mymall;

import java.util.List;

public class HomepageModel {
    public static final int Banner_Slider=0;
    public static final int Strip_Add=1;
    public static final int Horizental_product_view=2;
    public static final int Grid_product_view=3;

    private int type;
    private List<sliderModel> sliderModelList;

    public HomepageModel(int type, List<sliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }
    ////Banner slider




    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<sliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<sliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    ///Banner slider

    //strip add
    private String resource;
    private String color;

    public HomepageModel(int type, String resource) {
        this.type = type;


        this.resource = resource;

    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }


    //strip add

    ////horizental product layout && and grid product layout
    private String title;
    private String background;
    private List<horizental_scroll_product_model> horizentalScrollProductModelList;
    private List<wishlistModel>viewallproduct;

    public HomepageModel(int type ,String title, List<horizental_scroll_product_model>horizental_scroll_product_modelList,List<wishlistModel>viewallproduct) {
        this.type = type;
        this.title = title;
        this.horizentalScrollProductModelList=horizental_scroll_product_modelList;

        this.viewallproduct = viewallproduct;
    }

    public HomepageModel(int type ,String title, List<horizental_scroll_product_model> horizentalScrollProductModelList) {
        this.type = type;
        this.title = title;

        this.horizentalScrollProductModelList = horizentalScrollProductModelList;
    }

    public List<wishlistModel> getViewallproduct() {
        return viewallproduct;
    }

    public void setViewallproduct(List<wishlistModel> viewallproduct) {
        this.viewallproduct = viewallproduct;
    }

    public List<horizental_scroll_product_model> getHorizentalScrollProductModelList() {
        return horizentalScrollProductModelList;
    }

    public void setHorizentalScrollProductModelList(List<horizental_scroll_product_model> horizentalScrollProductModelList) {
        this.horizentalScrollProductModelList = horizentalScrollProductModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    ////horizental product layout  && and grid product layout



}
