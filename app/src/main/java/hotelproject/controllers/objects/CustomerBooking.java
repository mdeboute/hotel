package hotelproject.controllers.objects;

public class CustomerBooking {
    private int customer_ss_number;
    private int booking_id;

    public CustomerBooking(int customer_ss_number, int booking_id) {
        this.customer_ss_number = customer_ss_number;
        this.booking_id = booking_id;
    }

    public int getCustomer_ss_number() {
        return customer_ss_number;
    }

    public void setCustomer_ss_number(int customer_ss_number) {
        this.customer_ss_number = customer_ss_number;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    @Override
    public String toString() {
        return "CustomerBooking{" +
                "customer_ss_number=" + customer_ss_number +
                ", booking_id=" + booking_id +
                '}';
    }
}
