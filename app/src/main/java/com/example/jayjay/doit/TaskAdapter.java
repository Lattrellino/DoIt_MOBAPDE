package com.example.jayjay.doit;

/**
 * Created by Jayjay on 04/12/2017.
 */
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task>{
    private ArrayList<Task> taskArrayList;

    public TaskAdapter(Context context, int resource, ArrayList<Task> taskArrayList) {
        super(context, resource, taskArrayList);
        this.taskArrayList = taskArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        if(convertView == null){
            convertView = ((LayoutInflater)getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.task_item, parent, false);

            TextView tvNote = (TextView) convertView.findViewById(R.id.tv_content);
            TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            TextView tvTime = (TextView) convertView.findViewById(R.id.tv_time);

            Button btnDone = (Button) convertView.findViewById(R.id.btn_isDone);

            btnDone.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                }
            });

            convertView.setTag(tvNote);
        }

        /*((TextView)convertView.getTag()).setText(
                getItem(position).getContent());
                */

        ((TextView)convertView.getTag()).setText(getItem(position).getDate());
        ((TextView)convertView.getTag()).setText(getItem(position).getTime());
        return convertView;
    }

}
