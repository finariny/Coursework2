import tasks.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {

    private static final TaskService taskService = new TaskService();
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}\\:\\d{2}");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static void printMenu() {
        System.out.println("1. Добавить задачу;\n2. Удалить задачу;\n3. Получить задачу на указанный день;\n0. Выход");
    }

    public static void inputTask(Scanner scanner) {
        scanner.useDelimiter("\n");

        System.out.println("Введите название задачи:");
        String title = scanner.next();

        System.out.println("Введите описание задачи:");
        String description = scanner.next();

        System.out.println("Введите цифру, обозначающую тип задачи (1 - рабочая; 2 - личная):");
        int numberOfType = scanner.nextInt();
        Type type;
        if (numberOfType == 1) {
            type = Type.PERSONAL;
        } else {
            type = Type.WORK;
        }

        System.out.println("Введите дату и время задачи в формате dd.MM.yyyy HH:mm:");
        LocalDateTime taskTime;
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
        taskService.addTask(task);
        System.out.println("Задача добавлена");
    }

    public static void deleteTask(Scanner scanner) {
        System.out.println("Введи ID задачи, которую хотите удалить:");
        int idNumber = scanner.nextInt();
        taskService.remove(idNumber);
        scanner.close();
    }

    public static void getTaskForSpecifiedDay(Scanner scanner) {
        System.out.println("Введите дату и время задачи в формате dd.MM.yyyy HH:mm:");
        LocalDateTime taskTime = null;
        if (scanner.hasNext(DATE_TIME_PATTERN)) {
            String dateTime = scanner.next(DATE_TIME_PATTERN);
            taskTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        } else {
            System.out.println("Задачи на день не найдены");
        }
        scanner.close();
        taskService.getAllByDate(taskTime.toLocalDate());
    }
}