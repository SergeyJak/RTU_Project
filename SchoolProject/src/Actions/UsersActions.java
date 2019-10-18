package Actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import static DataBase.ConnectionToDB.connectionToDb;
import static Resources.CommonMethods.ClearScreen;
import static Resources.CommonMethods.returnProductsDescriptionByQuery;
import static Resources.Constants.DO_CHOICE;
import static Resources.Constants.EXIT;
import static Resources.Constants.INFORMATION_MSG;
import static Resources.Constants.NEW_ORD;
import static Resources.Constants.NEW_SERV;
import static Resources.Constants.RETURN_TO_LOGIN;
import static Resources.Constants.SEE_ORD;
import static Resources.Constants.SEE_SERV;
import static Resources.Constants.WRONG_CHOICE;

class UsersActions {
    static void userMainChoice(int userId) throws SQLException, ClassNotFoundException, IOException {
        String mainChoice;

        Scanner in = new Scanner(System.in);

        connectionToDb();

        System.out.println(INFORMATION_MSG); //Message should be updated


        do {
            System.out.print(DO_CHOICE);
            mainChoice = in.nextLine();

            switch (mainChoice) {
                case "products":
                    listProducts();
                    break;
                case NEW_ORD:
                    addNewOrder(userId);
                    break;
                case NEW_SERV:
                    addNewServiceRequest(userId);
                    break;
                case SEE_ORD:
                    seeAllOrders(userId);
                    break;
                case SEE_SERV:
                    seeAllServices(userId);
                    break;
                case EXIT:
                    System.out.println(RETURN_TO_LOGIN);
                    break;
                default:
                    System.out.println(WRONG_CHOICE);
                    break;
            }
        } while (!mainChoice.equals(EXIT));

        connectionToDb().close();
    }
    private static void listProducts() throws SQLException, IOException, ClassNotFoundException {
        ClearScreen();

        String query = "SELECT * FROM main.products where product_count != 0";
        returnProductsDescriptionByQuery(query);
    }
    private static void addNewOrder(int userId){

    }
    private static void addNewServiceRequest(int userId){

    }
    private static void seeAllOrders(int userId){

    }
    private static void seeAllServices(int userId){

    }
}
