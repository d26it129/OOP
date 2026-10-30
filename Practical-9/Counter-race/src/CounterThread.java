public class CounterThread extends Thread {
    private final Counter counter;
    private final int times;
    private final boolean safe;

    public CounterThread(Counter counter, int times, boolean safe) {
        this.counter = counter;
        this.times = times;
        this.safe = safe;
    }

    @Override
    public void run() {
        for (int i = 0; i < times; i++) {
            if (safe) counter.incrementSafe(); else counter.incrementUnsafe();
        }
    }
}
