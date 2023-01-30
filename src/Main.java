import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                Menu.printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menuNumber = scanner.nextInt();
                    switch (menuNumber) {
                        case 1:
                            Menu.inputTask(scanner);
                            break;
                        case 2:
                            Menu.deleteTask(scanner);
                            break;
                        case 3:
                            Menu.getTaskForSpecifiedDay(scanner);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }
}