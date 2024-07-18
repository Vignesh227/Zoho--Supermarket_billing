import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class billdetailsdate {

    // Set username, pass, and establish connection url
    static String url = "jdbc:mysql://localhost:3308/supermarket?allowPublicKeyRetrieval=true&useSSL=false";
    static String username = "root";
    static String pass = "admin";

    Scanner sc = new Scanner(System.in);

    // Function to fetch all bills on a particular date
    public void billdate(){

        System.out.print("\n Enter the date (yyyy/mm/dd) : ");
        
        String date = sc.next();
        Integer prevCustId = 0;

        String billsql = "SELECT B.billid, B.date, B.totprice, B.custid,C.custname,C.phnno FROM billing B INNER JOIN customer C ON B.custid=C.custid WHERE date=? ORDER BY custid";

        try(Connection connection = DriverManager.getConnection(url, username, pass);
            PreparedStatement statement = connection.prepareStatement(billsql);){

                statement.setString(1, date);

                ResultSet billingresult = statement.executeQuery();

                
                while(billingresult.next()){
                    Integer custid = billingresult.getInt("custid");

                    // if previous customer id doesn't match with current customer id
                    // then display the customer details, else skip the customer details
                    if(custid != prevCustId){

                        System.out.print("\n_____________________________________________________________ \n");

                        String custname = billingresult.getString("custname");
                        String phnno = billingresult.getString("phnno");
                        System.out.print("\n Customer ID : " + custid);
                        System.out.print("\n Name : " + custname + "\n Phn No : " + phnno);

                        prevCustId = custid;
                    }

                    Integer billid = billingresult.getInt("billid");

                    System.out.print("\n\n Bill ID : " + billid);

                    // Call existing function to display the bill
                    billdisplay.displayBill(billid);


                }

                System.out.print("\n_____________________________________________________________ \n");

        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
}
