package hotelproject.controllers.db;

import hotelproject.controllers.objects.*;

import java.util.ArrayList;

public class DBData {
    public final DatabaseManager dbm;
    private final ArrayList<Room> rooms;
    private final ArrayList<RoomType> roomTypes;
    private final ArrayList<Booking> bookings;
    private final ArrayList<User> users;
    private final ArrayList<Customer> customers;  // Will be integrated later

    public DBData (DatabaseManager dbm, ArrayList<Room> rooms, ArrayList<RoomType> roomTypes, ArrayList<Booking> bookings,
                   ArrayList<User> users) {
        this.dbm = dbm;
        this.rooms = rooms;
        this.roomTypes = roomTypes;
        this.bookings = bookings;
        this.users = users;
        this.customers = null;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<RoomType> getRoomTypes() {
        return roomTypes;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
