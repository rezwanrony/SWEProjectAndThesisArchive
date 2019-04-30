package com.example.lenovo.sweprojectothesis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class VirtualclassroomStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualclassroom_student);
        TextView tv_title=findViewById(R.id.toolbartitle);
        tv_title.setText("Virtual classroom");
    }
}
