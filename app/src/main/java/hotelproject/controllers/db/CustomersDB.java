package hotelproject.controllers.db;

import hotelproject.controllers.objects.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersDB {

    private final Connection conn;

    public CustomersDB(Connection conn) {
        this.conn = conn;
    }

    /**
     * @brief Identify if the customer already exists in the database.This method will be used by addCustomer() method.
     * @param customer The customer for checking.
     * @return If customer's social security number already exists will return true, otherwise return false.
     */
    public boolean customerExists(Customer customer) {
        try {
            String sql = "SELECT * FROM `customer`";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getInt("c_ss_number") == customer.getC_ss_number()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @brief Add a new customer if the social security number is not duplicated.
     * @param customer The inserted customer object.
     */
    public void addCustomer(Customer customer) {
        try {
            if (customerExists(customer) == false) {
                String sql = "INSERT INTO `customer` (`c_ss_number`, `c_address`, `c_full_name`, `c_phone_num`, `c_email`) VALUES (?,?,?,?,?)";
                PreparedStatement statement = conn.prepareStatement(sql);

                statement.setInt(1, customer.getC_ss_number());
                statement.setString(2, customer.getC_adress());
                statement.setString(3, customer.getC_full_name());
                statement.setInt(4, customer.getC_phone_num());
                statement.setString(5, customer.getC_email());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        /**
         * @brief update customer in the database.
         * @param customer The updated Customer object.
         * @param oldCustomerSSNumber The old customer's social security number that is to be updated.
         */
        public void updateCustomer (Customer customer,int oldCustomerSSNumber){

            try {
                String sql = "UPDATE `customer` SET `c_ss_number` = ?, `c_address` = ?, `c_full_name` = ?, `c_phone_num` = ?, `c_email` = ? WHERE `c_ss_number` = ? ";

                PreparedStatement statement = conn.prepareStatement(sql);

                statement.setInt(1, customer.getC_ss_number());
                statement.setString(2, customer.getC_adress());
                statement.setString(3, customer.getC_full_name());
                statement.setInt(4, customer.getC_phone_num());
                statement.setString(5, customer.getC_email());
                statement.setInt(6, oldCustomerSSNumber);

                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    /**
     * @brief Delete the customer from database.
     * @param customer The customer object for delete.
     */
    public void deleteCustomer (Customer customer) {
            try{
                String sql = "DELETE FROM `customer` WHERE `c_ss_number` = ?";
                int c_ss_number = customer.getC_ss_number();

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1,c_ss_number);

                statement.executeUpdate();

            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }