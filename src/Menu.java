import exceptions.TaskNotFoundException;
import tasks.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {

    private static final TaskService TASK_SERVICE = new TaskService();
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}\\:\\d{2}");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void printMenu() {
        System.out.println("1. Добавить задачу;\n2. Удалить задачу;\n3. Получить задачу на указанный день;\n" +
                "4. Редактировать задачу (заголовок или описание);\n5. Получить список всех задач, сгруппированных по дате;\n" +
                "6. Получить список всех удалённых задач;\n0. Выход");
    }

    public static void inputTask(Scanner scanner) {
        scanner.useDelimiter("\n");

        System.out.println("Введите название задачи:");
        String title = scanner.next();

        System.out.println("Введите описание задачи:");
        String description = scanner.next();

        System.out.println("Введите цифру, обозначающую тип задачи (1 - рабочая; 2 - личная):");
        int numberOfType = scanner.nextInt();
        Type type = null;
        if (numberOfType == 1) {
            type = Type.WORK;
        } else {
            type = Type.PERSONAL;
        }

        System.out.println("Введите дату и время задачи в формате dd.MM.yyyy HH:mm:");
        LocalDateTime taskTime = null;
        if (scanner.hasNext(DATE_TIME_PATTERN)) {
            String dateTime = scanner.next(DATE_TIME_PATTERN);
            taskTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        } else {
            taskTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        }

        System.out.println("Введите цифру, обозначающую частоту повторяемости задачи (1 - однократно; 2 - каждый день; " +
                "3 - каждую неделю; 4 - каждый месяц; 5 - каждый год):");
        Task task = null;
        if (scanner.hasNextInt()) {
            int number = scanner.nextInt();
            switch (number) {
                case 2:
                    task = new DailyTask(title, description, type, taskTime);
                    break;
                case 3:
                    task = new WeeklyTask(title, description, type, taskTime);
                    break;
                case 4:
                    task = new MonthlyTask(title, description, type, taskTime);
                    break;
                case 5:
                    task = new YearlyTask(title, description, type, taskTime);
                    break;
                default:
                    task = new OneTimeTask(title, description, type, taskTime);
            }
        }
        TASK_SERVICE.addTask(task);
        System.out.println("Задача добавлена:");
        System.out.println(task);
    }

    public static void deleteTask(Scanner scanner) throws TaskNotFoundException {
        System.out.println("Введи ID задачи, которую хотите удалить:");
        if (scanner.hasNextInt()) {
            int idNumber = scanner.nextInt();
            System.out.println("Задача удалена:");
            System.out.println(TASK_SERVICE.remove(idNumber));
        } else {
            System.out.println("ID введён некорректно");
        }
    }

    public static void getTaskForSpecifiedDay(Scanner scanner) {
        System.out.println("Введите дату задачи в формате dd.MM.yyyy:");
        LocalDate taskTime;
        if (scanner.hasNext(DATE_PATTERN)) {
            String dateTime = scanner.next(DATE_PATTERN);
            taskTime = LocalDate.parse(dateTime, DATE_FORMATTER);
            System.out.println(TASK_SERVICE.getAllByDate(taskTime));
        } else {
            System.out.println("Задачи на день не найдены");
        }
    }

    public static void editTask(Scanner scanner) throws TaskNotFoundException{
        System.out.println("Введите ID задачи, которую хотите отредактировать:");
        int id = scanner.nextInt();
        System.out.println("Введите новый текст для заголовка или описания:");
        String string = scanner.next();
        System.out.println("Введите цифру, обозначающую, что вы хотите отредактировать (1 - заголовок; 2 - описание):");
        if (scanner.hasNextInt()) {
            int number = scanner.nextInt();
            switch (number) {
                case 1:
                    TASK_SERVICE.updateTitle(id, string);
                    break;
                case 2:
                    TASK_SERVICE.updateDescription(id, string);
                    break;
            }
            System.out.println("Задача обновлена");
        } else {
            System.out.println("Задача не найдена");
        }
    }

    public static void getAllTasksGroupByDate() {
        System.out.println(TASK_SERVICE.getAllGroupByDate());
    }

    public static void getListOfAllRemovedTasks() {
        System.out.println(TASK_SERVICE.getAllRemovedTasks());
    }
}