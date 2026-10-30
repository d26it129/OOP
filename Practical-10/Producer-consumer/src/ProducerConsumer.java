public class ProducerConsumer {

    static class Buffer {
        private final int[] items = new int[3];
        private int count = 0, in = 0, out = 0;

        synchronized void put(int value) throws InterruptedException {
            while (count == items.length) wait();          // buffer full
            items[in] = value;
            in = (in + 1) % items.length;
            count++;
            notifyAll();
        }

        synchronized int take() throws InterruptedException {
            while (count == 0) wait();                     // buffer empty
            int value = items[out];
            out = (out + 1) % items.length;
            count--;
            notifyAll();
            return value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int N = 8;
        Buffer buffer = new Buffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= N; i++) { buffer.put(i); System.out.println("Produced " + i); }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= N; i++) {
                    int v = buffer.take();
                    System.out.println("    Consumed " + v);
                    Thread.sleep(40);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "consumer");

        producer.start(); consumer.start();
        producer.join(); consumer.join();
        System.out.println("Done: " + N + " items produced and consumed in order.");
    }
}
