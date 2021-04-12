package hotelproject.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.sql.DataSource;

import hotelproject.controllers.db.DatabaseManagement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class DatabaseManagementTest {

  @Mock
  private DataSource dataSource;

  @Mock
  private Connection conn;

  @Mock
  private DatabaseMetaData databaseMetaData ;

  @Before
  public void setUp() throws Exception {

    conn = mock(Connection.class);
    dataSource = mock(DataSource.class);
    databaseMetaData  = mock(DatabaseMetaData.class);

    given(conn.getMetaData()).willReturn(databaseMetaData);
    //given(dataSource.getConnection()).willReturn(conn);

  }


  @Test
  public void testIsTableExist() throws Exception{

    final String TABLE = "table";

    ResultSet metaDataResultSet  = mock(ResultSet.class);
    given(metaDataResultSet.next()).willReturn(true, false);

    given(databaseMetaData.getTables(null, null, TABLE, null)).willReturn(metaDataResultSet);

    //ResultSet tables = databaseMetaData.getTables(null, null, TABLE, null);
    //assertNotNull("Should be non-null",tables);

    DatabaseManagement databaseManagement = new DatabaseManagement();
    ArrayList<String> log = new ArrayList<>();
    log.add("item");
    //assertNotNull("Should return true", databaseManagement.isTableExist(conn, TABLE, log));
    assertTrue("Should return true",databaseManagement.tableExists(conn, TABLE, log));


  }
}
