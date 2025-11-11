import javax.swing.*;
import java.util.ArrayList;

public class HotelBookingApp {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Hotel> rooms = new ArrayList<>();

        // Hotel Transylvania Room Options
        rooms.add(new Hotel("Standard Queen", "One queen bed, ideal for 1-2 adults.", 2000, 10));
        rooms.add(new Hotel("Standard Twin", "Two single beds, perfect for small groups.", 2500, 8));
        rooms.add(new Hotel("Standard Double", "One double bed.", 2800, 6));
        rooms.add(new Hotel("Deluxe Room", "Larger room with one queen and one single bed (up to 3 adults).", 3100, 5));
        rooms.add(new Hotel("Family Room", "One single and two queen beds (great for families or groups).", 3700, 4));

        // === MAIN MENU LOOP ===
        while (true) {
            String[] options = {"Register", "Login", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Welcome to Hotel Transylvania Booking System",
                    "Main Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            if (choice == 0) { // Register
                register(users);
            } else if (choice == 1) { // Login
                User loggedUser = login(users);
                if (loggedUser != null) {
                    userMenu(loggedUser, rooms);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Thank you for visiting Hotel Transylvania!");
                break;
            }
        }
    }

    // ===== REGISTER =====
    private static void register(ArrayList<User> users) {
        String username = JOptionPane.showInputDialog("Enter new username:");
        if (username == null || username.trim().isEmpty()) return;

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                JOptionPane.showMessageDialog(null, "Username already exists!");
                return;
            }
        }

        String password = JOptionPane.showInputDialog("Enter password:");
        if (password == null || password.trim().isEmpty()) return;

        users.add(new User(username, password));
        JOptionPane.showMessageDialog(null, "Registration successful!");
    }

    // ===== LOGIN =====
    private static User login(ArrayList<User> users) {
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");
        if (username == null || password == null) return null;

        for (User u : users) {
            if (u.checkLogin(username, password)) {
                JOptionPane.showMessageDialog(null, "Welcome, " + u.getUsername() + "!");
                return u;
            }
        }
        JOptionPane.showMessageDialog(null, "Invalid username or password.");
        return null;
    }

    // ===== USER MENU =====
    private static void userMenu(User user, ArrayList<Hotel> rooms) {
        while (true) {
            String[] userOptions = {"View Rooms", "Book a Room", "Logout"};
            int action = JOptionPane.showOptionDialog(
                    null,
                    "Welcome, " + user.getUsername() + "!\nWhat would you like to do?",
                    "User Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, userOptions, userOptions[0]);

            if (action == 0) {
                viewRooms(rooms);
            } else if (action == 1) {
                bookRoom(rooms);
            } else {
                JOptionPane.showMessageDialog(null, "You have logged out.");
                return;
            }
        }
    }

    // ===== VIEW ROOMS =====
    private static void viewRooms(ArrayList<Hotel> rooms) {
        StringBuilder sb = new StringBuilder("Hotel Transylvania Room Options \n\n");
        sb.append("Standard Rooms:\n----------------------------\n");
        for (Hotel h : rooms) {
            if (h.getRoomType().startsWith("Standard"))
                sb.append("• ").append(h.getRoomInfo()).append("\n");
        }
        sb.append("\nDeluxe and Family Rooms:\n----------------------------\n");
        for (Hotel h : rooms) {
            if (!h.getRoomType().startsWith("Standard"))
                sb.append("• ").append(h.getRoomInfo()).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // ===== BOOK ROOM (Dropdown List) =====
    private static void bookRoom(ArrayList<Hotel> rooms) {
        try {
            // Create dropdown (combo box)
            JComboBox<String> comboBox = new JComboBox<>();
            for (Hotel h : rooms) {
                comboBox.addItem(h.getRoomType() + " (₱" + h.getPrice() + ")");
            }

            int result = JOptionPane.showConfirmDialog(
                    null,
                    comboBox,
                    "Select a room type:",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION) return;

            int selectedIndex = comboBox.getSelectedIndex();
            if (selectedIndex == -1) return;

            Hotel selected = rooms.get(selectedIndex);
            String input = JOptionPane.showInputDialog("How many rooms would you like to book?");
            if (input == null) return;

            int count = Integer.parseInt(input.trim());
            if (selected.bookRoom(count)) {
                double total = selected.calculateTotal(count);
                JOptionPane.showMessageDialog(null,
                        "✅ Booking Successful!\n\nRoom Type: " + selected.getRoomType() +
                        "\nRooms Booked: " + count +
                        "\nTotal Price: ₱" + total +
                        "\n\nThank you for booking with Hotel Transylvania!");
            } else {
                JOptionPane.showMessageDialog(null, "Sorry, not enough rooms available!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
    }
}
