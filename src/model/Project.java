package model;

import java.time.LocalDate;

public class Project {
    private  String projectId;
    private Developer devId;
    private String projectName;
    private int durationMonths;
    private LocalDate startDate;

    public Project(String projectId, Developer devId, String projectName, int durationMonths, LocalDate startDate) {
        this.projectId = projectId;
        this.devId = devId;
        this.projectName = projectName;
        this.durationMonths = durationMonths;
        this.startDate = startDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Developer getDevId() {
        return devId;
    }

    public void setDevId(Developer devId) {
        this.devId = devId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId='" + projectId + '\'' +
                ", devId=" + devId +
                ", projectName='" + projectName + '\'' +
                ", durationMonths=" + durationMonths +
                ", startDate=" + startDate +
                '}';
    }
}
