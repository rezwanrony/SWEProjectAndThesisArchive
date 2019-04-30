package com.example.lenovo.sweprojectothesis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomCourseListAdapter extends BaseAdapter {
    Context context;
    List<Course> courseList;

    public CustomCourseListAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.custom_course_list,viewGroup,false);

        }
        TextView tv_coursecode=(TextView)view.findViewById(R.id.tv_coursecode_courses);
        tv_coursecode.setText(courseList.get(i).getCoursecode());
        TextView tv_coursename=(TextView)view.findViewById(R.id.tv_coursename_courses);
        tv_coursename.setText(courseList.get(i).getCoursename());

       /* Button btn_login=(Button)view.findViewById(R.id.btn_submitproject);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(context,TeachersActivity.class);
                context.startActivity(i);
            }
        });*/
        return view;
    }
}
