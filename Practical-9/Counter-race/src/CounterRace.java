public class CounterRace {

    static int runRound(boolean safe, int threads, int times) throws InterruptedException {
        Counter counter = new Counter();
        Thread[] ts = new Thread[threads];
        for (int i = 0; i < threads; i++) { ts[i] = new CounterThread(counter, times, safe); ts[i].start(); }
        for (Thread t : ts) t.join();
        return counter.getCount();
    }

    public static void main(String[] args) throws InterruptedException {
        int threads = 8, times = 200_000;
        System.out.println("Expected total = " + (threads * times));
        for (int run = 1; run <= 3; run++)
            System.out.println("Without synchronization, run " + run + ": " + runRound(false, threads, times));
        for (int run = 1; run <= 3; run++)
            System.out.println("With synchronized,       run " + run + ": " + runRound(true, threads, times));
    }
}
