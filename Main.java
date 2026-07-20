import java.io.*;
import java.util.*;

class Room {
    private int roomNumber;
    private String category;
    private double price;
    private boolean available;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void displayRoom() {
        System.out.println("--------------------------------");
        System.out.println("Room Number : " + roomNumber);
        System.out.println("Category    : " + category);
        System.out.println("Price       : ₹" + price);
        System.out.println("Status      : " + (available ? "Available" : "Booked"));
    }
}

class Booking {
    private String customerName;
    private int roomNumber;

    public Booking(String customerName, int roomNumber) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String toString() {
        return customerName + "," + roomNumber;
    }
}

class Hotel {

    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Booking> bookings = new ArrayList<>();

    public Hotel() {

        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(301, "Suite", 5000));

        loadBookings();
    }

    public void searchRooms() {

        System.out.println("\n===== Available Rooms =====");

        boolean found = false;

        for (Room room : rooms) {
            if (room.isAvailable()) {
                room.displayRoom();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No Rooms Available.");
        }
    }

    public void bookRoom(Scanner sc) {

        System.out.print("Enter Customer Name : ");
        String name = sc.nextLine();

        System.out.print("Enter Room Number : ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        for (Room room : rooms) {

            if (room.getRoomNumber() == roomNo && room.isAvailable()) {

                System.out.println("Room Price : ₹" + room.getPrice());
                System.out.println("Payment Successful...");

                room.setAvailable(false);

                Booking booking = new Booking(name, roomNo);

                bookings.add(booking);

                saveBooking(booking);

                System.out.println("Room Booked Successfully.");

                return;
            }
        }

        System.out.println("Room Not Available.");
    }

    public void cancelBooking(Scanner sc) {

        System.out.print("Enter Room Number : ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        Booking removeBooking = null;

        for (Booking booking : bookings) {

            if (booking.getRoomNumber() == roomNo) {

                removeBooking = booking;
                break;
            }
        }

        if (removeBooking != null) {

            bookings.remove(removeBooking);

            for (Room room : rooms) {
                if (room.getRoomNumber() == roomNo) {
                    room.setAvailable(true);
                }
            }

            rewriteFile();

            System.out.println("Booking Cancelled Successfully.");
        } else {

            System.out.println("Booking Not Found.");
        }
    }

    public void viewBookings() {

        if (bookings.isEmpty()) {
            System.out.println("No Booking Found.");
            return;
        }

        System.out.println("\n===== Booking Details =====");

        for (Booking booking : bookings) {

            System.out.println("Customer Name : " + booking.getCustomerName());
            System.out.println("Room Number   : " + booking.getRoomNumber());
            System.out.println("----------------------------");
        }
    }

    private void saveBooking(Booking booking) {

        try {

            FileWriter fw = new FileWriter("bookings.txt", true);

            fw.write(booking.toString() + "\n");

            fw.close();

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    private void loadBookings() {

        File file = new File("bookings.txt");

        if (!file.exists())
            return;

        try {

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                String[] data = line.split(",");

                String name = data[0];

                int room = Integer.parseInt(data[1]);

                bookings.add(new Booking(name, room));

                for (Room r : rooms) {

                    if (r.getRoomNumber() == room) {
                        r.setAvailable(false);
                    }
                }
            }

            fileScanner.close();

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    private void rewriteFile() {

        try {

            FileWriter fw = new FileWriter("bookings.txt");

            for (Booking booking : bookings) {

                fw.write(booking.toString() + "\n");
            }

            fw.close();

        } catch (Exception e) {

            System.out.println(e);
        }
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Hotel hotel = new Hotel();

        while (true) {

            System.out.println("\n========= HOTEL RESERVATION SYSTEM =========");
            System.out.println("1. Search Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");

            System.out.print("Enter Your Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    hotel.searchRooms();
                    break;

                case 2:
                    hotel.bookRoom(sc);
                    break;

                case 3:
                    hotel.cancelBooking(sc);
                    break;

                case 4:
                    hotel.viewBookings();
                    break;

                case 5:
                    System.out.println("Thank You!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }
}
