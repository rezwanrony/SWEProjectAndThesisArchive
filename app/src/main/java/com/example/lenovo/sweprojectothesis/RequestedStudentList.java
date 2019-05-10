package com.example.lenovo.sweprojectothesis;

public class RequestedStudentList {

    private String name;
    private String email;
    private String teacher_email;
    private int classroom_id;
    private String classroomname;

    public RequestedStudentList(String name, String email, String teacher_email, int classroom_id, String classroomname) {
        this.name = name;
        this.email = email;
        this.teacher_email = teacher_email;
        this.classroom_id = classroom_id;
        this.classroomname = classroomname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeacher_email() {
        return teacher_email;
    }

    public void setTeacher_email(String teacher_email) {
        this.teacher_email = teacher_email;
    }

    public int getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(int classroom_id) {
        this.classroom_id = classroom_id;
    }

    public String getClassroomname() {
        return classroomname;
    }

    public void setClassroomname(String classroomname) {
        this.classroomname = classroomname;
    }
}
