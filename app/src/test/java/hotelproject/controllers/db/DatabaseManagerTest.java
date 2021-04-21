//TODO: This is broken, fix pls

package hotelproject.controllers.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DatabaseManagerTest {


    private DatabaseManager dbm;
    private ArrayList<String> log;
    private String tableName;
    private String body;

    /**
     * Setting up variables need in test methods
     * @see DatabaseManager
     */
    @Before
    public void setUp() {
        dbm = new DatabaseManager();
        log = new ArrayList<>();
        body = "test_column varchar(255)";
    }

    /**
     * Ensures there are no remnants in the database after test session
     * @see DatabaseManager
     */
    @After
    public void tearDown() {
        dbm.dropTable("test_table", log); // test
    }

    /**
     * Tests the 'tableExists()' method, by testing if table 'room_type', known to exist, exists in the SQL database
     * @see DatabaseManager
     */
    @Test
    public void testTableExist() {
        tableName = "room_type"; // Exists
        assertTrue(dbm.tableExists(tableName,log));
    }

    /**
     * Tests 'createTable()' method, by use of the 'tableExists()' method on table 'test_table'
     * @see DatabaseManager
     */
    @Test
    public void testCreateTable() {
        tableName = "test_table";
        dbm.createTable(tableName, body, log); // test
        assertTrue(dbm.tableExists(tableName, log));
        dbm.dropTable(tableName, log); // test
    }

    /**
     * Tests 'dropTable()' method, by use of 'tableExists()' method on table 'test_table'
     * @see DatabaseManager
     */
    @Test
    public void testDropTable() {
        tableName = "test_table";
        dbm.createTable(tableName, body, log);
        dbm.dropTable(tableName, log); // test
        assertFalse(dbm.tableExists(tableName, log));
    }
}
