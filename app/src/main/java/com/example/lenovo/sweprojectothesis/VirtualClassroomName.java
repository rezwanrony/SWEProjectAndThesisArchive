package com.example.lenovo.sweprojectothesis;

public class VirtualClassroomName {
    private String section;
    private String semester;
    private String course_name;

    public VirtualClassroomName(String section, String semester, String course_name) {
        this.section = section;
        this.semester = semester;
        this.course_name = course_name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}
