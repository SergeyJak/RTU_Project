package Resources;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DataBase.ConnectionToDB.connectionToDb;
import static Resources.Constants.PROD_PRODUCT_DESCRIPTION;
import static Resources.Constants.PROD_PRODUCT_NAME;

public class CommonMethods {
    public static void ClearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void returnProductsDescriptionByQuery(String query) throws SQLException, ClassNotFoundException, IOException {
        ResultSet rs = connectionToDb().createStatement().executeQuery(query);

        while (rs.next()) {
            String nameProd = rs.getString(PROD_PRODUCT_NAME);
            String descriptionProd = rs.getString(PROD_PRODUCT_DESCRIPTION);
            Float porPrice = rs.getFloat("product_price");

            // print the results
            System.out.format("Name: %s\t Model: %s\t EUR %s\n", nameProd, descriptionProd, porPrice);
        }

    }

    public static void insertOrderLine(int userId, int productId, int productCount, float totalPrice, String prodDelivery, String prodDescription) throws SQLException, IOException, ClassNotFoundException {
        String query = "insert into main.order (user_id, product_id, count, total_price, delivery, description) values (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = connectionToDb().prepareStatement(query);
        preparedStmt.setInt(1, userId);
        preparedStmt.setInt(2, productId);
        preparedStmt.setInt(3, productCount);
        preparedStmt.setFloat(4, totalPrice);
        preparedStmt.setString(5, prodDelivery);
        preparedStmt.setString(6, prodDescription);
        preparedStmt.execute();
    }
    public static void updateOrderCount(int productCount, int productId) throws SQLException, IOException, ClassNotFoundException {
        String query = "UPDATE main.products SET product_count = " + productCount + " WHERE product_id = " + productId + ";";

        connectionToDb().prepareStatement(query).execute();
    }
    public static void returnOrderDescriptionByQuery(String query) throws SQLException, ClassNotFoundException, IOException {
        ResultSet rs = connectionToDb().createStatement().executeQuery(query);

        while (rs.next()) {
            int orderId = rs.getInt("order.order_id");
            int productId = rs.getInt("order.user_id");
            String productCount = rs.getString("order.count");
            String totalPrice = rs.getString("order.total_price");
            String productDelivery = rs.getString("order.delivery");
            String status = rs.getString("order.status");
            String productName = rs.getString("products.product_name");
            String productModel = rs.getString("products.product_description");

            System.out.format("ID: %s\t\t Product Model: %s\t %s\t Count: %s\t EUR: %s\t Delivery: %s\t Status: %s\n", orderId, productName, productModel, productCount, totalPrice, productDelivery, status);
        }
    }
}



