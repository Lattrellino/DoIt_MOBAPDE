package com.example.jayjay.doit;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    RecyclerView rvTasks;
    FloatingActionButton btn_add;
    Button btn_done;
    DbHelper dbHelper;
    TaskCursorAdapter taskAdapter;
    Toolbar toolbar;
    private int menuPosition = 0;
    //Spinner spinner;
    //ArrayAdapter<CharSequence> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        rvTasks = (RecyclerView) findViewById(R.id.lv_tasks);
        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
        btn_done = (Button) findViewById(R.id.btn_isDone);
       // spinner = (Spinner) findViewById(R.id.spinner);
       // spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_list_item_1);

       // spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // spinner.setAdapter(spinnerAdapter);

        dbHelper = new DbHelper(getBaseContext());
        taskAdapter = new TaskCursorAdapter(getBaseContext(), null, menuPosition);

        rvTasks.setAdapter(taskAdapter);
        rvTasks.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AddTaskActivity.class));
            }
        });

//        btn_done.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Heyyyy", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Cursor cursor = null;
        switch (item.getItemId()){
            case R.id.menu_all:
                cursor = taskAdapter.swapCursor(dbHelper.queryAllTasks());
                menuPosition = 0;
                break;
            case R.id.menu_finished:
                cursor = taskAdapter.swapCursor(dbHelper.queryDoneTasks());
                menuPosition = 1;
                break;
            case R.id.menu_thisday:
                cursor = taskAdapter.swapCursor(dbHelper.queryTodayTasks());
                menuPosition = 2;
                break;
            case R.id.menu_thisweek:
                cursor = taskAdapter.swapCursor(dbHelper.queryWeekTasks());
                menuPosition = 3;
                break;
            default:

        }
        if (cursor != null) {
            taskAdapter.notifyItemInserted(cursor.getCount());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = null;//dbHelper.queryAllTasksAsCursor();
        switch (menuPosition){
            case 0:
                cursor = taskAdapter.swapCursor(dbHelper.queryAllTasks());
                break;
            case 1:
                cursor = taskAdapter.swapCursor(dbHelper.queryDoneTasks());
                break;
            case 2:
                cursor = taskAdapter.swapCursor(dbHelper.queryTodayTasks());
                break;
            case 3:
                cursor = taskAdapter.swapCursor(dbHelper.queryWeekTasks());
                break;
        }
        //taskAdapter.swapCursor(cursor);
    }
}
