package com.example.mymall;

public class categorymodel {
    private String CategoryIconlink;
    private String categoryname;


    public String getCategoryIconlink() {
        return CategoryIconlink;
    }

    public void setCategoryIconlink(String categoryIconlink) {
        CategoryIconlink = categoryIconlink;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public categorymodel(String categoryIconlink, String categoryname) {
        CategoryIconlink = categoryIconlink;
        this.categoryname = categoryname;
    }

}
