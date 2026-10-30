import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PoolRunner {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int id = 1; id <= 10; id++) {
            final int taskId = id;
            pool.execute(() -> {
                System.out.println("Task " + taskId + " running on " + Thread.currentThread().getName());
                try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
        }
        pool.shutdown();
        pool.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("All 10 tasks finished using only 3 pool threads.");
    }
}
