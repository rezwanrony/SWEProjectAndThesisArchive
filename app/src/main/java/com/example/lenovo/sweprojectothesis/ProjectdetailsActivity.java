package com.example.lenovo.sweprojectothesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ProjectdetailsActivity extends AppCompatActivity {

    TextView tv_name,tv_projectdetails,tv_language,tv_semester,tv_developedby,tv_githublink,tv_projectreprt;
    ArrayList<AllStudentProject>studentProjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectdetails);
        CoursesActivity.getToolbar(ProjectdetailsActivity.this,"Project details");
        tv_name=findViewById(R.id.et_title_projectdetails);
        tv_projectdetails=findViewById(R.id.et_projectdetails_projectdetails);
        tv_language=findViewById(R.id.et_programminglanguage_projectdetails);
        tv_semester=findViewById(R.id.spinner_semester_projectdetails);
        tv_developedby=findViewById(R.id.et_developedby_projectdetails);
        tv_githublink=findViewById(R.id.et_githublink_projectdetails);
        tv_projectreprt=findViewById(R.id.tv_projectreportname_projectdetails);
        final String report=String.valueOf(getIntent().getStringExtra("report"));
        tv_name.setText(String.valueOf(getIntent().getStringExtra("title")));
        tv_projectdetails.setText(String.valueOf(getIntent().getStringExtra("desc")));
        tv_language.setText(String.valueOf(getIntent().getStringExtra("language")));
        tv_semester.setText(String.valueOf(getIntent().getStringExtra("semester"))+" "+String.valueOf(getIntent().getStringExtra("year")));
        tv_developedby.setText(String.valueOf(getIntent().getStringExtra("owner")));
        tv_githublink.setText(String.valueOf(getIntent().getStringExtra("github")));
        tv_projectreprt.setText(String.valueOf(getIntent().getStringExtra("report")));

        if (report.equals("")){
            tv_projectreprt.setVisibility(View.GONE);
        }

        tv_projectreprt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectdetailsActivity.this,RemotePDFActivity.class).putExtra("url",report));
            }
        });
    }
}
