package Actions;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.w3c.dom.ls.LSOutput;

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

class AdminActions {
    static void adminChoice() throws SQLException, IOException, ClassNotFoundException {
        String mainChoice;

        Scanner in = new Scanner(System.in);

        connectionToDb();

        System.out.println("empty"); //Information - main page

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
                    System.out.println(EXIT);
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

	private static void editOrder() throws ClassNotFoundException, SQLException, IOException{
		Scanner inn = new Scanner(System.in);
		System.out.println("Choose order which status should be changed:");
		String order_id = inn.nextLine();// ne nado li tut int?
		String query = "Select \n" +
                 "O.order_id,U.username, P.product_name, P.product_description,O.count,O.total_price,O.delivery, O.date, O.status\n" +
                 "FROM main.products P\n" +
                 "INNER JOIN main.order O ON P.product_id=O.product_id\n" +
                 "INNER JOIN main.user U ON U.user_id=O.user_id\n"+
                 "where O.order_id = " + order_id + "";
		returnOrderListByQuery(query);		
		System.out.println("\nChoose new status:"); 
		String statusNew = inn.nextLine();
		changeStatusForOrder(order_id,statusNew);
    }
    
    private static void editService(){

    }
    
    private static void search() throws SQLException, IOException, ClassNotFoundException {
    String mainSearch;
        Scanner typeSearch = new Scanner(System.in);
        System.out.println("Choose Search field\n By status: [status]\n By order id number: [order_id] \n By delivery: [delivery]");
        mainSearch = typeSearch.nextLine();
            switch (mainSearch){
                case "status":
                    String prodState;
                    Scanner status = new Scanner(System.in);
                    System.out.println("Choose status: [approved], [declined], [delivered] or [submited]");
                    prodState = status.nextLine();
                    System.out.println(prodState);
                    String query1 = "Select * FROM main.order where status = \"" +  prodState + "\"";
                    returnSearchResult(query1);
                    break;
                case "delivery":
                    String deliveryType;
                    Scanner statusDel = new Scanner(System.in);
                    System.out.println("Choose delivery type: [mail], [shop] or [courier]");
                    deliveryType = statusDel.nextLine();
                    String query2 = "Select * FROM main.order where delivery = \"" + deliveryType + "\"";
                    returnSearchResult(query2);
                    break;
                case "order_id":
                    String orderID;
                    Scanner number = new Scanner(System.in);
                    System.out.println("Enter order number: ____");
                    orderID = number.nextLine();
                    String query3 = "Select * FROM main.order where order_id = \"" + orderID + "\"";
                    returnSearchResult(query3);
                    break;
            }
         }
    }

