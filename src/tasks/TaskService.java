package tasks;

import exceptions.TaskNotFoundException;

import java.time.LocalDate;
import java.util.*;

public class TaskService {
    private final Map<Integer, Task> taskMap = new HashMap<>();
    private final List<Task> removedTasks = new ArrayList<>();

    public final void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public final Task remove(int id) {
        try {
            if (!taskMap.containsKey(id)) {
                throw new TaskNotFoundException("Задача не найдена");
            }
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
        removedTasks.add(taskMap.get(id));
        return taskMap.remove(id);
    }

    public final List<Task> getAllByDate(LocalDate localDate) {
        List<Task> allTasksByDate = new ArrayList<>();
        for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
            if (entry.getValue().getDateTime().toLocalDate() == localDate) {
                allTasksByDate.add(entry.getValue());
            }
        }
        try {
            if (allTasksByDate.isEmpty()) {
                throw new TaskNotFoundException("На указанный день задач не найдено");
            }
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return allTasksByDate;
    }

    public final Task updateDescription(int number, String string) {
        try {
            if (!taskMap.containsKey(number)) {
                throw new TaskNotFoundException("Задача не найдена");
            }
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
        taskMap.get(number).setDescription(string);
        return taskMap.get(number);
    }

    public final Task updateTitle(int number, String string){
        try {
            if (!taskMap.containsKey(number)) {
                throw new TaskNotFoundException("Задача не найдена");
            }
        } catch(TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
        taskMap.get(number).setTitle(string);
        return taskMap.get(number);
    }

    public final List<Task> getAllRemovedTasks() {
        return removedTasks;
    }

    public final Map<LocalDate, List<Task>> getAllGroupByDate() {
        Map<LocalDate, List<Task>> allGroupByDate = new TreeMap<>();
        for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
            if (allGroupByDate.containsKey(entry.getValue().getDateTime().toLocalDate())) {
                allGroupByDate.get(entry.getValue().getDateTime().toLocalDate()).add(entry.getValue());
            } else {
                List<Task> tasks = new ArrayList<>();
                tasks.add(entry.getValue());
                allGroupByDate.put(entry.getValue().getDateTime().toLocalDate(), tasks);
            }
        }
        return allGroupByDate;
    }
}