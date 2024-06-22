package com.prod_management.com.prod_management.product;

public class Categories {

    public String CategoryID;
    public String CategoryName;

    public Categories(String categoryID, String categoryName) {
        CategoryID = categoryID;
        CategoryName = categoryName;
    }
    
    public String getCategoryID() {
        return CategoryID;
    }
    @Override
    public String toString() {
        return "Categories [CategoryID=" + CategoryID + ", CategoryName=" + CategoryName + "]";
    }
    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }
    public String getCategoryName() {
        return CategoryName;
    }
    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
    
    
    
}
