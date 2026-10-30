import java.util.concurrent.atomic.AtomicInteger;

public class SeatBookingRace {

    static void round(String label, boolean safe) throws InterruptedException {
        SeatBooking booking = new SeatBooking();
        AtomicInteger ok = new AtomicInteger();
        Thread[] ts = new Thread[10];
        for (int i = 0; i < ts.length; i++) { ts[i] = new BookingThread(booking, safe, ok); ts[i].start(); }
        for (Thread t : ts) t.join();
        System.out.println(label + ": successful bookings = " + ok.get()
                + ", seatsLeft = " + booking.getSeatsLeft());
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("5 seats, 10 threads each try to book one seat");
        round("Without synchronization", false);
        round("With synchronized book()", true);
    }
}
