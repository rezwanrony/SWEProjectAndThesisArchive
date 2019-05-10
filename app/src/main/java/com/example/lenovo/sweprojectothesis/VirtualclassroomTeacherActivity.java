package com.example.lenovo.sweprojectothesis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class VirtualclassroomTeacherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualclassroom_teacher);
        TextView tv_title=findViewById(R.id.toolbartitle);
        tv_title.setText("Virtual classroom");
        Button btn_add=findViewById(R.id.btn_callassignments);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAddCoursesPopup dialog = new CustomAddCoursesPopup(VirtualclassroomTeacherActivity.this);
                WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
                lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT; // this is where the magic happens
                lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.show();
                dialog.getWindow().setAttributes(lWindowParams);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });
    }
}
