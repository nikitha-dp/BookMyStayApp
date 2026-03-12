import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Book My Stay App Started\n");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);
        inventory.addRoomType("Deluxe Room", 1);

        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom(inventory));
        rooms.add(new DoubleRoom(inventory));
        rooms.add(new DeluxeRoom(inventory));

        SearchService search = new SearchService();
        search.displayAvailableRooms(rooms);

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Anita", "Double Room"));
        queue.addRequest(new Reservation("Karan", "Single Room"));

        queue.displayRequests();

        BookingService bookingService = new BookingService();
        List<ConfirmedReservation> confirmed = bookingService.processBookings(queue, inventory);

        AddOnServiceManager addOnManager = new AddOnServiceManager();

        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 800);
        AddOnService spa = new AddOnService("Spa Access", 1200);

        for (ConfirmedReservation r : confirmed) {

            if (r.guestName.equals("Rahul")) {
                addOnManager.addService(r.roomID, breakfast);
                addOnManager.addService(r.roomID, spa);
            }

            if (r.guestName.equals("Anita")) {
                addOnManager.addService(r.roomID, airportPickup);
            }
        }

        System.out.println("\nAdd-On Service Summary\n");
        addOnManager.printServices();

        System.out.println("\nApplication Closed.");
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

    void decreaseRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

/* ---------------- ROOM ---------------- */

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

/* ---------------- CONFIRMED RESERVATION ---------------- */

class ConfirmedReservation {

    String guestName;
    String roomType;
    String roomID;

    ConfirmedReservation(String guestName, String roomType, String roomID) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomID = roomID;
    }
}

/* ---------------- QUEUE ---------------- */

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

    List<ConfirmedReservation> processBookings(BookingRequestQueue queue, RoomInventory inventory) {

        List<ConfirmedReservation> confirmedList = new ArrayList<>();

        System.out.println("\nProcessing Booking Requests\n");

        while (queue.hasRequests()) {

            Reservation request = queue.getNextRequest();

            if (inventory.getAvailability(request.roomType) > 0) {

                String roomID = request.roomType.substring(0,2).toUpperCase() + roomCounter++;

                inventory.decreaseRoom(request.roomType);

                System.out.println("Booking Confirmed");
                System.out.println("Guest: " + request.guestName);
                System.out.println("Room ID: " + roomID);
                System.out.println();

                confirmedList.add(new ConfirmedReservation(
                        request.guestName,
                        request.roomType,
                        roomID
                ));

            } else {

                System.out.println("Booking Failed for " + request.guestName);
                System.out.println();
            }
        }

        return confirmedList;
    }
}

/* ---------------- ADD ON SERVICE ---------------- */

class AddOnService {

    String serviceName;
    int price;

    AddOnService(String serviceName, int price) {
        this.serviceName = serviceName;
        this.price = price;
    }
}

/* ---------------- ADD ON MANAGER ---------------- */

class AddOnServiceManager {

    private HashMap<String, List<AddOnService>> addOns = new HashMap<>();

    void addService(String reservationID, AddOnService service) {

        addOns.putIfAbsent(reservationID, new ArrayList<>());
        addOns.get(reservationID).add(service);
    }

    void printServices() {

        for (String reservationID : addOns.keySet()) {

            int total = 0;

            System.out.println("Reservation ID: " + reservationID);

            for (AddOnService s : addOns.get(reservationID)) {

                System.out.println("  Service: " + s.serviceName + " ₹" + s.price);
                total += s.price;
            }

            System.out.println("  Total Add-On Cost: ₹" + total);
            System.out.println();
        }
    }
}

