import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();

    public void add(String title) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Title is required");
        tasks.add(new Task(title.trim()));
    }

    public void complete(int index) {
        if (index < 0 || index >= tasks.size())
            throw new IllegalArgumentException("No task at index " + index);
        tasks.get(index).markDone();
    }

    public int count() { return tasks.size(); }
    public List<Task> all() { return tasks; }
}
