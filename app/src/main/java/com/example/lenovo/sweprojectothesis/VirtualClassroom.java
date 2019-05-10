package com.example.lenovo.sweprojectothesis;

public class VirtualClassroom {
    private int id;
    private String name;
    private String createdby;

    public VirtualClassroom(int id, String name, String createdby) {
        this.id = id;
        this.name = name;
        this.createdby = createdby;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VirtualClassroom(String name) {
        this.name = name;
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
}
