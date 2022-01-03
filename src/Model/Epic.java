package Model;

import java.util.ArrayList;

public class Epic extends Task{
    ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description, Integer id) {
        super(name, description, id);
    }
}
