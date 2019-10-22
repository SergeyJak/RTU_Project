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
            int orderId = rs.getInt(ORDER_ID);
            String productCount = rs.getString(ORDER_COUNT);
            String totalPrice = rs.getString(ORDER_TOTAL);
            String productDelivery = rs.getString(ORDER_DELIVERY);
            String status = rs.getString(ORDER_STATUS);
            String productName = rs.getString(PROD_PRODUCT_COUNT);
            String productModel = rs.getString(PROD_PRODUCT_DESCRIPTION);

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

        System.out.format("%-4s %-16s %-17s %-11s Total € %-8.2s %s\n", "ID", "Product Name", "Request Date", "Detail", "Total Price", "Status");
        System.out.println("------------------------------------------------------------------------------------------");

        while (rs.next()) {
            int orderId = rs.getInt(SERVICE_ID);
            String productName = rs.getString(SERVICE_PR_NAME);
            Date data = rs.getDate(SERVICE_DATE);
            String brokenDetail = rs.getString(SERVICE_DETAIL);
            String status = rs.getString(SERVICE_STATUS);
            String servicePrice = rs.getString(SERVICE_PRICE);

            System.out.format("%-4s %-16s %-17s %-11s Total € %-8.2s %s\n", orderId, productName, data, brokenDetail, servicePrice, status);
        }
    }
    
    public static void returnServiceListByQuery(String query) throws SQLException, ClassNotFoundException, IOException {
        ResultSet rs = connectionToDb().createStatement().executeQuery(query);

        System.out.println("\nNr   User     Product         Date      Detail      Description               Amount Status");
        System.out.println("------------------------------------------------------------------------------------------");
        while (rs.next()) {
            String sID = rs.getString(SERVICE_ID);
            String uName = rs.getString(USER_NAME);
            String sName = rs.getString(SERVICE_PR_NAME);
            String sDate = rs.getString(SERVICE_DATE);    
            String sDetail = rs.getString(SERVICE_DETAIL);
            String sDescr = rs.getString(SERVICE_DESCRITPION);
            Float sPrice = rs.getFloat(SERVICE_PRICE);
            String sStatus = rs.getString(SERVICE_STATUS);
            
            // print the results
            System.out.format("%-4s %-8s %-15s %-10s %-10s %-25s %-6.2f %-10s\n",sID,uName,sName,sDate,sDetail,sDescr,sPrice,sStatus);
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }
    
    public static void returnOrderListByQuery(String query) throws SQLException, ClassNotFoundException, IOException {
        ResultSet rs = connectionToDb().createStatement().executeQuery(query);
        
		System.out.println("\nNr   User    Product    Description          Count  Total    Delivery   Date      Status");
        System.out.println("------------------------------------------------------------------------------------------");
        while (rs.next()) {
            String oID = rs.getString(ORDER_ID);
            String uName = rs.getString(USER_NAME);
            String pName = rs.getString(PROD_PRODUCT_NAME);
            String pDescr = rs.getString(PROD_PRODUCT_DESCRIPTION);
            String oCount = rs.getString(ORDER_COUNT);
            Float oTotal = rs.getFloat(ORDER_TOTAL);
            String oDelivery = rs.getString(ORDER_DELIVERY);
            String oDate = rs.getString(ORDER_DATE);        
            String oStatus = rs.getString(ORDER_STATUS);    
            
            // print the results
            System.out.format("%-4s %-8s %-10s %-20s %-5s %-8.2f %-10s %-10s %-10s\n", oID,uName,pName,pDescr,oCount,oTotal,oDelivery,oDate,oStatus);
        }
    } 
    
    public static void changeStatusForOrder(int order_id,String statusNew) throws SQLException, ClassNotFoundException, IOException {
        String query = "UPDATE main.order SET status = " + statusNew + " WHERE order_id = " + order_id + ";";
        connectionToDb().prepareStatement(query).execute();
        
    }
}


