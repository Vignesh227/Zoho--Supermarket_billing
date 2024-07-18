import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class products {

    // Set username, pass, and establish connection url
    static String url = "jdbc:mysql://localhost:3308/supermarket?allowPublicKeyRetrieval=true&useSSL=false";
    static String username = "root";
    static String pass = "admin";

    static String sql = "SELECT * FROM products";

    // function to display the products detials 
    public static void displayproducts(){

        try (Connection connection = DriverManager.getConnection(url, username, pass);
             PreparedStatement statement = connection.prepareStatement(sql);){

            ResultSet rs = statement.executeQuery();
            
            System.out.print("\n__________________________________________________ ");
            System.out.println("\n Prod_id   Prod_name\t  Price\t  Availability");
            System.out.print("__________________________________________________ \n");
            
            // fetch details row by row and display them
            while (rs.next()) {
                int availability = rs.getInt("availability");
                int prodid = rs.getInt("prodid");
                String prodname = rs.getString("prodname");
                int price = rs.getInt("prodprice");
                
                System.out.println( " " + prodid + " \t    " + prodname + "    \t  " + price + "  \t  " + availability);
            }
            System.out.print("__________________________________________________ \n");
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
