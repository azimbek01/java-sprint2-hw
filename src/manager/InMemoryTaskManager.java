package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    Integer idTask = 0;
    Integer idEpic = 0;
    Integer idSubtask = 0;
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    @Override
    public Integer getNewIdTask() {
        return ++idTask;
    }

    @Override
    public Integer getNewIdEpic() {
        return ++idEpic;
    }

    @Override
    public Integer getNewIdSubtask() {
        return ++idSubtask;
    }

    // Получение списка всех задач.
    @Override
    public Collection<Task> getAllTasks() {
        if (!tasks.isEmpty()) {
            return tasks.values();
        } else {
            return null;
        }
    }

    // Получение списка всех эпиков.
    @Override
    public Collection<Epic> getAllEpics() {
        if (!epics.isEmpty()) {
            return epics.values();
        }
        return null;
    }

    // Получение списка всех подзадач определённого эпика.
    @Override
    public ArrayList<Subtask> getAllSubtaskOfEpic(Epic epic) {
        if (!epics.isEmpty()) {
            Epic objectEpic = epics.get(epic.getId());
            return objectEpic.getSubtasks();
        } else {
            return null;
        }
    }

    // Получение задачи любого типа по идентификатору.
    @Override
    public Task getTaskById(Task task) {
        if (tasks.containsKey(task.getId())) {
            return tasks.get(task.getId());
        } else {
            return null;
        }
    }

    @Override
    public Epic getEpicById(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            inMemoryHistoryManager.add(epic);
            return epics.get(epic.getId());
        } else {
            return null;
        }
    }

    @Override
    public Subtask getSubtaskById(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            inMemoryHistoryManager.add(subtask);
            return subtasks.get(subtask.getId());
        } else {
            return null;
        }
    }

    /* Добавление новой задачи, эпика и подзадачи. Сам объект должен передаваться в качестве
    параметра. */
    @Override
    public void createTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            tasks.put(idTask, task);
        } else {
            System.out.println("Задача с ид=" + task.getId() + " уже существует.");
        }

    }

    @Override
    public void createEpic(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            epics.put(idEpic, epic);
        } else {
            System.out.println("Эпик с ид=" + epic.getId() + " уже существует.");
        }
    }

    @Override
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

    /* Обновление задачи, эпика по идентификатору. Новая версия объекта передаётся в виде
    параметра. */
    @Override
    public void updateTaskById(Task task) {
        Task objectTask = tasks.get(task.getId());
        objectTask.setName(task.getName());
        objectTask.setDescription(task.getDescription());
        objectTask.setStatus(task.getStatus());
    }

    @Override
    public void updateEpicById(Epic epic) {
        Epic objectEpic = epics.get(epic.getId());
        objectEpic.setName(epic.getName());
        objectEpic.setDescription(epic.getDescription());
    }

    // Обновление всех полей подзадачи. Обновление статуса в связанном эпике objectEpic
    @Override
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
                if (subtaskValue.getStatus().equals(Status.NEW)) {
                    newStatus = false;
                    break;
                }
            }
            for (Subtask subtaskValue : subtasksEpic) {
                if (subtaskValue.getStatus().equals(Status.DONE)) {
                    doneStatus = false;
                    break;
                }
            }
            if (newStatus) {
                objectEpic.setStatus(Status.NEW);
            } else if (doneStatus){
                objectEpic.setStatus(Status.DONE);
            } else {
                objectEpic.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    // Удаление всех ранее добавленных задач. Удаление истории поиска.
    @Override
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
        if (!inMemoryHistoryManager.getHistory().isEmpty()) {
            inMemoryHistoryManager.getHistory().clear();
        }
    }

    // Удаление по идентификатору ранее добавленных задач.
    @Override
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

    // Удаление эпика из таблицы epics и подзадачи из таблицы subtasks.
    @Override
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
                // Удаление из истории просмотров historyList эпика и связанных подзадач
                List<Task> historyList = inMemoryHistoryManager.getHistory();
                if (!historyList.isEmpty()) {
                    if (historyList.contains(epic)) {
                        historyList.remove(epic);
                    }
                    for (Task subtask : subtasksEpic) {
                        if (historyList.contains(subtask)) {
                            historyList.remove(subtask);
                        }
                    }
                }
            } else {
                System.out.println("Эпик с ид=" + epic.getId() + " не существует.");
            }
        } else {
            System.out.println("Таблица эпиков пуста.");
        }

    }

    // Удаление подзадачи из таблицы subtasks
    @Override
    public void deleteSubtaskById(Subtask subtask) {
        if (!subtasks.isEmpty()) {
            if (subtasks.containsKey(subtask.getId())) {
                subtasks.remove(subtask.getId(), subtask);
                // Удаление подзадачи из истории просмотров historyList
                List<Task> historyList = inMemoryHistoryManager.getHistory();
                if (!historyList.isEmpty()) {
                    if (historyList.contains(subtask)) {
                        historyList.remove(subtask);
                    }
                }
                // Удаление подзадачи из списка subtasksEpic в эпике
                Epic epic = subtask.getEpic();
                ArrayList<Subtask> subtasksEpic = epic.getSubtasks();
                for(Subtask subtaskValue : subtasksEpic) {
                    if (subtaskValue.equals(subtask)) {
                        subtasksEpic.remove(subtask);
                        break;
                    }
                }
                // Удаление эпика при пустом списке подзадач subtasksEpic
                if(subtasksEpic.isEmpty()) {
                    epics.remove(epic.getId(), epic);
                    // Удаление эпика из истории просмотров historyList
                    if (!historyList.isEmpty()) {
                        if (historyList.contains(epic)) {
                            historyList.remove(epic);
                        }
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
