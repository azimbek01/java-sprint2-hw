package tasks;

import java.util.ArrayList;
import java.util.UUID;

public class Epic extends Task{
    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description, UUID id) {
        super(name, description, id);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }
}
