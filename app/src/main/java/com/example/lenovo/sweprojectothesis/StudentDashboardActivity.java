package com.example.lenovo.sweprojectothesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentDashboardActivity extends AppCompatActivity {

    TextView tv_name,tv_email,tv_phone,tv_address;
    SQLiteHandler db;
    CardView card_seecourse,card_virtualclassroom,card_previousprojects,card_projectstats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        ImageView img_settings=findViewById(R.id.img_settings_userprofileupdate);
        tv_name=findViewById(R.id.tv_proname_studentdashboard);
        tv_email=findViewById(R.id.tv_email_studentdashboard);
        tv_phone=findViewById(R.id.tv_phone_studentdashboard);
        tv_address=findViewById(R.id.tv_address_studentdashboard);
        db=new SQLiteHandler(StudentDashboardActivity.this);
        card_seecourse=findViewById(R.id.card_seecourselist);
        card_virtualclassroom=findViewById(R.id.card_virtualclassroom);
        card_previousprojects=findViewById(R.id.card_seeprojectlist);
        card_projectstats=findViewById(R.id.card_projectstats);

        List<Student>studentList=new ArrayList<Student>();
        studentList=db.getStudentDetails();

        tv_name.setText(studentList.get(0).getName());
        tv_email.setText(studentList.get(0).getEmail());
        tv_phone.setText(studentList.get(0).getPhone());
        tv_address.setText(studentList.get(0).getAddress());


        img_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDashboardActivity.this,VirtualclassroomStudentActivity.class));
            }
        });

        card_seecourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDashboardActivity.this,CoursesActivity.class));
            }
        });
        card_virtualclassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDashboardActivity.this,SearchVirtualClassroomActivity.class));
            }
        });
    }
}
