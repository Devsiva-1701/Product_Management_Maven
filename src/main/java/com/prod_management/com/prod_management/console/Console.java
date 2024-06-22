package com.prod_management.com.prod_management.console;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.prod_management.com.prod_management.Mongo.MongoDBClient;
import com.prod_management.com.prod_management.Postgresql.PostgresSQLClient;
import com.prod_management.com.prod_management.product.Product;
import com.prod_management.com.prod_management.users.customer.Customer;
import com.prod_management.com.prod_management.users.seller.Seller;

public class Console {

    private PostgresSQLClient client;
    private MongoDBClient mongoDBClient;
    private Customer customer = new Customer();
    private Seller seller = new Seller();
    // private Console console;
    private boolean hideOptions = false;
    private int option = 0;

    public Console( PostgresSQLClient client , MongoDBClient mongoDBClient)
    {
        this.client = client;
        this.mongoDBClient = mongoDBClient;
    }

    public void setHide(boolean hide)
    {
        hideOptions = hide;
    }

    public boolean getHide()
    {
        return hideOptions;
    }

    public void setOption( int option )
    {
        this.option =option;
    }
    public int getOption()
    {
        return option;
    }

    public void setSeller( Console console )
    {
        this.seller = new Seller(client , console);
    }

    public void setCustomer( Console console )
    {
        this.customer = new Customer(client , console);
    }

    Scanner input = new Scanner(System.in);

    public void runConsole(boolean run , Console console)
    {
        boolean running = run;
        while (running) {
            
            String prod_ID;
            if(!hideOptions)
            {
                System.out.println("\nEnter the option : \n1.View Product \n2.Add Product \n3.Delete Product \n4.Add Stock \n5.Remove Stock \n" + //
                            "6.Update Price \n7.Purchase Product \n8.Purchase History \n9.End Console");
                try {
                    option = input.nextInt();
                } catch (Exception InputException) {
                    System.err.println("Input Missmatch...");
                }
            }
        
            switch (option) {
                case 1:
                    customer.viewProducts(false);
                    System.out.println("Want to download this product excel file ? (y/n)");
                    String ch = input.next();
                    if(ch.toLowerCase().equals("y"))
                    {
                        customer.downloadDoc();
                    }
                    break;

                case 2:
                    seller.addProducts(createProductObject());
                    break;

                case 3:
                    customer.viewProducts(false);
                    System.out.println("Enter the Product ID : ");
                    input.nextLine();
                    prod_ID = input.nextLine();
                    seller.deleteProduct(prod_ID);
                    break;

                case 4:
                    customer.viewProducts(true);
                    System.out.println("Enter the Product ID : ");
                    input.nextLine();
                    prod_ID = input.nextLine();
                    seller.AddStock(prod_ID, input);
                    break;

                case 5:
                    System.out.println("Enter in remove stocks");
                    customer.viewProducts(false);
                    System.out.println("Enter the Product ID : ");
                    input.nextLine();
                    prod_ID = input.nextLine();
                    seller.removeStock(prod_ID, input);
                    break;

                case 6:
                    customer.viewProducts(false);
                    System.out.println("Enter the Product ID : ");
                    input.nextLine();
                    prod_ID = input.nextLine();
                    seller.updatePrice(prod_ID , input);
                    break;

                case 7:
                    customer.viewProducts(false);
                    System.out.println("Enter the Product ID : ");
                    input.nextLine();
                    prod_ID = input.nextLine();
                    customer.purchaseProduct(prod_ID, mongoDBClient, input);
                    break;

                case 8:
                    customer.viewPurchaseHistory( mongoDBClient );
                    break;

                case 9:
                    running = false;
                    input.close(  );
                    System.err.println("Console has been terminated by the user.");
                    break;
            
                default:
                    System.err.println("Enter valid input...");
                    break;
            }
        }
        
    }

    private Product createProductObject()
    {
        String categoryTableQuery = "SELECT * FROM categories";
        String prodName = "";
        String categoryName = "";
        String categoryID = "";
        Integer price = 0 ;
        Long stock = 0l;
        try {
                Statement st = client.getConnectInstance().createStatement();
                ResultSet result = st.executeQuery(categoryTableQuery);
                List<String> resultList = new ArrayList<>();
                int count = 1;
                while(result.next())
                {
                    String ID = result.getString("CategoryID")+","+result.getString("CategoryName");
                    System.out.println(String.valueOf(count)+". "+result.getString("CategoryName"));
                    resultList.add(ID);
                    count++;
                }
                System.out.println("Enter the ProductCategory (number) : ");
                count = input.nextInt();
                String category[]  = resultList.get(count-1).split(",");
                categoryID = category[0];
                categoryName = category[1];

            } catch (Exception SQLException) {
                System.err.println(SQLException);
            }
        
        
            System.out.println("Enter the ProductName : ");
            prodName = input.nextLine();
            prodName = input.nextLine();
            System.out.println("Enter the Price : ");
            price = input.nextInt();
            System.out.println("Enter the Stock : ");
            stock = input.nextLong();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm-ss-SS");

            LocalTime currentTime = LocalTime.now();

            String prodID = "P"+currentTime.format(formatter)+"D";

            return new Product(prodName, prodID, price, stock, categoryName, categoryID);
        
    }
    
}
