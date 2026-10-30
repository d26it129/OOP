interface Switchable {
    void on();
    void off();
    boolean isOn();
    default void toggle() {
        if (isOn()) off(); else on();
    }
}

class Fan implements Switchable {
    private boolean running;
    public void on()  { running = true;  System.out.println("Fan ON"); }
    public void off() { running = false; System.out.println("Fan OFF"); }
    public boolean isOn() { return running; }
}

class Light implements Switchable {
    private boolean lit;
    public void on()  { lit = true;  System.out.println("Light ON"); }
    public void off() { lit = false; System.out.println("Light OFF"); }
    public boolean isOn() { return lit; }
}

interface SwitchPolicy {
    boolean mayTurnOn(Switchable device, int hour);
}

public class RemoteControl {
    public static void main(String[] args) {
        Switchable[] devices = { new Fan(), new Light() };
        System.out.println("-- toggle twice --");
        for (int round = 1; round <= 2; round++)
            for (Switchable d : devices) d.toggle();

        // 1) anonymous class
        SwitchPolicy anon = new SwitchPolicy() {
            public boolean mayTurnOn(Switchable device, int hour) {
                return !(device instanceof Light) || hour >= 18 || hour < 6;
            }
        };
        // 2) lambda doing the same job
        SwitchPolicy lambda = (device, hour) -> !(device instanceof Light) || hour >= 18 || hour < 6;

        System.out.println("-- policy: light only in the evening/night --");
        for (int hour : new int[] { 10, 20 }) {
            for (Switchable d : devices) {
                String n = d.getClass().getSimpleName();
                System.out.println(hour + ":00 " + n + " anonymous=" + anon.mayTurnOn(d, hour)
                        + " lambda=" + lambda.mayTurnOn(d, hour));
            }
        }
    }
}
