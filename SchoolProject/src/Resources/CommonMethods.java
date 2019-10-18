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
            String nameProd = rs.getString("product_name");
            String descriptionProd = rs.getString("product_description");
            Float porPrice = rs.getFloat("product_price");

            // print the results
            System.out.format("Name: %s\t\t Model: %s\t seconds %s\n", nameProd, descriptionProd, porPrice);
        }

    }
}
