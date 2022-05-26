package tasks;

import manager.Status;
import manager.Type;

import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description, Integer id, Type type) {
        super(name, description, id, type);
    }

    public Epic(String name, String description, Integer id, Status status, Type type) {
        super(name, description, id, status, type);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }
}
