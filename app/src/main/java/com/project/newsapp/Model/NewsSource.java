package com.project.newsapp.Model;

public class NewsSource {

    private String id;
    private String name;

    public NewsSource(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public NewsSource() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
