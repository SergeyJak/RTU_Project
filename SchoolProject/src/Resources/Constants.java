package Resources;

public class Constants {
    private Constants(){
    }
    //int constants
    public static final int ADMIN_PERMISSIONS = 1;
    public static final int USER_PERMISSIONS = 2;

    public static final String YES = "yes";
    public static final String NO = "no";


    //db constants
    public static final String USER_COLUMN = "username";
    public static final String PASSWORD_COLUMN = "password";
    public static final String LEVEL_COLUMN = "user_level";
    public static final String USER_ID_COLUMN = "user_id";

    //dialog constants
    public static final String ENTER_LOGIN = "Enter login: ";
    public static final String ENTER_PROD_NAME = "Enter Product: ";
    public static final String ENTER_PROD_MODEL = "Enter Product Model: ";

    public static final String PLEASE_ENTER_PASS = "Password please: ";
    public static final String UNEXPECTED_USER_LEVEL = "<<<Level not DETECTED. Please call support!!!>>>";
    public static final String WRONG_PASS = "Your password wrong, try again.";
    public static final String TRY_AGAIN = "Such user not exist. Please try again!";
    public static final String WRONG_CHOICE = "Your choice is unknown";
    public static final String ENTER_PRODUCT_NAME = "Enter product name: ";
    public static final String ENTER_PRODUCT_DESCRIPTION = "Enter description: ";
    public static final String RETURN_TO_LOGIN = "Return to login";
    public static final String DO_CHOICE ="Please choose action: ";
    public static final String DELIVERY_CHOICE = "Choose delivery(shop | mail): ";
    public static final String ENTER_COMMENT = "Enter Comments: ";
    public static final String ORDER_ACCEPTED = "<<< ORDER ACCEPTED >>>";
    public static final String WRONG_PRODUCT_CHOICE = "Wrong Product name or not exist in warehouse";

    //Info messages
    public static final String INF_MSG_USER_MAIN = "Products list   -   products\nMake order   -   new order\nExit - exit";
    public static final String INF_MSG_ORDER_CHOICE = "Please enter order, or choose \"exit\" to return in main menu:";
    public static final String INF_MSG_PRODUCT = "All information about your product: ";

    //actions strings
    public static final String EXIT = "exit";
    public static final String NEW_ORD = "new order";
    public static final String NEW_SERV = "new services";
    public static final String SEE_ORD = "see orders";
    public static final String SEE_SERV = "see services";
    public static final String FIND = "find";
    public static final String PRODUCTS = "products";

    //table columns
    public static final String PROD_PRODUCT_ID = "product_id";
    public static final String PROD_PRODUCT_NAME = "product_name";
    public static final String PROD_PRODUCT_DESCRIPTION = "product_description";
    public static final String PROD_PRODUCT_PRICE = "product_price";
    public static final String PROD_PRODUCT_COUNT = "product_count";



}
