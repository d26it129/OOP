package service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionProcessor {

    // 4 reusable worker threads instead of one new thread per task
    private final ExecutorService pool = Executors.newFixedThreadPool(4);

    public void submit(Runnable task) {
        pool.execute(task);
    }

    public void stop() throws InterruptedException {
        pool.shutdown();                                    // no new tasks; finish the queued ones
        pool.awaitTermination(30, TimeUnit.SECONDS);        // wait for completion
    }
}
