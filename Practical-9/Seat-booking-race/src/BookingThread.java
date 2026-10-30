import java.util.concurrent.atomic.AtomicInteger;

public class BookingThread extends Thread {
    private final SeatBooking booking;
    private final boolean safe;
    private final AtomicInteger successes;

    public BookingThread(SeatBooking booking, boolean safe, AtomicInteger successes) {
        this.booking = booking;
        this.safe = safe;
        this.successes = successes;
    }

    @Override
    public void run() {
        boolean ok = safe ? booking.bookSafe() : booking.bookUnsafe();
        if (ok) successes.incrementAndGet();
    }
}
