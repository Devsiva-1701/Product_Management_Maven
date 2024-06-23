package com.prod_management.com.prod_management.Postgresql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgresPurchaseThread extends Thread {

    final String updateStockQuery = "UPDATE products SET stock = ? WHERE productid = ?";
    private PreparedStatement st;
    private PostgresSQLClient pgClient;
    private Long stocks;
    private Long quantity;
    private String productID;


    public PostgresPurchaseThread(PreparedStatement st, PostgresSQLClient pgClient, Long stocks, Long quantity,
            String productID) {
        this.st = st;
        this.pgClient = pgClient;
        this.stocks = stocks;
        this.quantity = quantity;
        this.productID = productID;
    }


    public void run()
    {
        System.out.println("Postgres Thread Started...");
        try {
            st = pgClient.getConnectInstance().prepareStatement(updateStockQuery);
            st.setLong(1 , (stocks - quantity));
            st.setString(2 , productID);
            st.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println("Failed to add data into postgres DB...");
        }
            
    } 

    
    
}
