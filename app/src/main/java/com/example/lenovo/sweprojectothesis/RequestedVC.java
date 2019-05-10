package com.example.lenovo.sweprojectothesis;

public class RequestedVC {

    private int id;
    private String name;
    private String createdby;
    private String student_email;
    private int status;

    public RequestedVC(int id, String name, String createdby, String student_email, int status) {
        this.id = id;
        this.name = name;
        this.createdby = createdby;
        this.student_email = student_email;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
