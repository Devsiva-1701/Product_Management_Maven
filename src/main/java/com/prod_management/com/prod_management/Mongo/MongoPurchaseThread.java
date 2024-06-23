package com.prod_management.com.prod_management.Mongo;

import com.prod_management.com.prod_management.product.PurchaseDetails;

public class MongoPurchaseThread extends Thread {

    private PurchaseDetails purchaseDetails;
    private MongoDBClient mongoDBClient;

    public MongoPurchaseThread( PurchaseDetails purchaseDetails , MongoDBClient mongoDBClient )
    {
        this.purchaseDetails = purchaseDetails;
        this.mongoDBClient = mongoDBClient;
    }

    public void run()
    {
        System.out.println("Started Mongo Thread...");
        mongoDBClient.insertIntoCollection( purchaseDetails );
    }
    
}
