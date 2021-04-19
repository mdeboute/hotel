//TODO: This is broken, fix pls

package hotelproject.controllers.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;


public class DatabaseManagerTest {


    private DatabaseManager dbm;
    private ArrayList<String> log;
    private String tableName;
    private String body;

    @Before
    public void setUp()  {
        dbm = new DatabaseManager();
        log = new ArrayList<>();
        body = "test_column varchar(255)";
    }

    @Test
    public void testTableExist() throws Exception {
        tableName = "room_type"; // Exists
        assertTrue(dbm.tableExists(tableName,log));
    }

    @Test
    public void testCreateTable() {
        tableName = "test_table";
        dbm.createTable(tableName, body, log); // test
        assertTrue(dbm.tableExists(tableName, log));
    }

    @Test
    public void testDropTable() {
        tableName = "test_table";
        dbm.createTable(tableName, body, log);
        dbm.dropTable(tableName, log); // test
        assertFalse(dbm.tableExists(tableName, log));
    }
}
