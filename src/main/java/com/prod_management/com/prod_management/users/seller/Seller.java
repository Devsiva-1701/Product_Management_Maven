package com.prod_management.com.prod_management.users.seller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.prod_management.com.prod_management.Postgresql.PostgresSQLClient;
import com.prod_management.com.prod_management.console.Console;
import com.prod_management.com.prod_management.product.Product;

public class Seller {


    private PostgresSQLClient client;
    ResultSet prodResult;
    Console console;

    public Seller(PostgresSQLClient client , Console console) {
        this.client = client;
        this.console = console;
    }

   

    public Seller(){}

    public void addProducts( Product product )
    {
       final String insertQuery = "INSERT INTO Products (ProductName , Price , Stock , Visibility , Category , ProductID , CategoryID , isstockavail) "+
                                    "VALUES ( ? , ? , ? , ? , ? , ? , ? , ? )";

        try {

            PreparedStatement statement = client.getConnectInstance().prepareStatement(insertQuery);

            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getPrice());
            statement.setLong(3, product.getStock());
            statement.setBoolean(4, product.getVisibility());
            statement.setString(5, product.getCategoryName());
            statement.setString(6, product.getProductID());
            statement.setString(7, product.getCategoryID());
            statement.setBoolean(8, product.getIsStockAvail());

            // Use this commented lines for further development to avoid boilerplate code (Code Pending)
            // try {

            //     for( Map.Entry<String , Method> method_entry : product.productInfoGetters().entrySet() )
            //     {
            //         statement.set
            //     }
                
            // } catch (NoSuchMethodException methodException) {
            //     System.err.println("No Such Method...");
            // }

            

            statement.executeUpdate();

            System.out.println("Product Added Successfully...");

            
        } catch (SQLException sqlException) {
            System.err.println(sqlException);
            System.err.println("Failed to Add Product...");
        }
        


    }

    // public boolean executeTask(String prodID , Scanner input , PreparedStatement st , ResultSet producResult )
    // {
        
    // }

    public void updatePrice( String prod_ID , Scanner input )
    {
        String productDetailsQuery = "SELECT * FROM products WHERE productid = ? LIMIT 1 ";
        String updatePriceQuery = "UPDATE products SET price = ? WHERE productid = ?";
        Integer price = 0;
        try {
            PreparedStatement st = client.getConnectInstance().prepareStatement(productDetailsQuery);
            if( prodResult == null )
        {
            try {
                st.setString(1, prod_ID);
                prodResult = st.executeQuery();
                try {

                    while(prodResult.next())
                    {
                        String prodName = prodResult.getString("productname");
                        System.out.println("The price of the product : "+prodName+" is "+prodResult.getInt("price"));
                        
                        boolean validPrice = false;
                        while(!validPrice)
                        {
                            try {
                                System.out.println("Enter the new price of this product : ");
                                price = input.nextInt();
                                if(price <= 0 )
                                {
                                    System.out.println("Price can't be 0 or negative consider adding a valid price.");
                                }
                                else
                                {
                                    validPrice = true;
                                }
          
                            } catch (InputMismatchException inputException) {
                                System.err.println("Input Mismatch...");
                            }
                        }
                        st = client.getConnectInstance().prepareStatement(updatePriceQuery);
        
                        st.setInt(1, price);
                        st.setString(2, prod_ID);
        
                        st.executeUpdate();
        
                        System.out.println("The price : "+price+" updated to the product : "+prodName+" successfully...");
                        
                    }
        
                } catch (SQLException sqlException) {
                    System.err.println(sqlException);
                    System.err.println("Product not Available...");
                }
            } catch (SQLException sqlException) {
                System.err.println("Product not Found...");
            }
        }
        else{
            try {

                while(prodResult.next())
                {
                    String prodName = prodResult.getString("productname");
                    System.out.println("The price of the product : "+prodName+" is "+prodResult.getInt("price"));
                    
                    boolean validPrice = false;
                    while(!validPrice)
                    {
                        try {
                            System.out.println("Enter the new price of this product : ");
                            price = input.nextInt();
                            if(price <= 0 )
                            {
                                System.out.println("Price can't be 0 or negative consider adding a valid price.");
                            }
                            else
                            {
                                validPrice = true;
                            }
      
                        } catch (InputMismatchException inputException) {
                            System.err.println("Input Mismatch...");
                        }
                    }
                    st = client.getConnectInstance().prepareStatement(updatePriceQuery);
    
                    st.setInt(1, price);
                    st.setString(2, prod_ID);
    
                    st.executeUpdate();
    
                    System.out.println("The price : "+price+" updated to the product : "+prodName+" successfully...");
                    
                }
    
            } catch (SQLException sqlException) {
                System.err.println(sqlException);
                System.err.println("Product not Available...");
            }
        }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }

    public void AddStock( String prod_ID , Scanner input )
    {
        String productDetailsQuery = "SELECT * FROM products WHERE productid = ? LIMIT 1 ";
        String updatePriceQuery = "UPDATE products SET stock = ? WHERE productid = ?";
        String updateStockVisibilityQuery = "UPDATE products SET stock = ? , isstockavail = ? , visibility = ? , WHERE productid = ?";
        Long stock = 0l;
        try {
            PreparedStatement st = client.getConnectInstance().prepareStatement(productDetailsQuery);
            // if( prodResult == null )
            // {
            try {
                st.setString(1, prod_ID);
                prodResult = st.executeQuery();
                try {

                    while(prodResult.next())
                    {
                        String prodName = prodResult.getString("productname");
                        System.out.println("The stock of the product : "+prodName+" is "+prodResult.getInt("stock"));
                        
                        boolean validStock = false;
                        while(!validStock)
                        {
                            try {
                                System.out.println("Enter the stock value to be added for this product : ");
                                stock = input.nextLong();
                                if(stock < 0 )
                                {
                                    String ch;
                                    System.out.println("Negative value for additon of stock is not possible.\nWant to switch to Remove stocks ? [y / n]");
                                    ch =input.next();
                                    if( ch.toLowerCase().equals("y") )
                                    {
                                        validStock  = true;
                                        console.setHide(true);
                                        console.setOption(5);
                                        break;
                                    }     
                            
                                }
                                else
                                {
                                    validStock = true;
                                    if(console.getHide() == true)
                                    {
                                    console.setHide(false);
                                    } 
                                }
          
                            } catch (InputMismatchException inputException) {
                                System.err.println("Input Mismatch...");
                            }
                        }
                        if(console.getHide())
                        {
                            break;
                        }
                        if( prodResult.getBoolean("isstockavail") == false )
                        {
                            System.err.println("Stocks and visibile");
                            st = client.getConnectInstance().prepareStatement(updateStockVisibilityQuery);
                            st.setLong(1, stock+prodResult.getLong("stock"));
                            st.setBoolean(2, true);
                            st.setString(3, prod_ID);
                        }
                        else
                        {
                            st = client.getConnectInstance().prepareStatement(updatePriceQuery);
                            st.setLong(1, stock+prodResult.getLong("stock"));
                            st.setString(2, prod_ID);
                        }
        
                        st.executeUpdate();
                        if(stock == 0)
                        {
                            System.out.println("Stock value remains as samme value : "+(stock+prodResult.getLong("stock")));
                        }
                        else
                        {
                            System.out.println("The stocks : "+(stock+prodResult.getLong("stock"))+" updated to the product : "+prodName+" successfully...");
                        }
        
                    }
        
                } catch (SQLException sqlException) {
                    System.err.println(sqlException);
                    System.err.println("Product not Available...");
                }
            } catch (SQLException sqlException) {
                System.err.println("Product not Found...");
            }
        // }
        // else{
        //     try {

        //         while(prodResult.next())
        //             {
        //                 String prodName = prodResult.getString("productname");
        //                 System.out.println("The stock of the product : "+prodName+" is "+prodResult.getInt("stock"));
                        
        //                 boolean validStock_2 = false;
        //                 while(!validStock_2)
        //                 {
        //                     try {
        //                         System.out.println("Enter the stock value to be added for this product : ");
        //                         stock = input.nextLong();
        //                         if(stock < 0 )
        //                         {
        //                             boolean choice = false;
        //                             String ch;
        //                             System.out.println("Negative value for additon of stock is not possible.\nWant to switch to Remove stocks ? [y / n]");
        //                             ch =input.next();
        //                             if( ch.toLowerCase().equals("y") )
        //                             {
        //                                 validStock_2  = true;
        //                                 console.setHide(true);
        //                                 console.setOption(5);
        //                                 break;
        //                             }     
                            
        //                         }
        //                         else
        //                         {
        //                             validStock_2 = true;
        //                         }
          
        //                     } catch (InputMismatchException inputException) {
        //                         System.err.println("Input Mismatch...");
        //                     }
        //                 }
        //                 if(console.getHide())
        //                 {
        //                     break;
        //                 }
        //                 st = client.getConnectInstance().prepareStatement(updatePriceQuery);
        
        //                 st.setLong(1, stock+prodResult.getLong("stock"));
        //                 st.setString(2, prod_ID);
        
        //                 st.executeUpdate();
        
        //                 System.out.println("The stocks : "+(stock+prodResult.getLong("stock"))+" updated to the product : "+prodName+" successfully...");
        //                 if(console.getHide() == true)
        //                 {
        //                     console.setHide(false);
        //                 }
                        
        //             }
    
        //     } catch (SQLException sqlException) {
        //         System.err.println(sqlException);
        //         System.err.println("Product not Available...");
        //     }
        // }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }

    public void removeStock( String prod_ID , Scanner input )
    {
        String productDetailsQuery = "SELECT * FROM products WHERE productid = ? LIMIT 1 ";
        String updateStockQuery = "UPDATE products SET stock = ? WHERE productid = ?";
        String updateStockVisibilityQuery = "UPDATE products SET stock = ? , isstockavail = ? WHERE productid = ?";
        Long stock = 0l;
        try {
            PreparedStatement st = client.getConnectInstance().prepareStatement(productDetailsQuery);
            // if( prodResult == null )
            // {
            try {
                st.setString(1, prod_ID);
                prodResult = st.executeQuery();
                try {

                    while(prodResult.next())
                    {
                        if( prodResult.getBoolean("isstockavali") )
                        {
                            System.err.println("Cannot remove stock from a invisible product. Consider addiing stocks to this product...");
                        }
                        else
                        {
                            String prodName = prodResult.getString("productname");
                            System.out.println("The stock of the product : "+prodName+" is "+prodResult.getInt("stock"));
                            
                            boolean validStock = false;
                            boolean added = false;
                            while(!validStock)
                            {
                                try {
                                    System.out.println("Enter the stock value to be removed for this product : ");
                                    stock = input.nextLong();
                                    if( (stock < 0) && (Math.abs(stock) >= prodResult.getLong("stock") ))
                                    {
                                        String ch;
                                        System.out.println("The provided quantity is more than or same as available it will be out of stock until you add stocks. Want to Remove stocks ? [y / n]");
                                        ch =input.next();
                                        if( ch.toLowerCase().equals("y") )
                                        {
                                            validStock  = true;
                                            st = client.getConnectInstance().prepareStatement(updateStockVisibilityQuery);
            
                                            st.setLong(1, 0l);
                                            st.setBoolean(2, false);
                                            st.setString(3, prod_ID);
                                            
                                            st.executeUpdate();
                                            added = true;
                                            System.out.println("The stocks : "+(prodResult.getLong("stock") + stock)+" updated to the product : "+prodName+" successfully...");
                                            break;
                                        }     
                                
                                    }
                                    else if ( stock > 0 )
                                    {
                                        String ch;
                                        System.out.println("The provided value for removing of stock is not possible.\nWant to switch to Add stocks ? [y / n]");
                                        ch =input.next();
                                        if( ch.toLowerCase().equals("y") )
                                        {
                                            validStock  = true;
                                            console.setHide(true);
                                            console.setOption(4);
                                            break;
                                        }  
                                    }
                                    else
                                    {
                                        validStock = true;
                                        if(console.getHide() == true)
                                        {
                                        console.setHide(false);
                                        } 
                                    }
            
                                } catch (InputMismatchException inputException) {
                                    System.err.println("Input Mismatch...");
                                }
                            }
                            if(console.getHide() || added)
                            {
                                break;
                            }
                            st = client.getConnectInstance().prepareStatement(updateStockQuery);
            
                            st.setLong(1, (prodResult.getLong("stock") + stock));
                            st.setString(2, prod_ID);
            
                            st.executeUpdate();
                            if(stock == 0)
                            {
                                System.out.println("Stock value remains as samme value : "+(prodResult.getLong("stock") + stock));
                            }
                            else
                            {
                                System.out.println("The stocks : "+(prodResult.getLong("stock") + stock)+" updated to the product : "+prodName+" successfully...");
                            }
                        }
        
                        
                    }
        
                } catch (SQLException sqlException) {
                    System.err.println(sqlException);
                    System.err.println("Product not Available...");
                }
            } catch (SQLException sqlException) {
                System.err.println("Product not Found...");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }

    public void deleteProduct( String prod_ID )
    {
        String deleteQuery = "UPDATE products SET visibility = ? WHERE productid = ?";

        try {
            PreparedStatement st = client.getConnectInstance().prepareStatement(deleteQuery);
            st.setBoolean(1, false);
            st.setString(2, prod_ID);

            st.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println("Product not found..");
        }
    }
    
}
