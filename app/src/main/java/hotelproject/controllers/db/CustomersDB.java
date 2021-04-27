package hotelproject.controllers.db;

import java.sql.Connection;

public class CustomersDB {

    private final Connection conn;
    public CustomersDB (Connection conn) {
        this.conn = conn;
    }


}
