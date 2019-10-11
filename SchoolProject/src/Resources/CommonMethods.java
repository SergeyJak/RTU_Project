package Resources;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DataBase.ConnectionToDB.connectionToDb;

public class CommonMethods {
    public static void ClearScreen(){
        try{
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static void returnProductsDescriptionByQuery(String query) throws SQLException, ClassNotFoundException, IOException {
        ResultSet rs = connectionToDb().createStatement().executeQuery(query);

        while (rs.next()) {
            String firstProd = rs.getString("product_name");
            String descriptionProd = rs.getString("product_description");
            Date dateCreated = rs.getDate("request_time");
            String statusProd = rs.getString("status");

            // print the results
            System.out.format("%s, %s, %s, %s\n", firstProd, descriptionProd, dateCreated, statusProd);
        }

    }
}
