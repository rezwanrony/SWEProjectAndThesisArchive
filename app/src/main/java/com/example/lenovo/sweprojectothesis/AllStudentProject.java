package com.example.lenovo.sweprojectothesis;

public class AllStudentProject {

    private String projectname;
    private String projectowner;
    private String language;
    private String semester;
    private String year;

    public AllStudentProject(String projectname, String projectowner, String language, String semester, String year) {
        this.projectname = projectname;
        this.projectowner = projectowner;
        this.language = language;
        this.semester = semester;
        this.year = year;
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
