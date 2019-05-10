package com.example.lenovo.sweprojectothesis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAllStudentsProjectListAdapter extends BaseAdapter {

    Context context;
    List<AllStudentProject> studentProjectList;

    public CustomAllStudentsProjectListAdapter(Context context, List<AllStudentProject> studentProjectList) {
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
            view= LayoutInflater.from(context).inflate(R.layout.custom_all_student_project,viewGroup,false);

        }

        TextView tv_projectname=(TextView)view.findViewById(R.id.tv_projectnameallproject);
        tv_projectname.setText(studentProjectList.get(i).getProjectname());
        TextView tv_projectowner=(TextView)view.findViewById(R.id.tv_projectownernameallproject);
        tv_projectowner.setText(studentProjectList.get(i).getProjectowner());
        TextView tv_language=(TextView)view.findViewById(R.id.tv_languageallproject);
        tv_language.setText(studentProjectList.get(i).getLanguage());
        TextView tv_semester=(TextView)view.findViewById(R.id.tv_semesterallproject);
        tv_semester.setText(studentProjectList.get(i).getSemester()+" "+studentProjectList.get(i).getYear());
        return view;
    }
}
