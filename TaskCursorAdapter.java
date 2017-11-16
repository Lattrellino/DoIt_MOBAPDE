package com.example.jayjay.doit;

/**
 * Created by Jayjay on 11/13/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TaskCursorAdapter extends CursorRecyclerViewAdapter<TaskCursorAdapter.TaskViewHolder>{
    public TaskCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder viewHolder, Cursor cursor) {
        String content = cursor.getString(cursor.getColumnIndex(Task.COLUMN_CONTENT));
        viewHolder.tvContent.setText(content);

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
        return new TaskViewHolder(v);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView tvContent;
        View container;

        public TaskViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            container = itemView.findViewById(R.id.container);
        }
    }
}
