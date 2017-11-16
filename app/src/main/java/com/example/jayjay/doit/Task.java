package com.example.jayjay.doit;

/**
 * Created by Jayjay on 11/13/2017.
 */

public class Task {
    public static final String TABLE_NAME = "task";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";

    private int id;
    private String content;
    private String date;
    private String time;

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

    public Task(int id, String content, String date, String time) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.time = time;
    }

    public Task(String content, String date, String time) {
        this.content = content;
        this.date = date;
        this.time = time;
    }
}
