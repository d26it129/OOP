import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {
        List<String> logins = List.of("riya", "arjun", "riya", "meera", "arjun", "karan", "riya", "meera");

        Set<String> unique = new HashSet<>(logins);
        Set<String> ordered = new LinkedHashSet<>(logins);

        System.out.println("Unique visitors: " + unique.size());
        System.out.print("First three distinct (in first-seen order): ");
        int shown = 0;
        for (String name : ordered) {
            if (shown++ == 3) break;
            System.out.print(name + (shown < 3 ? ", " : ""));
        }
        System.out.println();
    }
}
