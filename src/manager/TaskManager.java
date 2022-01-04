package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    Integer idTask = 0;
    Integer idEpic = 0;
    Integer idSubtask = 0;

    // Счетчик ИД задач, эпиков и подзадач
    public Integer getNewIdTask() {
        return ++idTask;
    }

    public Integer getNewIdEpic() {
        return ++idEpic;
    }

    public Integer getNewIdSubtask() {
        return ++idSubtask;
    }

    // Получение списка всех задач.
    public Collection<Task> getAllTasks() {
        if (!tasks.isEmpty()) {
            return tasks.values();
        } else {
            return null;
        }

    }

    // Получение списка всех эпиков.
    public Collection<Epic> getAllEpics() {
        if (!epics.isEmpty()) {
            return epics.values();
        }
        return null;
    }

    // Получение списка всех подзадач определённого эпика.
    public ArrayList<Subtask> getAllSubtaskOfEpic(Epic epic) {
        if (!epics.isEmpty()) {
            Epic objectEpic = epics.get(epic.getId());
            return objectEpic.getSubtasks();
        } else {
            return null;
        }

    }

    // Получение задачи любого типа по идентификатору.
    public Task getTaskById(Task task) {
        if (tasks.containsKey(task.getId())) {
            return tasks.get(task.getId());
        } else {
            return null;
        }
    }

    public Epic getEpicById(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            return epics.get(epic.getId());
        } else {
            return null;
        }
    }

    public Subtask getSubtaskById(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            return subtasks.get(subtask.getId());
        } else {
            return null;
        }
    }

    /* Добавление новой задачи, эпика и подзадачи. Сам объект должен передаваться в качестве
    параметра. */
    public void createTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            tasks.put(idTask, task);
        } else {
            System.out.println("Задача с ид=" + task.getId() + " уже существует.");
        }

    }

    public void createEpic(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            epics.put(idEpic, epic);
        } else {
            System.out.println("Эпик с ид=" + epic.getId() + " уже существует.");
        }
    }

    public void createSubtask(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            if (epics.containsKey(subtask.getEpic().getId())) {
                Epic epic = epics.get(subtask.getEpic().getId());
                epic.getSubtasks().add(subtask);
            } else {
                System.out.println("Подзадача с ид=" + subtask.getId() + " не найдена в эпиках");
            }
        } else {
            System.out.println("Подзадача с ид=" + subtask.getId() + " уже существует.");
        }

    }

    /* Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде
    параметра. */
    public void updateTaskById(Task task) {
        Task objectTask = tasks.get(task.getId());
        objectTask.setName(task.getName());
        objectTask.setDescription(task.getDescription());
        objectTask.setStatus(task.getStatus());
    }

    public void updateEpicById(Epic epic) {
        Epic objectEpic = epics.get(epic.getId());
        objectEpic.setName(epic.getName());
        objectEpic.setDescription(epic.getDescription());
    }

    // Обновление всех полей подзадачи. Обновление статуса в связанном эпике objectEpic
    public void updateSubtaskById(Subtask subtask) {
        Subtask objectSubtask = subtasks.get(subtask.getId());
        objectSubtask.setName(subtask.getName());
        objectSubtask.setDescription(subtask.getDescription());
        objectSubtask.setStatus(subtask.getStatus());
        Epic objectEpic = objectSubtask.getEpic();
        ArrayList<Subtask> subtasksEpic = objectEpic.getSubtasks();
        if (!subtasksEpic.isEmpty()) {
            boolean newStatus = true;
            boolean doneStatus = true;
            for (Subtask subtaskValue : subtasksEpic) {
                if (subtaskValue.getStatus().equals("NEW")) {
                    newStatus = false;
                    break;
                }
            }
            for (Subtask subtaskValue : subtasksEpic) {
                if (subtaskValue.getStatus().equals("DONE")) {
                    doneStatus = false;
                    break;
                }
            }
            if (newStatus) {
                objectEpic.setStatus("NEW");
            } else if (doneStatus){
                objectEpic.setStatus("DONE");
            } else {
                objectEpic.setStatus("IN_PROGRESS");
            }
        }
    }

    // Удаление ранее добавленных задач — всех и по идентификатору.
    public void deleteAll() {
        if (!tasks.isEmpty()) {
            tasks.clear();
        }
        if (!epics.isEmpty()) {
            epics.clear();
        }
        if (!subtasks.isEmpty()) {
            subtasks.clear();
        }
    }

    public void deleteTaskById(Task task) {
        if (!tasks.isEmpty()) {
            if (tasks.containsKey(task.getId())) {
                tasks.remove(task.getId(), task);
            } else {
                System.out.println("Задача с ид=" + task.getId() + " не существует.");
            }
        } else {
            System.out.println("Таблица задач пуста.");
        }
    }

    // Удаление эпика из хэш-таблицы epics и подзадачи из хэш-таблицы subtasks
    public void deleteEpicById(Epic epic) {
        if (!epics.isEmpty()) {
            if (epics.containsKey(epic.getId())) {
                epics.remove(epic.getId(), epic);
                ArrayList<Subtask> subtasksEpic = epic.getSubtasks();
                for (Subtask subtaskValue : subtasksEpic) {
                    if (subtasks.containsKey(subtaskValue.getId())) {
                        subtasks.remove(subtaskValue.getId(), subtaskValue);
                    }
                }
            } else {
                System.out.println("Эпик с ид=" + epic.getId() + " не существует.");
            }
        } else {
            System.out.println("Таблица эпиков пуста.");
        }

    }

    /* Удаление подзадачи из хэш-таблицы subtasks и из списка subtasksEpic в эпике. Удаление эпика
    из хэш-таблицы epics при пустом списке */
    public void deleteSubtaskById(Subtask subtask) {
        if (!subtasks.isEmpty()) {
            if (subtasks.containsKey(subtask.getId())) {
                subtasks.remove(subtask.getId(), subtask);
                Epic epic = subtask.getEpic();
                ArrayList<Subtask> subtasksEpic = epic.getSubtasks();
                for(Subtask subtaskValue : subtasksEpic) {
                    if (subtaskValue.equals(subtask)) {
                        subtasksEpic.remove(subtask);
                    }
                    if(subtasksEpic.isEmpty()) {
                        epics.remove(epic.getId(), epic);
                        break;
                    }
                }
            } else {
                System.out.println("Подзадача с ид=" + subtask.getId() + " не существует.");
            }
        } else {
            System.out.println("Таблица подзадач пуста.");
        }

    }
}
