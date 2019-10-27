package Resources;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import static DataBase.ConnectionToDB.connectionToDb;
import static Resources.CommonMethods.changeStatusForService;
import static Resources.CommonMethods.isFloat;
import static Resources.Constants.*;
import static Actions.AdminActions.*;

public class CommonMethods {
    private int ID;
    private String USERNAME;
    private String NAME;
    private String STATUS;
    private Date DATA;
    private String BROKEN_DETAIL;
    private float PRICE;
    private String DESCRIPTION;
    private String COUNT;
    private String DELIVERY;
    private String MODEL;

    public static void ClearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void returnProductsDescriptionByQuery(String query) throws SQLException, ClassNotFoundException, IOException {
        CommonMethods com = new CommonMethods();

        ResultSet rs = connectionToDb().createStatement().executeQuery(query);

        while (rs.next()) {
            com.NAME = rs.getString(PROD_PRODUCT_NAME);
            com.DESCRIPTION = rs.getString(PROD_PRODUCT_DESCRIPTION);
            com.PRICE = rs.getFloat(PROD_PRODUCT_PRICE);

            // print the results
            System.out.format("Name: %s\t Model: %s\t EUR %s\n", com.NAME, com.DESCRIPTION, com.PRICE);
        }

    }

    public static void insertOrderLine(String query, int userId, int productId, int productCount, float totalPrice, String prodDelivery, String prodDescription) throws SQLException, IOException, ClassNotFoundException {

        PreparedStatement preparedStmt = connectionToDb().prepareStatement(query);
        preparedStmt.setInt(1, userId);
        preparedStmt.setInt(2, productId);
        preparedStmt.setInt(3, productCount);
        preparedStmt.setFloat(4, totalPrice);
        preparedStmt.setString(5, prodDelivery);
        preparedStmt.setString(6, prodDescription);
        preparedStmt.execute();
    }
    public static void updateOrderCount(String query) throws SQLException, IOException, ClassNotFoundException {

        connectionToDb().prepareStatement(query).execute();
    }
    public static void returnOrderDescriptionByQuery(String query) throws SQLException, ClassNotFoundException, IOException {
        CommonMethods com = new CommonMethods();
        ResultSet rs = connectionToDb().createStatement().executeQuery(query);

        while (rs.next()) {
            com.ID = rs.getInt(ORDER_ID);
            com.COUNT = rs.getString(ORDER_COUNT);
            com.PRICE = rs.getFloat(ORDER_TOTAL);
            com.DELIVERY = rs.getString(ORDER_DELIVERY);
            com.STATUS = rs.getString(ORDER_STATUS);
            com.NAME = rs.getString(PROD_PRODUCT_COUNT);
            com.MODEL = rs.getString(PROD_PRODUCT_DESCRIPTION);

            System.out.format("ID: %s\t\t Product Model: %s\t %s\t Count: %s\t Price: %s €\t Delivery: %s\t Status: %s\n", com.ID, com.NAME, com.MODEL, com.COUNT, com.PRICE, com.DELIVERY, com.STATUS);
        }
    }
    public static void insertServiceLine(String query, int userId, String productName, String brokenDetail, String prodDescription) throws SQLException, IOException, ClassNotFoundException {

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
        CommonMethods com = new CommonMethods();
        ResultSet rs = connectionToDb().createStatement().executeQuery(query);

        System.out.format("%-4s %-16s %-17s %-11s Total € %-8.2s %s\n", "ID", "Product Name", "Request Date", "Detail", "Total Price", "Status");
        System.out.println("------------------------------------------------------------------------------------------");

        while (rs.next()) {
            com.ID = rs.getInt(SERVICE_ID);
            com.NAME = rs.getString(SERVICE_PR_NAME);
            com.DATA = rs.getDate(SERVICE_DATE);
            com.BROKEN_DETAIL = rs.getString(SERVICE_DETAIL);
            com.STATUS = rs.getString(SERVICE_STATUS);
            com.PRICE = rs.getFloat(SERVICE_PRICE);

            System.out.format("%-4s %-16s %-17s %-11s Total € %-8.2s %s\n", com.ID, com.NAME, com.DATA, com.BROKEN_DETAIL, com.PRICE, com.STATUS);
        }
    }
    
