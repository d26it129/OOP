import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TaskServiceTest {

    @Test
    void addingIncreasesCount() {
        TaskService service = new TaskService();
        service.add("Write report");
        service.add("Submit lab");
        assertEquals(2, service.count());
    }

    @Test
    void blankTitleIsRejected() {
        TaskService service = new TaskService();
        assertThrows(IllegalArgumentException.class, () -> service.add("   "));
    }

    @Test
    void completingMarksTaskDone() {
        TaskService service = new TaskService();
        service.add("Revise OOP");
        service.complete(0);
        assertTrue(service.all().get(0).isDone());
    }

    @Test
    void invalidIndexThrows() {
        TaskService service = new TaskService();
        assertThrows(IllegalArgumentException.class, () -> service.complete(5));
    }
}
