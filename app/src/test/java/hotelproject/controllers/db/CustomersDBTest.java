package hotelproject.controllers.db;

import hotelproject.controllers.objects.Customer;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING )
public class CustomersDBTest {

    private DatabaseManager dm = new DatabaseManager();
    private Customer customer_1 = new Customer( 72091719,"Sivliden 71","Patrik Wikstrom", 761404361, "patrik.wikstrom@hotmail.com");
    private Customer customer_2 = new Customer( 11111111,"Street 1","James Green", 12345678, "james.green@hotmail.com");
    private Customer customer_3 = new Customer( 22222222,"Street 2","Lisa Green", 87654321, "lisa.green@hotmail.com");

    /**
     * @brief Test customerExists() method.
     * @result Customer_1 should return true; customer_2 and customer_3 should return false.
     */
    @Test
    public void test_001_UserExists() {
        Assert.assertEquals(dm.cdb.customerExists(customer_1),true);
        Assert.assertEquals(dm.cdb.customerExists(customer_2),false);
        Assert.assertEquals(dm.cdb.customerExists(customer_3),false);
    }

    /**
     * @brief Test addCustomer() method.
     * @result After adding customer_2 in the database, the customerExists() method should return true.
     */
    @Test
    public void test_002_AddCustomer() {
        Assert.assertEquals(dm.cdb.customerExists(customer_2),false);
        dm.cdb.addCustomer(customer_2);
        Assert.assertEquals(dm.cdb.customerExists(customer_2),true);
    }

    /**
     * @brief Test updateCustomer() method.
     * @result After updating the customer_2's information to customer_3, customer_2 should be replaced by customer_3's information.the customerExists() method should return true.
     */
    @Test
    public void test_003_UpdateCustomer() {
        dm.cdb.updateCustomer(customer_3,11111111);
        Assert.assertEquals(dm.cdb.customerExists(customer_3),true);
    }



}
