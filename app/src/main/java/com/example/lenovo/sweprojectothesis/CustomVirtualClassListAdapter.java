package com.example.lenovo.sweprojectothesis;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CustomVirtualClassListAdapter extends BaseAdapter {
    Context context;
    List<VirtualClassroom> studentProjectList;

    public CustomVirtualClassListAdapter(Context context, List<VirtualClassroom> studentProjectList) {
        this.context = context;
        this.studentProjectList = studentProjectList;
    }

    @Override
    public int getCount() {
        return studentProjectList.size();
    }

    @Override
    public Object getItem(int i) {
        return studentProjectList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.custom_vc_list,viewGroup,false);

        }

        TextView tv_teacherinit=(TextView)view.findViewById(R.id.tv_name_vc_a);
        tv_teacherinit.setText(studentProjectList.get(i).getName());

        Button btn_login=(Button)view.findViewById(R.id.btn_request_vc);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(context,TeachersActivity.class);
                context.startActivity(i);
            }
        });
        return view;
    }
}
