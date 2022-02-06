package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.Collection;

public interface TaskManager {
    // Счетчик ИД задач, эпиков и подзадач
    Integer getNewIdTask();

    Integer getNewIdEpic();

    Integer getNewIdSubtask();

    // Получение списка всех задач.
    Collection<Task> getAllTasks();

    // Получение списка всех эпиков.
    Collection<Epic> getAllEpics();

    // Получение списка всех подзадач определённого эпика.
    ArrayList<Subtask> getAllSubtaskOfEpic(Epic epic);

    // Получение задачи любого типа по идентификатору.
    Task getTask(Task task);

    Epic getEpic(Epic epic);

    Subtask getSubtask(Subtask subtask);

    /* Добавление новой задачи, эпика и подзадачи. Сам объект должен передаваться в качестве
    параметра. */
    void createTask(Task task);

    public void createEpic(Epic epic);

    public void createSubtask(Subtask subtask);

    /* Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде
    параметра. */
    void updateTaskById(Task task);

    void updateEpicById(Epic epic);

    // Обновление всех полей подзадачи. Обновление статуса в связанном эпике objectEpic
    void updateSubtaskById(Subtask subtask);

    // Удаление ранее добавленных задач — всех и по идентификатору.
    void deleteAll();

    void deleteTask(Task task);

    // Удаление эпика из хэш-таблицы epics и подзадачи из хэш-таблицы subtasks
    void deleteEpic(Epic epic);

    /* Удаление подзадачи из хэш-таблицы subtasks и из списка subtasksEpic в эпике. Удаление эпика
    из хэш-таблицы epics при пустом списке */
    void deleteSubtask(Subtask subtask);
}
