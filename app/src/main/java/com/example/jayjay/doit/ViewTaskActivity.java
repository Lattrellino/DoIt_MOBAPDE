package com.example.jayjay.doit;

/**
 * Created by Jayjay on 04/12/2017.
 */
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static com.example.jayjay.doit.AddTaskActivity.MAP_RESULT;

public class ViewTaskActivity extends AppCompatActivity{
    private EditText etTask, etDate, etTime;
    private ImageButton buttonSave, buttonDelete, buttonCancel;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private DbHelper dbHelper;
    private Task currentTask;
    TextView tvPlace;
    Button buttonMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        etTask = (EditText) findViewById(R.id.et_task);
        etDate = (EditText) findViewById(R.id.et_date);
        etTime = (EditText) findViewById(R.id.et_time);
        tvPlace = (TextView) findViewById(R.id.tv_place);
        buttonSave = (ImageButton) findViewById(R.id.btn_savechanges);
        buttonCancel = (ImageButton) findViewById(R.id.btn_cancel);
        buttonDelete = (ImageButton) findViewById(R.id.btn_delete);
        buttonMap = (Button) findViewById(R.id.btn_map);

        dbHelper = new DbHelper(getBaseContext());

        currentTask = dbHelper.queryTask(getIntent().getExtras().getInt(Task.COLUMN_ID));

        etDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ViewTaskActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
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

                TimePickerDialog dialog = new TimePickerDialog(ViewTaskActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mTimeSetListener, hour, min, true);
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

        buttonMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getBaseContext(), MapsActivity.class), MAP_RESULT);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = etTask.getText().toString();
                String date = etDate.getText().toString();
                String time = etTime.getText().toString();
                String place = tvPlace.getText().toString();
                dbHelper.updateTask(new Task(currentTask.getId(), task, date, time, place));
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextViewDisplay();
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteTask(currentTask.getId());
                finish();
            }
        });

        updateTextViewDisplay();
    }

    public void updateTextViewDisplay(){
        etTask.setText(currentTask.getContent());
        etDate.setText(currentTask.getDate());
        etTime.setText(currentTask.getTime());
        tvPlace.setText(currentTask.getLocation());
    }
}
