public class Thermostat {

    private String location;
    private int temperature;
    private static final int MIN = 16;
    private static final int MAX = 30;
    private static int activeCount = 0;

    public Thermostat(String location, int startTemp) {
        this.location = location;
        this.temperature = (startTemp >= MIN && startTemp <= MAX) ? startTemp : 22;
        activeCount++;
    }

    public Thermostat(String location) {
        this(location, 22);
    }

    public void raise() {
        if (temperature < MAX) temperature++;
        else System.out.println("Already at maximum (30)");
    }

    public void lower() {
        if (temperature > MIN) temperature--;
        else System.out.println("Already at minimum (16)");
    }

    public int getTemperature() { return temperature; }
    public static int getActiveCount() { return activeCount; }

    public static void main(String[] args) {
        Thermostat t1 = new Thermostat("Living Room", 24);
        Thermostat t2 = new Thermostat("Bedroom");

        System.out.println("-- " + t1.location + " --");
        for (int i = 1; i <= 10; i++) {
            t1.raise();
            System.out.println("raise " + i + " -> " + t1.getTemperature());
        }
        for (int i = 1; i <= 20; i++) {
            t1.lower();
            System.out.println("lower " + i + " -> " + t1.getTemperature());
        }
        System.out.println("-- " + t2.location + " starts at " + t2.getTemperature() + " --");
        System.out.println("Active thermostats: " + getActiveCount());
    }
}