    public static void returnServiceListByQuery(String query) throws SQLException, ClassNotFoundException, IOException {
        CommonMethods com = new CommonMethods();
        ResultSet rs = connectionToDb().createStatement().executeQuery(query);
        if (!rs.next()) {
        	System.out.println(WRONG_ORDER_SERVICE_ID);
            editService();
        }
        System.out.format("\n%-4s %-8s %-15s %-10s %-10s %-25s %-6s %-10s","Nr","User","Product","Date","Detail","Description","Amount","Status");
        System.out.println("\n---------------------------------------------------------------------------------------------");
        do{
            com.ID = rs.getInt(SERVICE_ID);
            com.USERNAME = rs.getString(USER_NAME);
            com.NAME = rs.getString(SERVICE_PR_NAME);
            com.DATA = rs.getDate(SERVICE_DATE);
            com.BROKEN_DETAIL = rs.getString(SERVICE_DETAIL);
            com.DESCRIPTION = rs.getString(SERVICE_DESCRITPION);
            com.PRICE = rs.getFloat(SERVICE_PRICE);
            com.STATUS = rs.getString(SERVICE_STATUS);

            System.out.format("%-4s %-8s %-15s %-10s %-10s %-25s %-6.2f %-10s\n",com.ID,com.USERNAME,com.NAME,com.DATA,com.BROKEN_DETAIL,com.DESCRIPTION,com.PRICE,com.STATUS);
        }while (rs.next());
        System.out.println("---------------------------------------------------------------------------------------------");
        rs.close();
    }
 
    public static void changeStatusForService(String service_id,String statusNew,String priceNew) throws SQLException, ClassNotFoundException, IOException {

    	String UpdateQuery = "UPDATE main.service SET status = (" + " \"" + statusNew + "\"" + "), price = (" + " \"" + priceNew + "\"" + ") WHERE service_id = " + service_id + ";";
        connectionToDb().prepareStatement(UpdateQuery).execute();
		System.out.format("Status for order %2s is changed to %s and new price is %s\n",service_id,statusNew,priceNew);
    }
    
    public static void returnOrderListByQuery(String query) throws SQLException, ClassNotFoundException, IOException {
        CommonMethods com = new CommonMethods();
        ResultSet rs = connectionToDb().createStatement().executeQuery(query);
        if (!rs.next()) {
        	System.out.println(WRONG_ORDER_SERVICE_ID);
            editOrder();
        }
        System.out.format("\n%-4s %-8s %-10s %-20s %-5s %-8.2s %-10s %-10s %-10s", "Nr" ,"User", "Product","Description","Count","Total","Delivery","Date","Status");
        System.out.println("\n---------------------------------------------------------------------------------------------------");
        do{
            com.ID = rs.getInt(ORDER_ID);
            com.USERNAME = rs.getString(USER_NAME);
            com.NAME = rs.getString(PROD_PRODUCT_NAME);
            com.DESCRIPTION = rs.getString(PROD_PRODUCT_DESCRIPTION);
            com.COUNT = rs.getString(ORDER_COUNT);
            com.PRICE = rs.getFloat(ORDER_TOTAL);
            com.DELIVERY = rs.getString(ORDER_DELIVERY);
            com.DATA = rs.getDate(ORDER_DATE);
            com.STATUS = rs.getString(ORDER_STATUS);

            System.out.format("%-4s %-8s %-10s %-20s %-5s %-8.2f %-10s %-10s %-10s\n", com.ID,com.USERNAME,com.NAME,com.DESCRIPTION,com.COUNT,com.PRICE,com.DELIVERY,com.DATA,com.STATUS);
        }while (rs.next());
        System.out.println("---------------------------------------------------------------------------------------------------");
        rs.close();
    } 

	public static void changeStatusForOrder(String order_id,String statusNew) throws SQLException, ClassNotFoundException, IOException {
        String query2 = "UPDATE main.order SET status = (" + " \"" + statusNew + "\"" + ") WHERE order_id = " + order_id + ";";
        connectionToDb().prepareStatement(query2).execute();
		System.out.format("Status for order %2s is changed to %s\n",order_id,statusNew);
    }
	
	public static boolean isNumeric(String strNum) {
	    return strNum.matches("\\d+");
	}
	
	public static boolean isFloat(String strFloat) {
	    return strFloat.matches("^(?=.*\\d)\\d*(?:\\.\\d\\d)?$");
	
	}
}


