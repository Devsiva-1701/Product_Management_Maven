package com.prod_management.com.prod_management.Mongo;

import org.bson.Document;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.prod_management.com.prod_management.product.PurchaseDetails;

public class MongoDBClient {

    private MongoDatabase database;

    public MongoDBClient() {}

    public void connect() {
        try{
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            System.out.println("Client Connection Successful...");
            setDatabase(mongoClient.getDatabase("TransactionDB"));
        } catch (Exception connectException) {
            connectException.printStackTrace();
            System.err.println("Failed to connect to the database...");
        }
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }

    public MongoCollection<Document> getPurcahseCollection()
    {
        return database.getCollection("PurchaseOrders");
    }

    public void insertIntoCollection( PurchaseDetails purchaseDetails )
    {

        try {
            
            Document JSONDoc = new Document("PurchaseID" , purchaseDetails.getPurchaseID() ).
                                            append("ProductID", purchaseDetails.getProductID() ).
                                            append("ProductName", purchaseDetails.getProduct_Name()).
                                            append("Quantity" , purchaseDetails.getQuantity()).
                                            append("Total Cost", purchaseDetails.getTotalCost()).
                                            append("Time of Purchase", purchaseDetails.getPurchasedTime());
            // try {
            //     OrderItemsCollection.createIndex(new Document("ProductID" , product.getProd_id()) , new IndexOptions().unique(true));  
            // } catch (Exception Duplicate) {
            //     System.err.println("The product with this ID is already presented...");
            // }
                  
            database.getCollection("PurchaseOrders").insertOne(JSONDoc);
            System.out.println("Insertion Successful...");
        } catch (Exception InsertionException) {
            InsertionException.printStackTrace();
            System.err.println("Failed to insert the Data...");
        }

    }

    // public void insertCategoriesIntoCollection()
    // {

    //     try {
    //         MongoCollection<Document> CategoryCollection = database.getCollection("Catagories");
    //         CategoryCollection.drop();
    //         Arrays.stream(ProductCategories.values()).forEach( 
    //             category -> {
    //                 Document JSONDoc = new Document().
    //                                 append("CategoryID", "C-"+String.valueOf(category.ordinal()+1)).
    //                                 append( "Category" , category.toString());
    //                                 CategoryCollection.insertOne(JSONDoc);
    //                             }
    //          );
    //         System.out.println("Insertion Successful...");
    //     } catch (Exception InsertionException) {
    //         InsertionException.printStackTrace();
    //         System.err.println("Failed to insert the Data...");
    //     }

    // }

    public void getProductLibrary_Mongo()
    {
        try( MongoCursor<Document> cursor = database.getCollection("Product").find().iterator() ) {

            while(cursor.hasNext())
            {
                Document doc = cursor.next();
                System.out.println(doc);
            }
            
        } catch (Exception fetchError) {
            System.err.println("Fetching error occured...");
        }
        
    }
    
}

