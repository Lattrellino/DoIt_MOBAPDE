package com.example.jayjay.doit;

/**
 * Created by Jayjay on 11/13/2017.
 */
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    private ArrayList<Task> taskArrayList;

    public TaskAdapter(Context context, int resource, ArrayList<Task> taskArrayList) {
        super(context, resource, taskArrayList);
        this.taskArrayList = taskArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        if(convertView == null){
            convertView = ((LayoutInflater)getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.task_item, parent, false);

            TextView tvNote = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(tvNote);
        }

        ((TextView)convertView.getTag()).setText(
                getItem(position).getContent());

        return convertView;
    }

}
