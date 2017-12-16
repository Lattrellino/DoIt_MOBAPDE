package com.example.jayjay.doit;

/**
 * Created by Jayjay on 04/12/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskCursorAdapter extends CursorRecyclerViewAdapter<TaskCursorAdapter.TaskViewHolder>{
    private int menuPosition;
    public TaskCursorAdapter(Context context, Cursor cursor, int menuPosition) {
        super(context, cursor);
        this.menuPosition = menuPosition;
    }
    private DbHelper dbHelper;

    @Override
    public void onBindViewHolder(final TaskViewHolder viewHolder, final Cursor cursor) {
        String content = cursor.getString(cursor.getColumnIndex(Task.COLUMN_CONTENT));
        String date = cursor.getString(cursor.getColumnIndex(Task.COLUMN_DATE));
        String time  = cursor.getString(cursor.getColumnIndex(Task.COLUMN_TIME));
        String location = cursor.getString(cursor.getColumnIndex(Task.COLUMN_LOCATION));
        int id = cursor.getInt(cursor.getColumnIndex(Task.COLUMN_ID));

        final Task task = new Task(id, content, date, time, location);

        viewHolder.tvContent.setText(content);
        viewHolder.tvDate.setText(date);
        viewHolder.tvTime.setText(time);

        viewHolder.setDoneListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setDone("true");
                dbHelper.updateTask(task);

                switch (menuPosition){
                    case 0:
                        swapCursor(dbHelper.queryAllTasks());
                        break;
                    case 1:
                        swapCursor(dbHelper.queryDoneTasks());
                        break;
                    case 2:
                        swapCursor(dbHelper.queryTodayTasks());
                        break;
                    case 3:
                        swapCursor(dbHelper.queryWeekTasks());
                        break;
                }
            }
        });

        int taskId = cursor.getInt(cursor.getColumnIndex(Task.COLUMN_ID));
        viewHolder.container.setTag(taskId);

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewNoteIntent = new Intent(v.getContext(), ViewTaskActivity.class);
                viewNoteIntent.putExtra(Task.COLUMN_ID, Integer.parseInt(v.getTag().toString()));
                v.getContext().startActivity(viewNoteIntent);
            }
        });
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        dbHelper = new DbHelper(parent.getContext());
        //currTask = dbh.queryTask(getCursor().getExtras().getInt(Task.COLUMN_ID));
        return new TaskViewHolder(v);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView tvContent;
        TextView tvDate, tvTime;
        Button btnDone;
        View container;

        public TaskViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            btnDone = (Button) itemView.findViewById(R.id.btn_isDone);
            container = itemView.findViewById(R.id.container);
        }

        public void setDoneListener(View.OnClickListener onClickListener) {
            btnDone.setOnClickListener(onClickListener);
        }
    }
}
