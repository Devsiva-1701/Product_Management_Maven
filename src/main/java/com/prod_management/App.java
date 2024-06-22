package com.prod_management;

import java.sql.SQLException;

import com.prod_management.com.prod_management.Mongo.MongoDBClient;
import com.prod_management.com.prod_management.Postgresql.PostgresSQLClient;
import com.prod_management.com.prod_management.console.Console;

public class App 
{

    private static String URL = "jdbc:postgresql://localhost:5432/ProductManage_Maven";
    private static String userName = "postgres";
    private static String password = "1234";

    public static void main( String[] args )
    {

        System.err.println( "\n\nTrying to connecting to PostgresqlDB..." );
        PostgresSQLClient client = new PostgresSQLClient(URL, userName, password);
        System.err.println( "\n\nTrying to connecting to MongoDB..." );
        MongoDBClient mongoDBClient = new MongoDBClient();

        try {

            client.connectClient();
            mongoDBClient.connect();
            System.err.println("Connection Successfull...");  
            
        } catch (Exception Connectionexception) {
            System.err.println(Connectionexception);
            System.err.println("Failed to connect to DB...");
        }

        Console console = new Console(client , mongoDBClient);
        console.setCustomer(console);
        console.setSeller(console);
        console.runConsole(true , console);
    }
}
