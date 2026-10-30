import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        String[] logs = {
            "10:05 alice Hello there",
            "10:07 carol See you at lunch",
            "malformed",
        };
        Scanner sc = new Scanner(System.in);
        System.out.print("Keyword: ");
        String keyword = sc.nextLine();
        System.out.print(ChatFilter.filter(logs, keyword));
        sc.close();
    }
}
