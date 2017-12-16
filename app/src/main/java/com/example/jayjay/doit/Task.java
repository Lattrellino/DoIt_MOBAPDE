package com.example.jayjay.doit;

/**
 * Created by Jayjay on 04/12/2017.
 */

public class Task {
    public static final String TABLE_NAME = "task";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_ISDONE = "isDone";

    public static final String TASK_DONE = "true";
    public static final String TASK_NOT_DONE = "false";

    private int id;
    private String content;
    private String date;
    private String time;
    private String location;
    private String isDone;

    public String getDone() {
        return isDone;
    }

    public void setDone(String done) {
        isDone = done;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Task(){}

    public Task(int id, String content, String date, String time,String location) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.time = time;
        this.location = location;
        this.isDone = isDone;
    }

    public Task(String content, String date, String time) {
        this.content = content;
        this.date = date;
        this.time = time;
    }
}
