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

public class AdminRegistrationActivity extends AppCompatActivity {

    EditText et_name,et_email,et_password,et_repassword;
    Button btn_signup;
    TextView btn_signin;
    ApiInterface apiInterface;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);
        et_name=findViewById(R.id.et_fullname_admin);
        et_email=findViewById(R.id.et_email_admin);
        et_password=findViewById(R.id.et_password_admin);
        img_back=findViewById(R.id.img_back_admin);
        et_repassword=findViewById(R.id.et_repassword_admin);
        btn_signin=findViewById(R.id.tv_signin_admin);
        btn_signup=findViewById(R.id.btn_signup_admin);
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
                    Toast.makeText(AdminRegistrationActivity.this, "You have to fill up all the required fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (et_password.getText().toString().trim().equals(et_repassword.getText().toString().trim())){

                        signup();
                    }
                    else {
                        Toast.makeText(AdminRegistrationActivity.this, "Password don't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminRegistrationActivity.this,MainActivity.class).putExtra("activity","admin"));
            }
        });

    }

    private boolean validate(){
        if(et_name.getText().toString().trim().equals(""))
            return false;
        else if(et_email.getText().toString().trim().equals(""))
            return false;
        else if(et_password.getText().toString().trim().equals(""))
            return false;
        else if(et_repassword.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

    private void signup(){

        final ProgressDialog dialog=PDialog.showDialog(AdminRegistrationActivity.this);
        Call<LoginResult> call=apiInterface.getStringAdminScalar(new AdminLoginData(et_name.getText().toString(),et_email.getText().toString(),et_password.getText().toString()));
        call.enqueue(new Callback<LoginResult>() {

            @Override
            public void onResponse(Call<LoginResult> call, retrofit2.Response<LoginResult> response) {
                dialog.dismiss();
                int status=response.body().getStatus();
                if (status==1) {
                    String msg = response.body().getMsg();

                    Log.d("VOLLEY", msg);

                    Toast.makeText(AdminRegistrationActivity.this, msg, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminRegistrationActivity.this,VerificationCodeActivity.class).putExtra("email",et_email.getText().toString().trim()).putExtra("verifystatus","student"));

                }
                else {
                    Toast.makeText(AdminRegistrationActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

                dialog.dismiss();
                Toast.makeText(AdminRegistrationActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
                //for getting error in network put here Toast, so get the error on network
            }
        });



    }
}
