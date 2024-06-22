package com.prod_management.com.prod_management.product;

import java.lang.reflect.Method;
import java.util.HashMap;

public class Product {

    private String productName;
    private String productID;
    private Integer price;
    private Long stock;
    private String CategoryName;
    private String  CategoryID;
    private Boolean visibility = true;
    private Boolean isStockAvail = true;
    HashMap<String , Method> methods;

    public Product(String productName, String productID, Integer price, Long stock, String categoryName,
            String categoryID) {
        this.productName = productName;
        this.productID = productID;
        this.price = price;
        this.stock = stock;
        CategoryName = categoryName;
        CategoryID = categoryID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public Boolean getIsStockAvail() {
        return isStockAvail;
    }

    public Boolean getVisibility()
    {
        return visibility;
    }

    public void setIsStockAvail(Boolean isStockAvail) {
        this.isStockAvail = isStockAvail;
    }

    @Override
    public String toString() {
        return "Product [productName=" + productName + ", productID=" + productID + ", price=" + price + ", stock="
                + stock + ", CategoryName=" + CategoryName + ", CategoryID=" + CategoryID + "]";
    }

    public HashMap<String , Method> productInfoGetters() throws NoSuchMethodException
    {
        methods.put("Name", Product.class.getMethod("getProductName"));
        methods.put("Price" , Product.class.getMethod("getPrice"));
        methods.put("Stock" , Product.class.getMethod("getStock"));
        methods.put("visibility" , Product.class.getMethod("getVisibility"));
        methods.put("category" , Product.class.getMethod("getCategoryName"));
        methods.put("productID" , Product.class.getMethod("getProductID"));
        methods.put("categoryID" , Product.class.getMethod("getCategoryID"));

        return methods;
    }

    // public String getDetails(  )
    // {

    // }

    
    
    
}
