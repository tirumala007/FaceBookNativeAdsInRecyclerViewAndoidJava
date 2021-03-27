package com.example.fbnativeadsrecyclerviewjava;

public class MainModel {

    public String title;
    public String subtitle;
    public String name;
    public String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public MainModel(String title, String subtitle, String name, String number) {
        this.title = title;
        this.subtitle = subtitle;
        this.name = name;
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}