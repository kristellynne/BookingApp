public class Hotel {
    private String name;
    private int availableRooms;
    private double pricePerNight;

    public Hotel(String name, int availableRooms, double pricePerNight) {
        this.name = name;
        this.availableRooms = availableRooms;
        this.pricePerNight = pricePerNight;
    }

    public boolean bookRoom(int rooms) {
        if (rooms <= availableRooms && rooms > 0) {
            availableRooms -= rooms;
            return true;
        }
        return false;
    }

    public double calculateTotal(int rooms) {
        return pricePerNight * rooms;
    }

    // Getters
    public String getName() { return name; }
    public int getAvailableRooms() { return availableRooms; }
    public double getPricePerNight() { return pricePerNight; }
}
