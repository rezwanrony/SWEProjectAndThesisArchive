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

public class CustomRequestedVCList extends BaseAdapter {
    Context context;
    List<RequestedVC>requestedVCList;

    public CustomRequestedVCList(Context context, List<RequestedVC> requestedVCList) {
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
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.custom_requested_vclist,viewGroup,false);

        }

        TextView vname=view.findViewById(R.id.tv_name_requestedvc);
        TextView status=view.findViewById(R.id.tv_status_requestedvc);
        Button btn_enter=view.findViewById(R.id.btn_enter_requestedvc);

        vname.setText(requestedVCList.get(i).getName());
        if (requestedVCList.get(i).getStatus()==0){
            status.setText("Pending");
            status.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            btn_enter.setVisibility(View.GONE);
        }
        else {
            status.setText("Approved");
            status.setTextColor(context.getResources().getColor(R.color.green));
            btn_enter.setVisibility(View.VISIBLE);
        }

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,VirtualclassroomStudentActivity.class).putExtra("vcname",requestedVCList.get(i).getName()).putExtra("vcid",requestedVCList.get(i).getId()));
            }
        });

        return view;
    }
}
