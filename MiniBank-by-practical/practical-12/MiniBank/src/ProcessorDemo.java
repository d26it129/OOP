import exception.BankException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import model.Account;
import model.Command;
import model.SavingsAccount;
import model.TransactionType;
import service.TransactionBuffer;
import service.TransactionProcessor;
import service.TransferLock;

public class ProcessorDemo {

    // ---------- 1. thread pool ----------
    static void poolDemo() throws InterruptedException {
        System.out.println("=== 1. Thread pool (4 threads, 30 tasks) ===");
        Account account = new SavingsAccount("Pool", 1000, 0);
        Set<String> threadsUsed = ConcurrentHashMap.newKeySet();
        TransactionProcessor processor = new TransactionProcessor();

        for (int i = 0; i < 20; i++) {
            processor.submit(() -> {
                try { account.deposit(100); } catch (BankException e) { System.out.println(e.getMessage()); }
                threadsUsed.add(Thread.currentThread().getName());
            });
        }
        for (int i = 0; i < 10; i++) {
            processor.submit(() -> {
                try { account.withdraw(50); } catch (BankException e) { System.out.println(e.getMessage()); }
                threadsUsed.add(Thread.currentThread().getName());
            });
        }
        processor.stop();
        System.out.println("Expected balance 1000 + 20*100 - 10*50 = 2500, actual = " + account.getBalance());
        System.out.println("Threads used: " + new java.util.TreeSet<>(threadsUsed));
    }

    // ---------- 2. producer / consumer ----------
    static void producerConsumerDemo() throws InterruptedException {
        System.out.println("\n=== 2. Producer-consumer with wait()/notify() ===");
        Account account = new SavingsAccount("Queue", 0, 0);
        TransactionBuffer buffer = new TransactionBuffer();
        final int N = 6;

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= N; i++) {
                    buffer.put(new Command(TransactionType.DEPOSIT, account.getAccountNumber(), i * 100L));
                    System.out.println("Produced DEPOSIT " + i * 100);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= N; i++) {
                    Command c = buffer.take();
                    account.deposit(c.amount());
                    System.out.println("    Processed " + c.type() + " " + c.amount()
                            + " -> balance " + account.getBalance());
                    Thread.sleep(30);
                }
            } catch (InterruptedException | BankException e) { System.out.println(e.getMessage()); }
        }, "consumer");

        producer.start(); consumer.start();
        producer.join(); consumer.join();
        System.out.println("Final balance " + account.getBalance() + " (expected 2100)");
    }

    // ---------- 3. deadlock and its fix ----------
    static void transferRound(boolean safe) throws InterruptedException {
        Account a = new SavingsAccount("A", 1000, 0);
        Account b = new SavingsAccount("B", 1000, 0);

        Runnable aToB = () -> { try { if (safe) TransferLock.transferSafe(a, b, 100); else TransferLock.transferUnsafe(a, b, 100); }
                                catch (BankException e) { System.out.println(e.getMessage()); } };
        Runnable bToA = () -> { try { if (safe) TransferLock.transferSafe(b, a, 50); else TransferLock.transferUnsafe(b, a, 50); }
                                catch (BankException e) { System.out.println(e.getMessage()); } };

        Thread t1 = new Thread(aToB, "A-to-B");
        Thread t2 = new Thread(bToA, "B-to-A");
        t1.setDaemon(true); t2.setDaemon(true);          // so a deadlocked demo cannot keep the JVM alive
        t1.start(); t2.start();
        t1.join(2000); t2.join(2000);

        if (t1.isAlive() || t2.isAlive()) {
            ThreadMXBean mx = ManagementFactory.getThreadMXBean();
            long[] ids = mx.findDeadlockedThreads();
            System.out.println("DEADLOCK! Both threads are stuck waiting for each other.");
            if (ids != null) {
                for (long id : ids) System.out.println("   deadlocked thread: " + mx.getThreadInfo(id).getThreadName());
            }
        } else {
            System.out.println("Both transfers finished. A = " + a.getBalance() + ", B = " + b.getBalance()
                    + " (expected A 950, B 1050)");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        poolDemo();
        producerConsumerDemo();
        System.out.println("\n=== 3a. Transfers with OPPOSITE lock order ===");
        transferRound(false);
        System.out.println("\n=== 3b. Transfers with consistent lock order (smaller account number first) ===");
        transferRound(true);
    }
}
