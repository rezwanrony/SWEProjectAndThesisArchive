package com.example.lenovo.sweprojectothesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class TeachersActivity extends AppCompatActivity {

    Spinner spinner_semester;
    Spinner spinner_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);
        spinner_semester=(Spinner)findViewById(R.id.spinner_semester_teacher);
        spinner_course=(Spinner)findViewById(R.id.spinner_course_teacher);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.semester_array, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner
        spinner_semester.setAdapter(adapter);

        ArrayAdapter<CharSequence> adaptertwo = ArrayAdapter.createFromResource(this,
                R.array.course_array, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adaptertwo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner
        spinner_course.setAdapter(adaptertwo);

        Button btn_login=(Button)findViewById(R.id.btn_save_teacher);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeachersActivity.this,AllStudentProjectActivity.class));
            }
        });
    }
}
