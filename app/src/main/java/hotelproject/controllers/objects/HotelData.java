package hotelproject.controllers.objects;

import hotelproject.controllers.db.DatabaseManager;
import hotelproject.controllers.objects.*;

import java.util.ArrayList;

public class HotelData {
    public final DatabaseManager dbm;
    private final ArrayList<Room> rooms;
    private final ArrayList<RoomType> roomTypes;
    private final ArrayList<Booking> bookings;
    private final ArrayList<User> users;
    private final ArrayList<Customer> customers;  // Will be integrated later

    public HotelData() {
        dbm = new DatabaseManager();
        rooms = dbm.rdb.findAllRooms();
        roomTypes = dbm.rdb.findAllRoomTypes();
        bookings = dbm.bdb.findAllBookings();
        users = dbm.udb.getAllUsers();
        customers = dbm.cdb.findAllCustomers();
    }

    public HotelData(DatabaseManager dbm, ArrayList<Room> rooms, ArrayList<RoomType> roomTypes, ArrayList<Booking> bookings,
                     ArrayList<User> users, ArrayList<Customer> customers) {
        this.dbm = dbm;
        this.rooms = rooms;
        this.roomTypes = roomTypes;
        this.bookings = bookings;
        this.users = users;
        this.customers = customers;
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

    public void addRoom(Room room) {
        rooms.add(room);
        dbm.rdb.addRoom(room);
    }
}
