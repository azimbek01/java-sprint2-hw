package tasks;

import manager.Status;
import manager.Type;

public class Subtask extends Task{
    private Integer epicId;

    public Subtask(String name, String description, Integer id, Integer epicId, Type type) {
        super(name, description, id, type);
        this.epicId = epicId;
    }

    // New
    public Subtask(String name, String description, Integer id, Status status, Integer epicId, Type type) {
        super(name, description, id, status, type); //added type
        //this.epic = epic;
        this.epicId = epicId;
    }

    // Rename method
    public Integer getEpicId() {
        //return epic;
        return epicId;
    }

    public void setEpic(Integer epicId) {
        //this.epic = epic;
        this.epicId = epicId;
    }
}
