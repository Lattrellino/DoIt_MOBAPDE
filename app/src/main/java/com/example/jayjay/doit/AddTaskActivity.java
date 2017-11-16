package com.example.jayjay.doit;

/**
 * Created by Jayjay on 11/13/2017.
 */
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity{
    EditText etTask;
    EditText etDate;
    EditText etTime;
    ImageButton buttonSave;
    ImageButton buttonCancel;
    DbHelper dbHelper;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = new DbHelper(getBaseContext());
        // last 3 parameters will be overridden in DatabaseHelper's constructor

        etTask = (EditText) findViewById(R.id.et_task);
        etDate = (EditText) findViewById(R.id.et_date);
        etTime = (EditText) findViewById(R.id.et_time);
        buttonSave = (ImageButton) findViewById(R.id.btn_save);
        buttonCancel = (ImageButton) findViewById(R.id.btn_cancel);

        etDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTaskActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = month + "/" + day +"/" + year;
                etDate.setText(date);
            }
        };

        etTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               Calendar cal = Calendar.getInstance();
               int hour = cal.get(Calendar.HOUR);
               int min = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(AddTaskActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mTimeSetListener, hour, min, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                String time = hour + ":" + min;
                etTime.setText(time);
            }
        };

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task();
                task.setContent(etTask.getText().toString());
                task.setDate(etDate.getText().toString());
                task.setTime(etTime.getText().toString());
                dbHelper.insertTask(task);
                setResult(RESULT_OK);
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

}
