package DataBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionToDB {
    public static java.sql.Connection connectionToDb() throws ClassNotFoundException, SQLException, IOException {



        String myDriver = "com.mysql.cj.jdbc.Driver";
        String ip = getProperties("host.ip");
        String port = getProperties("host.port");
        String user = getProperties("db.login");
        String password = getProperties("db.password");
        String dbname = getProperties("db.name");

        String url = "jdbc:mysql://"+ ip + ":" + port +"/" + dbname;


        Class.forName(myDriver);
        return DriverManager.getConnection(url, user, password);

    }
    private static String getProperties(String propertiesName) throws IOException {

        String propertiesString;

        Properties mainProperties = new Properties();

        FileInputStream file;

        String path = "C:\\Users\\sergejsj1\\Git_new\\SchoolProject\\src\\main.properties";
//        String path = "main.properties";

        file = new FileInputStream(path);

        mainProperties.load(file);

        file.close();

        propertiesString = mainProperties.getProperty(propertiesName);

        return propertiesString;
    }
}
