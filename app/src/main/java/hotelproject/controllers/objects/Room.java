package hotelproject.controllers.objects;

public class Room {
    private int r_num;
    private int r_floor;
    private String r_type;
    private int booked;

    public Room() {
    }

    public Room(int r_num, int r_floor, String r_type, int booked) {
        this.r_num = r_num;
        this.r_floor = r_floor;
        this.r_type = r_type;
        this.booked = booked;
    }

    public int getR_num() {
        return r_num;
    }

    public void setR_num(int r_num) {
        this.r_num = r_num;
    }

    public int getR_floor() {
        return r_floor;
    }

    public void setR_floor(int r_floor) {
        this.r_floor = r_floor;
    }

    public String getR_type() {
        return r_type;
    }

    public void setR_type(String r_type) {
        this.r_type = r_type;
    }

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

    /**
     * @brief Returns attribute information as a String
     * @return attribute information as a String
     */
    @Override
    public String toString() {
        return "Room{" +
                "r_num=" + r_num +
                ", r_floor=" + r_floor +
                ", r_type=" + r_type +
                ", booked=" + booked +
                '}';
    }
}
