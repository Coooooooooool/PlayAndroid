package com.alex.playandroid.module.main.model;

public class Upgrade {

    private String date;
    private String version;
    private String content;

    public Upgrade() {
    }

    public Upgrade(String date, String version, String content) {
        this.date = date;
        this.version = version;
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
