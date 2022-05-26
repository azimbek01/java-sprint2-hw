import manager.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        // Создание двух задач
        Integer idTaskOne = taskManager.getNewIdTask();
        Task taskOne = new Task("Задача1", "Задача1", idTaskOne, Type.TASK);
        taskManager.createTask(taskOne);

        Integer idTaskTwo = taskManager.getNewIdTask();
        Task taskTwo = new Task("Задача №2", "Задача №2", idTaskTwo, Type.TASK);
        taskManager.createTask(taskTwo);

        //Создание Эпика №1 с 3 подзадачами
        Integer idEpicOne = taskManager.getNewIdTask();
        Epic epicOne = new Epic("Эпик №1", "С 3 подзадачами", idEpicOne, Type.EPIC);
        taskManager.createEpic(epicOne);

        Subtask subtask1 = new Subtask("Subtask1", "Subtask1", taskManager.getNewIdTask(),
                                        epicOne.getId(), Type.SUBTASK);

        Subtask subtask2 = new Subtask("Subtask2", "Subtask2", taskManager.getNewIdTask(),
                                        epicOne.getId(), Type.SUBTASK);

        // Создание Эпика №2 без подзадач
        Integer idEpicTwo = taskManager.getNewIdTask();
        Epic epicTwo = new Epic("Эпик №2", "Без подзадач", idEpicTwo, Type.EPIC);
        taskManager.createEpic(epicTwo);

        // Запрос созданных задач несколько раз в разном порядке
        taskManager.getTask(taskOne);
        taskManager.getEpic(epicOne);
        taskManager.getSubtask(subtask1);
        taskManager.getSubtask(subtask2);
        taskManager.getEpic(epicTwo);

        taskManager.getTask(taskOne);
        taskManager.getEpic(epicOne);
        taskManager.getSubtask(subtask1);
        taskManager.getEpic(epicTwo);

        // Удаление задачи из истории
//        taskManager.deleteTask(taskOne);
//        taskManager.deleteSubtask(subtaskOne);
//        taskManager.deleteEpic(epicOne);
//
//        // Удаление эпика №1 с тремя подзадачами
//        taskManager.deleteEpic(epicOne);
//
//        // Удаление всех задач, эпиков, подзадач. Удаление всей истории поиска
//        taskManager.deleteAll();

        List<Task> taskList = historyManager.getHistory();
        for (Task task : taskList) {
            System.out.println(task.getName());
        }

    }
}
