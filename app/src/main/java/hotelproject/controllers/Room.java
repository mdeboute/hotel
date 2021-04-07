package hotelproject.controllers;

public class Room {
    private int size;
    private int beds;
    private int room_nb;
    private String location;


    public Room() {
    }

    public Room(int size, int beds, int roomNb, String location) {
        this.beds = beds;
        this.size = size;
        this.room_nb = roomNb;
        this.location = location;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getRoom_nb() {
        return room_nb;
    }

    public void setRoom_nb(int room_nb) {
        this.room_nb = room_nb;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Room{" +
                "size=" + size +
                ", beds=" + beds +
                ", roomNb=" + room_nb +
                ", location='" + location + '\'' +
                '}';
    }
}
