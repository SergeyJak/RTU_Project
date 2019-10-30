package Actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import static DataBase.ConnectionToDB.connectionToDb;
import static Resources.CommonMethods.*;
import static Resources.Constants.*;

public class AdminActions {
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
        ClearScreen();
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
            if (statusNew.equals(EXIT)) {
                adminChoice();
            }
            changeStatusForOrder(order_id,statusNew);
        }
        else if(order_id.equals(EXIT)) {
            adminChoice();
        }
		else {
			System.out.print("Wrong input\n");
            adminChoice();
		}
    }
    
    public static void editService() throws ClassNotFoundException, SQLException, IOException {
        ClearScreen();
        Scanner inn = new Scanner(System.in);
        System.out.println("Choose service which status should be changed:");
        String service_id = inn.nextLine();
        if (service_id.equals(EXIT)) {
            adminChoice();
        }

        if (isNumeric(service_id)) {
            String query = "Select \n" +
                    "S.service_id,U.username, S.product_name,S.date, S.description,S.broken_detail, S.status, S.price\n" +
                    "FROM main.user U\n" +
                    "INNER JOIN main.service S ON U.user_id=S.user_id\n" +
                    "where S.service_id = " + service_id + ";";

            returnServiceListByQuery(query);
            System.out.println("\nChoose new status:");
            String statusNew = inn.nextLine();
            if (statusNew.equals(EXIT)) {
                adminChoice();
            } else {
                System.out.println("\nEnter new price:");
                String priceNew = inn.nextLine();
                if (isFloat(priceNew)) {
                    changeStatusForService(service_id, statusNew, priceNew);
                } else if (priceNew.equals(EXIT)) {
                    adminChoice();
                } else {
                    System.out.println("Wrong input\n");
                    editService();
                }

            }
        } else {
            System.out.println("Wrong input\n");
            editService();
        }
    }
        private static void search() throws SQLException, IOException, ClassNotFoundException {
        ClearScreen();
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

