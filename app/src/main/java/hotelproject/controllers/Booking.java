package hotelproject.controllers;

import java.sql.Date;

public class Booking {
  private Integer id;
  private Integer rum_number;
  private Integer paid_by_card;
  private Date booking_from;
  private Date booking_till;
  private Integer fee;
  private Integer isPaid;

  public Booking() {
  }

  public Booking(Integer id, Integer rum_number, Integer paid_by_card, Date booking_from, Date booking_till, Integer fee, Integer isPaid) {
    this.id = id;
    this.rum_number = rum_number;
    this.paid_by_card = paid_by_card;
    this.booking_from = booking_from;
    this.booking_till = booking_till;
    this.fee = fee;
    this.isPaid = isPaid;
  }

  @Override
  public String toString() {
    return "Booking{" +
            "id=" + id +
            ", rum_number=" + rum_number +
            ", paid_by_card=" + paid_by_card +
            ", booking_from=" + booking_from +
            ", booking_till=" + booking_till +
            ", fee=" + fee +
            ", isPaid=" + isPaid +
            '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getRum_number() {
    return rum_number;
  }

  public void setRum_number(Integer rum_number) {
    this.rum_number = rum_number;
  }

  public Integer getPaid_by_card() {
    return paid_by_card;
  }

  public void setPaid_by_card(Integer paid_by_card) {
    this.paid_by_card = paid_by_card;
  }

  public Date getBooking_from() {
    return booking_from;
  }

  public void setBooking_from(Date booking_from) {
    this.booking_from = booking_from;
  }

  public Date getBooking_till() {
    return booking_till;
  }

  public void setBooking_till(Date booking_till) {
    this.booking_till = booking_till;
  }

  public Integer getFee() {
    return fee;
  }

  public void setFee(Integer fee) {
    this.fee = fee;
  }

  public Integer getIsPaid() {
    return isPaid;
  }

  public void setIsPaid(Integer isPaid) {
    this.isPaid = isPaid;
  }
}