//TODO: This is broken, fix pls

package hotelproject.controllers.db;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;


public class DatabaseManagerTest {


    private DatabaseManager dbm;
    private ArrayList<String> log;
    private String tableName;

    @Before
    public void setUp()  {
        dbm = new DatabaseManager();
        log = new ArrayList<>();
        tableName = "room_type";
    }

    @Test
    public void testIsTableExist() throws Exception {
        assertTrue(dbm.tableExists(tableName,log));
    }
}
