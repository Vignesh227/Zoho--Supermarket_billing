import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class billdisplay {

    // Set username, pass, and establish connection url
    static String url = "jdbc:mysql://localhost:3308/supermarket?allowPublicKeyRetrieval=true&useSSL=false";
    static String username = "root";
    static String pass = "admin";

    // Function to display the customer details, who purchased
    public static void displayCustomer(Integer billid){
        String billQuery = "SELECT b.billid, c.custname, c.phnno, b.totprice, b.date " +
                            "FROM billing b JOIN customer c ON b.custid = c.custid " +
                            "WHERE b.billid = ?";

        // Display Bill Summary
        try (Connection connection = DriverManager.getConnection(url, username, pass);
            PreparedStatement billStmt = connection.prepareStatement(billQuery);) {
            billStmt.setInt(1, billid);
            ResultSet billResult = billStmt.executeQuery();
            if (billResult.next()) {
                //System.out.print("\n_____________________________________________________________ \n");
                System.out.println("\n Bill ID: " + billResult.getInt("billid"));
                System.out.println(" Date: " + billResult.getString("date"));
                System.out.println(" Customer Name: " + billResult.getString("custname"));
                System.out.println(" Phone Number: " + billResult.getString("phnno"));
                //System.out.println(" Total Price: " + billResult.getInt("totprice"));
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    // function to display the bill items
    public static void displayBill(int billid) {
        
        
        String itemsQuery = "SELECT p.prodname, bi.quantity, p.prodprice " +
                             "FROM billingitems bi JOIN products p ON bi.prodid = p.prodid " +
                             "WHERE bi.billid = ?";
        
        int total = 0;
        try (Connection connection = DriverManager.getConnection(url, username, pass)) {

            // Display Purchased Items
            try (PreparedStatement itemsStmt = connection.prepareStatement(itemsQuery)) {
                itemsStmt.setInt(1, billid);
                ResultSet itemsResult = itemsStmt.executeQuery();
                System.out.print("\n--------------------------------------------------\n");
                System.out.println(" Product\tQuantity\tPrice\t Cost");
                System.out.print("--------------------------------------------------\n");
                while (itemsResult.next()) {
                    String prodName = itemsResult.getString("prodname");
                    int quantity = itemsResult.getInt("quantity");
                    int price = itemsResult.getInt("prodprice");
                    int cost = quantity * price;
                    System.out.println( " " + prodName + "\t\t" + quantity + " \t\t" + price + " \t " + cost);
                    total += cost;
                }
                System.out.print("--------------------------------------------------\n");
                System.out.println("\n Total Price: " + total);
                //System.out.print("\n_____________________________________________________________ \n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
