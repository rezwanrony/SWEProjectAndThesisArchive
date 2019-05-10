package com.example.lenovo.sweprojectothesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VirtualclassroomStudentActivity extends AppCompatActivity {

    TextView vc_name,vc_studentname;
    SQLiteHandler db;
    Button btn_addproject,btn_seeproject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualclassroom_student);
        TextView tv_title=findViewById(R.id.toolbartitle);
        tv_title.setText("Virtual classroom");
        initialize();
        List<Student> studentList=new ArrayList<Student>();
        studentList=db.getStudentDetails();
        vc_studentname.setText(studentList.get(0).getName());
        String vcname=getIntent().getStringExtra("vcname");
        vc_name.setText(String.valueOf(vcname));
        final int vcid=getIntent().getIntExtra("vcid",0);
        btn_addproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VirtualclassroomStudentActivity.this,AddProjectActivity.class).putExtra("addstatus","student").putExtra("classroomid",vcid));
            }
        });

        btn_seeproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VirtualclassroomStudentActivity.this,AllStudentProjectActivity.class).putExtra("projectliststatus","student").putExtra("studentvcid",vcid));
            }
        });
    }

    private void initialize() {
        vc_name=findViewById(R.id.vcname_studentvc);
        vc_studentname=findViewById(R.id.tv_proname_studentvc);
        db=new SQLiteHandler(VirtualclassroomStudentActivity.this);
        btn_addproject=findViewById(R.id.btn_addproject_studentvc);
        btn_seeproject=findViewById(R.id.btn_seeproject_studentvc);

    }
}
