package com.example.lenovo.sweprojectothesis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherRegisterActivity extends AppCompatActivity {

    EditText et_name,et_email,et_phone,et_address,et_password,et_repassword;
    Button btn_signup;
    TextView btn_signin;
    ApiInterface apiInterface;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);
        et_name=findViewById(R.id.et_fullname_teacherregister);
        et_email=findViewById(R.id.et_email_teacherregister);
        et_phone=findViewById(R.id.et_phone_teacherregister);
        et_password=findViewById(R.id.et_password_teacherregister);
        img_back=findViewById(R.id.img_back_teacherregister);
        et_repassword=findViewById(R.id.et_repassword_teacherregister);
        btn_signin=findViewById(R.id.tv_signin_teacherregister);
        btn_signup=findViewById(R.id.btn_signup_teacherregister);
        apiInterface=ApiUtils.getAPIService();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()==false){
                    Toast.makeText(TeacherRegisterActivity.this, "You have to fill up all the required fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (et_password.getText().toString().trim().equals(et_repassword.getText().toString().trim())){

                        signup();
                    }
                    else {
                        Toast.makeText(TeacherRegisterActivity.this, "Password don't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherRegisterActivity.this,MainActivity.class).putExtra("activity","teacher"));
            }
        });
    }

    private void signup(){

        final ProgressDialog dialog=PDialog.showDialog(TeacherRegisterActivity.this);
        Call<TeacherLoginResult> call=apiInterface.getStringTeacherScalar(new TeacherLoginData(et_name.getText().toString(),et_email.getText().toString(),et_password.getText().toString(),et_phone.getText().toString()));

        call.enqueue(new Callback<TeacherLoginResult>() {
            @Override
            public void onResponse(Call<TeacherLoginResult> call, Response<TeacherLoginResult> response) {
                dialog.dismiss();
                int status=response.body().getStatus();
                if (status==1) {
                    String msg = response.body().getMsg();

                    Log.d("VOLLEY", msg);

                    Toast.makeText(TeacherRegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TeacherRegisterActivity.this,VerificationCodeActivity.class).putExtra("email",et_email.getText().toString().trim()).putExtra("verifystatus","teacher"));

                }
                else {
                    Toast.makeText(TeacherRegisterActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TeacherLoginResult> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(TeacherRegisterActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });


    }





    private boolean validate(){
        if(et_name.getText().toString().trim().equals(""))
            return false;
        else if(et_email.getText().toString().trim().equals(""))
            return false;
        else if(et_phone.getText().toString().trim().equals(""))
            return false;
        else if(et_password.getText().toString().trim().equals(""))
            return false;
        else if(et_repassword.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }


}
