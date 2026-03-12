import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Book My Stay App Started\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);
        inventory.addRoomType("Deluxe Room", 1);

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

        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Anita", "Double Room"));
        queue.addRequest(new Reservation("Karan", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Deluxe Room"));

        queue.displayRequests();

        // Process bookings
        BookingService bookingService = new BookingService();
        bookingService.processBookings(queue, inventory);

        System.out.println("\nApplication Closed.");
    }
}

/* ---------------- INVENTORY SERVICE ---------------- */

class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    void decreaseRoom(String roomType) {
        int count = inventory.get(roomType);
        inventory.put(roomType, count - 1);
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

/* ---------------- BOOKING REQUEST QUEUE ---------------- */

class BookingRequestQueue {

    private LinkedList<Reservation> queue = new LinkedList<>();

    void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request added for " + reservation.guestName);
    }

    Reservation getNextRequest() {
        return queue.poll();
    }

    boolean hasRequests() {
        return !queue.isEmpty();
    }

    void displayRequests() {

        System.out.println("\nCurrent Booking Queue:");

        for (Reservation r : queue) {
            r.display();
        }
    }
}

/* ---------------- BOOKING SERVICE ---------------- */

class BookingService {

    private int roomCounter = 100;

    void processBookings(BookingRequestQueue queue, RoomInventory inventory) {

        System.out.println("\nProcessing Booking Requests\n");

        while (queue.hasRequests()) {

            Reservation request = queue.getNextRequest();

            int available = inventory.getAvailability(request.roomType);

            if (available > 0) {

                String roomID = request.roomType.substring(0,2).toUpperCase() + roomCounter++;

                inventory.decreaseRoom(request.roomType);

                System.out.println("Booking Confirmed");
                System.out.println("Guest: " + request.guestName);
                System.out.println("Room Type: " + request.roomType);
                System.out.println("Room ID: " + roomID);
                System.out.println();

            } else {

                System.out.println("Booking Failed for " + request.guestName +
                        " (No " + request.roomType + " available)");
                System.out.println();
            }
        }
    }
}
    }