package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    @Override
    public Task getTask(Task task) {
        Task t = super.getTask(task);
        save();
        return t;
    }

    @Override
    public Epic getEpic(Epic epic) {
        Epic e = super.getEpic(epic);
        save();
        return e;
    }

    @Override
    public Subtask getSubtask(Subtask subtask) {
        Subtask s = super.getSubtask(subtask);
        save();
        return s;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void updateTaskById(Task task) {
        super.updateTaskById(task);
        save();
    }

    @Override
    public void updateEpicById(Epic epic) {
        super.updateEpicById(epic);
        save();
    }

    @Override
    public void updateSubtaskById(Subtask subtask) {
        super.updateSubtaskById(subtask);
        save();
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
        save();
    }

    @Override
    public void deleteTask(Task task) {
        super.deleteTask(task);
        save();
    }

    @Override
    public void deleteEpic(Epic epic) {
        super.deleteEpic(epic);
        save();
    }

    @Override
    public void deleteSubtask(Subtask subtask) {
        super.deleteSubtask(subtask);
        save();
    }

    private String toString(Task task) {
        String result = task.getId()
                + "," + task.getType()
                + "," + task.getName()
                + "," + task.getStatus()
                + "," + task.getDescription();
        if (task.getType() == Type.SUBTASK) {
            Subtask subtask = (Subtask) task;
            result = result + "," + subtask.getEpicId();
        }
        return result;
    }

    public static FileBackedTasksManager loadFromFile(File file) {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.load(file);

        return fileBackedTasksManager;
    }

    private void load(File file) {
        int maxId = 0;
        try (FileReader fileReader = new FileReader(file, UTF_8)) {
            BufferedReader br = new BufferedReader(fileReader);
            br.readLine();
            while (true) {
                String str = br.readLine();
                if (str == null || str.isEmpty()) {
                    break;
                }
                Task task = fromStringToTask(str);
                if (maxId < task.getId()) {
                    maxId = task.getId();
                }
                if (task.getType() == Type.TASK) {
                    this.tasks.put(task.getId(), task);
                } else if (task.getType() == Type.EPIC) {
                    this.epics.put(task.getId(), (Epic) task);
                } else if (task.getType() == Type.SUBTASK) {
                    this.subtasks.put(task.getId(), (Subtask) task);
                }
            }
            String str = br.readLine();
            if (str == null || str.isEmpty()) {
                return;
            }
            List<Integer> historyId = fromStringHistory(str);
            for (Integer id : historyId) {
                if (tasks.containsKey(id)) {
                    historyManager.add(tasks.get(id));
                } else if (epics.containsKey(id)) {
                    historyManager.add(epics.get(id));
                } else if (subtasks.containsKey(id)) {
                    historyManager.add(subtasks.get(id));
                }
            }
        } catch (ManagerSaveException e) {
            e.getMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        idTask = maxId;
    }

    private void save() {
        try (FileWriter fw = new FileWriter(file)) {
            BufferedWriter writer = new BufferedWriter(fw);
            writer.append("id,type,name,status,description,epic");
            writer.newLine();
            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.newLine();
            }
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.newLine();
            }
            for (Map.Entry<Integer, Subtask> entry : subtasks.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.newLine();
            }
            writer.newLine();
            String history = historyToString(historyManager);
            writer.append(history);

            writer.close();
        } catch (ManagerSaveException e) {
            e.getMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Task fromStringToTask(String str) {
        String[] data = str.split(",");
        Type type = Type.valueOf(data[1]);
        Integer id = Integer.parseInt(data[0]);
        Status status = Status.valueOf(data[3]);
        switch (type) {
            case TASK: {
                return new Task(data[2], data[4], id, status, type);
            }
            case EPIC: {
                return new Epic(data[2], data[4], id, status, type);
            }
            case SUBTASK: {
                Integer epicId = Integer.parseInt(data[5]);
                return new Subtask(data[2], data[4], id, status, epicId, type);
            }
        }
        return null;
    }

    static String historyToString(HistoryManager manager) {
        StringBuilder sB = new StringBuilder();
        for (Task task : manager.getHistory()) {
            if (sB.length() != 0) {
                sB.append("," + task.getId());
            } else {
                sB.append(task.getId());
            }
        }
        return sB.toString();
    }

    static List<Integer> fromStringHistory(String value) {
        String[] id = value.split(",");
        List<Integer> history = new ArrayList<>();
        for (String idStr : id) {
            history.add(Integer.valueOf(idStr));
        }
        return history;
    }

    public static void main(String[] args) {
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(
                new File("resources/tasks.csv"));

        Task task1 = new Task("Task1", "Task1", fileBackedTasksManager.getNewIdTask(), Type.TASK);
        fileBackedTasksManager.createTask(task1);

        Task task2 = new Task("Task2", "Task2", fileBackedTasksManager.getNewIdTask(), Type.TASK);
        fileBackedTasksManager.createTask(task2);

        Epic epic1 = new Epic("Epic1", "Epic1", fileBackedTasksManager.getNewIdTask(), Type.EPIC);
        fileBackedTasksManager.createEpic(epic1);

        Epic epic2 = new Epic("Epic2", "Epic2", fileBackedTasksManager.getNewIdTask(), Type.EPIC);
        fileBackedTasksManager.createEpic(epic2);

        Subtask subtask1 = new Subtask("Subtask1", "Subtask1", fileBackedTasksManager.getNewIdTask(),
                Status.IN_PROGRESS, epic1.getId(), Type.SUBTASK);
        fileBackedTasksManager.createSubtask(subtask1);

        fileBackedTasksManager.getTask(task1);
        fileBackedTasksManager.getTask(epic1);
        fileBackedTasksManager.getSubtask(subtask1);
    }
}
