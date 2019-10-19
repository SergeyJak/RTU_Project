import Actions.LoginActions;
import Actions.UsersActions;
import Actions.UsersActionsOld;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        LoginActions.loginToPortal();
//        UsersActions.addNewOrder(2);

    }
}
