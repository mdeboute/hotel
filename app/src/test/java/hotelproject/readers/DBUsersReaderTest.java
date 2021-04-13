package hotelproject.readers;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DBUsersReaderTest {

  @Mock
  private Connection conn;

  @Mock
  private ResultSet rs;

  @Mock
  private Statement stmt;

  @Before
  public void setUp() throws Exception {

    conn = mock(Connection.class);
    stmt = mock(Statement.class);
    rs = mock(ResultSet.class);

    when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    when(rs.getString(1)).thenReturn("string1");
    when(rs.getString(2)).thenReturn("string2");
    when(rs.getInt(3)).thenReturn(0);
    String query = "SELECT * FROM users";
    given(stmt.executeQuery(query)).willReturn(rs);

    given(conn.createStatement()).willReturn(stmt);

  }


  @Test
  public void testDBAddUser() {

    assertTrue("Should equal true", DBUsersReader.DBAddUser(conn) instanceof ArrayList);
    assertEquals("Should equal true", DBUsersReader.DBAddUser(conn).size(), 0);
  }
}
