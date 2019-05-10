package com.example.lenovo.sweprojectothesis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CustomClassroomListAdapter extends BaseAdapter {
    Context context;
    List<CreatedClassroom> requestedVCList;
    SQLiteHandler db;

    public CustomClassroomListAdapter(Context context, List<CreatedClassroom> requestedVCList) {
        this.context = context;
        this.requestedVCList = requestedVCList;
    }

    @Override
    public int getCount() {
        return requestedVCList.size();
    }

    @Override
    public Object getItem(int i) {
        return requestedVCList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.custom_created_classroomlist, viewGroup, false);

        }

        db = new SQLiteHandler(context);

        TextView classroomname = view.findViewById(R.id.tv_classroomname_createdclassroom);
        classroomname.setText(requestedVCList.get(i).getName());

        return view;
    }
}
