package com.example.lenovo.sweprojectothesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button btn_student=(Button)findViewById(R.id.btn_student);

        btn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,MainActivity.class).putExtra("activity","student"));
            }
        });

        Button btn_teacher=(Button)findViewById(R.id.btn_teacher);

        btn_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,MainActivity.class).putExtra("activity","teacher"));
            }
        });

        Button btn_admin=(Button)findViewById(R.id.btn_admin);

        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,MainActivity.class).putExtra("activity","admin"));
            }
        });

        TextView tv_signup_student=findViewById(R.id.tv_signupmessage_student);
        TextView tv_signup_teacher=findViewById(R.id.tv_signupmessage_teacher);
        //TextView tv_signup_admin=findViewById(R.id.tv_signupmessage_admin);

        tv_signup_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,StudentRegisterActivity.class));
            }
        });

        tv_signup_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,TeacherRegisterActivity.class));
            }
        });

        /*tv_signup_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,AdminRegistrationActivity.class));
            }
        });*/
    }
}
