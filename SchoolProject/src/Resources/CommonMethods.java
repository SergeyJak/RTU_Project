package Resources;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import static DataBase.ConnectionToDB.connectionToDb;
import static Resources.Constants.*;

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
            Float porPrice = rs.getFloat(PROD_PRODUCT_PRICE);

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
            String productCount = rs.getString("order.count");
            String totalPrice = rs.getString("order.total_price");
            String productDelivery = rs.getString("order.delivery");
            String status = rs.getString("order.status");
            String productName = rs.getString("products.product_name");
            String productModel = rs.getString("products.product_description");

            System.out.format("ID: %s\t\t Product Model: %s\t %s\t Count: %s\t Price: %s €\t Delivery: %s\t Status: %s\n", orderId, productName, productModel, productCount, totalPrice, productDelivery, status);
        }
    }
    public static void insertServiceLine(int userId, String productName, String brokenDetail, String prodDescription) throws SQLException, IOException, ClassNotFoundException {
        String query = "insert into main.service (user_id, product_name, date, broken_detail, description) values (?, ?, ?, ?, ?)";

        Calendar calendar = Calendar.getInstance();
        Date startDate = new Date(calendar.getTime().getTime());

        PreparedStatement preparedStmt = connectionToDb().prepareStatement(query);
        preparedStmt.setInt(1, userId);
        preparedStmt.setString(2, productName);
        preparedStmt.setDate(3, startDate);
        preparedStmt.setString(4, brokenDetail);
        preparedStmt.setString(5, prodDescription);
        preparedStmt.execute();
    }
    public static void returnServicesDescriptionByQuery(String query) throws SQLException, ClassNotFoundException, IOException {
        ResultSet rs = connectionToDb().createStatement().executeQuery(query);

        while (rs.next()) {
            int orderId = rs.getInt("service_id");
            String productName = rs.getString("product_name");
            Date data = rs.getDate("date");
            String brokenDetail = rs.getString("broken_detail");
            String status = rs.getString("status");
            String servicePrice = rs.getString("price");

            System.out.format("ID: %s\t\t Product Name: %s\t Request Date: %s\t Detail: %s\t Total Price: %s €\t Status: %s\n", orderId, productName, data, brokenDetail, servicePrice, status);
        }
    }
}



