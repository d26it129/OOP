import model.Account;
import model.SavingsAccount;
import service.AccountWorker;

public class ThreadDemo {

    static long runRound(boolean safe) throws InterruptedException {
        Account account = new SavingsAccount("Shared", 0, 0);
        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new AccountWorker(account, 1000, 1, safe), "Worker-" + (i + 1));
        }
        System.out.println("Before start(): " + threads[0].getName() + " is " + threads[0].getState());
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        System.out.println("After join()  : " + threads[0].getName() + " is " + threads[0].getState());
        return account.getBalance();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("10 threads x 1000 deposits of 1  ->  expected balance 10000\n");

        System.out.println("=== WITHOUT synchronization ===");
        System.out.println("Final balance: " + runRound(false));

        System.out.println("\n=== WITH synchronized deposit() ===");
        System.out.println("Final balance: " + runRound(true));
    }
}
