package Actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import static DataBase.ConnectionToDB.connectionToDb;
import static Resources.CommonMethods.*;
import static Resources.Constants.*;
//import static Resources.Constants.DO_CHOICE;
//import static Resources.Constants.EXIT;
//import static Resources.Constants.RETURN_TO_LOGIN;
//import static Resources.Constants.WRONG_CHOICE;

public class AdminActions {
    public static void adminChoice(String userLogin) throws SQLException, IOException, ClassNotFoundException {
        String mainChoice;
        Scanner in = new Scanner(System.in);

        connectionToDb();

        System.out.format("Welcome %s\n",userLogin); //Information - main page

        do {
            System.out.print(DO_CHOICE);
            System.out.print(ADMIN_CHOICE);
            mainChoice = in.nextLine();

            switch (mainChoice) {
                case "vo":
                    viewAllOrders();
                    break;
                case "vs":
                    viewAllServices();
                    break;
                case "eo":
                    editOrder();
                    break;
                case "es":
                    editService();
                    break;
                case "srch":
                	search();
                    break;
                case "EXIT":
                    System.out.println(RETURN_TO_LOGIN);
                    break;
                default:
                    System.out.println(WRONG_CHOICE);
                    break;
            }
        } while (!mainChoice.equals(EXIT));

        connectionToDb().close();

    }
    private static void viewAllOrders() throws SQLException, IOException, ClassNotFoundException{
        ClearScreen();
        String query = "Select \n" +
                "O.order_id,U.username, P.product_name, P.product_description,O.count,O.total_price,O.delivery, O.date, O.status\n" +
                "FROM main.products P\n" +
                "INNER JOIN main.order O ON P.product_id=O.product_id\n" +
                "INNER JOIN main.user U ON U.user_id=O.user_id";

        returnOrderListByQuery(query);

    }
    
    private static void viewAllServices() throws SQLException, IOException, ClassNotFoundException{
        ClearScreen();
        String query = "Select \r\n" + 
        		"S.service_id,U.username, S.product_name,S.date, S.description,S.broken_detail, S.status, S.price\r\n" + 
        		"FROM main.user U\r\n" + 
        		"INNER JOIN main.service S ON U.user_id=S.user_id";

        returnServiceListByQuery(query);
    }

	public static void editOrder() throws ClassNotFoundException, SQLException, IOException{
		Scanner inn = new Scanner(System.in);
		System.out.println("Choose order which status should be changed:");
		String order_id = inn.nextLine();
		
		if (isNumeric(order_id)){
		String query = "Select \n" +
                 "O.order_id,U.username, P.product_name, P.product_description,O.count,O.total_price,O.delivery, O.date, O.status\n" +
                 "FROM main.products P\n" +
                 "INNER JOIN main.order O ON P.product_id=O.product_id\n" +
                 "INNER JOIN main.user U ON U.user_id=O.user_id\n"+
                 "where O.order_id = " + order_id + ";";
		
		returnOrderListByQuery(query);		
		System.out.println("\nChoose new status:"); 
		String statusNew = inn.nextLine();
		changeStatusForOrder(order_id,statusNew);
		}
		else {
			System.out.print("Wrong input\n");
			editOrder();
		}
    }
    
    public static void editService() throws ClassNotFoundException, SQLException, IOException{
    	Scanner inn = new Scanner(System.in);
		System.out.println("Choose service which status should be changed:");
		String service_id = inn.nextLine();
		
		if (isNumeric(service_id)){
        String query = "Select \n" + 
        		"S.service_id,U.username, S.product_name,S.date, S.description,S.broken_detail, S.status, S.price\n" + 
        		"FROM main.user U\n" + 
        		"INNER JOIN main.service S ON U.user_id=S.user_id\n" + 
                "where S.service_id = " + service_id + ";";
		
        returnServiceListByQuery(query);		
		System.out.println("\nChoose new status:"); 
		String statusNew = inn.nextLine();
		System.out.println("\nChoose new price:"); 
		String priceNew = inn.nextLine();
		changeStatusForService(service_id,statusNew,priceNew); 
		}
		else {
			System.out.println("Wrong input\n");
			editService();
			}
		}
  
    
    private static void search() {
    	// Valery part

    	
    }
}
