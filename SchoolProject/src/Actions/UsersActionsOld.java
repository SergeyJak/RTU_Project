package Actions;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Scanner;

import static DataBase.ConnectionToDB.connectionToDb;
import static Resources.CommonMethods.ClearScreen;
import static Resources.CommonMethods.returnProductsDescriptionByQuery;
import static Resources.Constants.DO_CHOICE;
import static Resources.Constants.ENTER_PRODUCT_DESCRIPTION;
import static Resources.Constants.ENTER_PRODUCT_NAME;
import static Resources.Constants.EXIT;
import static Resources.Constants.FIND;
import static Resources.Constants.RETURN_TO_LOGIN;
import static Resources.Constants.WRONG_CHOICE;


// !!!!!!!!!!!!This class not actual anymore. After all other clas an methods implementation should be removed!!!!!!!!!

public class UsersActionsOld {
    public static void userMainChoice(int userId) throws SQLException, ClassNotFoundException, IOException {
        String mainChoice;

        Scanner in = new Scanner(System.in);

        connectionToDb();


        do {
            System.out.print(DO_CHOICE);
            mainChoice = in.nextLine();

            switch (mainChoice) {
                case "New":
                    addNewRequestInDb(userId);
                    break;
                case "See all":
                    seeAllUserProducts(userId);
                    break;
                case FIND:
                    findUserRequestByName(userId);
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

    private static void findUserRequestByName(int userId) throws SQLException, ClassNotFoundException, IOException{
        String productName;

        ClearScreen();

        System.out.print(ENTER_PRODUCT_NAME);

        Scanner in = new Scanner(System.in);
        productName = in.nextLine();

        String query = "select * from products where user_id = " + userId + " AND product_name = '" + productName + "'";

        returnProductsDescriptionByQuery(query);

    }

    private static void addNewRequestInDb(int userId) throws SQLException, ClassNotFoundException, IOException {
        String productName;
        String productDescription;

        ClearScreen();

        Scanner in = new Scanner(System.in);

        Calendar calendar = Calendar.getInstance();
        Date startDate = new Date(calendar.getTime().getTime());


        System.out.print(ENTER_PRODUCT_NAME);
        productName = in.nextLine();
        System.out.print(ENTER_PRODUCT_DESCRIPTION);
        productDescription = in.nextLine();

        String query = "insert into products (user_id, product_name, product_description, request_time)"
                + " values (?, ?, ?, ?)";

        PreparedStatement preparedStmt = connectionToDb().prepareStatement(query);
        preparedStmt.setInt(1, userId);
        preparedStmt.setString(2, productName);
        preparedStmt.setString(3, productDescription);
        preparedStmt.setDate(4, startDate);
        preparedStmt.execute();

    }

    private static void seeAllUserProducts(int userId) throws SQLException, ClassNotFoundException, IOException {
        ClearScreen();

        String query = "select * from products where user_id = " + userId;
        returnProductsDescriptionByQuery(query);
    }


}
