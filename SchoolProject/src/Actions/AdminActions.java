package Actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import static DataBase.ConnectionToDB.connectionToDb;
import static Resources.Constants.DO_CHOICE;
import static Resources.Constants.EXIT;
import static Resources.Constants.RETURN_TO_LOGIN;
import static Resources.Constants.WRONG_CHOICE;

class AdminActions {
    static void adminChoice() throws SQLException, IOException, ClassNotFoundException {
        String mainChoice;

        Scanner in = new Scanner(System.in);

        connectionToDb();

        System.out.println("empty"); //Information - main page

        do {
            System.out.print(DO_CHOICE);
            mainChoice = in.nextLine();

            switch (mainChoice) {
                case "view orders":
                    viewAllOrders();
                    break;
                case "view services":
                    viewAllServices();
                    break;
                case "edit orders":
                    editOrder();
                    break;
                case "edit services":
                    editService();
                    break;
                case "search":

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
    private static void viewAllOrders(){

    }
    private static void viewAllServices(){

    }
    private static void editOrder(){

    }
    private static void editService(){

    }
}
