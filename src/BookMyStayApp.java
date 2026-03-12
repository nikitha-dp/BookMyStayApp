public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Book My Stay App Started");
        System.out.println("Available Room Types\n");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room deluxe = new DeluxeRoom();

        single.displayRoomDetails();
        doubleRoom.displayRoomDetails();
        deluxe.displayRoomDetails();

        System.out.println("Application Closed.");
    }
}

abstract class Room {

    String roomType;
    double price;
    int availableRooms;

    Room(String roomType, double price, int availableRooms) {
        this.roomType = roomType;
        this.price = price;
        this.availableRooms = availableRooms;
    }

    abstract void displayRoomDetails();
}

class SingleRoom extends Room {

    SingleRoom() {
        super("Single Room", 1500, 5);
    }

    void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: ₹" + price);
        System.out.println("Available Rooms: " + availableRooms);
        System.out.println();
    }
}

class DoubleRoom extends Room {

    DoubleRoom() {
        super("Double Room", 2500, 3);
    }

    void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: ₹" + price);
        System.out.println("Available Rooms: " + availableRooms);
        System.out.println();
    }
}

class DeluxeRoom extends Room {

    DeluxeRoom() {
        super("Deluxe Room", 4000, 2);
    }

    void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: ₹" + price);
        System.out.println("Available Rooms: " + availableRooms);
        System.out.println();
    }
}