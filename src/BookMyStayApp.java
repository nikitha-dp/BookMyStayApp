import java.util.HashMap;

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Book My Stay App Started\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Register room types
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Deluxe Room", 2);

        // Create room objects
        Room single = new SingleRoom(inventory);
        Room doubleRoom = new DoubleRoom(inventory);
        Room deluxe = new DeluxeRoom(inventory);

        System.out.println("Available Room Types\n");

        single.displayRoomDetails();
        doubleRoom.displayRoomDetails();
        deluxe.displayRoomDetails();

        System.out.println("Application Closed.");
    }
}


class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    // Register room type
    void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get availability
    int getAvailability(String roomType) {
        return inventory.get(roomType);
    }

    // Update availability
    void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }
}



abstract class Room {

    String roomType;
    double price;
    RoomInventory inventory;

    Room(String roomType, double price, RoomInventory inventory) {
        this.roomType = roomType;
        this.price = price;
        this.inventory = inventory;
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
        System.out.println("Available Rooms: " + inventory.getAvailability(roomType));
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
        System.out.println("Available Rooms: " + inventory.getAvailability(roomType));
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
        System.out.println("Available Rooms: " + inventory.getAvailability(roomType));
        System.out.println();
    }
}
