import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class customerdetails {
    static String url = "jdbc:mysql://localhost:3308/supermarket?allowPublicKeyRetrieval=true&useSSL=false";
    static String username = "root";
    static String pass = "admin";


    public void customer() {

        Scanner sc = new Scanner(System.in);

        System.out.print("\n Enter your name : ");
        String name = sc.next();

        System.out.print(" Enter your phone number : ");
        String phnno = sc.next();

        // create a object for a bill class
        generatebill obj = new generatebill();

        if (obj.checkCustomer(name, phnno)) {

            // display the customer details
            // ...

            int custid = getCustomerId(name, phnno);
            displayCustomerDetails(custid);
        } 
        
        // Customer doesn't exist
        else {
            System.out.print("\n Invalid Customer ID !! \n");
        }
    }

    // to get cust id
    public static int getCustomerId(String name, String phnno) {
        String sql = "SELECT custid FROM customer WHERE custname = ? AND phnno = ?";
        
        try (Connection connection = DriverManager.getConnection(url, username, pass);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, name);
            statement.setString(2, phnno);
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt("custid"); // Return customer ID
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return -1; // Customer does not exist
    }

    // to display customer details
    public static void displayCustomerDetails(int custid) {

        // to retrieve the customer details 
        String customerSql = "SELECT * FROM customer WHERE custid = ?";

        // to retrieve the bill details of the customer 
        String billSql = "SELECT * FROM billing WHERE custid = ?";

        // to fetch the product details of the bills that made by the respective customer
        String billingItemsSql = "SELECT bi.*, p.prodname, p.prodprice FROM billingitems bi JOIN products p ON bi.prodid = p.prodid WHERE bi.billid = ?";

        try (Connection connection = DriverManager.getConnection(url, username, pass);
             PreparedStatement customerStmt = connection.prepareStatement(customerSql);
             PreparedStatement billStmt = connection.prepareStatement(billSql);
             PreparedStatement billingItemsStmt = connection.prepareStatement(billingItemsSql)) {
            
            // Retrieve and display customer details
            customerStmt.setInt(1, custid);
            ResultSet customerResultSet = customerStmt.executeQuery();
            System.out.print("\n_____________________________________________________________ \n");
            if (customerResultSet.next()) {
                String custname = customerResultSet.getString("custname");
                String phnno = customerResultSet.getString("phnno");
                System.out.println("\n CUSTOMER ID : " + custid);
                System.out.println(" CUSTOMER Name : " + custname);
                System.out.println(" PHONE NUMBER : " + phnno);
            }
            
            // Retrieve and display bill details
            billStmt.setInt(1, custid);
            ResultSet billResultSet = billStmt.executeQuery();
            while (billResultSet.next()) {
                int billid = billResultSet.getInt("billid");
                String date = billResultSet.getString("date");
                int totprice = billResultSet.getInt("totprice");

                System.out.println("\n\n Bill ID: " + billid + "\t \t \t Date : " + date);
                
                // Retrieve and display billing items
                billingItemsStmt.setInt(1, billid);
                ResultSet billingItemsResultSet = billingItemsStmt.executeQuery();

                System.out.print("----------------------------------------------------- ");
                System.out.println("\n Product \tQuantity\tPrice\t   Cost");
                System.out.print("----------------------------------------------------- \n");

                while (billingItemsResultSet.next()) {
                    String prodname = billingItemsResultSet.getString("prodname");
                    int quantity = billingItemsResultSet.getInt("quantity");
                    int price = billingItemsResultSet.getInt("prodprice");
                    int cost = quantity * price;
                    
                    // System.out.println(" Product: " + prodname + ", Quantity: " + quantity + ", Price: " + price + ", Cost: " + cost);
                    System.out.println(" " + prodname + "  \t " + quantity + " \t\t  " + price + " \t   " + cost);
                }
                System.out.print("----------------------------------------------------- \n");
                System.out.println("\t\t\t\tTot Price: " + totprice );
            }
            System.out.print("\n_____________________________________________________________ \n\n");
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
    
    }
}