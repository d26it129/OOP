import java.util.Scanner;

public class TaskApp {
    public static void main(String[] args) {
        TaskService service = new TaskService();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("1.Add 2.Complete 3.List 0.Exit > ");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> { System.out.print("Title: "); service.add(sc.nextLine()); }
                    case "2" -> { System.out.print("Index: "); service.complete(Integer.parseInt(sc.nextLine().trim())); }
                    case "3" -> { int i = 0; for (Task t : service.all()) System.out.println(i++ + ". " + t); }
                    case "0" -> { System.out.println("Bye"); return; }
                    default  -> System.out.println("Invalid choice");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
