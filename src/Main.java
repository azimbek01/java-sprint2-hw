import manager.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new Managers().getDefault();
        HistoryManager historyManager = new Managers().getDefaultHistory();

        // Создание задачи №1
        Integer idTask1 = taskManager.getNewIdTask();
        Task task1 = new Task("Задача. Продлить подписку", "Продлить подписку",
                              idTask1);
        taskManager.createTask(task1);

        // Создание задачи №2
        Integer idTask2 = taskManager.getNewIdTask();
        Task task2 = new Task("Задача. Проверить почту", "Проверить почту", idTask2);
        taskManager.createTask(task2);

        // Создание эпика №1
        Integer idEpic1 = taskManager.getNewIdEpic();
        Epic epic1 = new Epic("Эпик. Сходить в магазин", "Сходить в магазин",
                              idEpic1);
        taskManager.createEpic(epic1);

        // Создание эпика №2
        Integer idEpic2 = taskManager.getNewIdEpic();
        Epic epic2 = new Epic("Эпик. Сходить в кино", "Сходить в кино", idEpic2);
        taskManager.createEpic(epic2);

        // Создание подзадачи №1
        Integer idSubtask1 = taskManager.getNewIdSubtask();
        Subtask subtask1 = new Subtask("Подзадача. Купить хлеб", "Купить хлеб",
                                       idSubtask1, epic1);
        taskManager.createSubtask(subtask1);

        // Создание подзадачи №2
        Integer idSubtask2 = taskManager.getNewIdSubtask();
        Subtask subtask2 = new Subtask("Подзадача. Купить молоко", "Купить молоко",
                                       idSubtask2, epic1);
        taskManager.createSubtask(subtask2);

        // Создание подзадачи №3
        Integer idSubtask3 = taskManager.getNewIdSubtask();
        Subtask subtask3 = new Subtask("Подзадача. Купить билеты", "Купить билеты",
                                       idSubtask3, epic2);
        taskManager.createSubtask(subtask3);

        // Получение списка всех задач
        taskManager.getAllTasks();

        // Получение списка всех эпиков
        taskManager.getAllEpics();

        // Получение списка всех подзадач определённого эпика
        taskManager.getAllSubtaskOfEpic(epic1);
        taskManager.getAllSubtaskOfEpic(epic2);

        // Получение задачи, эпика, подзадачи по идентификатору
        taskManager.getTaskById(task1);
        taskManager.getEpicById(epic1);
        taskManager.getEpicById(epic2);
        taskManager.getSubtaskById(subtask1);

        // Получение, печать истории поиска задач
        List<Task> historyList = historyManager.getHistory();
        if (!historyList.isEmpty()) {
            System.out.println("История поиска до удаления эпиков, подзадач:");
            for (Task historyObject : historyList) {
                System.out.println("ID=" + historyObject.getId() + " " + historyObject.getName());
            }
        }

        // Обновление задачи, эпика, подзадачи по идентификатору. Объект передаётся в виде параметра
        task1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTaskById(task1);

        epic1.setName("Эпик. Обновленное название");
        taskManager.updateEpicById(epic1);

        subtask1.setStatus(Status.DONE);
        taskManager.updateSubtaskById(subtask1);
        subtask3.setStatus(Status.DONE);
        taskManager.updateSubtaskById(subtask3);

        // Удаление ранее добавленных задач — по идентификатору и всех
        taskManager.deleteTaskById(task1);
        taskManager.deleteEpicById(epic2);
        taskManager.deleteSubtaskById(subtask2);

        // Получение, печать истории поиска задач
        List<Task> historyList1 = historyManager.getHistory();
        if (!historyList.isEmpty()) {
            System.out.println("История поиска после удаления эпиков, подзадач:");
            for (Task historyObject : historyList1) {
                System.out.println("ID=" + historyObject.getId() + " " + historyObject.getName());
            }
        }

        taskManager.deleteAll();
    }
}
