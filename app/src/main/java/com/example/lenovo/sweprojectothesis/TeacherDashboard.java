package com.example.lenovo.sweprojectothesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TeacherDashboard extends AppCompatActivity {

    TextView tv_name,tv_email,tv_phone;
    SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        tv_name=findViewById(R.id.tv_proname_teacherdashboard);
        tv_email=findViewById(R.id.tv_email_teacherdashboard);
        tv_phone=findViewById(R.id.tv_phone_teacherdashboard);
        db=new SQLiteHandler(TeacherDashboard.this);
        ImageView img_settings=findViewById(R.id.img_settings_teacher);

        List<Teacher>teacherList=new ArrayList<Teacher>();
        teacherList=db.getTeacherDetails();

        tv_name.setText(teacherList.get(0).getName().toString());
        tv_email.setText(teacherList.get(0).getEmail().toString());
        tv_phone.setText(teacherList.get(0).getPhone().toString());

        img_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboard.this,VirtualclassroomTeacherActivity.class));
            }
        });

        CardView card_seecourse=findViewById(R.id.card_seecourselist);
        card_seecourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboard.this,CreateVirtualClassroomActivity.class));
            }
        });
        CardView card_seecourse2=findViewById(R.id.card_virtualclassroomteacher);
        card_seecourse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboard.this,SearchVirtualClassroomActivity.class));
            }
        });
    }
}
