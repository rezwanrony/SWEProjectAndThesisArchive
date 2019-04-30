package com.example.lenovo.sweprojectothesis;

import android.app.Activity;
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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class StudentRegisterActivity extends AppCompatActivity {

    EditText et_name,et_email,et_phone,et_address,et_password,et_repassword;
    Button btn_signup;
    TextView btn_signin;
    ApiInterface apiInterface;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        et_name=findViewById(R.id.et_fullname_studentregistration);
        et_email=findViewById(R.id.et_email_studentregistration);
        et_phone=findViewById(R.id.et_phone_studentregistration);
        et_address=findViewById(R.id.et_address_studentregistration);
        et_password=findViewById(R.id.et_password_studentregistration);
        img_back=findViewById(R.id.img_back_studentregister);
        et_repassword=findViewById(R.id.et_repassword_studentregistration);
        btn_signin=findViewById(R.id.tv_signin_studentregistration);
        btn_signup=findViewById(R.id.btn_signup_studentregistration);
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
                    Toast.makeText(StudentRegisterActivity.this, "You have to fill up all the required fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (et_password.getText().toString().trim().equals(et_repassword.getText().toString().trim())){

                            signup();
                    }
                    else {
                        Toast.makeText(StudentRegisterActivity.this, "Password don't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentRegisterActivity.this,MainActivity.class).putExtra("activity","student"));
            }
        });




    }

    private void signup(){

        final ProgressDialog dialog=PDialog.showDialog(StudentRegisterActivity.this);
        Call<LoginResult> call=apiInterface.getStringScalar(new LoginData(et_name.getText().toString(),et_email.getText().toString(),et_password.getText().toString(),et_phone.getText().toString(),et_address.getText().toString()));
        call.enqueue(new Callback<LoginResult>() {

            @Override
            public void onResponse(Call<LoginResult> call, retrofit2.Response<LoginResult> response) {
                dialog.dismiss();
                    int status=response.body().getStatus();
                    if (status==1) {
                        String msg = response.body().getMsg();

                        Log.d("VOLLEY", msg);

                        Toast.makeText(StudentRegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StudentRegisterActivity.this,VerificationCodeActivity.class).putExtra("email",et_email.getText().toString().trim()).putExtra("verifystatus","student"));

                    }
                    else {
                        Toast.makeText(StudentRegisterActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }



            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

                dialog.dismiss();
                Toast.makeText(StudentRegisterActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
                //for getting error in network put here Toast, so get the error on network
            }
        });

        /*String url="http://192.168.0.110/sweprojectandthesis/webservices/studentregister.php";
        final String TAG="Volley Response";

        RequestQueue queue = Volley.newRequestQueue(this);*/



        /*Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("name", et_name.getText().toString());
        postParam.put("email", et_email.getText().toString());
        postParam.put("password", et_password.getText().toString());
        postParam.put("phone", et_phone.getText().toString());
        postParam.put("address", et_address.getText().toString());*/


        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("name", et_name.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonBody.put("email", et_email.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonBody.put("password", et_password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonBody.put("phone", et_phone.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonBody.put("address", et_address.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String requestBody = jsonBody.toString();
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

        };

        queue.add(stringRequest);
    }*/ /*catch (JSONException e) {
        e.printStackTrace();
    }*/

        /*final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            int status=response.getInt("status");
                            if (status==1) {
                                Toast.makeText(StudentRegisterActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(StudentRegisterActivity.this,VerificationCodeActivity.class));
                            }
                            else {
                                Toast.makeText(StudentRegisterActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(StudentRegisterActivity.this, "Error!!!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Check your internet connection" + error.getMessage());
            }
        }) {

            *//**
         * Passing some request headers
         * *//*
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }





        };

        jsonObjReq.setTag(TAG);
        // Adding request to request queue
        queue.add(jsonObjReq);*/
        //}

    }





    private boolean validate(){
        if(et_name.getText().toString().trim().equals(""))
            return false;
        else if(et_email.getText().toString().trim().equals(""))
            return false;
        else if(et_phone.getText().toString().trim().equals(""))
            return false;
        else if(et_address.getText().toString().trim().equals(""))
            return false;
        else if(et_password.getText().toString().trim().equals(""))
            return false;
        else if(et_repassword.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }



}
