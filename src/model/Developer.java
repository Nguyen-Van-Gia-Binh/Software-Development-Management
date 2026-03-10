package model;

import java.util.List;

public class Developer {
    private String devId;
    private String fullName;
    private List<String> programmingLanguages;
    private Double salaryUSD;

    public Developer(String devId, String fullName, List<String> programmingLanguages, Double salaryUSD) {
        this.devId = devId;
        this.fullName = fullName;
        this.programmingLanguages = programmingLanguages;
        this.salaryUSD = salaryUSD;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getProgrammingLanguages() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(List<String> programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }

    public Double getSalaryUSD() {
        return salaryUSD;
    }

    public void setSalaryUSD(Double salaryUSD) {
        this.salaryUSD = salaryUSD;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "devId='" + devId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", programmingLanguages=" + programmingLanguages +
                ", salary_USD=" + salaryUSD +
                '}';
    }
}
