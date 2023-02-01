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

    public final Task remove(int id)  {
        if (!taskMap.containsKey(id)) {
            throw new TaskNotFoundException("Задача по ID: " + id + " не найдена");
        }
        removedTasks.add(taskMap.get(id));
        return taskMap.remove(id);
    }

    public final List<Task> getAllByDate(LocalDate localDate) {
        List<Task> allTasksByDate = new ArrayList<>();
        for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
            if (entry.getValue().appearsIn(localDate)) {
                allTasksByDate.add(entry.getValue());
            }
        }
        return allTasksByDate;
    }

    public final Task updateDescription(int id, String text) {
        if (!taskMap.containsKey(id)) {
            throw new TaskNotFoundException("Задача по ID: " + id + " не найдена");
        }
        taskMap.get(id).setDescription(text);
        return taskMap.get(id);
    }

    public final Task updateTitle(int id, String text) {
        if (!taskMap.containsKey(id)) {
            throw new TaskNotFoundException("Задача по ID: " + id + " не найдена");
        }
        taskMap.get(id).setTitle(text);
        return taskMap.get(id);
    }

    public List<Task> getAllRemovedTasks() {
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