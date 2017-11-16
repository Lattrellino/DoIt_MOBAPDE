package com.example.jayjay.doit;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvTasks;
    ImageButton btn_add;
    DbHelper dbHelper;
    TaskCursorAdapter taskAdapter;
    Spinner spinner;
    ArrayAdapter<CharSequence> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTasks = (RecyclerView) findViewById(R.id.lv_tasks);
        btn_add = (ImageButton) findViewById(R.id.btn_add);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_list_item_1);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        dbHelper = new DbHelper(getBaseContext());
        taskAdapter = new TaskCursorAdapter(getBaseContext(), null);

        rvTasks.setAdapter(taskAdapter);
        rvTasks.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AddTaskActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = dbHelper.queryAllTasksAsCursor();
        taskAdapter.swapCursor(cursor);
    }
}
