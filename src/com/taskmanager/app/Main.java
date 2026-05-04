package com.taskmanager.app;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager(scanner);

        int choice;

        do {
            printMenu();
            choice = readMenuChoice(scanner);

            switch (choice) {
                case 1:
                    taskManager.addTask();
                    break;
                case 2:
                    taskManager.viewAllTasks();
                    break;
                case 3:
                    taskManager.markTaskAsCompleted();
                    break;
                case 4:
                    taskManager.deleteTask();
                    break;
                case 5:
                    taskManager.searchTaskByTitle();
                    break;
                case 6:
                    taskManager.showOverdueTasks();
                    break;
                case 7:
                    taskManager.sortTasksByDeadline();
                    break;
                case 8:
                    taskManager.sortTasksByPriority();
                    break;
                case 9:
                    taskManager.showStatistics();
                    break;
                case 10:
                    taskManager.showPendingTasks();
                    break;
                case 11:
                    taskManager.clearAllTasks();
                    break;
                case 0:
                    System.out.println("\nTasks saved. Exiting the program.");
                    break;
                case 12:
                    taskManager.exitAndClearAll();
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }

        } while (choice != 0 && choice != 12);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== Smart Task Manager =====");
        System.out.println("1. Add Task");
        System.out.println("2. View All Tasks");
        System.out.println("3. Mark Task as Completed");
        System.out.println("4. Delete Task");
        System.out.println("5. Search Task by Title");
        System.out.println("6. Show Overdue Tasks");
        System.out.println("7. Sort Tasks by Deadline");
        System.out.println("8. Sort Tasks by Priority");
        System.out.println("9. Show Task Statistics");
        System.out.println("10. Show Pending Tasks");
        System.out.println("11. Clear All Tasks");
        System.out.println("0. Exit and Save");
        System.out.println("12. Exit and Clear All");
    }

    private static int readMenuChoice(Scanner scanner) {
        while (true) {
            System.out.print("Choose an option: ");
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= 0 && choice <= 12) {
                    return choice;
                } else {
                    System.out.println("Please enter a number between 0 and 12.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers only.");
            }
        }
    }
}