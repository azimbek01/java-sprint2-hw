package manager;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {
    List<Task> history = new ArrayList<>();

    static void getDefaultHistory() {
    }

    void add(Task task);

    List<Task> getHistory();
}
