package hotelproject.controllers.db;

import hotelproject.controllers.objects.Booking;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class BookingsDBTest {

    private DatabaseManager dbm = new DatabaseManager();

    /**
     * @brief Test findAllBookings() method.
     * @result The method should be able to retrieve all bookings in the database and the result should be 10.
     */
    @Test
    public void testFindAllBookings(){
        ArrayList<Booking> allBookings;
        allBookings = dbm.bdb.findAllBookings();
        Assert.assertEquals(allBookings.size(),10);
        int i = 1;
        int count = 0;
        for (Booking booking: allBookings) {
            if(booking.getB_id() == i){
                count++;
            }
            i++;
        }
        Assert.assertEquals(count,10);
    }

    /**
     * @brief Test getBookingsForSpecificDay(String date) method.
     * @result Use two different dates to test this method. The first date should return one record. The second date should return two records.
     */
    @Test
    public void testGetBookingsForSpecificDay() {
        ArrayList<Integer> bookingsFromTheDay;

        bookingsFromTheDay = dbm.bdb.getBookingsForSpecificDay("2021-04-10");
        Assert.assertEquals(bookingsFromTheDay.size(),1);

        bookingsFromTheDay = dbm.bdb.getBookingsForSpecificDay("2021-05-29");
        Assert.assertEquals(bookingsFromTheDay.size(),2);
    }

    /**
     * @brief Test getBookingDetails(int b_id) method, using "b_id = 1" record to test.
     * @result TrueCount should equal to 7 in this test which means all the key-value pairs are corrected.
     */
    @Test
    public void testGetBookingDetails(){
        Hashtable<String, String> bookingDetails;
        bookingDetails = dbm.bdb.getBookingDetails(1);
        Set set1 = bookingDetails.keySet();
        int trueCount = 0;
        for(Iterator iter = set1.iterator(); iter.hasNext(); ){
            String key = (String)iter.next();
            String value = (String)bookingDetails.get(key);

            if(key.equals("b_id")) {
                if (value.equals("1")) {
                    trueCount++;
                }
            } else if (key.equals("r_num")) {
                if (value.equals("2")) {
                    trueCount++;
                }
            } else if (key.equals("paid_by_card")) {
                if (value.equals("1")) {
                    trueCount++;
                }
            } else if (key.equals("b_from")){
                if (value.equals("2021-04-10")) {
                    trueCount++;
                }
            } else if (key.equals("b_till")) {
                if (value.equals("2021-04-11")) {
                    trueCount++;
                }
            } else if (key.equals("b_fee")) {
                if (value.equals("799")) {
                    trueCount++;
                }
            } else if (key.equals("b_is_paid")) {
                if (value.equals("0")) {
                    trueCount++;
                }
            }
        }
        Assert.assertEquals(trueCount,7);
    }

}
