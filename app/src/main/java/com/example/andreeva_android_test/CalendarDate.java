package com.example.andreeva_android_test;

import java.sql.Timestamp;

public class CalendarDate {
    private String name, description;
    private Timestamp date_start, date_finish;

    CalendarDate() { }
    CalendarDate(String name, String description, Timestamp date_start, Timestamp date_finish) {
        this.name = name;
        this.description = description;
        this.date_start = date_start;
        this.date_finish = date_finish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate_start(){
        return date_start;
    }

    public void setDate_start(Timestamp date_start){
        this.date_start = date_start;
    }

    public Timestamp getDate_finish(){
        return date_finish;
    }

    public void setDate_finish(Timestamp date_finish){
        this.date_finish = date_finish;
    }

    @Override
    public String toString() {
        return "Начало: " + date_start + "\nКонец: " + date_start + "\nНазвание:" + name + "\nОписание:" + description;
    }
}
