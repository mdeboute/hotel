package hotelproject.controllers.objects;

import java.sql.Date;

public class Booking {
    private int b_id;
    private int r_num;
    private int paid_by_card;
    private Date b_from;
    private Date b_till;
    private int b_fee;
    private int b_is_paid;


    public Booking(int b_id, int r_num, int paid_by_card, Date b_from, Date b_till, int b_fee, int b_is_paid) {
        this.b_id = b_id;
        this.r_num = r_num;
        this.paid_by_card = paid_by_card;
        this.b_from = b_from;
        this.b_till = b_till;
        this.b_fee = b_fee;
        this.b_is_paid = b_is_paid;
    }

    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public int getR_num() {
        return r_num;
    }

    public void setR_num(int r_num) {
        this.r_num = r_num;
    }

    public int getPaid_by_card() {
        return paid_by_card;
    }

    public void setPaid_by_card(int paid_by_card) {
        this.paid_by_card = paid_by_card;
    }

    public Date getB_from() {
        return b_from;
    }

    public void setB_from(Date b_from) {
        this.b_from = b_from;
    }

    public Date getB_till() {
        return b_till;
    }

    public void setB_till(Date b_till) {
        this.b_till = b_till;
    }

    public int getB_fee() {
        return b_fee;
    }

    public void setB_fee(int b_fee) {
        this.b_fee = b_fee;
    }

    public int getB_is_paid() {
        return b_is_paid;
    }

    public void setB_is_paid(int b_is_paid) {
        this.b_is_paid = b_is_paid;
    }

    /**
     * Returns attribute information as a String
     */
    @Override
    public String toString() {
        return "Booking{" +
                "b_id=" + b_id +
                ", r_num=" + r_num +
                ", paid_by_card=" + paid_by_card +
                ", b_from=" + b_from +
                ", b_till=" + b_till +
                ", b_fee=" + b_fee +
                ", b_is_paid=" + b_is_paid +
                '}';
    }
}
