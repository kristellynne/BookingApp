import javax.swing.*;
import java.util.ArrayList;

public class HotelBookingApp {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Hotel> hotels = new ArrayList<>();
        ArrayList<Booking> bookings = new ArrayList<>();

        // Sample hotels
        hotels.add(new Hotel("Manila Grand Hotel", 10, 2500));
        hotels.add(new Hotel("Cebu Paradise Resort", 5, 4000));
        hotels.add(new Hotel("Davao City Inn", 8, 1800));

        while (true) {
            String[] options = {"Register", "Login", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Welcome to Hotel Booking System!",
                    "Main Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]
            );

            if (choice == 0) { // Register
                String username = safeInput("Enter username:");
                if (username == null) continue;

                // Check if username exists
                boolean exists = false;
                for (User u : users) {
                    if (u.getUsername().equals(username)) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    JOptionPane.showMessageDialog(null, "Username already exists.");
                    continue;
                }

                String password = safeInput("Enter password:");
                if (password == null) continue;

                users.add(new User(username, password));
                JOptionPane.showMessageDialog(null, "Registration successful!");

            } else if (choice == 1) { // Login
                String username = safeInput("Enter username:");
                if (username == null) continue;
                String password = safeInput("Enter password:");
                if (password == null) continue;

                User currentUser = null;
                for (User u : users) {
                    if (u.getUsername().equals(username) && u.checkPassword(password)) {
                        currentUser = u;
                        break;
                    }
                }

                if (currentUser != null) {
                    currentUser.login();
                    JOptionPane.showMessageDialog(null, "Welcome, " + currentUser.getUsername() + "!");
                    userMenu(currentUser, hotels, bookings);
                    currentUser.logout();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login. Try again or register first.");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Goodbye!");
                break;
            }
        }
    }

    // ===== Helper Methods =====
    private static String safeInput(String message) {
        String s = JOptionPane.showInputDialog(message);
        if (s == null) return null;
        s = s.trim();
        return s.isEmpty() ? null : s;
    }

    private static void userMenu(User user, ArrayList<Hotel> hotels, ArrayList<Booking> bookings) {
        while (true) {
            String[] choices = {"Book Hotel", "View My Bookings", "Logout"};
            int sel = JOptionPane.showOptionDialog(
                    null,
                    "Welcome, " + user.getUsername(),
                    "User Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, choices, choices[0]
            );

            if (sel == 0) {
                bookHotel(user, hotels, bookings);
            } else if (sel == 1) {
                StringBuilder sb = new StringBuilder();
                int count = 0;
                for (Booking b : bookings) {
                    if (b.getUser().getUsername().equals(user.getUsername())) {
                        sb.append("Booking ").append(++count).append(":\n");
                        sb.append(b.getBookingDetails()).append("\n\n");
                    }
                }
                if (count == 0) sb.append("You have no bookings yet.");
                JOptionPane.showMessageDialog(null, sb.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Logged out.");
                break;
            }
        }
    }

    private static void bookHotel(User user, ArrayList<Hotel> hotels, ArrayList<Booking> bookings) {
        try {
            String hotelList = "Available Hotels:\n";
            for (int i = 0; i < hotels.size(); i++) {
                Hotel h = hotels.get(i);
                hotelList += (i + 1) + ". " + h.getName() + " (" + h.getAvailableRooms() +
                        " rooms, ₱" + h.getPricePerNight() + "/night)\n";
            }

            String choiceStr = JOptionPane.showInputDialog(hotelList + "\nChoose hotel (1-" + hotels.size() + "):");
            if (choiceStr == null) return;
            int choice = Integer.parseInt(choiceStr.trim());
            if (choice < 1 || choice > hotels.size()) {
                JOptionPane.showMessageDialog(null, "Invalid choice.");
                return;
            }

            Hotel selectedHotel = hotels.get(choice - 1);
            String roomsStr = JOptionPane.showInputDialog("How many rooms to book?");
            if (roomsStr == null) return;
            int rooms = Integer.parseInt(roomsStr.trim());

            if (selectedHotel.bookRoom(rooms)) {
                Booking booking = new Booking(user, selectedHotel, rooms);
                bookings.add(booking);
                JOptionPane.showMessageDialog(null, "Booking successful!\n\n" + booking.getBookingDetails());
            } else {
                JOptionPane.showMessageDialog(null, "Not enough rooms available.");
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
