package Actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static DataBase.ConnectionToDB.connectionToDb;
import static Resources.CommonMethods.*;
import static Resources.Constants.*;

public class UsersActions {
    static void userMainChoice(int userId) throws SQLException, ClassNotFoundException, IOException {
        String mainChoice;

        Scanner in = new Scanner(System.in);

        connectionToDb();

        System.out.println(INF_MSG_USER_MAIN); //Message should be updated


        do {
            System.out.print(DO_CHOICE);
            mainChoice = in.nextLine();

            switch (mainChoice) {
                case PRODUCTS:
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
    private static void listProducts() throws SQLException, IOException, ClassNotFoundException { //full list of products which are on warehouse
        ClearScreen();

        String query = "SELECT * FROM main.products where product_count != 0";
        returnProductsDescriptionByQuery(query);
    }
    private static void addNewOrder(int userId) throws SQLException, IOException, ClassNotFoundException { // new order method
        ClearScreen();

        Scanner in = new Scanner(System.in);
        System.out.println(INF_MSG_ORDER_CHOICE);
        System.out.println(ENTER_PROD_NAME);
        String productName = in.nextLine();

        if (productName.equals(EXIT)){          //Exit scenarios
            ClearScreen();
          userMainChoice(userId);
        }

        System.out.println(ENTER_PROD_MODEL);
        String productModel = in.nextLine();

        String checkQuery = "SELECT * FROM main.products where product_name = '" + productName + "' and product_description = '" + productModel + "' and product_count > 0;";

        checkProductsDescriptionByQuery(checkQuery, userId);
    }
    private static void addNewServiceRequest(int userId) throws SQLException, IOException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        ClearScreen();

        System.out.println("Please enter <<<Product Name>>>: ");
        String productName = in.nextLine();
        System.out.println("Enter broken <<<Detail Name>>>: ");
        String brokenDetail = in.nextLine();
        System.out.println("Enter additional description: ");
        String description = in.nextLine();

        insertServiceLine(userId, productName, brokenDetail, description);

        System.out.println(ORDER_ACCEPTED);
    }
    private static void seeAllOrders(int userId) throws SQLException, IOException, ClassNotFoundException {
        ClearScreen();

        String query = "SELECT * FROM main.products join main.order ON order.product_id = products.product_id where user_id = " + userId;
        returnOrderDescriptionByQuery(query);
    }
    private static void seeAllServices(int userId) throws SQLException, IOException, ClassNotFoundException {
        ClearScreen();

        String query = "SELECT * FROM main.service where user_id = " + userId;
        returnServicesDescriptionByQuery(query);

    }
    private static void checkProductsDescriptionByQuery(String query, int userId) throws SQLException, ClassNotFoundException, IOException {
        int productId;
        String nameProd;
        String descriptionProd;
        float porPrice;
        int productCount;
        String accept;
        String delivery;
        String description;

        Scanner in = new Scanner(System.in);
        ResultSet rs = connectionToDb().createStatement().executeQuery(query);

        ClearScreen();

        if (rs.next()) {
            System.out.println(INF_MSG_PRODUCT);

            productId = rs.getInt(PROD_PRODUCT_ID);
            nameProd = rs.getString(PROD_PRODUCT_NAME);
            descriptionProd = rs.getString(PROD_PRODUCT_DESCRIPTION);
            porPrice = rs.getFloat(PROD_PRODUCT_PRICE);
            productCount = rs.getInt(PROD_PRODUCT_COUNT);

            System.out.format("Name: %s\t Model: %s\t EUR %s\n", nameProd, descriptionProd, porPrice);


            System.out.print("Accept you choice " + YES + " / "+  NO +": ");
            accept = in.nextLine();

            if (accept.equals(YES)) {

                System.out.println(DELIVERY_CHOICE);
                delivery = in.nextLine();
                System.out.println(ENTER_COMMENT);
                description = in.nextLine();

                insertOrderLine(userId, productId, 1, porPrice, delivery, description);

                productCount = productCount - 1; //after order accept product count should be less for one count

                updateOrderCount(productCount, productId);

                System.out.println(ORDER_ACCEPTED);
            }
        } else {
            System.out.println(WRONG_PRODUCT_CHOICE);
        }
    }
}
