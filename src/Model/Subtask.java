package Model;

public class Subtask extends Task{
    Epic epic;

    public Subtask(String name, String description, Integer id, Epic epic) {
        super(name, description, id);
        this.epic = epic;
    }
}
