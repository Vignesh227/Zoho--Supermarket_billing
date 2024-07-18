import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;


public class insertUpdateProduct {

    // Set username, pass, and establish connection url
    static String url = "jdbc:mysql://localhost:3308/supermarket?allowPublicKeyRetrieval=true&useSSL=false";
    static String username = "root";
    static String pass = "admin";

    Scanner sc = new Scanner(System.in);

    // Function to insert a new product in the Products Table
    public void insertProduct(){
        System.out.print("\n Enter product name : ");
        String name = sc.next();

        System.out.print("\n Enter product price : ");
        Integer prodprice = sc.nextInt();

        System.out.print("\n Enter product availability : ");
        Integer availability = sc.nextInt();

        String sql = "INSERT INTO products (prodname, prodprice, availability) VALUES (?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url, username, pass);
            PreparedStatement statement = connection.prepareStatement(sql);) {
                
                statement.setString(1, name);
                statement.setInt(2, prodprice);
                statement.setInt(3, availability);

                int insert = statement.executeUpdate();

                if(insert > 0){
                    System.out.print("\n Product Inserted Successfully! ");
                    products.displayproducts();
                }

        } catch (Exception e) {
            e.printStackTrace();;
        }
        
    }

    // Function to update the existing records in the 'product' table
    public void updateProduct(){

        products.displayproducts();

        System.out.print("\n Enter product ID to be updated : ");
        Integer id = sc.nextInt();

        System.out.print("\n Update product price : ");
        Integer prodprice = sc.nextInt();

        System.out.print("\n Update product availability : ");
        Integer availability = sc.nextInt();

        String sql = "UPDATE products SET prodprice=? , availability=? WHERE prodid = ?";
        try(Connection connection = DriverManager.getConnection(url, username, pass);
            PreparedStatement statement = connection.prepareStatement(sql);) {
                
                statement.setInt(1, prodprice);
                statement.setInt(2, availability);
                statement.setInt(3, id);

                int insert = statement.executeUpdate();

                if(insert > 0){
                    System.out.print("\n Product Updated Successfully! ");
                    products.displayproducts();
                }

        } catch (Exception e) {
            e.printStackTrace();;
        }
    }
    
}
