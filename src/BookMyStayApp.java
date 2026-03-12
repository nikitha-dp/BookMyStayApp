import java.util.ArrayList;
import java.util.HashMap;

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Book My Stay App Started\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Deluxe Room", 2);

        // Create room objects
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom(inventory));
        rooms.add(new DoubleRoom(inventory));
        rooms.add(new DeluxeRoom(inventory));

        // Guest searches rooms
        SearchService search = new SearchService();
        search.displayAvailableRooms(rooms);

        // Booking request queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Guests submit booking requests
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Anita", "Double Room"));
        queue.addRequest(new Reservation("Karan", "Deluxe Room"));

        // Show queued requests
        queue.displayRequests();

        System.out.println("\nRequests waiting for allocation...");
        System.out.println("Application Closed.");
    }
}

/* ---------------- INVENTORY ---------------- */

class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

/* ---------------- ABSTRACT ROOM ---------------- */

abstract class Room {

    String roomType;
    double price;
    RoomInventory inventory;

    Room(String roomType, double price, RoomInventory inventory) {
        this.roomType = roomType;
        this.price = price;
        this.inventory = inventory;
    }

    int getAvailableRooms() {
        return inventory.getAvailability(roomType);
    }

    abstract void displayRoomDetails();
}

/* ---------------- ROOM TYPES ---------------- */

class SingleRoom extends Room {

    SingleRoom(RoomInventory inventory) {
        super("Single Room", 1500, inventory);
    }

    void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: ₹" + price);
        System.out.println("Available Rooms: " + getAvailableRooms());
        System.out.println();
    }
}

class DoubleRoom extends Room {

    DoubleRoom(RoomInventory inventory) {
        super("Double Room", 2500, inventory);
    }

    void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: ₹" + price);
        System.out.println("Available Rooms: " + getAvailableRooms());
        System.out.println();
    }
}

class DeluxeRoom extends Room {

    DeluxeRoom(RoomInventory inventory) {
        super("Deluxe Room", 4000, inventory);
    }

    void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: ₹" + price);
        System.out.println("Available Rooms: " + getAvailableRooms());
        System.out.println();
    }
}

/* ---------------- SEARCH SERVICE ---------------- */

class SearchService {

    void displayAvailableRooms(ArrayList<Room> rooms) {

        System.out.println("Available Room Types\n");

        for (Room room : rooms) {
            if (room.getAvailableRooms() > 0) {
                room.displayRoomDetails();
            }
        }
    }
}

/* ---------------- RESERVATION ---------------- */

class Reservation {

    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    void display() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

/* ---------------- BOOKING QUEUE ---------------- */

class BookingRequestQueue {

    private LinkedList<Reservation> queue = new LinkedList<>();

    void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request added for " + reservation.guestName);
    }

    void displayRequests() {

        System.out.println("\nCurrent Booking Queue:");

        for (Reservation r : queue) {
            r.display();
        }
    }
}