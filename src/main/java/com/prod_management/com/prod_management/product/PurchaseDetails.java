package com.prod_management.com.prod_management.product;

import org.bson.Document;

public class PurchaseDetails {

    private Long quantity;
    private Long totalCost;
    private String purchaseID;
    private String purchasedTime;
    private String product_Name;
    private String productID;
    // private HashMap<String , String> products;

    public PurchaseDetails(Long quantity, Long totalCost, String purchaseID, String purchasedTime, String product_Name , String productID
            // HashMap<String, String> products
            ) {
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.purchaseID = purchaseID;
        this.purchasedTime = purchasedTime;
        this.product_Name = product_Name;
        this.productID = productID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public Long getquantity() {
        return quantity;
    }
    public void setquantity(Long quantity) {
        this.quantity = quantity;
    }
    public Long getTotalCost() {
        return totalCost;
    }
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String productID) {
        this.product_Name = productID;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }
    public String getPurchaseID() {
        return purchaseID;
    }
    public void setPurchaseID(String purchaseID) {
        this.purchaseID = purchaseID;
    }
    public String getPurchasedTime() {
        return purchasedTime;
    }
    public void setPurchasedTime(String purchasedTime) {
        this.purchasedTime = purchasedTime;
    }
    // public HashMap<String, String> getProducts() {
    //     return products;
    // }
    // public void setProducts(HashMap<String, String> products) {
    //     this.products = products;
    // }

    @Override
    public String toString() {
        return "PurchaseDetails [quantity=" + quantity + ", totalCost=" + totalCost + ", purchaseID="
                + purchaseID + ", purchasedTime=" + purchasedTime + ", productsName=" + product_Name + "]";
    }


    

    
    
}
