import java.util.Scanner;

public class App {

    static String url = "jdbc:mysql://localhost:3308/supermarket?allowPublicKeyRetrieval=true&useSSL=false";
    static String user = "root";
    static String pass = "admin";

    static Scanner sc = new Scanner(System.in);

    public static boolean checkAdmin(String user, String pass){
        // get user name input
        System.out.print("\n Enter the Admin User Name : ");
        String adminuser = sc.next();

        // get password input
        System.out.print(" Enter the Admin Password : ");
        String adminpass = sc.next();

        if(adminuser.equals(user) && adminpass.equals(pass))
            return true;

        return false;
    }
    public static void main(String[] args) throws Exception {

        while(true){
            System.out.print("\n 1 -> Billing Section ");
            System.out.print("\n 2 -> Display all bills of a Customer ");
            System.out.print("\n 3 -> Display Product availablity ");
            System.out.print("\n 4 -> Display all bills on a particular date");
            System.out.print("\n 5 -> Insert / Update Product ");
            System.out.print("\n 6 -> Exit ");
            System.out.print("\n Enter your choice : ");

            int choice = sc.nextInt();

            switch(choice){ 
                // billing
                case 1:
                    generatebill gen = new generatebill();
                    gen.bill();
                    break;
                
                // display customer details
                case 2:
                    customerdetails cust = new customerdetails();
                    cust.customer();
                    break;

                // display product availability
                case 3:
                    if(checkAdmin(user, pass)){
                        products.displayproducts();
                    }
                    else{
                        System.out.print("\n Invalid Admin UserName / Password ! \n");
                    }
                    break;

                // display all bills on a date
                case 4:
                    if(checkAdmin(user, pass)){
                        billdetailsdate prod = new billdetailsdate();
                        prod.billdate();
                    }
                    else{
                        System.out.print("\n Invalid Admin UserName / Password ! \n");
                    }
                    
                    break;

                // Insert / Update Product
                case 5:
                if(checkAdmin(user, pass)){
                    insertUpdateProduct ins = new insertUpdateProduct();

                    System.out.print("\n 1 -> Insert Product \n");
                    System.out.print(" 2 -> Update Product \n Enter your choice : ");

                    Integer insertUpdateChoice = sc.nextInt();

                    switch (insertUpdateChoice) {
                        case 1:
                            ins.insertProduct();
                            break;
                        case 2:
                            ins.updateProduct();
                            break;
                        default:
                            System.out.print("\n Enter a valid choice! \n");
                            break; 
                    }

                }
                break;
                
                // Exit the program
                case 6: 
                    System.out.print("\n Thanks for using our Supermarket Application ! \n\n");
                    sc.close();
                    System.exit(0);
            }
        }
    }       
}

