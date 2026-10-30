public class Task {
    private final String title;
    private boolean done;

    public Task(String title) { this.title = title; }

    public String getTitle() { return title; }
    public boolean isDone() { return done; }
    public void markDone() { this.done = true; }

    @Override
    public String toString() { return (done ? "[x] " : "[ ] ") + title; }
}
