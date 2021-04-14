package hotelproject.controllers.db;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class DatabaseManagementTest {

    @Mock
    private Connection conn;

    @Mock
    private DatabaseMetaData databaseMetaData;

    @Before
    public void setUp() throws Exception {

        conn = mock(Connection.class);
        databaseMetaData = mock(DatabaseMetaData.class);

        given(conn.getMetaData()).willReturn(databaseMetaData);

    }


    @Test
    public void testIsTableExist() throws Exception {

        final String TABLE = "table";

        ResultSet metaDataResultSet = mock(ResultSet.class);
        given(metaDataResultSet.next()).willReturn(true, false);

        given(databaseMetaData.getTables(null, null, TABLE, null)).willReturn(metaDataResultSet);

        ArrayList<String> log = new ArrayList<>();
        log.add("item");
        assertTrue("Should return true", DatabaseManagement.tableExists(conn, TABLE, log));


    }
}
