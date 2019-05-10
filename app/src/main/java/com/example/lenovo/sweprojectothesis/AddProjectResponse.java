package com.example.lenovo.sweprojectothesis;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddProjectResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    private final static long serialVersionUID = -4012006159652892359L;

    /**
     * No args constructor for use in serialization
     *
     */
    public AddProjectResponse() {
    }

    /**
     *
     * @param status
     * @param msg
     */
    public AddProjectResponse(int status, String msg) {
        super();
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
