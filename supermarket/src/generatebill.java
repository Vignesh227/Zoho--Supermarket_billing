import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class generatebill {
    String url = "jdbc:mysql://localhost:3308/supermarket?allowPublicKeyRetrieval=true&useSSL=false";
    String username = "root";
    String pass = "admin";

    Scanner sc = new Scanner(System.in);

    public void bill() {

        System.out.print("\n Enter your name : ");
        String name = sc.next();

        System.out.print(" Enter your phone number : ");
        String phnno = sc.next();
        
        if (checkCustomer(name, phnno)) {
            System.out.println("\n Hello " + name + "! your Account logged-in successfully ");
        } 
        
        // Customer doesn't exist
        else {
            addCustomer(name, phnno);
        }

        
        int custid = customerdetails.getCustomerId(name, phnno);
        getDetails(custid);
        
    }

    // function to check if a customer already exists
    public boolean checkCustomer(String name, String phnno) {
        
        String sql = "SELECT * FROM customer WHERE phnno = ? AND custname = ?";
        
        try (Connection connection = DriverManager.getConnection(url, username, pass);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
                statement.setString(1, phnno);
                statement.setString(2, name);
                 
                ResultSet resultSet = statement.executeQuery();
                
                if (resultSet.next()) {
                    return true; // Customer exists
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false; // Customer does not exist
    }

    // function to add a new customer
    public void addCustomer(String name, String phnno) {
        
        String sql = "INSERT INTO customer (custname, phnno) VALUES (?, ?)";
        
        try (Connection connection = DriverManager.getConnection(url, username, pass);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, name);
            statement.setString(2, phnno);
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("\n Hello! "+ name + " your Customer Account has been created successsfully! ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // Function to get details of the purchase
    public void getDetails(Integer custid){

        System.out.print("\n Enter the number of products : ");
        int num = sc.nextInt();

        displayProducts();

        int totalprice = 0;

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = currentDate.format(formatter);

        try (Connection connection = DriverManager.getConnection(url, username, pass);) {

            // Insert the bill and get the generated bill ID
            int billid = insertBill(connection, custid, date);

            while(num > 0){
                System.out.print("\n Enter product id : ");
                int prodid = sc.nextInt();

                System.out.print("\n Enter quantity : ");
                int quantity = sc.nextInt();

                // Return the current product availablity
                int currAvailabilty = returnProductAvailability(prodid);

                
                if(currAvailabilty >= quantity){
                    System.out.print("\n Product purchased successfully \n");

                    totalprice = purchaseProduct(custid, prodid, quantity, totalprice, date, billid);
            
                }

                // If quantity is not sufficient
                else if(currAvailabilty > 0){
                    System.out.print("\n Sorry, " + quantity + " items are not available ! ");

                    System.out.print("\n Only " + currAvailabilty + " product(s) are available !");
                    System.out.print("\n Do you want to purchase currently available " + currAvailabilty + " products? \n 1 -> Yes \n 2 -> No");
                    int ch = sc.nextInt();

                    if(ch == 1){
                        System.out.print("\n - - Product purchased successfully - - \n");

                        totalprice =  purchaseProduct(custid, prodid, currAvailabilty, totalprice, date, billid);
                    }
                    else{
                        System.out.print("\n Item Skipped \n");
                    }
                }
                else{
                    //
                    System.out.print("\n Item Skipped \n");
                }

                --num;  
            }
            // Update the total price in the bill table
            updateBillTotalPrice(connection, billid, totalprice);

            System.out.print("\n_____________________________________________________________ \n");
            billdisplay.displayCustomer(billid);
            billdisplay.displayBill(billid);
            System.out.print("\n_____________________________________________________________ \n");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // function to display the products and id to the customer
    public void displayProducts(){

        String sql = "SELECT prodid, prodname, availability FROM products";
        
        try (Connection connection = DriverManager.getConnection(url, username, pass);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
                ResultSet rs = statement.executeQuery();
                System.out.print("\n----------------------");
                System.out.print("\n Prod_id   Prod_name");
                System.out.print("\n----------------------\n");
                while (rs.next()) {

                    int availability = rs.getInt("availability");

                    // Display product, only if it is available!
                    if(availability > 0){
                        int prodid = rs.getInt("prodid");
                        String prodname = rs.getString("prodname");
                        System.out.println( " " + prodid + " \t    " + prodname);
                    }
                }
                System.out.print("----------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // function to return the product availabilty
    public int returnProductAvailability(Integer prodid){
        String sql = "SELECT availability FROM products WHERE prodid = ?";
        
        try (Connection connection = DriverManager.getConnection(url, username, pass);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
                statement.setInt(1, prodid);

                ResultSet rs = statement.executeQuery();

                if(rs.next()){
                    Integer availability = rs.getInt("availability");
                    return availability; 
                }              
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }



    // Function to purchase the product
    public int purchaseProduct(Integer custid, Integer prodid,Integer quantity, Integer totalprice, String date, Integer billid){

        // String sql = "SELECT availability FROM products WHERE prodid = ?";
        
        try (Connection connection = DriverManager.getConnection(url, username, pass);) {

           

            int productPrice = getProductPrice(connection, prodid);
            int cost = productPrice * quantity;
            totalprice += cost;

            // Update product availability and insert billing item
            updateProductTableAndPurchase(connection, billid, prodid, quantity);

            return totalprice;
            
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // Function to generate a bill ID
    /**
     * @param connection
     * @param custid
     * @param date
     * @return
     */
    public int insertBill(Connection connection, Integer custid, String date){

        String sql = "INSERT INTO billing (custid, date) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            statement.setInt(1, custid);
            statement.setString(2, date);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return -1;

    }

    // Function to get product price
    public int getProductPrice(Connection connection, Integer prodid){

        String sql = "SELECT prodprice FROM products WHERE prodid = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql);){

            statement.setInt(1, prodid);

            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                Integer prodprice = rs.getInt("prodprice");
                return prodprice; 
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }


    // Function to update the product Table and make a purchase
    public void updateProductTableAndPurchase(Connection connection, int billid, int prodid, int quantity) {

        String updateProductSql = "UPDATE products SET availability = availability - ? WHERE prodid = ?";
        String insertBillingItemSql = "INSERT INTO billingitems (billid, prodid, quantity) VALUES (?, ?, ?)";
        
        try (PreparedStatement updateProductStmt = connection.prepareStatement(updateProductSql);
             PreparedStatement insertBillingItemStmt = connection.prepareStatement(insertBillingItemSql)) {
            
            // Update product availability
            updateProductStmt.setInt(1, quantity);
            updateProductStmt.setInt(2, prodid);
            updateProductStmt.executeUpdate();
            
            // Insert billing item
            insertBillingItemStmt.setInt(1, billid);
            insertBillingItemStmt.setInt(2, prodid);
            insertBillingItemStmt.setInt(3, quantity);
            insertBillingItemStmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }




    // Function to update the total amount
    public void updateBillTotalPrice(Connection connection, int billid, int totalPrice){

        String sql = "UPDATE billing SET totprice = ? WHERE billid = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, totalPrice);
            statement.setInt(2, billid);
            statement.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
    

