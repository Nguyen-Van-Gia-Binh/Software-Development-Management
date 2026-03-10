package service;

import fileUtils.FileUtils;
import model.Developer;
import model.Project;
import utilities.Inputter;
import utilities.Validator;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectList extends ArrayList<Project> implements Iservice<Project> {

    private DeveloperList developerList;

    public ProjectList() {
    }

    public void setDeveloperList(DeveloperList developerList) {
        this.developerList = developerList;
    }

    @Override
    public void addNew() {
        //
        String projectId;
        while (true) {
            projectId = Inputter.getString("Enter project ID: ", Validator.PROJ_ID_REGEX, false);
            if (searchProjectById(projectId) != null) {
                System.out.println("Project Id exist !");
                continue;
            }
            break;
        }

        // Dev Id
        String devId;
        while (true) {
            developerList.listAllDevelopers();
            devId = Inputter.getString("Enter Dev ID: ", Validator.PROJ_ID_REGEX, false);
            if (developerList.searchById(devId) == null) {
                System.out.println("Dev ID does not exist !");
                continue;
            }
            break;
        }

        // Project name
        String projectName = Inputter.getString("Enter project name: ", null, false);

        // Duration
        int duration = Inputter.getInt("Enter duration > 1 :", 1, Integer.MAX_VALUE);

        // Start day
        LocalDate startDay;
        while (true) {
            startDay = Inputter.getLocalDate("Enter start day in the future: ", false);
            if (!(startDay.isAfter(LocalDate.now()))) {
                System.out.println("Start day must  in the future !");
                continue;
            }
            break;

        }

        Project newProject = new Project(projectId, developerList.searchById(devId), projectName, duration, startDay);
        this.add(newProject);
        System.out.println("Add project successfully!");
    }

    @Override
    public void readFromFile(String fileName) {
        this.clear();
        this.addAll(FileUtils.readFromFile(fileName, (String line) -> {
            // PROJ08, DEV012, Data Warehouse, 0, 01/06/2026

            String[] parts = line.split(",");

            if (parts.length == 5) {
                try {
                    String projectId = parts[0].trim();
                    String devIdString = parts[1].trim();
                    String projectName = parts[2].trim();
                    String durationMonthsString = parts[3].trim();
                    String startDateString = parts[4].trim();

                    Developer devId = developerList.searchById(devIdString);

                    // Validation
                    if (!Validator.isValid(projectId, Validator.PROJ_ID_REGEX))
                        return null;
                    if (projectName.isEmpty())
                        return null;
                    if (!Validator.isValid(durationMonthsString, Validator.DURATION_REGEX))
                        return null;
                    if (devId == null)
                        return null;

                    // Parse
                    int durationMonths = Integer.parseInt(durationMonthsString);
                    LocalDate startDate = LocalDate.parse(startDateString, Validator.DATE_TIME_FORMATTER);

                    return new Project(projectId, devId, projectName, durationMonths, startDate);
                } catch (Exception e) {
                    return null;
                }
            }
            return null;
        }));
    }

    @Override
    public void saveToFile(String fileName) {
        FileUtils.writeToFile(fileName, this);
    }

    public void listAllProjectByDeveloper() {
        if (this.isEmpty()) {
            System.out.println("No developers found.");
            return;
        }

        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-25s | %-40s | %-12s |%n", "ID", "Name", "Languages", "Salary ($)");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");

        for (Developer dev : developerList) {
            String languages = String.join(",", dev.getProgrammingLanguages());
            System.out.printf("| %-10s | %-25s | %-40s | %-12.2f |%n",
                    dev.getDevId(),
                    dev.getFullName(),
                    languages,
                    dev.getSalaryUSD());
            listProjectsByDeveloperId(dev.getDevId());
        }
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
    }

    public Project searchProjectById(String projectId) {
        for (Project project : this) {
            if (project.getProjectId().equalsIgnoreCase(projectId)) {
                return project;
            }
        }
        return null;
    }

    public void listProjectsByDeveloperId(String devId) {
        boolean flag = false;
        for (Project prj : this) {
            if (prj.getDevId().getDevId().equalsIgnoreCase(devId)) {
                System.out.printf("Project: %s %d %s", prj.getProjectId(), prj.getDurationMonths(),
                        prj.getStartDate().toString());
            }
            flag = true;
        }
        if (!flag) {
            System.out.println("Not found project !");
        }
    }

    public boolean isDeveloperJoinProject(Developer dev) {
        for (Project prj : this) {
            if (prj.getDevId().equals(dev)) {
                return true;
            }
        }
        return false;
    }

    public void totalExperienceByDevId() {
        // Dev Id
        String devId;
        while (true) {
            developerList.listAllDevelopers();
            devId = Inputter.getString("Enter Dev ID: ", Validator.PROJ_ID_REGEX, false);
            if (developerList.searchById(devId) == null) {
                System.out.println("Dev ID does not exist !");
                continue;
            }
            break;
        }

        int sum = 0;
        boolean flag = false;
        for (Project prj : this) {
            if (prj.getDevId().getDevId().equalsIgnoreCase(devId)) {
                LocalDate endDate = prj.getStartDate().plusMonths(prj.getDurationMonths());
                if (endDate.isBefore(LocalDate.now())) {
                    sum += prj.getDurationMonths();
                } else {
                    sum += LocalDate.now().getMonthValue() - prj.getStartDate().getMonthValue();
                }
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("Developer does not join any project !");
        } else {
            System.out.println("Total experience: " + sum + " months.");
        }
    }

    public void removeDeveloperById() {
        developerList.listAllDevelopers();
        String devId = Inputter.getString("Enter Dev ID: ", Validator.PROJ_ID_REGEX, false);
        if (developerList.searchById(devId) == null) {
            System.out.println("Dev ID does not exist !");
            return;
        }
        Developer dev = developerList.searchById(devId);

        if (isDeveloperJoinProject(dev)) {
            System.out.println("Cannot delete: Developer is assigned to projects. !");
            return;
        }
        developerList.remove(dev);
        System.out.println("Remove successful !");
    }
}
