import controller.Controller;
import view.Menu;
import utilities.Inputter;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        Menu menu = new Menu();
        String[] options = {
                "List all Developers",
                "Add a new Developer",
                "Search for a Developer by ID",
                "Update a Developer's salary by ID",
                "List all Developers by Language",
                "Add a new Project",
                "List all Projects by Developer (Grouped)",
                "Calculate Total Experience by Dev ID",
                "Remove a Developer by ID",
                "Sort Developers by Salary",
                "Save data to files",
                "Quit program"
        };

        int choice;
        do {
            choice = menu.getChoice(options);
            switch (choice) {
                case 1:
                    controller.listAllDevelopers();
                    break;
                case 2:
                    controller.addDeveloper();
                    break;
                case 3:
                    controller.searchDeveloperById();
                    break;
                case 4:
                    controller.updateDeveloperSalary();
                    break;
                case 5:
                    controller.listDevelopersByLanguage();
                    break;
                case 6:
                    controller.addProject();
                    break;
                case 7:
                    controller.listProjectsByDeveloper();
                    break;
                case 8:
                    controller.calculateTotalExperience();
                    break;
                case 9:
                    controller.removeDeveloper();
                    break;
                case 10:
                    controller.sortDevelopersBySalary();
                    break;
                case 11:
                    controller.saveData();
                    break;
                case 12:
                    if (Inputter.getYesNo("Do you want to save data before quitting?")) {
                        controller.saveData();
                    }
                    System.out.println("Goodbye!");
                    break;
            }
        } while (choice != 12);
    }
}