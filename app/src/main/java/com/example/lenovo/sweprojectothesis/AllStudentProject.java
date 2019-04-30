package com.example.lenovo.sweprojectothesis;

public class AllStudentProject {

    private String projectname;
    private String projectowner;
    private String language;
    private String course;
    private String semester;

    public AllStudentProject(String projectname, String projectowner, String language, String course, String semester) {
        this.projectname = projectname;
        this.projectowner = projectowner;
        this.language = language;
        this.course = course;
        this.semester = semester;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
