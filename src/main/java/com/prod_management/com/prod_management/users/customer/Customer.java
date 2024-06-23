package com.prod_management.com.prod_management.users.customer;

import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;

import com.mongodb.client.MongoCursor;
import com.prod_management.com.prod_management.Mongo.MongoDBClient;
import com.prod_management.com.prod_management.Mongo.MongoPurchaseThread;
import com.prod_management.com.prod_management.Postgresql.PostgresPurchaseThread;
import com.prod_management.com.prod_management.Postgresql.PostgresSQLClient;
import com.prod_management.com.prod_management.console.Console;
import com.prod_management.com.prod_management.product.PurchaseDetails;

public class Customer {

    // PostgresSQLClient client;
    Console console;

    public Customer( Console console )
    {
        this.console = console;
    }

    public Customer(){}

    public void viewProducts( boolean prodViewReq ,PostgresSQLClient client )
    {
        String productFetchQuery = "SELECT * FROM products";
        try {
            Statement st = client.getConnectInstance().createStatement();
            ResultSet result = st.executeQuery(productFetchQuery);

            while (result.next()) {
                
                if( prodViewReq )
                {
                    System.out.println(client.getProductDetails(result));
                }
                else
                {
                    if( result.getBoolean("Visibility") == true )
                    {
                        System.out.println(client.getProductDetails(result)); 
                    }
                }

                
                
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException);
        }
        
    }

    public void downloadDoc( PostgresSQLClient client )
    {
        String productFetchQuery = "SELECT * FROM products";
        try {
            Statement st = client.getConnectInstance().createStatement();
            ResultSet result = st.executeQuery(productFetchQuery);
            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("Products");

            // This is for header row
            int currentRowNumber = 0;
            Row currentRow = sheet.createRow(currentRowNumber);
            String[] headers = {"Product ID", "Product Name", "Category ID", "Category Name" , "Price", "Stock", "Visibility"};
            for (int i = 0; i < headers.length; i++) {
                currentRow.createCell(i).setCellValue(headers[i]);
            } 
            //--------------------------------------------
            
            //Product rows
            currentRowNumber++;
            while (result.next()) {
  
                if( result.getBoolean("Visibility") == true )
                {
                    currentRow = sheet.createRow(currentRowNumber);
                    currentRow.createCell(0).setCellValue(result.getString("productid"));
                    currentRow.createCell(1).setCellValue(result.getString("productname"));
                    currentRow.createCell(2).setCellValue(result.getString("categoryid"));
                    currentRow.createCell(3).setCellValue(result.getString("category"));
                    currentRow.createCell(4).setCellValue(result.getInt("price"));
                    currentRow.createCell(5).setCellValue(result.getLong("stock"));
                    currentRow.createCell(6).setCellValue(result.getBoolean("visibility"));
                    currentRowNumber++;
                }
 
            }

            for( int i = 0 ; i < headers.length ; i++ )
            {
                sheet.autoSizeColumn(i);
            }

            try( FileOutputStream fileOutput = new FileOutputStream("Products.xlsx") ) {

                workbook.write(fileOutput);
                System.out.println("File downloaded successfully...");
                workbook.close();
                
            } catch (Exception filException) {
                System.err.println("Some error occured while doenloading the file...");
            }

        } catch (SQLException sqlException) {
            System.err.println(sqlException);
        }
        
    }

    public void purchaseProduct( String productID , MongoDBClient mongoDBClient , Scanner input , PostgresSQLClient client )
    {
        String getProductDetails = "SELECT productName , stock , visibility , price FROM products WHERE productid = ? LIMIT 1";

        try {
            PreparedStatement st = client.getConnectInstance().prepareStatement(getProductDetails);
            st.setString(1, productID);

            ResultSet result = st.executeQuery();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm-ss-SS");

            while(result.next())
            {
                Long stocks = result.getLong("stock");
                boolean validStock = false;
                if( stocks != 0 )
                {
                    while( !validStock )
                    {
                        System.out.println("The available stocks for the product : "+result.getString("productname")+" is "+stocks);
                        System.out.println("Enter the number stocks you want to purchase : ");
                        Long quantity = input.nextLong();

                        if( quantity > stocks || quantity < 0)
                        {
                            System.out.println("Enter the valid quantity it should between 0 - "+stocks);
                        }
                        else
                        {
                            String time = formatter.format(LocalTime.now());
                            MongoPurchaseThread mongoPurchaseThread = new MongoPurchaseThread( (new PurchaseDetails(quantity, (result.getInt("price") * quantity) , ("P"+time+"R") , 
                            time, result.getString("productname") , productID)) , mongoDBClient); 
                            PostgresPurchaseThread pgPurchaseThread = new PostgresPurchaseThread( st , client , stocks , quantity ,productID );

                            mongoPurchaseThread.start();
                            pgPurchaseThread.start();

                            try {
                                mongoPurchaseThread.join();
                                pgPurchaseThread.join();
                            } catch (InterruptedException threadInterruption) {
                                System.err.println("Failed to start the postgres data injection...");
                            }
                            
                            System.out.println("Purchase success");
                            validStock =true;
                        }
                    }
                    
                }
                else
                {
                    System.err.println("This product is out of stock. Consider purchasing another product.");
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.err.println("Product not found...");
        }

        
    }

    public void viewPurchaseHistory( MongoDBClient mongoDBClient )
    {
        MongoCursor<Document> iterator = mongoDBClient.getPurcahseCollection().find().iterator();

        try {
            while (iterator.hasNext()) {
                Document purchaseDoc = iterator.next();
                System.out.println( "--------------------------"+
                        "\nPurchase ID : "+ purchaseDoc.getString("PurchaseID")+
                        "\nProduct Name : "+ purchaseDoc.getString("ProductName")+
                        "\nProduct ID : "+ purchaseDoc.getString("ProductID")+
                        "\nQuantity : "+ purchaseDoc.getLong("Quantity")+
                        "\nTotal Cost : "+ purchaseDoc.getLong("Total Cost")+
                        "\nTime of Purchase : "+ purchaseDoc.getString("Time of Purchase")) ;
                }
            } 
         catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            iterator.close();
        }
    }
    
}
