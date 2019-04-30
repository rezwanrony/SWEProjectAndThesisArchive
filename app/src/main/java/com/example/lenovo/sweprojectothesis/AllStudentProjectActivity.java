package com.example.lenovo.sweprojectothesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AllStudentProjectActivity extends AppCompatActivity {
    ListView lv_students;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student_project);
        lv_students=(ListView)findViewById(R.id.lv_listofprojectteacher);
        List<AllStudentProject> studentProjects=new ArrayList<AllStudentProject>();
        studentProjects.add(new AllStudentProject("ProjectOThesis","Rezwan Rony","Java,Android","SWE 331","Spring 2019"));
        studentProjects.add(new AllStudentProject("Sound Filterer","Jaber Ahmed","Java,Android","SWE 333","Fall 2017"));
        studentProjects.add(new AllStudentProject("Bidding Software","Sanour Tomal","PHP","SWE 331","Summer 2019"));
        studentProjects.add(new AllStudentProject("Docto Rajjo","Atikur Khan","Python","SWE 431","Spring 2018"));
        studentProjects.add(new AllStudentProject("Question Maker","Rakib Hasan","C++","SWE 431","Spring 2019"));

        CustomAllStudentsProjectListAdapter customAllStudentsProjectListAdapter=new CustomAllStudentsProjectListAdapter(AllStudentProjectActivity.this,studentProjects);

        lv_students.setAdapter(customAllStudentsProjectListAdapter);

        lv_students.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(AllStudentProjectActivity.this,StudentDashboardActivity.class));
            }
        });

    }
}
