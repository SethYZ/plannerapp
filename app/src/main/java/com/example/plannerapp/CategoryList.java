package com.example.plannerapp;

import java.io.Serializable;

public class CategoryList implements Serializable {

    private String title;
    private int duration;

    public CategoryList(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
