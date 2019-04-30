package com.example.lenovo.sweprojectothesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateVirtualClassroomActivity extends AppCompatActivity {

    Spinner spinner_semester;
    Spinner spinner_course;
    Button btn_create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_virtual_classroom);
        spinner_semester=(Spinner)findViewById(R.id.spinner_semester_vc);
        spinner_course=(Spinner)findViewById(R.id.spinner_course_vc);
        TextView tv_title=findViewById(R.id.toolbartitle);
        tv_title.setText("Create Virtual classroom");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CreateVirtualClassroomActivity.this,
                R.array.semester_array, R.layout.spinner_item_black);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_semester.setAdapter(adapter);

        ArrayAdapter<CharSequence> adaptertwo = ArrayAdapter.createFromResource(CreateVirtualClassroomActivity.this,
                R.array.course_array, R.layout.spinner_item_black);
// Specify the layout to use when the list of choices appears
        adaptertwo.setDropDownViewResource(android.R.layout.simple_spinner_item);
// Apply the adapter to the spinner
        spinner_course.setAdapter(adaptertwo);

        Button btn_login=(Button)findViewById(R.id.btn_create_vc);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateVirtualClassroomActivity.this,AllStudentProjectActivity.class));
            }
        });

    }


}
