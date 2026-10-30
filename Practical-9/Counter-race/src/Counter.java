public class Counter {
    private volatile int count = 0;

    public void incrementUnsafe() { count++; }                    // read-modify-write, not atomic

    public synchronized void incrementSafe() { count++; }         // one thread at a time

    public int getCount() { return count; }
}
