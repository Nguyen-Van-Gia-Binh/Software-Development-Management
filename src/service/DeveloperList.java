package service;

import fileUtils.FileUtils;
import model.Developer;
import utilities.Inputter;
import utilities.Validator;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeveloperList extends ArrayList<Developer> implements Iservice<Developer> {
    public DeveloperList() {
    }

    @Override
    public void readFromFile(String fileName) {
        this.clear();
        // DEV001, Nguyen Van A, [Java, C++], 5000
        this.addAll(FileUtils.readFromFile(fileName, (String line) -> {
            // Sử dụng Regex để phân tích dòng dữ liệu một cách linh hoạt và chính xác hơn
            // Pattern: ID, Name, [Languages], Salary
            String regex = "^(.*?)\\s*,\\s*(.*?)\\s*,\\s*\\[(.*?)\\]\\s*,\\s*(.*?)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                try {
                    String devId = matcher.group(1).trim();
                    String fullName = matcher.group(2).trim();
                    String languagesStr = matcher.group(3).trim();
                    String salaryStr = matcher.group(4).trim();

                    // Validate dữ liệu
                    if (!Validator.isValid(devId, Validator.DEV_ID_REGEX))
                        return null;
                    if (!Validator.isValid(fullName, Validator.FULL_NAME_REGEX))
                        return null;
                    if (!Validator.isValid(salaryStr, Validator.SALARY_REGEX))
                        return null;

                    double salaryUSD = Double.parseDouble(salaryStr);

                    ArrayList<String> programmingLanguages = new ArrayList<>();
                    if (!languagesStr.isEmpty()) {
                        String[] langs = languagesStr.split(",");
                        for (String lang : langs) {
                            programmingLanguages.add(lang.trim());
                        }
                    }

                    return new Developer(devId, fullName, programmingLanguages, salaryUSD);
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

    @Override
    public void addNew() {
        String devId;
        while (true) {
            devId = Inputter.getString("Enter Developer ID: ", Validator.DEV_ID_REGEX, false);
            if (this.searchById(devId) == null) {
                break;
            }
            System.out.println("Developer ID already exists!");
        }

        String fullName = Inputter.getString("Enter Developer Name: ", Validator.FULL_NAME_REGEX, false);

        ArrayList<String> programmingLanguages = new ArrayList<>();
        String programmingLanguagesString;
        programmingLanguagesString = Inputter.getString("Enter programming languages (separated by comma): ", null,
                false);
        String[] parts = programmingLanguagesString.trim().split(",");
        for (String language : parts) {
            programmingLanguages.add(language.trim());
        }

        Double salaryUSD = Inputter.getDouble("Enter Salary (USD): ", false);

        this.add(new Developer(devId, fullName, programmingLanguages, salaryUSD));
    }

    /**
     * Return null or Developer if find
     *
     */
    public Developer searchById(String devIdString) {
        for (Developer dev : this) {
            if (dev.getDevId().equalsIgnoreCase(devIdString)) {
                return dev;
            }
        }
        return null;
    }

    public void listAllDevelopers() {
        if (this.isEmpty()) {
            System.out.println("No developers found.");
            return;
        }

        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-25s | %-40s | %-12s |%n", "ID", "Name", "Languages", "Salary ($)");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");

        for (Developer dev : this) {
            String languages = String.join(",", dev.getProgrammingLanguages());
            System.out.printf("| %-10s | %-25s | %-40s | %-12.2f |%n",
                    dev.getDevId(),
                    dev.getFullName(),
                    languages,
                    dev.getSalaryUSD());
        }
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
    }

    public void listDevelopersByLanguage() {
        String input = Inputter.getString("Enter language to search: ", null, false);
        ArrayList<Developer> results = new ArrayList<>();
        for (Developer dev : this) {
            String language = String.join(",", dev.getProgrammingLanguages());
            if (language.toLowerCase().contains(input.toLowerCase()))
                results.add(dev);
        }

        if (results.isEmpty()) {
            System.out.println("No developers found with language: " + input);
            return;
        }

        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-25s | %-40s | %-12s |%n", "ID", "Name", "Languages", "Salary ($)");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");

        for (Developer dev : results) {
            String languages = String.join(",", dev.getProgrammingLanguages());
            System.out.printf("| %-10s | %-25s | %-40s | %-12.2f |%n",
                    dev.getDevId(),
                    dev.getFullName(),
                    languages,
                    dev.getSalaryUSD());
        }
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
    }

    public void listDeveloperBySalry() {
        if (this.isEmpty()) {
            System.out.println("Empty List !");
            return;
        }
        this.sort((dev1, dev2) -> Double.compare(dev1.getSalaryUSD(), dev2.getSalaryUSD()));
        listAllDevelopers();
    }

    public void searchDeveloperById() {
        String devId = Inputter.getString("Enter Dev Id: ", Validator.DEV_ID_REGEX, false);
        Developer dev = searchById(devId);
        if (dev == null) {
            System.out.println("Developer ID does not exist!");
            return;
        }
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-25s | %-40s | %-12s |%n", "ID", "Name", "Languages", "Salary ($)");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        String languages = String.join(",", dev.getProgrammingLanguages());
        System.out.printf("| %-10s | %-25s | %-40s | %-12.2f |%n",
                dev.getDevId(),
                dev.getFullName(),
                languages,
                dev.getSalaryUSD());
    }

    public void updateDeveloperSalary() {
        String devId = Inputter.getString("Enter Dev Id: ", Validator.DEV_ID_REGEX, false);
        Developer dev = searchById(devId);
        if (dev == null) {
            System.out.println("Developer ID does not exist!");
            return;
        }
        Double newSalary;
        while (true) {
            newSalary = Inputter.getDouble("Enter new Salary (USD) > 1000: ", false);
            if (newSalary > 1000) {
                break;
            }
            System.out.println("Salary must be greater than 1000");
        }
        dev.setSalaryUSD(newSalary);
        System.out.println("Salary updated successfully.");
    }

}
