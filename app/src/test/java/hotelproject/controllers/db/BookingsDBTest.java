package hotelproject.controllers.db;

import hotelproject.controllers.objects.Booking;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.util.*;

public class BookingsDBTest {

    private DatabaseManager dbm = new DatabaseManager();

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

    @Test
    public void testGetBookingsForSpecificDay() {
        ArrayList<Integer> bookingsFromTheDay;

        bookingsFromTheDay = dbm.bdb.getBookingsForSpecificDay("2021-04-10");
        Assert.assertEquals(bookingsFromTheDay.size(),1);

        bookingsFromTheDay = dbm.bdb.getBookingsForSpecificDay("2021-05-29");
        Assert.assertEquals(bookingsFromTheDay.size(),2);
    }

    @Test
    public void testGetBookingDetails(){
        Hashtable<String, String> bookingDetails;
        Booking booking_1 = new Booking(1,2,1, Date.valueOf("2021-04-10"), Date.valueOf("2021-04-11"),799,0);
        String[] bookingHeaders = { "b_id", "r_num", "paid_by_card", "b_from", "b_till", "b_fee", "b_is_paid" };
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
