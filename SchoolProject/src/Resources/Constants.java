package Resources;

public class Constants {
    private Constants(){
    }
    //int constants
    public static final int ADMIN_PERMISSIONS = 1;
    public static final int USER_PERMISSIONS = 2;

    public static final String YES = "yes";
    public static final String NO = "no";

    //dialog constants
    public static final String ENTER_LOGIN = "Enter <<<Login>>>: ";
    public static final String ENTER_PROD_NAME = "Enter <<<Product>>>: ";
    public static final String ENTER_PROD_MODEL = "Enter <<<Product Model>>>: ";
    public static final String ENTER_PRODUCT_DESCRIPTION = "Enter <<<Description>>>: ";
    public static final String ENTER_COMMENT = "Enter <<<Comments>>>: ";
    public static final String ENTER_PRODUCT_NAME = "Please enter <<<Product Name>>>: ";
    public static final String ENTER_BROKEN_DETAIL = "Enter broken <<<Detail Name>>>: ";
    public static final String ENTER_PASS = "Enter <<<Password>>>: ";

    public static final String UNEXPECTED_USER_LEVEL = "<<<Level not DETECTED. Please call support!!!>>>";
    public static final String WRONG_PASS = "Your password wrong, try again.";
    public static final String TRY_AGAIN = "Such user not exist. Please try again!";
    public static final String WRONG_CHOICE = "Your choice is unknown";
    public static final String RETURN_TO_LOGIN = "Return to login";
    public static final String DO_CHOICE ="Please choose action: ";
    public static final String DELIVERY_CHOICE = "Choose delivery(shop | mail): ";
    public static final String ORDER_ACCEPTED = "<<< ORDER ACCEPTED >>>";
    public static final String WRONG_PRODUCT_CHOICE = "Wrong Product name or not exist in warehouse";
    static final String WRONG_ORDER_SERVICE_ID ="Id doesn't exist, please try again";

    //Info messages
    public static final String INF_MSG_USER_MAIN = "Products list   -   products\nMake order   -   new\nRequest service   -   service\nSee all your order   -   see\nSee all your services   -   see services\nExit - exit";
    public static final String INF_MSG_ORDER_CHOICE = "Please enter order, or choose \"exit\" to return in main menu:";
    public static final String INF_MSG_PRODUCT = "All information about your product: ";
    public static final String ADMIN_CHOICE = "\nView orders - [vo]\nView services - [vs]\nEdit orders- [eo]\nEdit services- [es]\nSearch- [srch]\nExit -[exit]";
    //actions strings
    public static final String EXIT = "exit";
    public static final String NEW_ORD = "new";
    public static final String NEW_SERV = "service";
    public static final String SEE_ORD = "see";
    public static final String SEE_SERV = "see services";
    public static final String FIND = "find";
    public static final String PRODUCTS = "products";

    //product table
    public static final String PROD_PRODUCT_ID = "product_id";
    public static final String PROD_PRODUCT_NAME = "product_name";
    public static final String PROD_PRODUCT_DESCRIPTION = "product_description";
    public static final String PROD_PRODUCT_PRICE = "product_price";
    public static final String PROD_PRODUCT_COUNT = "product_count";

    //user table
    public static final String USER_ID = "user_id";
	public static final String USER_NAME = "username";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_TIME = "create_time";
    public static final String USER_LEVEL = "user_level";

    //order table
    static final String ORDER_ID = "order_id";
    public static final String ORDER_USER_ID = "user_id";
    public static final String ORDER_PRODUCT_ID = "product_id";
    static final String ORDER_COUNT = "count";
    static final String ORDER_TOTAL = "total_price";
    static final String ORDER_DELIVERY = "delivery";
    public static final String ORDER_DESCRIPTION = "description";
    static final String ORDER_DATE = "date";
    static final String ORDER_STATUS = "status";
    
    //service table
    static final String SERVICE_ID = "service_id";
    public static final String SERVICE_USER_ID = "user_id";
    static final String SERVICE_PR_NAME = "product_name";
    static final String SERVICE_DATE = "date";
    static final String SERVICE_DETAIL = "broken_detail";
    static final String SERVICE_DESCRITPION = "description";
    static final String SERVICE_STATUS = "status";
    static final String SERVICE_PRICE = "price";
    
    
}
