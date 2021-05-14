package hotelproject.controllers.db;

import hotelproject.controllers.objects.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

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

    public void deleteRoom(Room room) {
        rooms.remove(room);
        dbm.rdb.deleteRoom(room);
    }

    public void updateRoom(Room room, int oldRNum) {
        for (Room r : rooms) {
            if (oldRNum == r.getR_num()) {
                int index = rooms.indexOf(r);
                rooms.set(index, room);
                dbm.rdb.updateRoom(room, oldRNum);
                break;
            }
        }
    }

    public ArrayList<Integer> availableRooms(Date checkIn, Date checkOut) {

        ArrayList<Integer> allRooms = dbm.rdb.findAllRoomNums();
        ArrayList<Integer> allBookedRooms = dbm.rdb.findAllBookedRooms(checkIn, checkOut);

        ArrayList<Integer> union = new ArrayList<Integer>(allRooms);
        union.addAll(allBookedRooms);

        ArrayList<Integer> intersection = new ArrayList<Integer>(allRooms);

        intersection.retainAll(allBookedRooms);
        union.removeAll(intersection);

        // return the union after intersection
        return union;
    }

    public Hashtable<String, String> viewDetails(Room room) {
        return dbm.rdb.viewRoomDetails(room);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        dbm.cdb.addCustomer(customer);
    }

    public void updateCustomer(Customer customer, int oldCSSNum) {
        for (Customer c : customers) {
            if (oldCSSNum == c.getC_ss_number()) {
                int index = customers.indexOf(c);
                customers.set(index, customer);
                dbm.cdb.updateCustomer(customer, oldCSSNum);
                break;
            }
        }
    }

    public void addUser(User user) {
        users.add(user);
        dbm.udb.addUser(user);
    }

    public void updateUserInformation(User user, String oldUName) throws SQLException {
        for (User u : users) {
            if (oldUName.equals(u.getU_name())) {
                int index = users.indexOf(u);
                users.set(index, user);
                dbm.udb.updateUserInformation(user, oldUName);
                break;
            }
        }
    }

    public int getBookingAutoID() {
        Booking lastBID = bookings.get(bookings.size() - 1);
        return (lastBID.getB_id() + 1);
    }

    public void addBooking(Booking newBooking) {
        bookings.add(newBooking); 
        dbm.bdb.addBooking(newBooking);
    }

    public void updateBooking(Booking updateBooking) {
        for(Booking b : bookings) {
            if (b.getB_id() == updateBooking.getB_id()) {
                int index = bookings.indexOf(b);
                bookings.set(index, updateBooking);
                dbm.bdb.updateBooking(updateBooking);
                break;
            }
        }
    }
}
