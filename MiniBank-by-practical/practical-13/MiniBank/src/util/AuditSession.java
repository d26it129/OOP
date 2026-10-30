package util;

// a small AutoCloseable resource: close() is called automatically by try-with-resources
public class AuditSession implements AutoCloseable {

    private final String name;

    public AuditSession(String name) {
        this.name = name;
        System.out.println("[audit] session '" + name + "' opened");
    }

    public void record(String text) {
        System.out.println("[audit] " + name + ": " + text);
    }

    @Override
    public void close() {
        System.out.println("[audit] session '" + name + "' closed");
    }
}
