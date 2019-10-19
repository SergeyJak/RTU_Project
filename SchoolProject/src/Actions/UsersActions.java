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

        System.out.println(INFORMATION_MSG); //Message should be updated


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
    private static void listProducts() throws SQLException, IOException, ClassNotFoundException {
        ClearScreen();

        String query = "SELECT * FROM main.products where product_count != 0";
        returnProductsDescriptionByQuery(query);
    }
    private static void addNewOrder(int userId) throws SQLException, IOException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter order, or choose \"exit\" to return in main menu:");
        System.out.println("Enter Product Name: ");
        String productName = in.nextLine();
        System.out.println("Enter Product Model: ");
        String productModel = in.nextLine();

        String checkQuery = "SELECT * FROM main.products where product_name = '" + productName + "' and product_description = '" + productModel + "' and product_count > 0;";


        checkProductsDescriptionByQuery(checkQuery, userId);

    }
    private static void addNewServiceRequest(int userId){

    }
    private static void seeAllOrders(int userId){

    }
    private static void seeAllServices(int userId){

    }
    private static void checkProductsDescriptionByQuery(String query, int userId) throws SQLException, ClassNotFoundException, IOException {
        Scanner in = new Scanner(System.in);
        ResultSet rs = connectionToDb().createStatement().executeQuery(query);

        ClearScreen();

        if (rs.next()) {
            System.out.println("All information about your product: ");

            int productId = rs.getInt(1);
            String nameProd = rs.getString("product_name");
            String descriptionProd = rs.getString("product_description");
            float porPrice = rs.getFloat("product_price");
            int productCount = rs.getInt(5);
            System.out.format("Name: %s\t Model: %s\t EUR %s\n", nameProd, descriptionProd, porPrice);


            System.out.print("Accept you choice yes / no: ");
            String accept = in.nextLine();
            if (accept.equals("yes")) {
                System.out.println("Choose delivery(shop | mail): ");
                String delivery = in.nextLine();
                System.out.println("Comments: ");
                String description = in.nextLine();

                insertOrderLine(userId, productId, 1, porPrice, delivery, description);

                productCount = productCount - 1;

                updateOrderCount(productCount, productId);

                System.out.println("Product added!!!");

            } else {
                System.out.println("Return");
            }

        } else {
            System.out.println("Wrong Product name or not exist in wearhous");
        }

    }
}
