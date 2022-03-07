import manager.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        // Создание двух задач
        UUID idTaskOne = taskManager.getNewIdTask();
        Task taskOne = new Task("Задача №1", "Задача №1", idTaskOne);
        taskManager.createTask(taskOne);

        UUID idTaskTwo = taskManager.getNewIdTask();
        Task taskTwo = new Task("Задача №2", "Задача №2", idTaskTwo);
        taskManager.createTask(taskTwo);

        //Создание Эпика №1 с 3 подзадачами
        UUID idEpicOne = taskManager.getNewIdEpic();
        Epic epicOne = new Epic("Эпик №1", "С 3 подзадачами", idEpicOne);
        taskManager.createEpic(epicOne);

        UUID idSubtaskOne = taskManager.getNewIdSubtask();
        Subtask subtaskOne = new Subtask("Подзадача №1", "Эпик № 1", idSubtaskOne,
                                         epicOne);
        taskManager.createSubtask(subtaskOne);

        UUID idSubtaskTwo = taskManager.getNewIdSubtask();
        Subtask subtaskTwo = new Subtask("Подзадача №2", "Эпик № 1", idSubtaskTwo,
                epicOne);
        taskManager.createSubtask(subtaskTwo);

        UUID idSubtaskThree = taskManager.getNewIdSubtask();
        Subtask subtaskThree = new Subtask("Подзадача №3", "Эпик № 1",
                                           idSubtaskThree, epicOne);
        taskManager.createSubtask(subtaskThree);

        // Создание Эпика №2 без подзадач
        UUID idEpicTwo = taskManager.getNewIdEpic();
        Epic epicTwo = new Epic("Эпик №2", "Без подзадач", idEpicTwo);
        taskManager.createEpic(epicTwo);

        // Запрос созданных задач несколько раз в разном порядке
        taskManager.getTask(taskOne);
        taskManager.getEpic(epicOne);
        taskManager.getSubtask(subtaskOne);
        taskManager.getSubtask(subtaskTwo);
        taskManager.getEpic(epicTwo);

        taskManager.getTask(taskOne);
        taskManager.getEpic(epicOne);
        taskManager.getSubtask(subtaskOne);
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
        taskManager.deleteAll();

        List<Task> taskList = historyManager.getHistory();
        for (Task task: taskList) {
            System.out.println(task.getName());
        }

    }
}
