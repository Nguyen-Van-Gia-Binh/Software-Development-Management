package controller;

import service.DeveloperList;
import service.ProjectList;

public class Controller {
    private final String fileNameDeveloper = "Data/developers.txt";
    private final String fileNameProject = "Data/projects.txt";
    private DeveloperList developerList;
    private ProjectList projectList;

    public Controller() {
        developerList = new DeveloperList();
        projectList = new ProjectList();
        projectList.setDeveloperList(developerList);
        loadFromFile();
    }

    public void loadFromFile() {
        developerList.readFromFile(fileNameDeveloper);
        projectList.readFromFile(fileNameProject);
        if (developerList.isEmpty() || projectList.isEmpty()) {
            System.out.println("Can not load ");
            System.exit(1);
        }
        System.out.println("Load successfully !!");
    }

    public void saveData() {
        developerList.saveToFile(fileNameDeveloper);
        projectList.saveToFile(fileNameProject);
        System.out.println("Save successfully !");
    }

    public void listAllDevelopers() {
        developerList.listAllDevelopers();
    }

    public void addDeveloper() {
        developerList.addNew();
    }

    public void searchDeveloperById() {
        developerList.searchDeveloperById();
    }

    public void updateDeveloperSalary() {
        developerList.updateDeveloperSalary();
    }

    public void listDevelopersByLanguage() {
        developerList.listDevelopersByLanguage();
    }

    public void addProject() {
        projectList.addNew();
    }

    public void listProjectsByDeveloper() {
        projectList.listAllProjectByDeveloper();
    }

    public void calculateTotalExperience() {
        projectList.totalExperienceByDevId();
    }

    public void removeDeveloper() {
        projectList.removeDeveloperById();
    }

    public void sortDevelopersBySalary() {
        developerList.listDeveloperBySalry();
    }

}
