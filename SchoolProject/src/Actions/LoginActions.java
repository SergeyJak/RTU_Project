package Actions;

import DataBase.ConnectionToDB;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static Resources.CommonMethods.ClearScreen;
import static Resources.Constants.*;

public class LoginActions {
    public static void loginToPortal() throws SQLException, IOException, ClassNotFoundException {
        String userLogin;

        Scanner in = new Scanner(System.in);

        Statement connection = ConnectionToDB.connectionToDb().createStatement();

        do {

            System.out.print(ENTER_LOGIN);
            userLogin = in.nextLine();

            if (userLogin.equals(EXIT))
                break;

            ResultSet userTable = connection.executeQuery("select * from main.user where " + USER_COLUMN + " = '" + userLogin + "'");


            passwordVerification(userTable, userLogin, in);


        }while (true);

    }
    private static void passwordVerification(ResultSet userTable, String userLogin, Scanner in) throws SQLException, IOException, ClassNotFoundException {
        String password;

        while (userTable.next())

            //Password verification
            if (userTable.getString(USER_COLUMN).equals(userLogin)){

                System.out.print(ENTER_PASS);
                password = in.nextLine();
                if (password.equals(userTable.getString(PASSWORD_COLUMN))){
                    if (ADMIN_PERMISSIONS == userTable.getInt(LEVEL_COLUMN)){

                        AdminActions.adminChoice();
                        AdminActions.adminChoice(userLogin);

                    } else if (USER_PERMISSIONS == userTable.getInt(LEVEL_COLUMN)){

                        UsersActions.userMainChoice(userTable.getInt(USER_ID_COLUMN));

                    }else
                        System.out.println(UNEXPECTED_USER_LEVEL);
                }else
                    System.out.println(WRONG_PASS);

            }else
                System.out.println(TRY_AGAIN); //Bug!!! Can't call try again message

        ClearScreen();

    }
}
