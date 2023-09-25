/* 
Name - PRATHEEK U B
Reg. No. - 20ETIS411018

Hi EI! I have created a simple To-Do List Manager where users can add tasks, delete, mark them as completed. 
The user can also view all tasks or filter them based on their completion status.
*/

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

class Task {
    private String description;
    private boolean completed;
    private Date dueDate;

    public Task(String description, Date dueDate) {
        this.description = description;
        this.completed = false;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }

    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return description + " - " + (completed ? "Completed" : "Pending") +
                (dueDate != null ? ", Due: " + sdf.format(dueDate) : "");
    }
}

class TaskListManager {
    private List<Task> tasks = new ArrayList<>();
    private Stack<List<Task>> undoStack = new Stack<>();
    private Stack<List<Task>> redoStack = new Stack<>();

    public void addTask(Task task) {
        tasks.add(task);
        saveState();
    }

    public void markTaskCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markCompleted();
            saveState();
        } else {
            System.out.println("Invalid task index.");
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveState();
        } else {
            System.out.println("Invalid task index.");
        }
    }

    public List<Task> filterTasks(boolean showCompleted) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (showCompleted || !task.isCompleted()) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(new ArrayList<>(tasks));
            tasks = undoStack.pop();
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(new ArrayList<>(tasks));
            tasks = redoStack.pop();
        } else {
            System.out.println("Nothing to redo.");
        }
    }

    private void saveState() {
        undoStack.push(new ArrayList<>(tasks));
        redoStack.clear();
    }
    
}

class Todo {
    public static void main(String[] args) {
        TaskListManager taskListManager = new TaskListManager();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTo-Do ");
            System.out.println("-----------------------");
            System.out.println("1. Add Task");
            System.out.println("2. Mark Task as Completed");
            System.out.println("3. Delete Task");
            System.out.println("4. View All Tasks");
            System.out.println("5. View Pending Tasks");
            System.out.println("6. Undo");
            System.out.println("7. Redo");
            System.out.println("8. Exit");
            System.out.println("");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter due date (yyyy/MM/dd, leave empty if none): ");
                    String dueDateStr = scanner.nextLine();
                    Date dueDate = null;
                    try {
                        if (!dueDateStr.isEmpty()) {
                            dueDate = sdf.parse(dueDateStr);
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Task added without a due date.");
                    }
                    Task newTask = new Task(description, dueDate);
                    taskListManager.addTask(newTask);
                    break;
                case 2:
                    System.out.print("Enter task index to mark as completed: ");
                    int taskIndexToComplete = scanner.nextInt();
                    taskListManager.markTaskCompleted(taskIndexToComplete - 1);
                    break;
                case 3:
                    System.out.print("Enter task index to delete(task index starts from 1): ");
                    int taskIndexToDelete = scanner.nextInt();
                    taskListManager.deleteTask(taskIndexToDelete - 1);
                    break;
                case 4:
                    System.out.println("All Tasks:");
                    taskListManager.filterTasks(true).forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("Pending Tasks:");
                    taskListManager.filterTasks(false).forEach(System.out::println);
                    break;
                case 6:
                    taskListManager.undo();
                    break;
                case 7:
                    taskListManager.redo();
                    break;
                case 8:
                    System.out.println("Exiting... ");
                    System.out.println("Thank you for visiting!!");
                    System.out.println("CODE BY PRATHEEK");
                    System.out.println("************************\n");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}