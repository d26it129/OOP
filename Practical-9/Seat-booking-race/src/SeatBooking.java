public class SeatBooking {
    private int seatsLeft = 5;

    // not synchronized: two threads can both pass the check
    public boolean bookUnsafe() {
        if (seatsLeft > 0) {
            pause();                       // simulates payment delay
            seatsLeft--;
            return true;
        }
        return false;
    }

    public synchronized boolean bookSafe() {
        if (seatsLeft > 0) {
            pause();
            seatsLeft--;
            return true;
        }
        return false;
    }

    private void pause() {
        try { Thread.sleep(5); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public int getSeatsLeft() { return seatsLeft; }
}
