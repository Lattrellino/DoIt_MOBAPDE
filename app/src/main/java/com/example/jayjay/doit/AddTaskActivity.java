package com.example.jayjay.doit;

/**
 * Created by Jayjay on 04/12/2017.
 */
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity{
    EditText etTask;
    EditText etDate;
    EditText etTime;
    ImageButton buttonSave;
    ImageButton buttonCancel;
    TextView tvPlace;
    Button buttonMaps;
    DbHelper dbHelper;
    public final static int MAP_RESULT=1;
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
        tvPlace = (TextView) findViewById(R.id.tv_place);
        buttonSave = (ImageButton) findViewById(R.id.btn_savetask);
        buttonCancel = (ImageButton) findViewById(R.id.btn_cancel);
        buttonMaps = (Button) findViewById(R.id.btn_map);

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

        buttonMaps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            /*
                int PLACE_PICKER_REQUEST = 1;
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(AddTaskActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                    Log.d("TAG", "Error: services Repairable Exception");
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                    Log.d("TAG", "Error: services Not Available Exception");
                }*/
                startActivityForResult(new Intent(getBaseContext(), MapsActivity.class),MAP_RESULT);

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
                task.setLocation(tvPlace.getText().toString());
                task.setDone("false");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MAP_RESULT){
            if(resultCode == Activity.RESULT_OK) {
                String locationName = data.getStringExtra(MapsActivity.LOCATION_NAME);
                tvPlace.setText(locationName);//
            }
        }
    }

}
