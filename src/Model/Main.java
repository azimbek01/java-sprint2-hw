package Model;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        // Добавление новых задач
        Integer idTask1 = taskManager.getNewIdTask();
        Task task1 = new Task("Имя 1", "Описание 1", idTask1);
        taskManager.createTask(task1);
        Integer idTask2 = taskManager.getNewIdTask();
        Task task2 = new Task("Имя 2", "Описание 2", idTask2);
        taskManager.createTask(task2);

        // Добавление новых эпиков
        Integer idEpic1 = taskManager.getNewIdEpic();
        Epic epic1 = new Epic("Имя эпика 1", "Описание эпика 1", idEpic1);
        taskManager.createEpic(epic1);
        Integer idEpic2 = taskManager.getNewIdEpic();
        Epic epic2 = new Epic("Имя эпика 2", "Описание эпика 2", idEpic2);
        taskManager.createEpic(epic2);

        // Добавление новых подзадач
        Integer idSubtask1 = taskManager.getNewIdSubtask();
        Subtask subtask1 = new Subtask("Имя подзадачи 1", "Описание подзадачи 1",
                                       idSubtask1, epic1);
        taskManager.createSubtask(subtask1);
        Integer idSubtask2 = taskManager.getNewIdSubtask();
        Subtask subtask2 = new Subtask("Имя подзадачи 2", "Описание подзадачи 2",
                                       idSubtask2, epic1);
        taskManager.createSubtask(subtask2);
        Integer idSubtask3 = taskManager.getNewIdSubtask();
        Subtask subtask3 = new Subtask("Имя подзадачи 3", "Описание подзадачи 3",
                                       idSubtask3, epic2);
        taskManager.createSubtask(subtask3);

        // Получение списка всех задач.
        taskManager.getAllTasks();

        // Получение списка всех эпиков.
        taskManager.getAllEpics();

        // Получение списка всех подзадач определённого эпика.
        taskManager.getAllSubtaskOfEpic(epic1);
        taskManager.getAllSubtaskOfEpic(epic2);

        // Получение задачи любого типа по идентификатору.
        taskManager.getTaskById(task1);
        taskManager.getEpicById(epic1);
        taskManager.getSubtaskById(subtask1);

        // Обновление задачи любого типа по идентификатору. Объект передаётся в виде параметра.
        task1.setStatus("IN_PROGRESS");
        taskManager.updateTaskById(task1);
        epic1.setName("Новое название эпика");
        taskManager.updateEpicById(epic1);
        subtask1.setStatus("DONE");
        taskManager.updateSubtaskById(subtask1);
        subtask3.setStatus("DONE");
        taskManager.updateSubtaskById(subtask3);

        // Удаление ранее добавленных задач — всех и по идентификатору.
        taskManager.deleteTaskById(task1);
        taskManager.deleteEpicById(epic2);
        taskManager.deleteSubtaskById(subtask1);
        taskManager.deleteSubtaskById(subtask2);
        taskManager.deleteAll();
    }
}
