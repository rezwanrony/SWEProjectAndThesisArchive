package com.example.lenovo.sweprojectothesis;

public class AllStudentProject {

    private String projectname;
    private String projectowner;
    private String language;
    private String semester;
    private String year;
    private String project_details;
    private String project_report;
    private String githublink;

    public AllStudentProject(String projectname, String projectowner, String language, String semester, String year) {
        this.projectname = projectname;
        this.projectowner = projectowner;
        this.language = language;
        this.semester = semester;
        this.year = year;
    }

    public AllStudentProject(String projectname, String projectowner, String language, String semester, String year, String project_details, String project_report, String githublink) {
        this.projectname = projectname;
        this.projectowner = projectowner;
        this.language = language;
        this.semester = semester;
        this.year = year;
        this.project_details = project_details;
        this.project_report = project_report;
        this.githublink = githublink;
    }

    public String getProject_details() {
        return project_details;
    }

    public void setProject_details(String project_details) {
        this.project_details = project_details;
    }

    public String getProject_report() {
        return project_report;
    }

    public void setProject_report(String project_report) {
        this.project_report = project_report;
    }

    public String getGithublink() {
        return githublink;
    }

    public void setGithublink(String githublink) {
        this.githublink = githublink;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getProjectowner() {
        return projectowner;
    }

    public void setProjectowner(String projectowner) {
        this.projectowner = projectowner;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
