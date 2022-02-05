package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public interface TaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    Integer idTask = 0;
    Integer idEpic = 0;
    Integer idSubtask = 0;

    // Счетчик ИД задач, эпиков и подзадач
    public Integer getNewIdTask();

    public Integer getNewIdEpic();

    public Integer getNewIdSubtask();

    // Получение списка всех задач.
    public Collection<Task> getAllTasks();

    // Получение списка всех эпиков.
    public Collection<Epic> getAllEpics();

    // Получение списка всех подзадач определённого эпика.
    public ArrayList<Subtask> getAllSubtaskOfEpic(Epic epic);

    // Получение задачи любого типа по идентификатору.
    public Task getTaskById(Task task);

    public Epic getEpicById(Epic epic);

    public Subtask getSubtaskById(Subtask subtask);

    /* Добавление новой задачи, эпика и подзадачи. Сам объект должен передаваться в качестве
    параметра. */
    public void createTask(Task task);

    public void createEpic(Epic epic);

    public void createSubtask(Subtask subtask);

    /* Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде
    параметра. */
    public void updateTaskById(Task task);

    public void updateEpicById(Epic epic);

    // Обновление всех полей подзадачи. Обновление статуса в связанном эпике objectEpic
    public void updateSubtaskById(Subtask subtask);

    // Удаление ранее добавленных задач — всех и по идентификатору.
    public void deleteAll();

    public void deleteTaskById(Task task);

    // Удаление эпика из хэш-таблицы epics и подзадачи из хэш-таблицы subtasks
    public void deleteEpicById(Epic epic);

    /* Удаление подзадачи из хэш-таблицы subtasks и из списка subtasksEpic в эпике. Удаление эпика
    из хэш-таблицы epics при пустом списке */
    public void deleteSubtaskById(Subtask subtask);

    //Отображение последних просмотренных задач
    //public List <Task> history(Task task);
}
