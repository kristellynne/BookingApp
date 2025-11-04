public class Booking {
    private User user;
    private Hotel hotel;
    private int roomsBooked;

    public Booking(User user, Hotel hotel, int roomsBooked) {
        this.user = user;
        this.hotel = hotel;
        this.roomsBooked = roomsBooked;
    }

    public double getTotalPrice() {
        return hotel.calculateTotal(roomsBooked);
    }

    public String getBookingDetails() {
        return "Customer: " + user.getUsername() +
               "\nHotel: " + hotel.getName() +
               "\nRooms Booked: " + roomsBooked +
               "\nTotal Price: ₱" + getTotalPrice();
    }

    public User getUser() { return user; }
}
