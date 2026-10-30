package service;

import model.Command;

// A small bounded buffer shared by a producer thread and a consumer thread.
public class TransactionBuffer {

    private final Command[] items = new Command[3];
    private int count = 0, in = 0, out = 0;

    public synchronized void put(Command c) throws InterruptedException {
        while (count == items.length) {
            wait();                                 // buffer full: wait for the consumer
        }
        items[in] = c;
        in = (in + 1) % items.length;
        count++;
        notifyAll();                                // wake the consumer
    }

    public synchronized Command take() throws InterruptedException {
        while (count == 0) {
            wait();                                 // buffer empty: wait for the producer
        }
        Command c = items[out];
        out = (out + 1) % items.length;
        count--;
        notifyAll();                                // wake the producer
        return c;
    }
}
