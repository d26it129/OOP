interface Notifier {
    void send(String message);
}

interface Urgent { }                         // marker interface

interface UrgentNotifier extends Notifier, Urgent { }

public class NotificationSender {
    public static void main(String[] args) {
        Notifier email = msg -> System.out.println("[EMAIL] " + msg);
        UrgentNotifier sms = msg -> System.out.println("[SMS]   " + msg);   // marked urgent

        Notifier[] senders = { email, sms };
        String message = "Server maintenance at 10 PM";

        for (Notifier n : senders) {
            n.send(message);
            if (n instanceof Urgent) {
                System.out.println("  (urgent sender, sending again)");
                n.send(message);
            }
        }
    }
}
