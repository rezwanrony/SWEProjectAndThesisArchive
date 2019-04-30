package com.example.lenovo.sweprojectothesis;

public class Course {

    private int id;
    private String coursecode;
    private String coursename;

    public Course(String coursecode, String coursename) {
        this.coursecode = coursecode;
        this.coursename = coursename;
    }

    public Course(int id, String coursecode, String coursename) {
        this.id = id;
        this.coursecode = coursecode;
        this.coursename = coursename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
}
