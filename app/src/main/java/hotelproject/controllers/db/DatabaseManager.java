package hotelproject.controllers.db;

import hotelproject.controllers.utils.ConfigManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseManager {
    private final Connection conn;
    public final RoomsDB rdb;
    public final UserDB udb;
    public final BookingsDB bdb;
    public final CustomersDB cdb;

    /**
     * @brief Creates a connection to the database with default login details.
     */
    public DatabaseManager() {
        conn = checkAndGetConnection("app.properties");

        rdb = new RoomsDB(conn);
        udb = new UserDB(conn);
        bdb = new BookingsDB(conn);
        cdb = new CustomersDB(conn);
    }

    /**
     * @brief Check connections to the DB between local vs Gitlab environments.
     */
    public static Connection checkAndGetConnection(String filepath) {
        ConfigManager cm = new ConfigManager(filepath);
        try {
            return DriverManager.getConnection(cm.getPValue("db.url"), cm.getPValue("db.user"), cm.getPValue("db.password"));
        } catch (SQLException e1) {
            try {
                return DriverManager.getConnection(cm.getPValue("db.alias"), cm.getPValue("db.user"), cm.getPValue("db.password"));
            } catch (SQLException e2) {
                System.err.println("Error : " + e2);
                System.err.println("Error : " + e1);
                return null;
            }
        }
    }

    /**
     * @brief Checks if a table exists.
     * @param tableName name of the table whose existence will be verified
     * @param log an ArrayList of strings to store audit logs
     * @return boolean regarding the existence of the input table
     */
    public boolean tableExists(String tableName, ArrayList<String> log) {
        try {
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName, null);
            if (tables.next()) {
                log.add(tableName + " exists.");
                return true;
            }
            log.add(tableName + " doesn't exist.");
            return false;
        } catch (SQLException e) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    /**
     * @brief Creates a table in the provided database connection.
     * @param tableName name of the table which will be created
     * @param log an ArrayList of strings to store audit logs
     * @param body column names for the new table separated by commas
     */
    public void createTable(String tableName, String body, ArrayList<String> log) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format("CREATE TABLE `%s` (%s)", tableName, body));
            log.add(tableName + " is created.");
        } catch (SQLException e) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * @brief Drops a table in the database if it exists.
     * @param tableName name of the table which will be dropped
     * @param log an ArrayList of strings to store audit logs
     */
    public void dropTable(String tableName, ArrayList<String> log) {
        try {
            Statement stmt = conn.createStatement();
            if (stmt.executeUpdate(String.format("DROP TABLE IF EXISTS %s", tableName)) != 0)
                log.add(tableName + " is deleted.");
        } catch (SQLException e) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
