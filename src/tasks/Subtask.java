package tasks;

import java.util.UUID;

public class Subtask extends Task{
    private Epic epic;

    public Subtask(String name, String description, UUID id, Epic epic) {
        super(name, description, id);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }
}
