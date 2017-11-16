package com.example.jayjay.doit;

/**
 * Created by Jayjay on 11/13/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public static final String SCHEMA = "notebook";
    public static int VERSION = 3;

    public DbHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // called once when SCHEMA has not been created.
        String noteTable = "CREATE TABLE " + Task.TABLE_NAME + " ( "
                + Task.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Task.COLUMN_CONTENT + " STRING, "
                + Task.COLUMN_DATE + " STRING, "
                + Task.COLUMN_TIME + " STRING );";
        db.execSQL(noteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method will be called when the version is incremented
    }

    public void insertTask(Task t){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Task.COLUMN_CONTENT, t.getContent());
        contentValues.put(Task.COLUMN_DATE, t.getDate());
        contentValues.put(Task.COLUMN_TIME, t.getTime());
        db.insert(Task.TABLE_NAME, null, contentValues);
        db.close();
    }

    public Task queryTask(int id){
        SQLiteDatabase db = getReadableDatabase();
        Task task = new Task();
        Cursor cursor = db.query(Task.TABLE_NAME, null,
                Task.COLUMN_ID + " =? ", new String[]{String.valueOf(id)},
                null, null, null);
        if(cursor.moveToFirst()){
            task.setId(cursor.getInt(cursor.getColumnIndex(Task.COLUMN_ID)));
            task.setContent(cursor.getString(cursor.getColumnIndex(Task.COLUMN_CONTENT)));
            task.setDate(cursor.getString(cursor.getColumnIndex(Task.COLUMN_DATE)));
            task.setTime(cursor.getString(cursor.getColumnIndex(Task.COLUMN_TIME)));
        }
        cursor.close();
        db.close();
        return task;
    }

    public void updateTask(Task t){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Task.COLUMN_CONTENT, t.getContent());
        contentValues.put(Task.COLUMN_DATE, t.getDate());
        contentValues.put(Task.COLUMN_TIME, t.getTime());
        db.update(Task.TABLE_NAME, contentValues,
                Task.COLUMN_ID + " =? ", new String[]{String.valueOf(t.getId())});
        db.close();
    }

    public void deleteTask(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Task.TABLE_NAME, Task.COLUMN_ID + " =? ", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Task> queryAllTasks(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Task.TABLE_NAME, null,
                null, null, null, null, null, null);

        ArrayList<Task> taskArrayList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(Task.COLUMN_ID)));
                task.setContent(cursor.getString(cursor.getColumnIndex(Task.COLUMN_CONTENT)));
                task.setDate(cursor.getString(cursor.getColumnIndex(Task.COLUMN_DATE)));
                task.setTime(cursor.getString(cursor.getColumnIndex(Task.COLUMN_TIME)));
                taskArrayList.add(task);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskArrayList;
    }

    public Cursor queryAllTasksAsCursor(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Task.TABLE_NAME, null,
                null, null, null, null, null, null);

        return cursor;
    }
}
