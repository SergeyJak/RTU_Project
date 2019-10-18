package Actions;

import DataBase.ConnectionToDB;
import Resources.Constants;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static Resources.CommonMethods.ClearScreen;

public class LoginActions {
    public static void loginToPortal() throws SQLException, IOException, ClassNotFoundException {
        String userLogin;

        Scanner in = new Scanner(System.in);

        Statement connection = ConnectionToDB.connectionToDb().createStatement();

        do {

            System.out.print(Constants.ENTER_LOGIN);
            userLogin = in.nextLine();

            if (userLogin.equals(Constants.EXIT))
                break;

            ResultSet userTable = connection.executeQuery("select * from main.user where " + Constants.USER_COLUMN + " = '" + userLogin + "'");


            passwordVerification(userTable, userLogin, in);


        }while (true);

    }
    private static void passwordVerification(ResultSet userTable, String userLogin, Scanner in) throws SQLException, IOException, ClassNotFoundException {
        String password;

        while (userTable.next())

            //Password verification
            if (userTable.getString(Constants.USER_COLUMN).equals(userLogin)){

                System.out.print(Constants.PLEASE_ENTER_PASS);
                password = in.nextLine();
                if (password.equals(userTable.getString(Constants.PASSWORD_COLUMN))){
                    if (Constants.ADMIN_PERMISSIONS == userTable.getInt(Constants.LEVEL_COLUMN)){

                        AdminActions.adminChoice();

                    } else if (Constants.USER_PERMISSIONS == userTable.getInt(Constants.LEVEL_COLUMN)){

                        UsersActions.userMainChoice(userTable.getInt(Constants.USER_ID_COLUMN));

                    }else
                        System.out.println(Constants.UNEXPECTED_USER_LEVEL);
                }else
                    System.out.println(Constants.WRONG_PASS);

            }else
                System.out.println(Constants.TRY_AGAIN); //Bug!!! Can't call try again message

        ClearScreen();

    }
}
