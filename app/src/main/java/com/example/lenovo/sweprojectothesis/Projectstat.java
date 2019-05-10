package com.example.lenovo.sweprojectothesis;

public class Projectstat {

    private String programming_language;
    private int count;

    public Projectstat(String programming_language, int count) {
        this.programming_language = programming_language;
        this.count = count;
    }

    public String getProgramming_language() {
        return programming_language;
    }

    public void setProgramming_language(String programming_language) {
        this.programming_language = programming_language;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
