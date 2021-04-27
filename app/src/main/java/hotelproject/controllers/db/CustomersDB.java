package hotelproject.controllers.db;

import hotelproject.controllers.objects.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomersDB {

    private final Connection conn;
    public CustomersDB (Connection conn) {
        this.conn = conn;
    }

    /**
     * @brief update customer in the database
     * @param customer The updated Customer object
     * @param oldCustomerSSNumber The old customer's social security number that is to be updated
     */
    public void updateCustomer(Customer customer, int oldCustomerSSNumber) {

        try {
            String sql = "UPDATE `customer` SET `c_ss_number` = ?, `c_address` = ?, `c_full_name` = ?, `c_phone_num` = ?, `c_email` = ? WHERE `c_ss_number` = ? ";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1,customer.getC_ss_number());
            statement.setString(2,customer.getC_adress());
            statement.setString(3,customer.getC_full_name());
            statement.setInt(4,customer.getC_phone_num());
            statement.setString(5,customer.getC_email());
            statement.setInt(6,oldCustomerSSNumber);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
