package baseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {

    private static final String FILE = "connection.xml";

    public static Connection getConnection() {
        Connection con = null;
        try {
            ConnectionProperties properties = XMLManager.readXML(new ConnectionProperties(), FILE);
            con = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
