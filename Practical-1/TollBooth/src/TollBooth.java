import java.util.Scanner;

public class TollBooth {

    record Vehicle(String number, String type) { }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int total = 0, bike = 0, car = 0, truck = 0;

        while (true) {
            System.out.print("Vehicle number (or 'done'): ");
            String number = sc.next();
            if (number.equalsIgnoreCase("done")) break;

            System.out.print("Vehicle type (bike/car/truck): ");
            Vehicle v = new Vehicle(number, sc.next().toLowerCase());

            int toll = switch (v.type()) {
                case "bike"  -> 20;
                case "car"   -> 50;
                case "truck" -> 150;
                default      -> 0;
            };
            if (toll == 0) {
                System.out.println("Unknown type '" + v.type() + "', vehicle skipped.");
                continue;
            }
            total += toll;
            switch (v.type()) {
                case "bike"  -> bike++;
                case "car"   -> car++;
                default      -> truck++;
            }
            System.out.println(v + " -> toll " + toll);
        }

        String most = "bike";
        int max = bike;
        if (car > max)   { most = "car";   max = car; }
        if (truck > max) { most = "truck"; max = truck; }

        System.out.println("Total toll: " + total);
        System.out.println("Most frequent: " + most);
        sc.close();
    }
}
