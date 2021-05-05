package hotelproject.controllers.db;

import hotelproject.controllers.objects.Booking;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;


@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class BookingsDBTest {

    private final DatabaseManager dbm = new DatabaseManager();

    /**
     * @brief Test findAllBookings() method.
     * @result The method should be able to retrieve all bookings in the database and the result should be 10.
     */
    @Test
    public void test_001_findAllBookings() {
        ArrayList<Booking> allBookings;
        allBookings = dbm.bdb.findAllBookings();
        Assert.assertEquals(allBookings.size(), 10);
        int i = 1;
        int count = 0;
        for (Booking booking : allBookings) {
            if (booking.getB_id() == i) {
                count++;
            }
            i++;
        }
        Assert.assertEquals(count, 10);
    }

    /**
     * @brief Test getBookingsForSpecificDay(String date) method.
     * @result Use two different dates to test this method. The first date should return one record. The second date should return two records.
     */
    @Test
    public void test_002_getBookingsForSpecificDay() {
        ArrayList<Integer> bookingsFromTheDay;

        bookingsFromTheDay = dbm.bdb.getBookingsForSpecificDay("2021-05-29");
        Assert.assertEquals(bookingsFromTheDay.size(), 2);
    }

//    /**
//     * @brief Test getBookingDetails(int b_id) method, using "b_id = 1" record to test.
//     * @result TrueCount should equal to 7 in this test which means all the key-value pairs are corrected.
//     */
//    @Test
//    public void test_003_getBookingDetails() {
//        Hashtable<String, String> bookingDetails;
//        bookingDetails = dbm.bdb.getBookingDetails(1);
//        Set<String> set1 = bookingDetails.keySet();
//        int trueCount = 0;
//        boolean flag = false;
//        for (String o : set1) {
//            String value = bookingDetails.get(o);
//
//            switch (o) {
//                case "b_id":
//                    if (value.equals("1")) {
//                        trueCount++;
//                    }
//                case "r_num":
//                    if (value.equals("2")) {
//                        trueCount++;
//                    }
//                    break;
//                case "paid_by_card":
//                    if (value.equals("1")) {
//                        trueCount++;
//                    }
//                    break;
//                case "b_from":
//                    if (value.equals("2021-04-10")) {
//                        trueCount++;
//                    }
//                    break;
//                case "b_till":
//                    if (value.equals("2021-04-11")) {
//                        trueCount++;
//                    }
//                    break;
//                case "b_fee":
//                    if (value.equals("799")) {
//                        trueCount++;
//                    }
//                    break;
//                case "b_is_paid":
//                    if (value.equals("0")) {
//                        trueCount++;
//                    }
//                    break;
//            }
//        }
//        if (trueCount == 7) {
//            flag = true;
//        }
//        assertTrue(flag);
//    }

}
