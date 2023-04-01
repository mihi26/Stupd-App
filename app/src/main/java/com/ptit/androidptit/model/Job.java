package com.ptit.androidptit.model;

import java.util.ArrayList;
import java.util.List;

public class Job {
    private int id;
    private String name;
    private String description;
    private Boolean isMale;
    private String date;

    public Job(String name, String description, Boolean isMale, String date) {
        this.name = name;
        this.description = description;
        this.isMale = isMale;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsMale() {
        return isMale;
    }

    public String getDate() {
        return date;
    }

    public void update(Job job) {
        name = job.name;
        description = job.description;
        isMale = job.isMale;
        date = job.date;
    }

    public Boolean matched(String search) {
        List<String> stringList = new ArrayList<>();
        stringList.add(name);
        for (String s: stringList) {
            if (s.toUpperCase().contains(search.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
