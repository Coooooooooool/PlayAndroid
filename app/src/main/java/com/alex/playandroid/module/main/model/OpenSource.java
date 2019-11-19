package com.alex.playandroid.module.main.model;

public class OpenSource {

    private String name;
    private String desc;
    private String link;

    public OpenSource() {
    }

    public OpenSource(String name, String desc, String link) {
        this.name = name;
        this.desc = desc;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
