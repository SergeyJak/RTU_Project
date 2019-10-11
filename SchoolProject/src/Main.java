import Actions.UsersActions;
import DataBase.ConnectionToDB;
import Resources.Constants;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, InterruptedException {

        Scanner in = new Scanner(System.in);

        String userLogin;
        String password;

        do {
            Statement connection = ConnectionToDB.connectionToDb().createStatement();

            System.out.print(Constants.ENTER_LOGIN);
            userLogin = in.nextLine();

            if (userLogin.equals(Constants.EXIT))
                break;

            ResultSet userTable = connection.executeQuery("select * from main.user where " + Constants.USER_COLUMN + " = '" + userLogin + "'");

            System.out.println(userTable.getString(Constants.USER_COLUMN).equals(userLogin));

            while (userTable.next())

                //Password verification
                if (userTable.getString(Constants.USER_COLUMN).equals(userLogin)){

                    System.out.print(Constants.PLEASE_ENTER_PASS);
                    password = in.nextLine();
                    if (password.equals(userTable.getString(Constants.PASSWORD_COLUMN))){
                        if (Constants.ADMIN_PERMISSIONS == userTable.getInt(Constants.LEVEL_COLUMN)){
                            System.out.println("You are admin!!!"); // after implementation, should be removed

                            //Insert admin class

                        } else if (Constants.USER_PERMISSIONS == userTable.getInt(Constants.LEVEL_COLUMN)){

                            UsersActions.userMainChoice(userTable.getInt(Constants.USER_ID_COLUMN));

                        }else
                            System.out.println(Constants.UNEXPECTED_USER_LEVEL);
                    }else
                        System.out.println(Constants.WRONG_PASS);

                }else if (userTable.getString(Constants.USER_COLUMN).equals(null)){
                    System.out.println(Constants.TRY_AGAIN);
                }

            connection.close();

        }while (true);

    }
}
